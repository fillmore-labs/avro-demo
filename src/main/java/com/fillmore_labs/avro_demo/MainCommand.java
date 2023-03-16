package com.fillmore_labs.avro_demo;

import com.fillmore_labs.avro_demo.dynamic.DynamicSchemaHelper;
import com.fillmore_labs.avro_demo.json.JsonSchemaHelper;
import com.fillmore_labs.avro_demo.reflect.ReflectSchemaHelper;
import com.fillmore_labs.avro_demo.specific.SpecificSchemaHelper;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSortedMap;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import org.apache.avro.Schema;
import org.apache.avro.SchemaNormalization;
import org.apache.avro.generic.GenericContainer;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericDatumWriter;
import org.apache.avro.message.SchemaStore;
import org.apache.avro.message.SchemaStore.Cache;
import org.apache.avro.reflect.ReflectData;
import org.apache.avro.reflect.ReflectDatumWriter;
import org.apache.avro.specific.SpecificData;
import org.apache.avro.specific.SpecificDatumWriter;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "avro_demo", mixinStandardHelpOptions = true)
public final class MainCommand {
  @SuppressWarnings("NullAway.Init")
  @Option(
      names = {"-m", "--model"},
      defaultValue = "DYNAMIC",
      description = "Valid values: ${COMPLETION-CANDIDATES}")
  public DataModel selectedModel;

  private MainCommand() {}

  public static Object command() {
    return new MainCommand();
  }

  private static GenericData getModel(DataModel dataModel) {
    return switch (dataModel) {
      case DYNAMIC, JSON -> GenericData.get();
      case REFLECT -> ReflectData.get();
      case SPECIFIC -> SpecificData.get();
    };
  }

  private static <D> GenericDatumWriter<D> getDatumWriter(DataModel dataModel) {
    return switch (dataModel) {
      case DYNAMIC, JSON -> new GenericDatumWriter<>();
      case REFLECT -> new ReflectDatumWriter<>();
      case SPECIFIC -> new SpecificDatumWriter<>();
    };
  }

  private static ImmutableMap<String, ? extends GenericContainer> createSampleMap(
      DataModel dataModel) {
    return switch (dataModel) {
      case DYNAMIC -> DynamicSchemaHelper.createSampleMap();
      case JSON -> JsonSchemaHelper.createSampleMap();
      case REFLECT -> ReflectSchemaHelper.createSampleMap();
      case SPECIFIC -> SpecificSchemaHelper.createSampleMap();
    };
  }

  @SuppressWarnings("SystemOut")
  private static PrintStream getOutputStream() {
    return System.out;
  }

  private static ImmutableMap<String, Schema> createSchemaMap(
      Map<String, ? extends GenericContainer> sampleMap) {
    var builder = ImmutableSortedMap.<String, Schema>naturalOrder();
    for (var entry : sampleMap.entrySet()) {
      builder.put(entry.getKey(), entry.getValue().getSchema());
    }
    return builder.build();
  }

  private static SchemaStore createSchemaStore(Iterable<Schema> schemata) {
    var resolver = new Cache();
    schemata.forEach(resolver::addSchema);
    return resolver;
  }

  @Command(name = "schema")
  public void schema() {
    var out = getOutputStream();
    var sampleMap = createSampleMap(selectedModel);

    for (var entry : sampleMap.entrySet()) {
      var sample = entry.getValue();
      var schema = sample.getSchema();
      var fingerprint = SchemaNormalization.parsingFingerprint64(schema);

      out.printf(
          "%s: %s (fingerprint %s)%n",
          entry.getKey(), schema, String.format("0x%016X", fingerprint));
    }
  }

  @Command(name = "sample")
  public void sample() {
    var out = getOutputStream();
    var sampleMap = createSampleMap(selectedModel);

    for (var entry : sampleMap.entrySet()) {
      var sample = entry.getValue();
      out.printf("%s sample: %s%n", entry.getKey(), sample);
    }
  }

  @Command(name = "encode")
  public void encode() throws IOException {
    var out = getOutputStream();
    var sampleMap = createSampleMap(selectedModel);
    var model = getModel(selectedModel);
    var encoded = EncodingHelper.encodeSamples(model, sampleMap);

    EncodingHelper.print(out, encoded);
  }

  @Command(name = "encode_json")
  public void json() throws IOException {
    var out = getOutputStream();
    var sampleMap = createSampleMap(selectedModel);
    var writer = MainCommand.<GenericContainer>getDatumWriter(selectedModel);
    var encoded = EncodingHelper.encodeSamplesJson(writer, sampleMap);

    for (var entry : encoded.entrySet()) {
      var resource = entry.getKey();
      var buffer = entry.getValue();
      var bytes = new byte[buffer.remaining()];
      buffer.get(bytes);
      buffer.position(buffer.position() - bytes.length);
      out.printf("%s encoded: %s%n", resource, new String(bytes, StandardCharsets.UTF_8));
    }
  }

  @Command(name = "decode")
  public void decode() throws IOException {
    var out = getOutputStream();
    var sampleMap = createSampleMap(selectedModel);
    var model = getModel(selectedModel);
    var encoded = EncodingHelper.encodeSamples(model, sampleMap);

    var schemaMap = createSchemaMap(sampleMap);
    var resolver = createSchemaStore(schemaMap.values());

    EncodingHelper.decode(out, model, encoded, resolver);
  }

  @Command(name = "compatible")
  public void compatible() throws IOException {
    var out = getOutputStream();

    var writerMap = createSampleMap(selectedModel);
    var model = getModel(selectedModel);
    var encoded = EncodingHelper.encodeSamples(model, writerMap);

    var builder = ImmutableSortedMap.<String, GenericContainer>naturalOrder();
    for (var m : DataModel.values()) {
      var readerMap = createSampleMap(m);
      builder.putAll(readerMap);
    }
    var readerMap = builder.build();

    var writerSchemaMap = createSchemaMap(writerMap);
    var resolver = createSchemaStore(writerSchemaMap.values());

    var readerSchemaMap = createSchemaMap(readerMap);
    var graph = CompatibilityHelper.calculateCompatibility(writerSchemaMap, readerSchemaMap);

    CompatibilityHelper.logCompatible(out, graph, readerSchemaMap, encoded, resolver);
  }

  @Command(name = "transitive")
  public void transitive() {
    var out = getOutputStream();
    var sampleMap = createSampleMap(selectedModel);
    var schemaMap = createSchemaMap(sampleMap);

    var graph = CompatibilityHelper.calculateCompatibility(schemaMap, schemaMap);
    CompatibilityHelper.logNonTransitive(out, graph);
  }

  public enum DataModel {
    DYNAMIC,
    JSON,
    REFLECT,
    SPECIFIC
  }
}

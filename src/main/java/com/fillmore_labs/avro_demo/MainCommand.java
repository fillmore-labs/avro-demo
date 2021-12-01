package com.fillmore_labs.avro_demo;

import com.fillmore_labs.avro_demo.dynamic.DynamicSchemaHelper;
import com.fillmore_labs.avro_demo.json.JsonSchemaHelper;
import com.fillmore_labs.avro_demo.reflect.ReflectSchemaHelper;
import com.fillmore_labs.avro_demo.specific.SpecificSchemaHelper;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSortedMap;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Map;
import org.apache.avro.Schema;
import org.apache.avro.SchemaNormalization;
import org.apache.avro.generic.GenericContainer;
import org.apache.avro.generic.GenericData;
import org.apache.avro.message.SchemaStore;
import org.apache.avro.message.SchemaStore.Cache;
import org.apache.avro.reflect.ReflectData;
import org.apache.avro.specific.SpecificData;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "avro_demo", mixinStandardHelpOptions = true)
public final class MainCommand {

  @SuppressWarnings("NullAway.Init")
  @Option(
      names = {"-m", "--model"},
      defaultValue = "DYNAMIC",
      description = "Valid values: ${COMPLETION-CANDIDATES}")
  public DataModel dataModel;

  private MainCommand() {}

  public static Object command() {
    return new MainCommand();
  }

  @Command(name = "schema")
  public void schema() {
    var out = getOutputStream();
    var sampleMap = createSampleMap();

    for (var entry : sampleMap.entrySet()) {
      var sample = entry.getValue();
      var schema = sample.getSchema();
      var fingerprint = SchemaNormalization.parsingFingerprint64(schema);

      out.printf(
          "%s: %s (fingerprint %s)\n",
          entry.getKey(), schema, String.format("0x%016X", fingerprint));
    }
  }

  @Command(name = "sample")
  public void sample() {
    var out = getOutputStream();
    var sampleMap = createSampleMap();

    for (var entry : sampleMap.entrySet()) {
      var sample = entry.getValue();
      out.printf("%s sample: %s\n", entry.getKey(), sample);
    }
  }

  @Command(name = "encode")
  public void encode() throws IOException {
    var out = getOutputStream();
    var sampleMap = createSampleMap();
    var model = getModel();
    var encoded = EncodingHelper.encodeSamples(model, sampleMap);

    EncodingHelper.print(out, encoded);
  }

  @Command(name = "decode")
  public void decode() throws IOException {
    var out = getOutputStream();
    var sampleMap = createSampleMap();
    var schemaMap = createSchemaMap(sampleMap);
    var resolver = createSchemaStore(schemaMap.values());
    var model = getModel();
    var encoded = EncodingHelper.encodeSamples(model, sampleMap);

    EncodingHelper.decode(out, model, encoded, resolver);
  }

  @Command(name = "compatible")
  public void compatible() throws IOException {
    var out = getOutputStream();
    var sampleMap = createSampleMap();
    var schemaMap = createSchemaMap(sampleMap);
    var resolver = createSchemaStore(schemaMap.values());
    var graph = CompatibilityHelper.calculateCompatibility(schemaMap);

    var model = getModel();
    var encoded = EncodingHelper.encodeSamples(model, sampleMap);
    CompatibilityHelper.logCompatible(out, graph, schemaMap, encoded, resolver);
  }

  @Command(name = "transitive")
  public void transitive() {
    var out = getOutputStream();
    var sampleMap = createSampleMap();
    var schemaMap = createSchemaMap(sampleMap);

    var graph = CompatibilityHelper.calculateCompatibility(schemaMap);
    CompatibilityHelper.logNonTransitive(out, graph);
  }

  private GenericData getModel() {
    switch (dataModel) {
      case DYNAMIC:
        return GenericData.get();

      case JSON:
        return GenericData.get();

      case REFLECT:
        return ReflectData.get();

      case SPECIFIC:
        return SpecificData.get();
    }

    throw new IllegalArgumentException("Unknown model: " + dataModel);
  }

  private ImmutableMap<String, ? extends GenericContainer> createSampleMap() {
    switch (dataModel) {
      case DYNAMIC:
        return DynamicSchemaHelper.createSampleMap();

      case JSON:
        return JsonSchemaHelper.createSampleMap();

      case REFLECT:
        return ReflectSchemaHelper.createSampleMap();

      case SPECIFIC:
        return SpecificSchemaHelper.createSampleMap();
    }

    throw new IllegalArgumentException("Unknown model: " + dataModel);
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

  public enum DataModel {
    DYNAMIC,
    JSON,
    REFLECT,
    SPECIFIC;
  }
}

package com.fillmore_labs.avro_demo.confluent;

import com.fillmore_labs.avro_demo.dynamic.DynamicSchemaHelper;
import com.fillmore_labs.avro_demo.json.JsonSchemaHelper;
import com.fillmore_labs.avro_demo.reflect.ReflectSchemaHelper;
import com.fillmore_labs.avro_demo.specific.SpecificSchemaHelper;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSortedMap;
import io.confluent.kafka.serializers.AbstractKafkaSchemaSerDeConfig;
import io.confluent.kafka.serializers.KafkaAvroDeserializerConfig;
import java.io.PrintStream;
import java.util.Map;
import java.util.Random;
import org.apache.avro.Schema;
import org.apache.avro.generic.GenericContainer;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "confluent_demo", mixinStandardHelpOptions = true)
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

  @SuppressWarnings("SystemOut")
  private static PrintStream getOutputStream() {
    return System.out;
  }

  @Command(name = "encode")
  public void encode() {
    var out = getOutputStream();
    var sampleMap = createSampleMap();

    var config = getConfig();
    var encoded = EncodingHelper.encodeSamples(sampleMap, config);

    EncodingHelper.print(out, encoded);
  }

  @Command(name = "decode")
  public void decode() {
    var out = getOutputStream();
    var sampleMap = createSampleMap();

    var config = getConfig();
    var encoded = EncodingHelper.encodeSamples(sampleMap, config);

    EncodingHelper.decode(out, encoded, config);
  }

  @Command(name = "compatible")
  public void compatible() {
    var out = getOutputStream();
    var sampleMap = createSampleMap();
    var schemaMap = createSchemaMap(sampleMap);
    var graph = CompatibilityHelper.calculateCompatibility(schemaMap);

    var config = getConfig();
    var encoded = EncodingHelper.encodeSamples(sampleMap, config);
    CompatibilityHelper.logCompatible(out, graph, schemaMap, encoded, config);
  }

  private ImmutableMap<String, ?> getConfig() {
    var random = new Random().nextInt(10_000);
    var registryUrl = "mock://random-" + random;

    var builder = ImmutableMap.<String, Object>builder();
    builder.put(AbstractKafkaSchemaSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, registryUrl);

    switch (dataModel) {
      case REFLECT -> builder.put(KafkaAvroDeserializerConfig.SCHEMA_REFLECTION_CONFIG, true);
      case SPECIFIC -> builder.put(KafkaAvroDeserializerConfig.SPECIFIC_AVRO_READER_CONFIG, true);
      default -> {}
    }

    return builder.build();
  }

  private ImmutableMap<String, ? extends GenericContainer> createSampleMap() {
    return switch (dataModel) {
      case DYNAMIC -> DynamicSchemaHelper.createSampleMap();
      case JSON -> JsonSchemaHelper.createSampleMap();
      case REFLECT -> ReflectSchemaHelper.createSampleMap();
      case SPECIFIC -> SpecificSchemaHelper.createSampleMap();
    };
  }

  private static ImmutableMap<String, Schema> createSchemaMap(
      Map<String, ? extends GenericContainer> sampleMap) {
    var builder = ImmutableSortedMap.<String, Schema>naturalOrder();
    for (var entry : sampleMap.entrySet()) {
      builder.put(entry.getKey(), entry.getValue().getSchema());
    }
    return builder.build();
  }

  public enum DataModel {
    DYNAMIC,
    JSON,
    REFLECT,
    SPECIFIC
  }
}

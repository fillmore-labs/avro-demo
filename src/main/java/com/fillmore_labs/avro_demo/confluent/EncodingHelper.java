package com.fillmore_labs.avro_demo.confluent;

import com.google.common.collect.ImmutableMap;
import com.google.common.io.BaseEncoding;
import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import io.confluent.kafka.serializers.KafkaAvroSerializer;
import java.io.PrintStream;
import java.nio.ByteBuffer;
import java.util.Map;

public final class EncodingHelper {
  private static final int REGISTRY_ID_LENGTH = 1;
  private static final int FINGERPRINT_LENGTH = 4;
  private static final String TOPIC = "topic";

  private EncodingHelper() {}

  public static ImmutableMap<String, ByteBuffer> encodeSamples(
      Map<String, ?> sampleMap, Map<String, ?> config) {
    var builder = ImmutableMap.<String, ByteBuffer>builderWithExpectedSize(sampleMap.size());

    for (var entry : sampleMap.entrySet()) {
      var sample = entry.getValue();

      ByteBuffer encoded;
      try (var serializer = new KafkaAvroSerializer()) {
        serializer.configure(config, /* isKey= */ false);
        var serialized = serializer.serialize(TOPIC, sample);
        encoded = ByteBuffer.wrap(serialized);
      }

      builder.put(entry.getKey(), encoded);
    }

    return builder.build();
  }

  public static void print(PrintStream out, Map<String, ByteBuffer> encoded) {
    for (var entry : encoded.entrySet()) {
      var resource = entry.getKey();
      var buffer = entry.getValue();
      out.printf("%s encoded: %s%n", resource, hex(buffer));
    }
  }

  public static void decode(
      PrintStream out, Map<String, ByteBuffer> encoded, Map<String, ?> config) {
    try (var deserializer = new KafkaAvroDeserializer()) {
      deserializer.configure(config, /* isKey= */ false);

      for (var entry : encoded.entrySet()) {
        var resource = entry.getKey();
        var buffer = entry.getValue();
        if (buffer.hasArray() && buffer.arrayOffset() >= 0) {
          var decoded = deserializer.deserialize(TOPIC, buffer.array());
          out.printf("%s decoded: %s%n", resource, decoded);
        }
      }
    }
  }

  public static String hex(ByteBuffer buffer) {
    var magic = new byte[REGISTRY_ID_LENGTH];
    var registryID = new byte[FINGERPRINT_LENGTH];
    var payload = new byte[buffer.limit() - REGISTRY_ID_LENGTH - FINGERPRINT_LENGTH];

    buffer.get(0, magic);
    buffer.get(REGISTRY_ID_LENGTH, registryID);
    buffer.get(REGISTRY_ID_LENGTH + FINGERPRINT_LENGTH, payload);

    var baseEncoding = BaseEncoding.base16().withSeparator(" ", 2);

    return String.format(
        "magic %s - registry ID %s - payload %s",
        baseEncoding.encode(magic), baseEncoding.encode(registryID), baseEncoding.encode(payload));
  }
}

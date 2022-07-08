package com.fillmore_labs.avro_demo;

import com.google.common.collect.ImmutableMap;
import com.google.common.io.BaseEncoding;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.ByteBuffer;
import java.util.Map;
import org.apache.avro.generic.GenericContainer;
import org.apache.avro.generic.GenericData;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.io.EncoderFactory;
import org.apache.avro.message.BinaryMessageDecoder;
import org.apache.avro.message.BinaryMessageEncoder;
import org.apache.avro.message.SchemaStore;
import org.apache.avro.util.NonCopyingByteArrayOutputStream;

public final class EncodingHelper {
  private static final int MAGIC_LENGTH = 2;
  private static final int FINGERPRINT_LENGTH = 8;

  private EncodingHelper() {}

  public static ImmutableMap<String, ByteBuffer> encodeSamples(
      GenericData model, Map<String, ? extends GenericContainer> sampleMap) throws IOException {
    var builder = ImmutableMap.<String, ByteBuffer>builderWithExpectedSize(sampleMap.size());

    for (var entry : sampleMap.entrySet()) {
      var sample = entry.getValue();
      var writerSchema = sample.getSchema();
      var encoder = new BinaryMessageEncoder<GenericContainer>(model, writerSchema);
      var encoded = encoder.encode(sample);
      builder.put(entry.getKey(), encoded);
    }

    return builder.build();
  }

  public static ImmutableMap<String, ByteBuffer> encodeSamplesJson(
      DatumWriter<GenericContainer> writer, Map<String, ? extends GenericContainer> sampleMap)
      throws IOException {
    var builder = ImmutableMap.<String, ByteBuffer>builderWithExpectedSize(sampleMap.size());

    var encoderFactory = EncoderFactory.get();
    for (var entry : sampleMap.entrySet()) {
      var sample = entry.getValue();
      var writerSchema = sample.getSchema();
      writer.setSchema(writerSchema);

      try (var encoded = new NonCopyingByteArrayOutputStream(512)) {
        var encoder = encoderFactory.jsonEncoder(writerSchema, encoded, /* pretty= */ true);
        writer.write(sample, encoder);
        encoder.flush();

        builder.put(entry.getKey(), encoded.asByteBuffer());
      }
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
      PrintStream out, GenericData model, Map<String, ByteBuffer> encoded, SchemaStore resolver)
      throws IOException {
    var decoder = new BinaryMessageDecoder<>(model, null, resolver);

    for (var entry : encoded.entrySet()) {
      var resource = entry.getKey();
      var buffer = entry.getValue();
      var decoded = decoder.decode(buffer);
      out.printf("%s decoded: %s%n", resource, decoded);
    }
  }

  public static String hex(ByteBuffer buffer) {
    var magic = new byte[MAGIC_LENGTH];
    var fingerprint = new byte[FINGERPRINT_LENGTH];
    var payload = new byte[buffer.limit() - MAGIC_LENGTH - FINGERPRINT_LENGTH];

    buffer.get(0, magic);
    buffer.get(MAGIC_LENGTH, fingerprint);
    buffer.get(MAGIC_LENGTH + FINGERPRINT_LENGTH, payload);

    var baseEncoding = BaseEncoding.base16().withSeparator(" ", 2);

    return String.format(
        "magic %s - fingerprint %s - payload %s",
        baseEncoding.encode(magic), baseEncoding.encode(fingerprint), baseEncoding.encode(payload));
  }
}

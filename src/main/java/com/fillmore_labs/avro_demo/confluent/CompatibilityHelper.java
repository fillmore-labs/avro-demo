package com.fillmore_labs.avro_demo.confluent;

import com.google.common.graph.ElementOrder;
import com.google.common.graph.Graph;
import com.google.common.graph.GraphBuilder;
import com.google.common.graph.ImmutableGraph;
import io.confluent.kafka.schemaregistry.CompatibilityChecker;
import io.confluent.kafka.schemaregistry.CompatibilityLevel;
import io.confluent.kafka.schemaregistry.avro.AvroSchema;
import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import java.io.PrintStream;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.Map;
import org.apache.avro.Schema;
import org.apache.avro.SchemaValidationException;
import org.apache.avro.SchemaValidator;
import org.apache.avro.SchemaValidatorBuilder;

public final class CompatibilityHelper {
  private CompatibilityHelper() {}

  public static ImmutableGraph<String> calculateCompatibility(
      Map<String, Schema> writerMap, Map<String, Schema> readerMap) {
    var graphBuilder =
        GraphBuilder.directed()
            .nodeOrder(ElementOrder.<String>natural())
            .allowsSelfLoops(true)
            .immutable();

    var validator = new CanReadValidator();
    for (var writer : writerMap.entrySet()) {
      var writerSchema = writer.getValue();

      for (var reader : readerMap.entrySet()) {
        var readerSchema = reader.getValue();
        if (validator.canRead(readerSchema, writerSchema)) {
          graphBuilder.putEdge(writer.getKey(), reader.getKey());
        }
      }
    }

    return graphBuilder.build();
  }

  public static void logCompatible(
      PrintStream out,
      Graph<String> graph,
      Map<String, Schema> writerSchemaMap,
      Map<String, Schema> readerSchemaMap,
      Map<String, ByteBuffer> encoded,
      Map<String, ?> config) {
    var checker = CompatibilityChecker.checker(CompatibilityLevel.FORWARD);

    for (var e : encoded.entrySet()) {
      var writer = e.getKey();
      var buffer = e.getValue();

      if (buffer != null && buffer.hasArray() && buffer.arrayOffset() >= 0) {
        try (var deserializer = new KafkaAvroDeserializer()) {
          deserializer.configure(config, /* isKey= */ false);
          var generic = deserializer.deserialize("TOPIC", buffer.array());

          var writerSchema = writerSchemaMap.get(writer);
          var parsedWriterSchema = new AvroSchema(writerSchema);

          out.println("---");
          for (var reader : graph.successors(writer)) {
            var readerSchema = readerSchemaMap.get(reader);
            var decoded = deserializer.deserialize("TOPIC", buffer.array(), readerSchema);

            var parsedReaderSchema = new AvroSchema(readerSchema);
            var violations = checker.isCompatible(parsedWriterSchema, List.of(parsedReaderSchema));

            var msg =
                violations.isEmpty() ? "" : " (Confluent Schema Registry forward incompatible)";
            out.printf("%s can be read as %s: %s -> %s%s%n", writer, reader, generic, decoded, msg);
          }
        }
      }
    }
  }

  private static final class CanReadValidator {
    private final SchemaValidator validator =
        new SchemaValidatorBuilder().canReadStrategy().validateAll();

    public boolean canRead(Schema toValidate, Schema existing) {
      try {
        validator.validate(toValidate, List.of(existing));
        return true;
      } catch (SchemaValidationException e) {
        return false;
      }
    }
  }
}

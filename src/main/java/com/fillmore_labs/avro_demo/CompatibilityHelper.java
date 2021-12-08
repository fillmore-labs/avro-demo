package com.fillmore_labs.avro_demo;

import com.google.common.graph.Graph;
import com.google.common.graph.GraphBuilder;
import com.google.common.graph.ImmutableGraph;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.Map;
import org.apache.avro.Schema;
import org.apache.avro.SchemaValidationException;
import org.apache.avro.SchemaValidator;
import org.apache.avro.SchemaValidatorBuilder;
import org.apache.avro.generic.GenericData;
import org.apache.avro.message.BinaryMessageDecoder;
import org.apache.avro.message.SchemaStore;

public final class CompatibilityHelper {
  private CompatibilityHelper() {}

  public static ImmutableGraph<String> calculateCompatibility(Map<String, Schema> schemaMap) {
    var graphBuilder = GraphBuilder.directed().allowsSelfLoops(true).<String>immutable();

    var validator = new CanReadValidator();
    for (var writer : schemaMap.entrySet()) {
      var writerSchema = writer.getValue();

      for (var reader : schemaMap.entrySet()) {
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
      Map<String, Schema> schemaMap,
      Map<String, ByteBuffer> encoded,
      SchemaStore resolver)
      throws IOException {

    var model = GenericData.get();

    for (var writer : graph.nodes().stream().sorted().toList()) {
      var buffer = encoded.get(writer);

      var genericDecoder = new BinaryMessageDecoder<>(model, null, resolver);
      var generic = genericDecoder.decode(buffer);

      out.println("---");
      for (var reader : graph.successors(writer).stream().sorted().toList()) {
        var readerSchema = schemaMap.get(reader);
        var decoder = new BinaryMessageDecoder<>(model, readerSchema, resolver);

        var decoded = decoder.decode(buffer);
        out.printf("%s can be read as %s: %s -> %s%n", writer, reader, generic, decoded);
      }
    }
  }

  public static <N> void logNonTransitive(PrintStream out, Graph<N> graph) {
    for (var writer : graph.nodes().stream().sorted().toList()) {
      var directCompatibles = graph.successors(writer);
      for (var directCompatible : directCompatibles) {
        for (var reader : graph.successors(directCompatible).stream().sorted().toList()) {
          if (!directCompatibles.contains(reader)) {
            out.printf("Not transitive: %s -> %s -> %s%n", writer, directCompatible, reader);
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

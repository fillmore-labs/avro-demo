package com.fillmore_labs.avro_demo.json;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSortedMap;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UncheckedIOException;
import org.apache.avro.Schema;
import org.apache.avro.Schema.Parser;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.generic.GenericRecordBuilder;

public final class JsonSchemaHelper {
  private static final int NUM_SCHEMATA = 12;

  private JsonSchemaHelper() {}

  public static ImmutableMap<String, GenericRecord> createSampleMap() {
    var builder = ImmutableSortedMap.<String, GenericRecord>naturalOrder();
    for (var index = 1; index <= NUM_SCHEMATA; index++) {
      var schema = readSchema(index);
      var record = createSampleRecord(index, schema);
      builder.put(String.format("Json %2d", index), record);
    }
    return builder.build();
  }

  private static Schema readSchema(int schemaNumber) {
    var schemaName = String.format("AvroRecord%d.avsc", schemaNumber);
    try (var resourceStream = JsonSchemaHelper.class.getResourceAsStream(schemaName)) {
      if (resourceStream == null) {
        throw new FileNotFoundException(String.format("Resource %s not found", schemaName));
      }
      return new Parser().parse(resourceStream);
    } catch (IOException e) {
      throw new UncheckedIOException(e);
    }
  }

  private static GenericRecord createSampleRecord(int index, Schema schema) {
    var builder = new GenericRecordBuilder(schema);

    switch (index) {
      case 1, 2, 3, 4, 6 -> builder.set("field1", "content 1");
      case 5, 7, 8, 9 -> builder.set("field1", "content 1").set("field2", null);
      case 11 -> builder.set("alias1", "alias 1");
      case 12 -> builder.set("field2", null);
      default -> {} // accept defaults
    }

    return builder.build();
  }
}

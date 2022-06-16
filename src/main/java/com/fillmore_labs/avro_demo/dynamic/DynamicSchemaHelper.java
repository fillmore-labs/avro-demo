package com.fillmore_labs.avro_demo.dynamic;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSortedMap;
import org.apache.avro.Schema;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.generic.GenericRecordBuilder;

public final class DynamicSchemaHelper {
  private static final Schema[] SCHEMATA = {
    AvroRecord1Helper.SCHEMA,
    AvroRecord2Helper.SCHEMA,
    AvroRecord3Helper.SCHEMA,
    AvroRecord4Helper.SCHEMA,
    AvroRecord5Helper.SCHEMA,
    AvroRecord6Helper.SCHEMA,
    AvroRecord7Helper.SCHEMA,
    AvroRecord8Helper.SCHEMA,
    AvroRecord9Helper.SCHEMA
  };

  private DynamicSchemaHelper() {}

  public static ImmutableMap<String, GenericRecord> createSampleMap() {
    var builder = ImmutableSortedMap.<String, GenericRecord>naturalOrder();
    for (var index = 1; index <= SCHEMATA.length; index++) {
      var schema = SCHEMATA[index - 1];
      var record = createSampleRecord(index, schema);
      builder.put(String.format("Dynamic %d", index), record);
    }
    return builder.build();
  }

  private static GenericRecord createSampleRecord(int index, Schema schema) {
    var builder = new GenericRecordBuilder(schema);

    switch (index) {
      case 1, 3 -> builder.set("field1", "content1");
      case 5, 7, 8, 9 -> builder.set("field2", null);
      default -> {} // accept defaults
    }

    return builder.build();
  }
}

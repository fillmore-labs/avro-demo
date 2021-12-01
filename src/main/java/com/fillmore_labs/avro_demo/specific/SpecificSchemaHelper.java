package com.fillmore_labs.avro_demo.specific;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSortedMap;
import org.apache.avro.specific.SpecificRecord;

public final class SpecificSchemaHelper {
  private SpecificSchemaHelper() {}

  public static ImmutableMap<String, SpecificRecord> createSampleMap() {
    var builder = ImmutableSortedMap.<String, SpecificRecord>naturalOrder();
    for (var index = 1; index <= 9; index++) {
      var record = createSampleRecord(index);
      builder.put(String.format("Specific %d", index), record);
    }
    return builder.build();
  }

  @SuppressWarnings("NullAway")
  private static SpecificRecord createSampleRecord(int index) {
    switch (index) {
      case 1:
        return AvroRecord1.newBuilder().setField1("content1").build();

      case 2:
        return AvroRecord2.newBuilder().build();

      case 3:
        return AvroRecord3.newBuilder().setField1("content1").build();

      case 4:
        return AvroRecord4.newBuilder().build();

      case 5:
        return AvroRecord5.newBuilder().setField2(null).build();

      case 6:
        return AvroRecord6.newBuilder().build();

      case 7:
        return AvroRecord7.newBuilder().setField2(null).build();

      case 8:
        return AvroRecord8.newBuilder().setField2(null).build();

      case 9:
        return AvroRecord9.newBuilder().setField2(null).build();

      default:
        throw new IllegalArgumentException(String.format("Index %d out of range", index));
    }
  }
}

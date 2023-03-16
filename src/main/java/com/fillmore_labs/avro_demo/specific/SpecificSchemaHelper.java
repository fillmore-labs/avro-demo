package com.fillmore_labs.avro_demo.specific;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSortedMap;
import org.apache.avro.specific.SpecificRecord;

public final class SpecificSchemaHelper {
  private SpecificSchemaHelper() {}

  public static ImmutableMap<String, SpecificRecord> createSampleMap() {
    var builder = ImmutableSortedMap.<String, SpecificRecord>naturalOrder();
    for (var index = 1; index <= 12; index++) {
      var record = createSampleRecord(index);
      builder.put(String.format("Specific %2d", index), record);
    }
    return builder.build();
  }

  @SuppressWarnings("NullAway")
  private static SpecificRecord createSampleRecord(int index) {
    return switch (index) {
      case 1 -> AvroRecord1.newBuilder().setField1("content 1").build();
      case 2 -> AvroRecord2.newBuilder().setField1("content 1").build();
      case 3 -> AvroRecord3.newBuilder().setField1("content 1").build();
      case 4 -> AvroRecord4.newBuilder().setField1("content 1").build();
      case 5 -> AvroRecord5.newBuilder().setField1("content 1").setField2(null).build();
      case 6 -> AvroRecord6.newBuilder().setField1("content 1").build();
      case 7 -> AvroRecord7.newBuilder().setField1("content 1").setField2(null).build();
      case 8 -> AvroRecord8.newBuilder().setField1("content 1").setField2(null).build();
      case 9 -> AvroRecord9.newBuilder().setField1("content 1").setField2(null).build();
      case 10 -> AvroRecord10.newBuilder().build();
      case 11 -> AvroRecord11.newBuilder().setAlias1("alias 1").build();
      case 12 -> AvroRecord12.newBuilder().setField2(null).build();
      default -> throw new IllegalArgumentException(String.format("Index %d out of range", index));
    };
  }
}

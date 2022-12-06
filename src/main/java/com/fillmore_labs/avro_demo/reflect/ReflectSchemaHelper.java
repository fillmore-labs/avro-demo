package com.fillmore_labs.avro_demo.reflect;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSortedMap;
import org.apache.avro.generic.GenericContainer;

public final class ReflectSchemaHelper {
  private static final String CONTENT1 = "content1";

  private ReflectSchemaHelper() {}

  public static ImmutableMap<String, GenericContainer> createSampleMap() {
    var builder = ImmutableSortedMap.<String, GenericContainer>naturalOrder();
    for (var index = 1; index <= 9; index++) {
      var record = createSampleRecord(index);
      builder.put(String.format("Reflect %d", index), record);
    }
    return builder.build();
  }

  private static GenericContainer createSampleRecord(int index) {
    switch (index) {
      case 1:
        var r1 = new AvroRecord1();
        r1.field1 = CONTENT1;
        return r1;

      case 2:
        var r2 = new AvroRecord2();
        r2.field1 = CONTENT1;
        return r2;

      case 3:
        var r3 = new AvroRecord3();
        r3.field1 = CONTENT1;
        return r3;

      case 4:
        var r4 = new AvroRecord4();
        r4.field1 = CONTENT1;
        return r4;

      case 5:
        var r5 = new AvroRecord5();
        r5.field1 = CONTENT1;
        return r5;

      case 6:
        var r6 = new AvroRecord6();
        r6.field1 = CONTENT1;
        return r6;

      case 7:
        var r7 = new AvroRecord7();
        r7.field1 = CONTENT1;
        return r7;

      case 8:
        var r8 = new AvroRecord8();
        r8.field1 = CONTENT1;
        return r8;

      case 9:
        var r9 = new AvroRecord9();
        r9.field1 = CONTENT1;
        return r9;

      default:
        throw new IllegalArgumentException(String.format("Index %d out of range", index));
    }
  }
}

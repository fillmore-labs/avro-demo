package com.fillmore_labs.avro_demo.dynamic;

import org.apache.avro.Schema;
import org.apache.avro.SchemaBuilder;

public final class AvroRecord3Helper {
  public static final Schema SCHEMA;

  static {
    SCHEMA =
        SchemaBuilder.builder("com.fillmore_labs.avro_demo")
            .record("AvroRecord")
            .aliases(
                "com.fillmore_labs.avro_demo.reflect.AvroRecord11",
                "com.fillmore_labs.avro_demo.specific.AvroRecord11")
            .fields()
            .name("field1")
            .aliases("alias1")
            .type()
            .stringType()
            .noDefault()
            .endRecord();
  }

  private AvroRecord3Helper() {}
}

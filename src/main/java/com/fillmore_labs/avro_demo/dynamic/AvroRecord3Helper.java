package com.fillmore_labs.avro_demo.dynamic;

import org.apache.avro.Schema;
import org.apache.avro.SchemaBuilder;

public final class AvroRecord3Helper {
  public static final Schema SCHEMA;

  static {
    SCHEMA =
        SchemaBuilder.builder("com.fillmore_labs.avro_demo")
            .record("AvroRecord")
            .fields()
            .name("field1")
            .aliases("alias1")
            .type()
            .stringType()
            .stringDefault("somethingelse")
            .endRecord();
  }

  private AvroRecord3Helper() {}
}

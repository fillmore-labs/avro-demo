package com.fillmore_labs.avro_demo.dynamic;

import org.apache.avro.Schema;
import org.apache.avro.SchemaBuilder;

public final class AvroRecord5Helper {
  public static final Schema SCHEMA;

  static {
    SCHEMA =
        SchemaBuilder.builder("com.fillmore_labs.avro_demo")
            .record("AvroRecord")
            .fields()
            .name("field1")
            .type()
            .stringType()
            .stringDefault("content1")
            .name("field2")
            .type()
            .nullType()
            .noDefault()
            .endRecord();
  }

  private AvroRecord5Helper() {}
}

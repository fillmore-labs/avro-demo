package com.fillmore_labs.avro_demo.dynamic;

import org.apache.avro.Schema;
import org.apache.avro.SchemaBuilder;

public final class AvroRecord7Helper {
  public static final Schema SCHEMA;

  static {
    SCHEMA =
        SchemaBuilder.builder("com.fillmore_labs.avro_demo")
            .record("AvroRecord")
            .fields()
            .name("field1")
            .type()
            .stringType()
            .stringDefault("default 1")
            .name("field2")
            .type()
            .unionOf()
            .nullType()
            .and()
            .stringType()
            .endUnion()
            .noDefault()
            .endRecord();
  }

  private AvroRecord7Helper() {}
}

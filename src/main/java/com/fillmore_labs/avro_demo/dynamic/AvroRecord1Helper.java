package com.fillmore_labs.avro_demo.dynamic;

import org.apache.avro.Schema;
import org.apache.avro.SchemaBuilder;

public final class AvroRecord1Helper {
  public static final Schema SCHEMA;

  static {
    SCHEMA =
        SchemaBuilder.builder("com.fillmore_labs.avro_demo")
            .record("AvroRecord")
            .fields()
            .requiredString("field1")
            .endRecord();
  }

  private AvroRecord1Helper() {}
}

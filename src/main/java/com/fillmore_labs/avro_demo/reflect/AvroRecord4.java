package com.fillmore_labs.avro_demo.reflect;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericContainer;
import org.apache.avro.reflect.AvroDefault;
import org.apache.avro.reflect.ReflectData;
import org.checkerframework.checker.nullness.qual.Nullable;

@SuppressWarnings("NullAway")
public final class AvroRecord4 implements GenericContainer {
  public static final ReflectData MODEL;
  public static final Schema SCHEMA;

  static {
    MODEL = new ReflectData();
    SCHEMA = MODEL.getSchema(AvroRecord4.class);
  }

  @AvroDefault("\"default 1\"")
  public String field1;

  @AvroDefault("null")
  public @Nullable Void field2;

  @Override
  public Schema getSchema() {
    return SCHEMA;
  }

  @Override
  public String toString() {
    return MODEL.toString(this);
  }
}

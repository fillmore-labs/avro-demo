package com.fillmore_labs.avro_demo.reflect;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericContainer;
import org.apache.avro.reflect.ReflectData;

@SuppressWarnings("NullAway")
public final class AvroRecord11 implements GenericContainer {
  public static final ReflectData MODEL;
  public static final Schema SCHEMA;

  static {
    MODEL = new ReflectData();
    SCHEMA = MODEL.getSchema(AvroRecord11.class);
  }

  public String alias1;

  @Override
  public Schema getSchema() {
    return SCHEMA;
  }

  @Override
  public String toString() {
    return MODEL.toString(this);
  }
}

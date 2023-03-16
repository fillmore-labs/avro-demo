package com.fillmore_labs.avro_demo.reflect;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericContainer;
import org.apache.avro.reflect.AvroDefault;
import org.apache.avro.reflect.ReflectData;
import org.apache.avro.reflect.Union;

@SuppressWarnings("NullAway")
public final class AvroRecord8 implements GenericContainer {
  public static final ReflectData MODEL;
  public static final Schema SCHEMA;

  static {
    MODEL = new ReflectData();
    SCHEMA = MODEL.getSchema(AvroRecord8.class);
  }

  @AvroDefault("\"default 1\"")
  public String field1;

  @AvroDefault("\"default 2\"")
  @Union({String.class, Void.class})
  public String field2;

  @Override
  public Schema getSchema() {
    return SCHEMA;
  }

  @Override
  public String toString() {
    return MODEL.toString(this);
  }
}

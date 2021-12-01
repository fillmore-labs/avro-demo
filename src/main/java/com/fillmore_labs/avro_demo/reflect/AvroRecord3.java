package com.fillmore_labs.avro_demo.reflect;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericContainer;
import org.apache.avro.reflect.AvroAlias;
import org.apache.avro.reflect.AvroDefault;
import org.apache.avro.reflect.ReflectData;

@SuppressWarnings("NullAway")
public final class AvroRecord3 implements GenericContainer {
  public static final ReflectData MODEL;
  public static final Schema SCHEMA;

  static {
    MODEL = new ReflectData();
    SCHEMA = MODEL.getSchema(AvroRecord3.class);
  }

  @AvroDefault("\"somethingelse\"")
  @AvroAlias(alias = "alias1")
  public String field1;

  @Override
  public Schema getSchema() {
    return SCHEMA;
  }

  @Override
  public String toString() {
    return MODEL.toString(this);
  }
}

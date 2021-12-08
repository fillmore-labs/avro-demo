package com.fillmore_labs.avro_demo;

import picocli.CommandLine;

public final class Main {
  private Main() {}

  @SuppressWarnings("SystemOut")
  public static void main(String... args) {
    var status =
        new CommandLine(MainCommand.command())
            .setCaseInsensitiveEnumValuesAllowed(true)
            .execute(args);
    if (status != 0) {
      System.err.printf("\u001B[31mTerminating with status %d\u001B[0m%n", status);
      System.exit(status);
    }
  }
}

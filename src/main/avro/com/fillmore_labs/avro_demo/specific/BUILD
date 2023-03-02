load("@io_bazel_rules_avro//avro:avro.bzl", "avro_idl_java_library")

AVRO_LIBS_LABELS = {
    "core": "@maven//:org_apache_avro_avro",
    "tools": "@maven//:org_apache_avro_avro_tools",
}

avro_idl_java_library(
    name = "specific",
    srcs = ["avro_record.avdl"],
    avro_libs = AVRO_LIBS_LABELS,
    strings = True,
    visibility = ["//visibility:public"],
)

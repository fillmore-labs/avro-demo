load("@io_bazel_rules_avro//avro:avro.bzl", "avro_java_library")

AVRO_LIBS_LABELS = {
    "core": "@maven//:org_apache_avro_avro",
    "tools": "@avro_tools//jar",
}

avro_java_library(
    name = "specific",
    srcs = glob(["*.avsc"]),
    avro_libs = AVRO_LIBS_LABELS,
    strings = True,
    visibility = ["//visibility:public"],
)

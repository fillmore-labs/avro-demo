load("@io_bazel_rules_avro//avro:avro.bzl", "avro_java_library")

avro_java_library(
    name = "specific",
    srcs = glob(["*.avsc"]),
    visibility = ["//visibility:public"],
)

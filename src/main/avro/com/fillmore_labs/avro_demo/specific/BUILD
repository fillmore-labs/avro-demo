load("//third_party/avro:rules_avro.bzl", "avro_java_library")

avro_java_library(
    name = "specific",
    srcs = glob(["*.avsc"]),
    javacopts = ["-XepDisableAllChecks"],
    visibility = ["//visibility:public"],
)

load("@rules_java//java:defs.bzl", "java_library")

java_library(
    name = "json",
    srcs = glob(["*.java"]),
    resources = [
        "//src/main/resources/com/fillmore_labs/avro_demo/json",
    ],
    visibility = ["//visibility:public"],
    deps = [
        "//toolchain:nullaway",
        "@maven//:com_google_errorprone_error_prone_annotations",
        "@maven//:com_google_guava_guava",
        "@maven//:org_apache_avro_avro",
        "@maven//:org_checkerframework_checker_qual",
    ],
)

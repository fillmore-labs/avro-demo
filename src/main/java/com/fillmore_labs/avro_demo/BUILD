load("@rules_java//java:defs.bzl", "java_binary")

java_binary(
    name = "avro_demo",
    srcs = glob(["*.java"]),
    jvm_flags = ["-XX:+CrashOnOutOfMemoryError"],
    main_class = "com.fillmore_labs.avro_demo.Main",
    visibility = ["//visibility:public"],
    runtime_deps = [
        "@maven//:org_slf4j_slf4j_jdk14",
    ],
    deps = [
        "//src/main/java/com/fillmore_labs/avro_demo/dynamic",
        "//src/main/java/com/fillmore_labs/avro_demo/json",
        "//src/main/java/com/fillmore_labs/avro_demo/reflect",
        "//src/main/java/com/fillmore_labs/avro_demo/specific",
        "//toolchain:nullaway",
        "@maven//:com_google_errorprone_error_prone_annotations",
        "@maven//:com_google_guava_guava",
        "@maven//:info_picocli_picocli",
        "@maven//:org_apache_avro_avro",
    ],
)

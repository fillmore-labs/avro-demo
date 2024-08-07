""" Confluent dependencies. """

load("@bazel_tools//tools/build_defs/repo:http.bzl", "http_archive")

CONFLUENT_ARTIFACTS = [
    "com.fasterxml.jackson.core:jackson-annotations:2.17.2",
    "com.fasterxml.jackson.core:jackson-core:2.17.2",
    "com.fasterxml.jackson.core:jackson-databind:2.17.2",
    "com.fasterxml.jackson.dataformat:jackson-dataformat-csv:2.17.2",
    "com.fasterxml.jackson.datatype:jackson-datatype-guava:2.17.2",
    "com.fasterxml.jackson.datatype:jackson-datatype-jdk8:2.17.2",
    "com.fasterxml.jackson.datatype:jackson-datatype-joda:2.17.2",
    "com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.17.2",
    "com.fasterxml.jackson.module:jackson-module-parameter-names:2.17.2",
    "com.github.erosb:everit-json-schema:1.14.4",
    "com.google.api.grpc:proto-google-common-protos:2.42.0",
    "com.squareup.wire:wire-runtime-jvm:4.9.9",
    "com.squareup.wire:wire-schema-jvm:4.9.9",
    "io.swagger.core.v3:swagger-annotations-jakarta:2.2.22",
    "org.apache.commons:commons-compress:1.26.2",
    "org.apache.commons:commons-lang3:3.15.0",
    "org.apache.kafka:kafka_2.13:3.7.1",
    "org.apache.kafka:kafka-clients:3.7.1",
    "org.jetbrains.kotlin:kotlin-stdlib:2.0.0",
    "org.json:json:20240303",
]

def confluent_repositories():
    http_archive(
        name = "confluent_schema_registry",
        build_file = "//third_party/confluent:BUILD.schema_registry.bazel",
        sha256 = "3b2ea70081889657179d94705cde4481cbf6d5e8da8c0b001e36835b10d5f321",
        strip_prefix = "schema-registry-7.6.2",
        url = "https://github.com/confluentinc/schema-registry/archive/refs/tags/v7.6.2.tar.gz",
    )
    http_archive(
        name = "confluent_common",
        build_file = "//third_party/confluent:BUILD.common.bazel",
        sha256 = "8515489cbf76655a69fb16372829f532690ddb0ff7d89293cfc199d14038e6fc",
        strip_prefix = "common-7.4.6",
        url = "https://github.com/confluentinc/common/archive/refs/tags/v7.4.6.tar.gz",
    )

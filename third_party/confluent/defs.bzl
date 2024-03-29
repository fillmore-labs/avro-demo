""" Confluent dependencies. """

load("@bazel_tools//tools/build_defs/repo:http.bzl", "http_archive")

CONFLUENT_ARTIFACTS = [
    "com.fasterxml.jackson.core:jackson-annotations:2.15.0",
    "com.fasterxml.jackson.core:jackson-core:2.15.0",
    "com.fasterxml.jackson.core:jackson-databind:2.15.0",
    "com.fasterxml.jackson.dataformat:jackson-dataformat-csv:2.15.0",
    "com.fasterxml.jackson.datatype:jackson-datatype-guava:2.15.0",
    "com.fasterxml.jackson.datatype:jackson-datatype-jdk8:2.15.0",
    "com.fasterxml.jackson.datatype:jackson-datatype-joda:2.15.0",
    "com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.15.0",
    "com.fasterxml.jackson.module:jackson-module-parameter-names:2.15.0",
    "com.github.erosb:everit-json-schema:1.14.2",
    "com.google.api.grpc:proto-google-common-protos:2.16.0",
    "com.squareup.wire:wire-runtime-jvm:4.5.5",
    "com.squareup.wire:wire-schema-jvm:4.5.5",
    "io.swagger.core.v3:swagger-annotations-jakarta:2.2.9",
    "org.apache.commons:commons-compress:1.23.0",
    "org.apache.commons:commons-lang3:3.12.0",
    "org.apache.kafka:kafka_2.13:3.4.0",
    "org.apache.kafka:kafka-clients:3.4.0",
    "org.jetbrains.kotlin:kotlin-stdlib:1.8.20",
    "org.json:json:20230227",
]

def confluent_repositories():
    http_archive(
        name = "confluent_common",
        build_file = "//third_party/confluent:BUILD.common.bazel",
        sha256 = "c706cc4982fcdbc9d4cb8bde4773d03503d9937c7e8729e5be5b6f581e4f5533",
        strip_prefix = "common-7.4.0-rc230501101036",
        url = "https://github.com/confluentinc/common/archive/refs/tags/v7.4.0-rc230501101036.tar.gz",
    )
    http_archive(
        name = "confluent_schema_registry",
        build_file = "//third_party/confluent:BUILD.schema_registry.bazel",
        sha256 = "37c4a64a41b564c8bc30420a72a07749cde08c76cdccf715a0490c9f4dee0649",
        strip_prefix = "schema-registry-7.4.0-rc230323205105",
        url = "https://github.com/confluentinc/schema-registry/archive/refs/tags/v7.4.0-rc230323205105.tar.gz",
    )

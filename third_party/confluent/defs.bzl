""" Confluent dependencies. """

load("@bazel_tools//tools/build_defs/repo:http.bzl", "http_archive")

CONFLUENT_ARTIFACTS = [
    "com.fasterxml.jackson.core:jackson-annotations:2.13.3",
    "com.fasterxml.jackson.core:jackson-core:2.13.3",
    "com.fasterxml.jackson.core:jackson-databind:2.13.3",
    "com.fasterxml.jackson.datatype:jackson-datatype-guava:2.13.3",
    "com.fasterxml.jackson.datatype:jackson-datatype-jdk8:2.13.3",
    "com.fasterxml.jackson.datatype:jackson-datatype-joda:2.13.3",
    "com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.13.3",
    "com.fasterxml.jackson.module:jackson-module-parameter-names:2.13.3",
    "com.github.erosb:everit-json-schema:1.14.1",
    "com.google.api.grpc:proto-google-common-protos:2.9.0",
    "com.kjetland:mbknor-jackson-jsonschema_2.13:1.0.39",
    "com.squareup.wire:wire-runtime:4.3.0",
    "com.squareup.wire:wire-schema:4.3.0",
    "io.swagger.core.v3:swagger-annotations:2.2.0",
    "jakarta.servlet:jakarta.servlet-api:4.0.4",
    "jakarta.validation:jakarta.validation-api:2.0.2",
    "jakarta.ws.rs:jakarta.ws.rs-api:2.1.6",
    "org.apache.kafka:kafka-clients:3.2.0",
    "org.apache.kafka:kafka_2.13:3.2.0",
    "org.jetbrains.kotlin:kotlin-stdlib:1.5.32",
    "org.json:json:20220320",
]

def confluent_repositories():
    http_archive(
        name = "confluent_common",
        build_file = "//third_party/confluent:BUILD.common.bazel",
        sha256 = "c6f41e0069bd21746e32bfe9a3dec73dcec9f07fe752e1e0c289b423b825ec93",
        strip_prefix = "common-7.1.1",
        urls = ["https://github.com/confluentinc/common/archive/refs/tags/v7.1.1.tar.gz"],
    )
    http_archive(
        name = "confluent_schema_registry",
        build_file = "//third_party/confluent:BUILD.schema_registry.bazel",
        sha256 = "7c954b5a544ab53257b23f407f3aaed5bf4a4bcbc9264c8544ac1db96b63154b",
        strip_prefix = "schema-registry-7.1.1",
        urls = ["https://github.com/confluentinc/schema-registry/archive/refs/tags/v7.1.1.tar.gz"],
    )

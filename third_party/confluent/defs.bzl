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
    "com.google.api.grpc:proto-google-common-protos:2.9.1",
    "com.kjetland:mbknor-jackson-jsonschema_2.13:1.0.39",
    "com.squareup.wire:wire-runtime:4.4.0",
    "com.squareup.wire:wire-schema:4.4.0",
    "io.swagger.core.v3:swagger-annotations:2.2.1",
    "jakarta.servlet:jakarta.servlet-api:4.0.4",
    "jakarta.validation:jakarta.validation-api:2.0.2",
    "jakarta.ws.rs:jakarta.ws.rs-api:2.1.6",
    "org.apache.kafka:kafka-clients:3.2.0",
    "org.apache.kafka:kafka_2.13:3.2.0",
    "org.jetbrains.kotlin:kotlin-stdlib:1.6.10",
    "org.json:json:20220320",
]

def confluent_repositories():
    http_archive(
        name = "confluent_common",
        build_file = "//third_party/confluent:BUILD.common.bazel",
        sha256 = "042dd64597f3b9f5fda0151fc9451e3b48cd1558f956fda9f39c2ce8a2b353b1",
        strip_prefix = "common-7.2.2",
        urls = ["https://github.com/confluentinc/common/archive/refs/tags/v7.2.2.tar.gz"],
    )
    http_archive(
        name = "confluent_schema_registry",
        build_file = "//third_party/confluent:BUILD.schema_registry.bazel",
        sha256 = "0ec430b4620b4d4047718831f02171235267c0544b86466c4ce5e326decea9e6",
        strip_prefix = "schema-registry-7.2.2",
        urls = ["https://github.com/confluentinc/schema-registry/archive/refs/tags/v7.2.2.tar.gz"],
    )

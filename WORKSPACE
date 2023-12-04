workspace(name = "com_fillmore_labs_avro_demo")

register_toolchains("//toolchain:toolchain_java17_definition")

load("@bazel_tools//tools/build_defs/repo:http.bzl", "http_archive")

# ---

http_archive(
    name = "bazel_skylib",
    sha256 = "cd55a062e763b9349921f0f5db8c3933288dc8ba4f76dd9416aac68acee3cb94",
    urls = [
        "https://github.com/bazelbuild/bazel-skylib/releases/download/1.5.0/bazel-skylib-1.5.0.tar.gz",
        "https://mirror.bazel.build/github.com/bazelbuild/bazel-skylib/releases/download/1.5.0/bazel-skylib-1.5.0.tar.gz",
    ],
)

http_archive(
    name = "io_bazel_rules_go",
    sha256 = "d6ab6b57e48c09523e93050f13698f708428cfd5e619252e369d377af6597707",
    urls = [
        "https://github.com/bazelbuild/rules_go/releases/download/v0.43.0/rules_go-v0.43.0.zip",
        "https://mirror.bazel.build/github.com/bazelbuild/rules_go/releases/download/v0.43.0/rules_go-v0.43.0.zip",
    ],
)

http_archive(
    name = "bazel_gazelle",
    sha256 = "b7387f72efb59f876e4daae42f1d3912d0d45563eac7cb23d1de0b094ab588cf",
    urls = [
        "https://github.com/bazelbuild/bazel-gazelle/releases/download/v0.34.0/bazel-gazelle-v0.34.0.tar.gz",
        "https://mirror.bazel.build/github.com/bazelbuild/bazel-gazelle/releases/download/v0.34.0/bazel-gazelle-v0.34.0.tar.gz",
    ],
)

http_archive(
    name = "rules_proto",
    sha256 = "dc3fb206a2cb3441b485eb1e423165b231235a1ea9b031b4433cf7bc1fa460dd",
    strip_prefix = "rules_proto-5.3.0-21.7",
    url = "https://github.com/bazelbuild/rules_proto/archive/refs/tags/5.3.0-21.7.tar.gz",
)

http_archive(
    name = "com_google_protobuf",
    sha256 = "9bd87b8280ef720d3240514f884e56a712f2218f0d693b48050c836028940a42",
    strip_prefix = "protobuf-25.1",
    url = "https://github.com/protocolbuffers/protobuf/archive/refs/tags/v25.1.tar.gz",
)

http_archive(
    name = "rules_jvm_external",
    sha256 = "d31e369b854322ca5098ea12c69d7175ded971435e55c18dd9dd5f29cc5249ac",
    strip_prefix = "rules_jvm_external-5.3",
    url = "https://github.com/bazelbuild/rules_jvm_external/releases/download/5.3/rules_jvm_external-5.3.tar.gz",
)

http_archive(
    name = "io_bazel_rules_scala",
    sha256 = "e23445659ed52dea230ab7e863fcc046b7be19ee50ec781f6d603b3591bea551",
    strip_prefix = "rules_scala-2ac956bb5db8e92fbf7f150040da7ee1ef4f5597",
    url = "https://github.com/bazelbuild/rules_scala/archive/2ac956bb5db8e92fbf7f150040da7ee1ef4f5597.tar.gz",
)

http_archive(
    name = "com_github_bazelbuild_buildtools",
    sha256 = "05c3c3602d25aeda1e9dbc91d3b66e624c1f9fdadf273e5480b489e744ca7269",
    strip_prefix = "buildtools-6.4.0",
    url = "https://github.com/bazelbuild/buildtools/archive/refs/tags/v6.4.0.tar.gz",
)

http_archive(
    name = "io_bazel_rules_avro",
    patches = ["//third_party/rules_avro:rules_avro.patch"],
    sha256 = "dc103ff94cbd8f99a8fd8e5bd862c54b1ad638d81e7194a0e03cd1b44ba25e87",
    strip_prefix = "rules_avro-cdecec069db96e5bb03127510e3a58f0226efb7b",
    url = "https://github.com/chenrui333/rules_avro/archive/cdecec069db96e5bb03127510e3a58f0226efb7b.tar.gz",
)

# ---

load("@bazel_skylib//:workspace.bzl", "bazel_skylib_workspace")

bazel_skylib_workspace()

# ---

load("@io_bazel_rules_go//go:deps.bzl", "go_register_toolchains", "go_rules_dependencies")

go_register_toolchains(go_version = "1.20.3")

go_rules_dependencies()

# ---

load("@bazel_gazelle//:deps.bzl", "gazelle_dependencies")

gazelle_dependencies()

# ---

load("@rules_jvm_external//:repositories.bzl", "rules_jvm_external_deps")

rules_jvm_external_deps()

load("@rules_jvm_external//:setup.bzl", "rules_jvm_external_setup")

rules_jvm_external_setup()

# ---

load("@com_google_protobuf//:protobuf_deps.bzl", "protobuf_deps")

protobuf_deps()

bind(
    name = "error_prone_annotations",
    actual = "@maven//:com_google_errorprone_error_prone_annotations",
)

bind(
    name = "gson",
    actual = "@maven//:com_google_code_gson_gson",
)

bind(
    name = "guava",
    actual = "@maven//:com_google_guava_guava",
)

bind(
    name = "jsr305",
    actual = "@maven//:com_google_code_findbugs_jsr305",
)

bind(
    name = "j2objc_annotations",
    actual = "@maven//:com_google_j2objc_j2objc_annotations",
)

# ---

load("@rules_proto//proto:repositories.bzl", "rules_proto_dependencies", "rules_proto_toolchains")

rules_proto_dependencies()

rules_proto_toolchains()

# ---

bind(
    name = "io_bazel_rules_scala/dependency/scala/guava",
    actual = "@maven//:com_google_guava_guava",
)

bind(
    name = "io_bazel_rules_scala/dependency/thrift/javax_annotation_api",
    actual = "@maven//:jakarta_annotation_jakarta_annotation_api",
)

load("@io_bazel_rules_scala//:scala_config.bzl", "scala_config")

scala_config(scala_version = "2.13.10")

load("@io_bazel_rules_scala//scala:scala.bzl", "scala_repositories")

scala_repositories(
    fetch_sources = True,
    overriden_artifacts = {
        "io_bazel_rules_scala_scala_compiler": {
            "artifact": "org.scala-lang:scala-compiler:2.13.10",
            "deps": [
                "@io_bazel_rules_scala_scala_library",
                "@io_bazel_rules_scala_scala_reflect",
            ],
            "sha256": "2cd4a964ea48e78c91a2a7b19c4fc67a9868728ace5ee966b1d498270b3c3aa7",
        },
        "io_bazel_rules_scala_scala_library": {
            "artifact": "org.scala-lang:scala-library:2.13.10",
            "sha256": "e6ca607c3fce03e8fa38af3374ce1f8bb098e316e8bf6f6d27331360feddb1c1",
        },
        "io_bazel_rules_scala_scala_reflect": {
            "artifact": "org.scala-lang:scala-reflect:2.13.10",
            "deps": [
                "@io_bazel_rules_scala_scala_library",
            ],
            "sha256": "62bd7a7198193c5373a992c122990279e413af3307162472a5d3cbb8ecadb35e",
        },
    },
)

load("@io_bazel_rules_scala//scala:toolchains.bzl", "scala_register_toolchains")

scala_register_toolchains()

# ---

load("@io_bazel_rules_avro//avro:avro.bzl", "avro_repositories")

avro_repositories(version = "1.11.1")

# ---

load("//third_party/jsonschema:defs.bzl", "jsonschema_repositories")

jsonschema_repositories()

# ---

load("//third_party/confluent:defs.bzl", "CONFLUENT_ARTIFACTS", "confluent_repositories")

confluent_repositories()

# ---

load("@rules_jvm_external//:defs.bzl", "maven_install")
load("@rules_jvm_external//:specs.bzl", "maven")

maven_install(
    artifacts = [
        "com.google.code.findbugs:jsr305:3.0.2",
        "com.google.code.gson:gson:2.10.1",
        "com.google.errorprone:error_prone_annotations:2.23.0",
        "com.google.flogger:flogger-system-backend:0.8",
        "com.google.flogger:flogger:0.8",
        "com.google.guava:guava:32.1.3-jre",
        "com.google.j2objc:j2objc-annotations:2.8",
        "com.uber.nullaway:nullaway:0.10.18",
        "info.picocli:picocli:4.7.5",
        "jakarta.annotation:jakarta.annotation-api:2.1.1",
        "org.apache.avro:avro-compiler:1.11.3",
        "org.apache.avro:avro:1.11.3",
        "org.checkerframework:checker-qual:3.41.0",
        "org.slf4j:slf4j-api:2.0.9",
        "org.slf4j:slf4j-jdk14:2.0.9",
        maven.artifact(
            artifact = "avro-tools",
            exclusions = ["*:*"],
            group = "org.apache.avro",
            version = "1.11.3",
        ),
    ] + CONFLUENT_ARTIFACTS,
    fetch_sources = True,
    maven_install_json = "//:maven_install.json",
    override_targets = {
        "com.google.protobuf:protobuf-java": "@com_google_protobuf//:protobuf_java",
        "com.google.protobuf:protobuf-java-util": "@com_google_protobuf//:protobuf_java_util",
        "javax.annotation:javax.annotation-api": ":jakarta_annotation_jakarta_annotation_api",
        "javax.servlet:javax.servlet-api": ":jakarta_servlet_jakarta_servlet_api",
        "javax.validation:validation-api": ":jakarta_validation_jakarta_validation_api",
        "javax.ws.rs:javax.ws.rs-api": ":jakarta_ws_rs_jakarta_ws_rs_api",
    },
    repositories = [
        "https://repo1.maven.org/maven2",
        "https://repo.maven.apache.org/maven2",
        "https://maven-central-eu.storage-download.googleapis.com/maven2",
    ],
    strict_visibility = True,
)

# ---

load("@maven//:defs.bzl", "pinned_maven_install")

pinned_maven_install()

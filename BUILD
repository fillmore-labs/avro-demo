load("@com_github_bazelbuild_buildtools//buildifier:def.bzl", "buildifier")

alias(
    name = "blog",
    actual = "//src/main/java/com/fillmore_labs/avro_demo/blog",
)

alias(
    name = "avro_demo",
    actual = "//src/main/java/com/fillmore_labs/avro_demo",
)

alias(
    name = "confluent_demo",
    actual = "//src/main/java/com/fillmore_labs/avro_demo/confluent",
)

buildifier(
    name = "lint_fix",
    lint_mode = "fix",
    lint_warnings = ["all"],
    mode = "fix",
)

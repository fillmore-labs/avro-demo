load("@bazel_tools//tools/jdk:default_java_toolchain.bzl", "default_java_toolchain")
load("@rules_java//java:defs.bzl", "java_library", "java_package_configuration", "java_plugin")

default_java_toolchain(
    name = "toolchain_java17",
    configuration = dict(),
    java_runtime = "@bazel_tools//tools/jdk:remotejdk_17",
    package_configuration = [
        ":src_package_configuration",
        ":external_package_configuration",
    ],
    source_version = "17",
    target_version = "17",
    visibility = ["//visibility:public"],
    xlint = [
        "all",
        "-processing",
        "-classfile",
    ],
)

JAVACOPTS = [
    "-Xpkginfo:nonempty",
    "-XepExcludedPaths:.*/_javac/.*",
    "-XepDisableWarningsInGeneratedCode",
    "-XepAllDisabledChecksAsWarnings",
    "-Xep:Java7ApiChecker:OFF",
    "-Xep:AndroidJdkLibsChecker:OFF",
    "-Xep:StaticOrDefaultInterfaceMethod:OFF",
    "-Xep:NoFunctionalReturnType:OFF",
    "-Xep:WildcardImport:OFF",
    "-Xep:ReturnMissingNullable:OFF",
    "-XepOpt:NullAway:AnnotatedPackages=com.fillmore_labs",
    "-XepOpt:NullAway:CheckOptionalEmptiness=true",
    "-XepOpt:NullAway:HandleTestAssertionLibraries=true",
]

java_package_configuration(
    name = "src_package_configuration",
    javacopts = JAVACOPTS + ["-Werror"],
    packages = [":src_package_group"],
)

package_group(
    name = "src_package_group",
    packages = [
        "//src/...",
    ],
)

java_package_configuration(
    name = "external_package_configuration",
    javacopts = ["-Xlint:none"],
    packages = [":external_package_group"],
)

package_group(
    name = "external_package_group",
    packages = [
        "-//src/...",
        "//...",
    ],
)

# ---
# https://github.com/uber/NullAway/wiki

java_plugin(
    name = "nullaway_processor",
    deps = ["@maven//:com_uber_nullaway_nullaway"],
)

java_library(
    name = "nullaway",
    exported_plugins = ["nullaway_processor"],
    neverlink = True,
    visibility = ["//visibility:public"],
    exports = [
        "@maven//:com_google_errorprone_error_prone_annotations",
        "@maven//:com_uber_nullaway_nullaway",
        "@maven//:org_checkerframework_checker_qual",
    ],
)

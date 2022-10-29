apply {
    from("$rootDir/base-module.gradle")
}

dependencies {
    "implementation"(project(Modules.common))
    "implementation"(project(Modules.domain))
}
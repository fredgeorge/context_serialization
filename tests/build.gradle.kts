dependencies {
    implementation(project(":engine"))

    implementation(libs.jackson.kotlin)
    implementation(libs.jackson.databind)

    // Optional: only keep these if you actually need them directly
    implementation(libs.jackson.core)
    implementation(libs.jackson.annotations)
    implementation(libs.jackson.datatype)

    testImplementation(platform(libs.junit.bom))
    testImplementation(libs.junit.jupiter)
    testImplementation(libs.junit.params)
    
    // this overrides the old one Gradle sneaks in
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.test {
    useJUnitPlatform()
}

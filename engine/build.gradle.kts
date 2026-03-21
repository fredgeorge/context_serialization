dependencies {
    implementation(libs.jackson.kotlin)
    implementation(libs.jackson.databind)

    // Optional: only keep these if you actually need them directly
    implementation(libs.jackson.core)
    implementation(libs.jackson.annotations)

    // Define your public API here. Example:
    // implementation("org.jetbrains.kotlin:kotlin-stdlib")
}

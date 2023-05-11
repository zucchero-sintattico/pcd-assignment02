plugins {
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
    implementation("io.vertx:vertx-core:4.4.1")
    implementation("io.vertx:vertx-web:4.4.1")
    implementation("io.vertx:vertx-web-client:4.4.1")
    runtimeOnly("io.netty:netty-resolver-dns-native-macos:4.1.76.Final:osx-aarch_64")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}

// create task for run Main
tasks.register<JavaExec>("runMain") {
    group = "Verification"
    description = "Run the main class"
    mainClass.set("Main")
    classpath = sourceSets["main"].runtimeClasspath
}

tasks.withType<JavaCompile> {
    options.compilerArgs.add("--enable-preview")
}

tasks.withType<JavaExec> {
    jvmArgs = listOf("--enable-preview")
}

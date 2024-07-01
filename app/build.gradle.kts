plugins {
    id("java")
    id("com.github.johnrengelman.shadow") version "8.1.1"
    application
}

application {
    mainClass = "hexlet.code.App"
}

group = "hexlet.code"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.javalin:javalin-rendering:6.1.3")
    implementation("io.javalin:javalin:6.1.3")
    implementation("gg.jte:jte:3.1.12")
    implementation("org.slf4j:slf4j-simple:2.0.7")
    compileOnly ("org.projectlombok:lombok:1.18.32")
    implementation("com.h2database:h2:2.2.224")
    implementation("com.zaxxer:HikariCP:5.1.0")
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}
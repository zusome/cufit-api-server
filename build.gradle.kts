plugins {
	val kotlinPluginVersion = "1.9.25"

	kotlin("jvm") version kotlinPluginVersion
	kotlin("plugin.spring") version kotlinPluginVersion
	id("org.springframework.boot") version "3.4.0"
	id("io.spring.dependency-management") version "1.1.6"

	kotlin("plugin.allopen") version kotlinPluginVersion
	kotlin("plugin.noarg") version kotlinPluginVersion
}

allOpen {
	annotation("jakarta.persistence.Entity")
	annotation("jakarta.persistence.MappedSuperclass")
	annotation("jakarta.persistence.Embeddable")
}

noArg {
	annotation("jakarta.persistence.Entity")
	annotation("jakarta.persistence.Embeddable")
}



group = "com.official"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")

	// WebFlux for WebClient
	implementation("org.springframework.boot:spring-boot-starter-webflux")

	// JPA
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")

	// QueryDSL

	// swagger
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.6.0")

	// postgresql
	runtimeOnly("org.postgresql:postgresql")

	// h2
	implementation("com.h2database:h2") // H2 데이터베이스 의존성

	// JWT
	implementation("io.jsonwebtoken:jjwt-api:0.11.5")
	implementation("io.jsonwebtoken:jjwt-impl:0.11.5")
	implementation("io.jsonwebtoken:jjwt-jackson:0.11.5")
}

kotlin {
	compilerOptions {
		freeCompilerArgs.addAll("-Xjsr305=strict")
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

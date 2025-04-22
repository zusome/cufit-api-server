plugins {
	val kotlinPluginVersion = "1.9.25"

	kotlin("jvm") version kotlinPluginVersion
	kotlin("plugin.spring") version kotlinPluginVersion
	id("org.springframework.boot") version "3.4.0"
	id("io.spring.dependency-management") version "1.1.6"

	kotlin("plugin.allopen") version kotlinPluginVersion
	kotlin("plugin.noarg") version kotlinPluginVersion
}
val springModulithVersion by extra("1.3.1")

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
	implementation("org.springframework.modulith:spring-modulith-starter-core")
	implementation("org.springframework.modulith:spring-modulith-starter-jpa")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
	testImplementation("org.springframework.modulith:spring-modulith-starter-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
	implementation("com.google.firebase:firebase-admin:9.4.2")

	// Sentry
	implementation("io.sentry:sentry-spring-boot-starter-jakarta:7.14.0")
	implementation("io.sentry:sentry-logback:7.16.0")

	// WebFlux for WebClient
	implementation("org.springframework.boot:spring-boot-starter-webflux")

	// JPA
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")

	// QueryDSL

	// swagger
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.8.4")

	// postgresql
	runtimeOnly("org.postgresql:postgresql")

	// h2
	implementation("com.h2database:h2") // H2 데이터베이스 의존성

	// JWT
	implementation("io.jsonwebtoken:jjwt-api:0.11.5")
	implementation("io.jsonwebtoken:jjwt-impl:0.11.5")
	implementation("io.jsonwebtoken:jjwt-jackson:0.11.5")

	// coroutine
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.0")

	// coolsms
	implementation("net.nurigo:sdk:4.3.2")
}
dependencyManagement {
	imports {
		mavenBom("org.springframework.modulith:spring-modulith-bom:$springModulithVersion")
	}
}

kotlin {
	compilerOptions {
		freeCompilerArgs.addAll("-Xjsr305=strict")
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

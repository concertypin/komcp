plugins {
	id("org.gradle.toolchains.foojay-resolver-convention") version ("0.9.0")
}
dependencyResolutionManagement {
	@Suppress("UnstableApiUsage")
	repositories {
		mavenCentral()
	}
	versionCatalogs {
		create("libs") {
			from(files("libs.versions.toml"))
		}
	}
}
rootProject.name = "komcp"

import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
	libs.plugins.run {
		`version-catalog`
		alias(kotlin.multiplatform)
		alias(serialization)
	}
}
repositories {
	mavenCentral()
}
kotlin {
	jvm {
		compilerOptions {
			jvmTarget.set(JvmTarget.JVM_21)
		}
		
	}
	js(IR) {
		browser {
			binaries.executable()
			commonWebpackConfig {
				cssSupport { enabled = true }
			}
		}
	}
	sourceSets {
		val commonMain by getting {
			dependencies {
				implementation(libs.sealization.json)
			}
		}
		val jvmMain by getting {
			dependencies {
			
			}
		}
		val jsMain by getting {
			dependencies {
			
			}
		}
	}
}
tasks.register<Jar>("jvmFatJar") {
	group = "build"
	archiveClassifier.set("fat")
	manifest {
		attributes["Main-Class"] = "MainKt"
	}
	duplicatesStrategy = DuplicatesStrategy.EXCLUDE
	from(kotlin.targets.getByName("jvm").compilations["main"].output)
	
	dependsOn("jvmMainClasses")
	
	// Include dependencies
	from({
		configurations["jvmRuntimeClasspath"].filter { it.name.endsWith("jar") }.map { zipTree(it) }
	})
}



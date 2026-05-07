plugins {
	id("fabric-loom") version "1.1-SNAPSHOT"
	id("maven-publish")
}

version = project.property("mod_version") as String
group = project.property("maven_group") as String

base {
	archivesName.set(project.property("archives_base_name") as String)
}

java {
	sourceCompatibility = JavaVersion.VERSION_16
	targetCompatibility = JavaVersion.VERSION_16
	withSourcesJar()
}

repositories {
}

dependencies {
	minecraft("com.mojang:minecraft:1.16.5")
	mappings("net.fabricmc:yarn:1.16.5+build.10:v2")
	modImplementation("net.fabricmc:fabric-loader:0.11.6")

	modImplementation("net.fabricmc.fabric-api:fabric-api:0.38.2+1.16")
}

tasks.processResources {
	inputs.property("version", project.version)

	filesMatching("fabric.mod.json") {
		expand("version" to project.version)
	}
}

loom {
	accessWidenerPath.set(project.file("src/main/resources/keystrokes.accesswidener"))
}

tasks.withType<JavaCompile>().configureEach {
	options.apply {
		// ensure that the encoding is set to UTF-8, no matter what the system default is
		// this fixes some edge cases with special characters not displaying correctly
		// see http://yodaconditions.net/blog/fix-for-java-file-encoding-problems-with-gradle.html
		// If Javadoc is generated, this must be specified in that task too.
		encoding = "UTF-8"
		// Minecraft 1.17 (21w19a) upwards uses Java 16.
		release.set(16)
	}
}

tasks.jar {
	from("LICENSE") {
		rename { "${it}_${base.archivesName}"}
	}
}

java {
	// Loom will automatically attach sourcesJar to a RemapSourcesJar task and to the "build" task
	// if it is present.
	// If you remove this line, sources will not be generated.
	withSourcesJar()
}

// configure the maven publication
publishing {
	publications {
		create<MavenPublication>("mavenJava") {
			// add all the jars that should be included when publishing to maven
			artifact(tasks.remapJar) {
				builtBy(tasks.remapJar)
			}
			artifact(tasks.remapSourcesJar) {
				builtBy(tasks.remapSourcesJar)
			}
		}
	}

	// See https://docs.gradle.org/current/userguide/publishing_maven.html for information on how to set up publishing.
	repositories {
		// Add repositories to publish to here.
		// Notice: This block does NOT have the same function as the block in the top level.
		// The repositories here will be used for publishing your artifact, not for
		// retrieving dependencies.
	}
}

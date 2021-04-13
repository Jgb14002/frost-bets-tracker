buildscript {
	repositories {
		gradlePluginPortal()
	}
}

plugins {
	checkstyle
}

allprojects {
	group = "com.github.jgb14002"
	version = ProjectVersions.rlVersion
}

subprojects {
	group = "com.github.jgb14002.frostbets"

	project.extra["PluginProvider"] = "LoudPacks"
	project.extra["PluginLicense"] = "3-Clause BSD License"

	repositories {
		jcenter {
			content {
				excludeGroupByRegex("com\\.openosrs.*")
				excludeGroupByRegex("com\\.runelite.*")
			}
		}

		exclusiveContent {
			forRepository {
				maven {
					url = uri("https://repo.runelite.net")
				}
			}
			filter {
				includeModule("net.runelite", "discord")
			}
		}

		exclusiveContent {
			forRepository {
				maven {
					url = uri("https://raw.githubusercontent.com/open-osrs/hosting/master")
				}
			}
			filter {
				includeModule("com.openosrs.rxrelay3", "rxrelay")
			}
		}

		exclusiveContent {
			forRepository {
				maven {
					url = uri("https://raw.githubusercontent.com/open-osrs/hosting/master")
				}
				mavenLocal()
			}
			filter {
				includeGroupByRegex("com\\.openosrs.*")
			}
		}
	}

	apply<JavaPlugin>()
	apply(plugin = "checkstyle")

	checkstyle {
		maxWarnings = 0
		toolVersion = "8.25"
		isShowViolations = true
		isIgnoreFailures = false
	}

	configure<JavaPluginConvention> {
		sourceCompatibility = JavaVersion.VERSION_11
		targetCompatibility = JavaVersion.VERSION_11
	}

	tasks {
		withType<JavaCompile> {
			options.encoding = "UTF-8"
		}

		withType<Jar> {
			doLast {
				copy {
					from("./build/libs/")
					into("../release/")
				}
				copy {
					from("./build/libs/")
					into("C:\\Users\\jbass\\.openosrs\\plugins")
				}
			}
		}

		withType<AbstractArchiveTask> {
			isPreserveFileTimestamps = false
			isReproducibleFileOrder = true
			dirMode = 493
			fileMode = 420
		}

		withType<Checkstyle> {
			group = "Verification"
		}

		register<Copy>("copyDeps") {
			into("./build/deps/")
			from(configurations["runtimeClasspath"])
		}
	}
}
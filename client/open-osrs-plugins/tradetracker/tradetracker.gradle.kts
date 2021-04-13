import ProjectVersions.rlVersion

version = "1.0.0"

project.extra["PluginName"] = "Trade Tracker"
project.extra["PluginDescription"] = "Logs host trades with bettors to automate Guilded ticket distribution"

dependencies {
	annotationProcessor(Libraries.lombok)
	annotationProcessor(Libraries.pf4j)

	compileOnly("com.openosrs:runelite-api:$rlVersion")
	compileOnly("com.openosrs:runelite-client:$rlVersion")
	compileOnly("com.openosrs:http-api:$rlVersion")

	compileOnly(Libraries.guice)
	compileOnly(Libraries.lombok)
	compileOnly(Libraries.pf4j)
}

tasks {
	jar {
		manifest {
			attributes(mapOf(
				"Plugin-Version" to project.version,
				"Plugin-Id" to nameToId(project.extra["PluginName"] as String),
				"Plugin-Provider" to project.extra["PluginProvider"],
				"Plugin-Description" to project.extra["PluginDescription"],
				"Plugin-License" to project.extra["PluginLicense"]
			))
		}
	}
}
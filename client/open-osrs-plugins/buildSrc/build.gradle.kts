plugins {
	`kotlin-dsl`
}

repositories {
	jcenter()
}

dependencies {
	implementation(gradleApi())
}

kotlinDslPluginOptions {
	experimentalWarning.set(false)
}
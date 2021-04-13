object ProjectVersions {
	const val rlVersion = "4.4.0"
	const val apiVersion = "^1.0.0"
	const val kotlinVersion = "1.3.72"
}

object Libraries {
	private object Versions {
		const val annotations = "20.1.0"
		const val apacheCommonsText = "1.9"
		const val gson = "2.8.6"
		const val guice = "4.2.3"
		const val junit = "4.13.1"
		const val lombok = "1.18.16"
		const val mockito = "3.6.0"
		const val okhttp3 = "4.9.0"
		const val pf4j = "3.5.0"
		const val rxjava = "3.0.7"
		const val slf4j = "1.7.30"
	}

	const val annotations = "org.jetbrains:annotations:${Versions.annotations}"
	const val apacheCommonsText = "org.apache.commons:commons-text:${Versions.apacheCommonsText}"
	const val gson = "com.google.code.gson:gson:${Versions.gson}"
	const val guice = "com.google.inject:guice:${Versions.guice}:no_aop"
	const val guiceTestlib = "com.google.inject.extensions:guice-testlib:${Versions.guice}"
	const val junit = "junit:junit:${Versions.junit}"
	const val lombok = "org.projectlombok:lombok:${Versions.lombok}"
	const val mockitoCore = "org.mockito:mockito-core:${Versions.mockito}"
	const val mockitoInline = "org.mockito:mockito-inline:${Versions.mockito}"
	const val okhttp3 = "com.squareup.okhttp3:okhttp:${Versions.okhttp3}"
	const val pf4j = "org.pf4j:pf4j:${Versions.pf4j}"
	const val rxjava = "io.reactivex.rxjava3:rxjava:${Versions.rxjava}"
	const val slf4jApi = "org.slf4j:slf4j-api:${Versions.slf4j}"
}
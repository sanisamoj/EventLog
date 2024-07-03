import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.compose.compiler)
}

kotlin {
    jvm("desktop")

    sourceSets {
        val desktopMain by getting
        
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.material3)
            implementation(libs.lifecycle.viewmodel.compose)
            runtimeOnly(libs.coroutines.swing)
            implementation(libs.navigation.compose)
            implementation(libs.retrofit)
            implementation(libs.converter.gson)
            implementation(libs.multiplatform.settings)
            implementation(libs.datastore.preferences)
            implementation(libs.serialization.json)

        }
        desktopMain.dependencies {
            implementation(compose.desktop.currentOs)
        }
    }
}


compose.desktop {
    application {
        mainClass = "MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "org.sanisamoj.eventlogger"
            packageVersion = "1.0.0"
        }
    }
}

compose.resources {
    publicResClass = true
    packageOfResClass = "sanisamoj.eventlogger.library.resources"
    generateResClass = always
}


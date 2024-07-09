plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.fabric.loom)
}

val modId = "nochangethegame"
group = "com.cecer1.projects.mc.nochangethegame"
version = "1.1.0-rc3"

repositories {
    mavenCentral()
    maven("https://maven.shedaniel.me/")
    maven("https://maven.terraformersmc.com/releases/")
}

dependencies {
    minecraft(libs.minecraft)
    mappings(loom.officialMojangMappings())
    modImplementation(libs.fabric.loader)
    modImplementation(libs.fabric.kotlin)

    modImplementation(libs.fabric.api)
    modApi(libs.clothconfig) {
        exclude(group = libs.fabric.api.get().group)
    }
    modApi(libs.modmenu)
}

java {
    toolchain.languageVersion = JavaLanguageVersion.of(libs.versions.java.get())
    withSourcesJar()
}
kotlin {
    jvmToolchain(libs.versions.java.get().toInt())
}

tasks {
    processResources {
        inputs.property("version", version)
        filesMatching("fabric.mod.json") {
            expand(
                "mod_id" to modId,
                "mod_package" to group,
                "version" to version,
                "fabric_loader_version" to libs.versions.fabric.loader.get(),
                "minecraft_version" to libs.versions.minecraft.get(),
                "fabric_api_version" to libs.versions.fabric.api.get(),
                "kotlin_adapter_version" to libs.versions.fabric.kotlin.get(),
                "cloth_config_version" to libs.versions.clothconfig.get(),
                "mod_menu_version" to libs.versions.modmenu.get(),
            )
        }
    }
    jar {
        from("LICENSE") {
            rename { "${it}_$modId"}
        }
    }
}
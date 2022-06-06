@file:Suppress("UnstableApiUsage")

pluginManagement {
  includeBuild("build_logic")
  repositories {
    maven { url = uri("https://maven.aliyun.com/repository/public") }
    maven { url = uri("https://maven.aliyun.com/repository/google") }
    maven { url = uri("https://jitpack.io") }
    gradlePluginPortal()
    google()
    mavenCentral()
  }
}
dependencyResolutionManagement {
  repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
  repositories {
    maven { url = uri("$rootDir/build/maven") }
    maven { url = uri("https://maven.aliyun.com/repository/public") }
    maven { url = uri("https://maven.aliyun.com/repository/google") }
    maven { url = uri("https://jitpack.io") }
    google()
    mavenCentral()
  }
}
rootProject.name = "WanAndroid_Multi"

// 测试使用，排除掉不需要的模块
val excludeList = listOf<String>(
)

//对文件夹进行遍历，深度为2
rootDir.walk()
  .maxDepth(2)
  .asSequence()
  .filter {
    //过滤掉干扰文件夹
    val isDirectory = it.isDirectory
    val isSubModule = file("$it/build.gradle").exists() || file("$it/build.gradle.kts").exists()
    val isIndependentProject = file("$it/settings.gradle").exists() || file("$it/settings.gradle.kts").exists()
    isDirectory && isSubModule && !isIndependentProject
  }
  .filter {
    //对module进行过滤
    "(api_.+)|(module_.+)|(lib_.+)".toRegex().matches(it.name) && it.name !in excludeList
  }
  .map {
    //将file映射到相对路径
    val parentFile = it.parentFile
    if (parentFile.path == rootDir.path) {
      ":${it.name}"
    } else {
      ":${parentFile.name}:${it.name}"
    }
  }
  .forEach {
    //进行include
    include(it)
  }
/**
 * 每次新建模块会自动添加 include()，请删除掉，因为上面会自动读取
 */
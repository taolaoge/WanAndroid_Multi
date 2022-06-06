import com.ndhzs.build.logic.project.*

/**
 * 不同模块分配不同的插件
 */
val projectName: String = project.name
when {
  projectName == "module_app" -> AppProject(project).apply()
  projectName == "lib_common" -> LibCommonProject(project).apply()
  projectName.startsWith("module_") -> ModuleProject(project).apply()
  projectName.startsWith("lib_") -> LibProject(project).apply()
  projectName.startsWith("api_") -> ApiProject(project).apply()
  else -> throw Exception("出现未知类型模块: name = $projectName   dir = $projectDir\n请为该模块声明对应的依赖插件！")
}

plugins {
  id("com.ndhzs.build.logic.publish.publications")
}


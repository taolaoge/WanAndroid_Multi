import com.ndhzs.build.logic.depend.*
import com.ndhzs.build.logic.depend.api.dependApiMain

plugins {
    id("module-manager")
//    id("module-debug")
    id("org.jetbrains.kotlin.android")
}

dependAndroidView()
dependAndroidKtx()
dependGlide()
dependRxjava()
dependNetwork()

dependencies {
implementation("androidx.appcompat:appcompat:1.3.0")
    implementation("com.google.android.material:material:1.4.0")
    implementation("androidx.constraintlayout:constraintlayout:2.0.4")
    // 这里面写该只有自己模块才会用到的依赖
}
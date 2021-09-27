# remote-debug-like-local

## 开发前准备

### 1. 通过 github 插件模板创建工程

github [idea插件模板](https://github.com/JetBrains/intellij-platform-plugin-template)

### 2. 添加 java 依赖

修改 gradle.properties

```properties
# Example: platformPlugins = com.intellij.java, com.jetbrains.php:203.4449.22
platformPlugins = com.intellij.java

# Java language level used to compile sources and to generate the files for - Java 11 is required since 2020.3
javaVersion = 11
```

### 3. 修改 java 字符集

修改 build.gradle.kts

```kotlin
tasks {
    // Set the JVM compatibility versions
    properties("javaVersion").let {
        withType<JavaCompile> {
            sourceCompatibility = it
            targetCompatibility = it
            options.encoding = "UTF-8"
        }
    }
}
```
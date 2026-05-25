# CLAUDE.md — demo-skeleton

DDD 项目骨架，基于 Spring Boot 3.2.0 + Java 21 + Gradle 8.5。

## 环境要求

```bash
# Java 21 (zulu-21.jdk) — 系统默认是 Java 8，必须显式设置
export JAVA_HOME=/Library/Java/JavaVirtualMachines/zulu-21.jdk/Contents/Home

# Gradle 8.5 (手动安装路径)
export GRADLE_HOME=/Users/user/gradle-8.5
export PATH=$GRADLE_HOME/bin:$PATH

# 建议别名
alias gradle='JAVA_HOME=/Library/Java/JavaVirtualMachines/zulu-21.jdk/Contents/Home /Users/user/gradle-8.5/bin/gradle --no-daemon'
```

## 常用命令

所有命令需使用 Java 21 + `--no-daemon`（避免守护进程缓存 Java 8 的 JVM）：

| 命令 | 说明 |
|------|------|
| `gradle build` | 完整构建（编译 + checkstyle + 测试 + JaCoCo 覆盖率检查） |
| `gradle check` | 检查生命周期（checkstyle + 测试 + JaCoCo） |
| `gradle test` | 运行所有测试 |
| `gradle :domain.core:test` | 运行指定模块测试 |
| `gradle :domain.core:checkstyleMain` | 运行指定模块 checkstyle（仅 main） |
| `gradle :domain.core:check` | 运行指定模块完整检查 |
| `gradle clean` | 清理构建产物 |

### 组合命令示例

```bash
# 全部模块 checkstyle（main + test）
gradle :domain.core:checkstyleMain :domain.core:checkstyleTest :application.core:checkstyleMain :application.core:checkstyleTest :context.core:checkstyleMain :context.core:checkstyleTest

# 全部模块测试
gradle :domain.core:test :application.core:test :context.core:test

# 全部模块完整检查
gradle :domain.core:check :application.core:check :context.core:check
```

## 项目结构

```
demo-skeleton/
├── frameworks/          # 框架层模块
│   ├── domain.core/     # 共享领域基础
│   ├── application.core/ # 共享应用基础
│   └── context.core/    # 请求上下文
├── modules/             # 业务模块（待创建）
├── gradle/config/       # Gradle 配置
│   ├── checkstyle/      # Checkstyle 规则
│   └── scripts/         # 构建脚本
├── scripts/             # 开发辅助脚本
├── build.gradle         # 根构建文件
├── settings.gradle      # 模块注册（自动发现 frameworks/ 和 modules/）
└── gradle.properties    # 依赖版本管理
```

## 技术栈

- **Java 21** (sourceCompatibility = 21)
- **Spring Boot 3.2.0** (Spring 6.1.1)
- **Gradle 8.5**
- **JUnit 5.10.1** + AssertJ 3.25.0 + Mockito 5.8.0
- **JaCoCo 0.8.11** (100% LINE 覆盖率要求)
- **Checkstyle 8.36.1** (仅检查 main 源码，test 默认跳过)
- **Lombok 1.18.30**

## 测试要求

- 100% LINE 覆盖率（JaCoCo 强制执行）
- JUnit 5 + AssertJ 流式断言
- 测试方法名使用 camelCase（不允许下划线）

## 模块命名

settings.gradle 自动注册 `frameworks/` 和 `modules/` 下的目录。模块名扁平化：
- `frameworks/domain.core` → `:domain.core`
- `frameworks/application.core` → `:application.core`
- `modules/ca.domain` → `:ca.domain`
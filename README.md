# Keystone 基础脚手架 / Keystone Foundation Scaffold

[![License](https://img.shields.io/badge/license-MIT-blue.svg)](LICENSE)
[![JDK](https://img.shields.io/badge/JDK-21-green.svg)](https://openjdk.org/projects/jdk/21/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.0.3-brightgreen.svg)](https://spring.io/projects/spring-boot)

[中文版 (Chinese)](#中文版) | [English Version](#english-version)

---

## 中文版

> 基于 Spring Boot 4.0 + Maven 4.0 + Java 21 的模块化单体脚手架，旨在为企业级应用提供轻量、高效、现代化的初始开发模板。

### ✨ 特性

- 🚀 **前沿框架**：Spring Boot 4.0.3 + Spring Framework 7.0 + Jakarta EE 11
- 🔒 **安全可靠**：Spring Security 7.0 + JWT 无状态认证体系
- 📊 **高效数据**：MyBatis-Flex 高性能 ORM 框架，简化 CRUD 操作
- 🤖 **AI 赋能（规划中）**：Spring AI 集成（计划深度结合 Spring AI Alibaba 与 PostgreSQL + pgvector 提供 RAG 检索增强生成能力）
- 🏢 **架构扩展（规划中）**：多租户 (Multi-tenant) 数据隔离支持
- 🐳 **云原生（规划中）**：Docker 容器化支持与 CI/CD 部署脚本

### 🛠️ 环境要求

- **JDK**: 21 或更高版本
- **Maven**: 4.0 或更高版本
- **数据库**: MySQL 8.0+ / PostgreSQL 15+ (配合 pgvector 扩展)

### 📦 快速开始

#### 1. 克隆项目
```bash
git clone [https://github.com/yourusername/keystone.git](https://github.com/yourusername/keystone.git)
cd keystone
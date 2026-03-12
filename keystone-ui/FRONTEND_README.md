# 前端项目运行指南 (Keystone UI)

本项目（`keystone-ui`）是一个基于 **Vue 3 + Vite + TypeScript** 和 **Vben Admin** 的 Monorepo 架构中后台前端工程，使用 **pnpm** 作为包管理工具，并使用 **Turbo** 管理工作区任务。

## 环境要求

- **Node.js**: >= 20.19.0
- **pnpm**: >= 10.0.0

在使用本项目前，请确保您已经在本地安装了正确版本的 Node.js 以及 pnpm 工具。如果尚未安装，可以通过以下方式安装 pnpm（如果 Node 版本已就绪）：
```bash
npm install -g pnpm@10.30.3
```

## 1. 依赖安装

在 `keystone-ui` 根目录下，执行以下命令安装项目所需的全部信赖包：

```bash
pnpm install
```

> **注意：** 项目启用了 `only-allow pnpm`，请务必使用 pnpm 进行安装，使用 npm/yarn 会被拦截。如果依赖安装失败或存在缓存问题，可运行 `pnpm reinstall` 彻底清理并重新安装。

## 2. 启动开发服务器 (本地运行)

项目内置了多个微应用（例如基于 Ant Design Vue 的 `web-antd` 等）。要启动默认的所有服务或指定子应用服务：

**推荐：启动 Ant Design 的主后台版本**
```bash
pnpm dev:antd
```
*这将会单独运行 `@vben/web-antd` 这个包，是日常开发最常用的启动命令。*

**其他启动方式**
- `pnpm dev`：通过 Turbo 运行所有的子包的 dev 脚本（同时启动多个终端 UI）。
- `pnpm dev:ele`：启动基于 Element Plus 的版本。
- `pnpm dev:naive`：启动基于 Naive UI 的版本。

启动成功后，控制台会输出本地访问 URL（类似 `http://localhost:5555`）。

## 3. 项目打包与构建 (生产环境)

准备进行环境部署时，通过以下命令将代码打包为用于生产环境的静态资源文件：

**推荐：构建 Ant Design 版本**
```bash
pnpm build:antd
```
打包成功后，产物会输出到 `apps/web-antd/dist/`（具体取决于 vite 配置），您可以将该目录下的静态文件部署到 Nginx 或其他静态服务器上。

**其他构建命令**
- `pnpm build`：使用 Turbo 并行构建所有应用（需大内存 `NODE_OPTIONS=--max-old-space-size=8192`）。
- `pnpm build:ele` / `pnpm build:naive`：分别构建独立的微应用版本。

## 4. 常见问题排查与清理操作

如果开发运行中因为缓存或其他原因产生未知报错，或需要执行代码检查，可以使用以下的命令：

- **深度清理项目：** 
  ```bash
  pnpm clean
  ```
  此命令会清除掉各类构建缓存（Node_modules 除外）。
- **完全重装：**
  ```bash
  pnpm reinstall
  ```
  将会删除锁文件并进行重新覆盖。

- **格式化代码：**
  ```bash
  pnpm format
  ```
- **检查及语法修复：**
  ```bash
  pnpm lint
  ```

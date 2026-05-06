# SimpleDAO 管理系统底座 · 后端

基于 Spring Boot + SimpleDAO 的后端项目骨架，集成 RBAC 权限体系，开箱即用。

## 技术栈 & 环境要求

| 技术 | 版本 |
|------|------|
| JDK | 21LTS |
| Spring Boot | 3.3.x |
| Spring JDBC | 6.1.x |
| SimpleDAO | 1.2.1 |
| MySQL | 8.0+ |
| Redis | 6.2+ |
| Lombok | 1.18.x |
| JWT | 4.4.x |
| Maven | 3.9+ |  

## 优点特色

- **极简SQL开发**：基于SimpleDAO对Spring JDBC的适度封装，基础CRUD无需编写SQL；复杂SQL拼接简洁，无需嵌套if语句或XML标签
- **启动性能极致**：启动仅需3秒、重启1秒，调试效率极高，拒绝臃肿
- **代码极易维护**：结构清晰，改动、扩展成本极低
- **全栈代码生成**：集成配套代码生成器，前后端代码一键生成，大幅降低重复开发工作量
- **审计字段自动填充**：插入时自动填充create_time、create_by，更新时自动填充update_time、update_by
- **软删除自动化**：自动处理dr删除标记字段，无需手动编写软删除逻辑
- **调试体验极佳**：控制台直接打印带参数值的完整SQL，问题定位更高效

## 快速启动

### 1. 创建数据库

登录 MySQL，执行以下命令：

```sql
CREATE DATABASE IF NOT EXISTS simple_admin DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
```

### 2. 导入数据库脚本

```bash
mysql -u root -p simple_admin < ../init-schema.sql
```

### 3. 修改配置

编辑 `src/main/resources/application.yml`，修改数据库和Redis连接信息：

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/simple_admin?useUnicode=true&characterEncoding=utf-8&serverTimezone=Asia/Shanghai
    username: root
    password: 你的密码
  redis:
    host: localhost
    port: 6379
    password: 你的Redis密码（如果没有密码，留空即可）
```

### 4. 启动项目

```bash
mvn spring-boot:run
```

启动后访问 `http://localhost:8888`，看到启动成功日志即表示运行正常。


## 内置功能

| 模块 | 功能 |
|------|------|
| 用户管理 | 用户信息维护、状态管理、密码重置、角色与部门分配 |
| 角色管理 | 基于 RBAC 的功能授权、数据授权、用户关联 |
| 菜单管理 | 树结构+列表双重展示，支持动态路由与按钮权限 |
| 部门管理 | 树结构+列表形式，满足组织架构的层级管理需求 |
| 字典管理 | 统一管理系统通用字典数据，支持动态配置 |
| 操作日志 | AOP 自动记录所有操作的请求参数与返回结果 |
| 接口监管 | 统计每个接口请求时长，精准定位性能瓶颈 |
| 接口安全 | 基于 JWT 的接口鉴权机制，保障访问安全 |

## 开发工具

推荐使用 IntelliJ IDEA 进行后端开发。

## 常见问题

**Q：启动报错“Access denied for user”？**
A：检查 `application.yml` 中的数据库用户名和密码是否正确。

**Q：启动报错“Unknown database”？**
A：请先执行步骤1创建数据库，再执行步骤2导入脚本。

**Q：想修改逻辑删除的标记字段？**
A：在 `application.yml` 中配置 `simple-dao.logic-delete.field` 即可，默认为 `dr`。

**Q：Redis 连接失败？**
A：检查 Redis 服务是否已启动，以及 `application.yml` 中的 Redis 连接配置是否正确。

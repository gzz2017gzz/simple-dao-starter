# SimpleDAO Management System Foundation · Front-end

A back-office administration front-end skeleton built with Vue 2 + Element UI, seamlessly integrated with the SimpleDAO back-end.

## Tech Stack & Environment Requirements

| Technology | Version |
|------------|---------|
| Vue        | 2.7.x   |
| Element UI | 2.15.x  |
| Axios      | 1.7.x   |
| Moment     | 2.30.x  |
| Webpack    | 5.x     |
| Node.js    | 18.x LTS |
| npm        | 8.x     |

## Quick Start

### 1. Install Dependencies

```bash
npm install --registry=https://registry.npmmirror.com
```

### 2. Configure Back-end Endpoint

Edit the `vue.config.js` file:

```js
target: 'http://localhost:8888/' 
```

### 3. Start the Development Server

```bash
npm run serve
```

After startup, open `http://localhost:9000` and log in with the default credentials:

| Account | Password |
|---------|----------|
| admin   | 123456   |

## Common Components

| Component | Purpose |
|-----------|---------|
| v-table   | A unified table component (borders, size, loading state, row styles) |
| v-select  | Dictionary dropdown (pass a `dictKey` to auto-render options) |
| v-page    | Unified pagination bar (page size options, page navigation) |
| v-footer  | Unified footer buttons for dialogs (confirm, cancel) |

## Mixins and Utility Functions

| File   | Purpose |
|--------|---------|
| page.js | Common logic for paginated list pages (pagination, search, reset, row selection, file download) |
| tree.js | Common logic for tree pages (tree loading, node operations) |
| dicts.js | Dictionary data reading and formatted translation |
| http.js  | Axios request interceptor (token injection, expiration handling, unified error handling) |

## Built-in Pages

| Page                | Functionality |
|---------------------|---------------|
| User Management     | User list, add/edit, role assignment, status management |
| Role Management     | Role list, menu permission assignment, user association |
| Menu Management     | Dual tree + list display, CRUD |
| Department Management | Tree structure, hierarchical management |
| Dictionary Management | Maintenance of dictionary types and dictionary entries |
| Operation Logs      | Viewing of key operation records |
| API Monitoring      | Statistics on API request durations |

## Development Tools

VSCode or WebStorm is recommended for front-end development.

## FAQ

**Q: "Network error" on login?**  
A: Check whether the back-end is running and whether the endpoint address in `.env.development` is correct.

**Q: Dictionary dropdown has no data?**  
A: Confirm that the corresponding dictionary type and entries have been configured in the back-end's dictionary management module.

**Q: Blank page or compilation errors?**  
A: Run `npm install` to reinstall dependencies, and ensure the Node.js version meets 18.x LTS.

**Q: How to change the front-end port?**  
A: Modify the `devServer.port` configuration item in `vue.config.js`.

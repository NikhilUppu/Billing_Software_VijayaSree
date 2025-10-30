VijayaSree Web App (Frontend)

- React + TypeScript + Vite
- Talks to backend via /api proxy (configured to http://localhost:8081)

Run locally
```bash
cd frontend/web-app
npm install
npm run dev
# open http://localhost:5173
```

Usage
- Enter backend credentials (defaults admin/admin123)
- Products tab: refresh list, add a simple product
- Users tab: list and add a user (ADMIN creds required)

Config
- Proxy target is set in vite.config.ts (change if backend runs on a different port)

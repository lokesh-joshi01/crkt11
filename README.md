# CRKT11 — IPL Fantasy Cricket App

A full-stack IPL fantasy cricket application built with **Spring Boot** (Java 17) and **React + TypeScript**. Features live match scores, player comparison, team builder with credit/squad-rule validation, and a real-time leaderboard.

---

## Tech Stack

| Layer | Technology |
|-------|-----------|
| Backend | Java 17, Spring Boot 3.2, Spring Security + JWT, Spring Data JPA |
| Frontend | React 18, TypeScript, Vite, TanStack Query, Zustand, Recharts |
| Database | H2 (dev) · PostgreSQL (prod) |
| DevOps | Docker, Docker Compose, GitHub Actions CI/CD |

---

## Project Structure

```
crkt11/
├── backend/                        # Spring Boot API
│   └── src/main/java/com/crkt11/
│       ├── controller/             # REST endpoints
│       ├── service/                # Business logic + points engine
│       ├── model/                  # JPA entities
│       ├── dto/                    # Request/response shapes
│       ├── repository/             # Spring Data repos
│       ├── config/                 # Security, CORS, data seeder
│       └── exception/              # Global error handling
├── frontend/                       # React + TypeScript SPA
│   └── src/
│       ├── components/             # UI components by feature
│       ├── hooks/                  # TanStack Query hooks
│       ├── services/               # Axios API layer
│       ├── store/                  # Zustand state (team builder)
│       └── types/                  # Shared TypeScript types
├── docker/
│   └── docker-compose.yml          # Full stack (backend + frontend + PostgreSQL)
└── .github/workflows/ci.yml        # CI: test → build → Docker push
```

---

## Features

- **Live match scores** — polling every 15s with over-by-over ball tracking and scorecard
- **Team builder** — 11-player selection with IPL squad rules enforced in both frontend (Zustand) and backend (Spring service)
  - Max 100 credits
  - Max 7 players from one team
  - Role quotas: 1–4 WK, 3–6 BAT, 3–6 BOWL, 1–4 AR
  - Captain (2×) and Vice-captain (1.5×) multipliers
- **Fantasy points engine** — full IPL scoring: runs, boundaries, milestones, SR bonus/penalty, wickets, economy, fielding
- **Player comparison** — side-by-side stat breakdown for any two players
- **Leaderboard** — live rankings with rank-change indicators

---

## Getting Started

### Prerequisites

- Java 17+
- Node 20+
- Maven 3.9+
- Docker + Docker Compose (optional, for full-stack run)

---

### Option A — Run with Docker (recommended)

```bash
# Clone the repo
git clone https://github.com/<your-username>/crkt11.git
cd crkt11

# Start everything (backend + frontend + PostgreSQL)
docker compose -f docker/docker-compose.yml up --build
```

| Service | URL |
|---------|-----|
| Frontend | http://localhost:3000 |
| Backend API | http://localhost:8080/api |
| H2 Console (dev) | http://localhost:8080/h2-console |

---

### Option B — Run locally

**Backend**

```bash
cd backend
mvn spring-boot:run
# API available at http://localhost:8080
# H2 console at http://localhost:8080/h2-console
```

**Frontend**

```bash
cd frontend
npm install
npm run dev
# App available at http://localhost:3000
```

The Vite dev server proxies `/api` requests to `localhost:8080` automatically.

---

## API Reference

### Players

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/players` | All players (filter by `?role=BAT` or `?team=MI`) |
| GET | `/api/players/{id}` | Single player |
| GET | `/api/players/top` | Top 20 by average fantasy points |
| GET | `/api/players/compare?playerAId=1&playerBId=2` | Head-to-head comparison |

### Matches

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/matches/live` | Current live match |
| GET | `/api/matches/upcoming` | Scheduled matches |
| GET | `/api/matches/recent` | Completed matches |
| GET | `/api/matches/{id}` | Match details + scorecard |

### Fantasy Teams (authenticated)

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/teams` | Create a team |
| GET | `/api/teams/my` | My teams |
| GET | `/api/teams/leaderboard` | Full leaderboard |

---

## Fantasy Points Scoring

| Action | Points |
|--------|--------|
| Playing XI | +4 |
| Run scored | +1 |
| Four | +1 |
| Six | +2 |
| Half-century | +8 |
| Century | +16 |
| Duck (batter/AR/WK) | −2 |
| SR > 170 (min 10b) | +6 |
| SR 150–170 | +4 |
| SR 130–150 | +2 |
| SR < 50 | −6 |
| Wicket | +25 |
| 4-wicket haul | +4 |
| 5-wicket haul | +8 |
| Economy < 5 (min 2 ov) | +6 |
| Economy > 12 | −6 |
| Catch | +8 |
| 3+ catches | +4 bonus |
| Stumping | +12 |
| Captain | 2× multiplier |
| Vice-captain | 1.5× multiplier |

---

## Running Tests

```bash
# Backend
cd backend && mvn test

# Frontend
cd frontend && npm run lint && npm run build
```

---

## CI/CD Pipeline

On every push to `main`:
1. **Backend tests** — Maven `mvn test`
2. **Frontend lint + build** — `npm run lint && npm run build`
3. **Docker push** — builds and pushes images to Docker Hub (requires `DOCKER_USERNAME` and `DOCKER_PASSWORD` secrets in repo settings)

Pull requests to `main` run steps 1–2 only (no push).

---

## GitHub Setup

```bash
git init
git add .
git commit -m "feat: initial CRKT11 IPL fantasy app"
git remote add origin https://github.com/<your-username>/crkt11.git
git push -u origin main
```

**Required GitHub Secrets** (for Docker push on main):

| Secret | Value |
|--------|-------|
| `DOCKER_USERNAME` | Your Docker Hub username |
| `DOCKER_PASSWORD` | Docker Hub access token |
| `JWT_SECRET` | Random 64+ char string for production |

---

## Roadmap

- [ ] WebSocket live score streaming (replace polling)
- [ ] User authentication (JWT register/login endpoints)
- [ ] Real cricket data via a live API (Cricbuzz, CricAPI)
- [ ] Transfers and team editing after lock-in
- [ ] Push notifications for wickets and milestones
- [ ] Mobile app (React Native)

---

## License

MIT

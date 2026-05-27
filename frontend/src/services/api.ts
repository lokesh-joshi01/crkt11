import axios from 'axios'
import type { Player, Match, FantasyTeam, FantasyTeamRequest, LeaderboardEntry } from '../types'

const api = axios.create({
  baseURL: '/api',
  headers: { 'Content-Type': 'application/json' },
})

// Attach JWT token if present
api.interceptors.request.use((config) => {
  const token = localStorage.getItem('token')
  if (token) config.headers.Authorization = `Bearer ${token}`
  return config
})

// Players
export const playerApi = {
  getAll: (params?: { role?: string; team?: string }) =>
    api.get<Player[]>('/players', { params }).then((r) => r.data),

  getById: (id: number) =>
    api.get<Player>(`/players/${id}`).then((r) => r.data),

  getTop: () =>
    api.get<Player[]>('/players/top').then((r) => r.data),

  compare: (playerAId: number, playerBId: number) =>
    api.get<Player[]>('/players/compare', { params: { playerAId, playerBId } }).then((r) => r.data),
}

// Matches
export const matchApi = {
  getLive: () =>
    api.get<Match>('/matches/live').then((r) => r.data),

  getById: (id: number) =>
    api.get<Match>(`/matches/${id}`).then((r) => r.data),

  getUpcoming: () =>
    api.get<Match[]>('/matches/upcoming').then((r) => r.data),

  getRecent: () =>
    api.get<Match[]>('/matches/recent').then((r) => r.data),
}

// Fantasy teams
export const teamApi = {
  create: (request: FantasyTeamRequest) =>
    api.post<FantasyTeam>('/teams', request).then((r) => r.data),

  getMyTeams: () =>
    api.get<FantasyTeam[]>('/teams/my').then((r) => r.data),

  getLeaderboard: () =>
    api.get<LeaderboardEntry[]>('/teams/leaderboard').then((r) => r.data),
}

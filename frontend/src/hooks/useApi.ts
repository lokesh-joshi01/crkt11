import { useQuery } from '@tanstack/react-query'
import { playerApi, matchApi, teamApi } from '../services/api'
import type { PlayerRole } from '../types'

export function useLiveMatch() {
  return useQuery({
    queryKey: ['match', 'live'],
    queryFn: matchApi.getLive,
    refetchInterval: 15_000, // poll every 15s for live updates
    retry: false,
  })
}

export function usePlayers(role?: PlayerRole, team?: string) {
  return useQuery({
    queryKey: ['players', role, team],
    queryFn: () => playerApi.getAll({ role, team }),
  })
}

export function usePlayer(id: number) {
  return useQuery({
    queryKey: ['player', id],
    queryFn: () => playerApi.getById(id),
    enabled: !!id,
  })
}

export function useComparePlayers(playerAId: number, playerBId: number) {
  return useQuery({
    queryKey: ['compare', playerAId, playerBId],
    queryFn: () => playerApi.compare(playerAId, playerBId),
    enabled: !!playerAId && !!playerBId,
  })
}

export function useMyTeams() {
  return useQuery({
    queryKey: ['teams', 'my'],
    queryFn: teamApi.getMyTeams,
  })
}

export function useLeaderboard() {
  return useQuery({
    queryKey: ['leaderboard'],
    queryFn: teamApi.getLeaderboard,
    refetchInterval: 30_000,
  })
}

export function useTopPlayers() {
  return useQuery({
    queryKey: ['players', 'top'],
    queryFn: playerApi.getTop,
  })
}

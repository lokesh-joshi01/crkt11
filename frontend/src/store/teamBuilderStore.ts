import { create } from 'zustand'
import type { Player } from '../types'

const MAX_CREDITS = 100
const MAX_PLAYERS = 11
const MAX_FROM_ONE_TEAM = 7

interface TeamBuilderState {
  selectedPlayers: Player[]
  captain: Player | null
  viceCaptain: Player | null

  addPlayer: (player: Player) => string | null  // returns error or null
  removePlayer: (playerId: number) => void
  setCaptain: (player: Player) => void
  setViceCaptain: (player: Player) => void
  isSelected: (playerId: number) => boolean
  totalCredits: () => number
  reset: () => void
}

export const useTeamBuilderStore = create<TeamBuilderState>((set, get) => ({
  selectedPlayers: [],
  captain: null,
  viceCaptain: null,

  addPlayer: (player) => {
    const { selectedPlayers } = get()

    if (selectedPlayers.length >= MAX_PLAYERS) return 'Team is full (11 players max)'

    if (selectedPlayers.some((p) => p.id === player.id)) return 'Player already selected'

    const creditsAfter = get().totalCredits() + player.credits
    if (creditsAfter > MAX_CREDITS) return `Exceeds credit limit (${creditsAfter.toFixed(1)}/${MAX_CREDITS})`

    const teamCount = selectedPlayers.filter((p) => p.team === player.team).length
    if (teamCount >= MAX_FROM_ONE_TEAM) return `Max ${MAX_FROM_ONE_TEAM} players from ${player.team}`

    set({ selectedPlayers: [...selectedPlayers, player] })
    return null
  },

  removePlayer: (playerId) => {
    set((state) => ({
      selectedPlayers: state.selectedPlayers.filter((p) => p.id !== playerId),
      captain: state.captain?.id === playerId ? null : state.captain,
      viceCaptain: state.viceCaptain?.id === playerId ? null : state.viceCaptain,
    }))
  },

  setCaptain: (player) => {
    set((state) => ({
      captain: player,
      viceCaptain: state.viceCaptain?.id === player.id ? null : state.viceCaptain,
    }))
  },

  setViceCaptain: (player) => {
    set((state) => ({
      viceCaptain: player,
      captain: state.captain?.id === player.id ? null : state.captain,
    }))
  },

  isSelected: (playerId) => get().selectedPlayers.some((p) => p.id === playerId),

  totalCredits: () => get().selectedPlayers.reduce((sum, p) => sum + p.credits, 0),

  reset: () => set({ selectedPlayers: [], captain: null, viceCaptain: null }),
}))

import { QueryClient, QueryClientProvider } from '@tanstack/react-query'
import { useState } from 'react'
import LiveScore from './components/live/LiveScore'
import PlayerCompare from './components/compare/PlayerCompare'
import Leaderboard from './components/leaderboard/Leaderboard'
import PlayerCard from './components/common/PlayerCard'
import { usePlayers } from './hooks/useApi'
import { useTeamBuilderStore } from './store/teamBuilderStore'
import type { PlayerRole } from './types'

const queryClient = new QueryClient({
  defaultOptions: { queries: { staleTime: 30_000 } },
})

type Tab = 'live' | 'team' | 'compare' | 'leaderboard'

const TABS: { id: Tab; label: string }[] = [
  { id: 'live', label: 'Live' },
  { id: 'team', label: 'My Team' },
  { id: 'compare', label: 'Compare' },
  { id: 'leaderboard', label: 'Ranks' },
]

function TeamBuilder() {
  const [roleFilter, setRoleFilter] = useState<PlayerRole | undefined>()
  const { data: players } = usePlayers(roleFilter)
  const { selectedPlayers, totalCredits } = useTeamBuilderStore()
  const roles: (PlayerRole | undefined)[] = [undefined, 'BAT', 'BOWL', 'AR', 'WK']
  const roleLabels: Record<string, string> = { undefined: 'All', BAT: 'BAT', BOWL: 'BOWL', AR: 'AR', WK: 'WK' }

  return (
    <div className="space-y-4">
      <div className="flex gap-2 justify-between text-sm">
        <span className="text-muted">{selectedPlayers.length}/11 players</span>
        <span className="text-amber-400 font-bold">{totalCredits().toFixed(1)}/100 cr</span>
      </div>
      <div className="flex gap-2 overflow-x-auto pb-1">
        {roles.map((r) => (
          <button key={String(r)} onClick={() => setRoleFilter(r)}
            className={`px-3 py-1.5 rounded-full text-xs font-semibold whitespace-nowrap border transition-colors
              ${roleFilter === r ? 'bg-orange-500 border-orange-500 text-white' : 'border-border text-muted bg-surface hover:text-foreground'}`}>
            {roleLabels[String(r)] ?? r}
          </button>
        ))}
      </div>
      <div className="space-y-2">
        {players?.map((p) => <PlayerCard key={p.id} player={p} />)}
      </div>
    </div>
  )
}

function AppShell() {
  const [tab, setTab] = useState<Tab>('live')

  return (
    <div className="min-h-screen bg-bg text-foreground font-sans">
      {/* Navbar */}
      <nav className="sticky top-0 z-10 bg-surface border-b border-border">
        <div className="max-w-2xl mx-auto px-4 h-14 flex items-center justify-between">
          <span className="font-black text-xl tracking-wide text-orange-500">
            CRKT<span className="text-foreground">11</span>
          </span>
          <div className="flex gap-1">
            {TABS.map((t) => (
              <button key={t.id} onClick={() => setTab(t.id)}
                className={`px-3 py-1.5 rounded-lg text-sm font-semibold transition-colors
                  ${tab === t.id ? 'bg-orange-500 text-white' : 'text-muted hover:text-foreground hover:bg-surface2'}`}>
                {t.label}
              </button>
            ))}
          </div>
        </div>
      </nav>

      {/* Content */}
      <main className="max-w-2xl mx-auto px-4 py-5">
        {tab === 'live' && <LiveScore />}
        {tab === 'team' && <TeamBuilder />}
        {tab === 'compare' && <PlayerCompare />}
        {tab === 'leaderboard' && <Leaderboard />}
      </main>
    </div>
  )
}

export default function App() {
  return (
    <QueryClientProvider client={queryClient}>
      <AppShell />
    </QueryClientProvider>
  )
}

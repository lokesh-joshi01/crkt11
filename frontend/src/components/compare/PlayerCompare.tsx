import { useState } from 'react'
import { useComparePlayers, usePlayers } from '../../hooks/useApi'
import type { Player } from '../../types'

interface StatRowProps {
  label: string
  a: number
  b: number
  higherIsBetter?: boolean
  format?: (n: number) => string
}

function StatRow({ label, a, b, higherIsBetter = true, format = (n) => n.toFixed(1) }: StatRowProps) {
  const aWins = higherIsBetter ? a >= b : a <= b
  const maxVal = Math.max(a, b) || 1
  return (
    <div className="grid grid-cols-[1fr_80px_1fr] gap-3 items-center py-2.5 px-4 bg-surface rounded-xl mb-1.5">
      <div>
        <div className={`font-bold font-mono text-lg ${aWins ? 'text-orange-400' : 'text-foreground'}`}>
          {format(a)}
        </div>
        <div className="h-1 bg-surface2 rounded mt-1.5 overflow-hidden">
          <div className="h-full bg-orange-500 rounded" style={{ width: `${(a / maxVal) * 100}%` }} />
        </div>
      </div>
      <div className="text-center text-xs text-muted">{label}</div>
      <div className="text-right">
        <div className={`font-bold font-mono text-lg ${!aWins ? 'text-green-400' : 'text-foreground'}`}>
          {format(b)}
        </div>
        <div className="h-1 bg-surface2 rounded mt-1.5 overflow-hidden ml-auto" style={{ maxWidth: '100%' }}>
          <div className="h-full bg-green-500 rounded ml-auto" style={{ width: `${(b / maxVal) * 100}%` }} />
        </div>
      </div>
    </div>
  )
}

export default function PlayerCompare() {
  const [playerAId, setPlayerAId] = useState<number>(0)
  const [playerBId, setPlayerBId] = useState<number>(0)
  const { data: players } = usePlayers()
  const { data: comparison } = useComparePlayers(playerAId, playerBId)

  const [a, b] = comparison ?? []

  return (
    <div className="space-y-4">
      {/* Player selectors */}
      <div className="grid grid-cols-[1fr_40px_1fr] gap-3 items-center">
        <select
          className="bg-surface border border-border rounded-xl p-2.5 text-sm w-full"
          value={playerAId}
          onChange={(e) => setPlayerAId(Number(e.target.value))}
        >
          <option value={0}>Select player A</option>
          {players?.map((p) => <option key={p.id} value={p.id}>{p.name}</option>)}
        </select>
        <div className="text-center text-muted font-bold text-sm">VS</div>
        <select
          className="bg-surface border border-border rounded-xl p-2.5 text-sm w-full"
          value={playerBId}
          onChange={(e) => setPlayerBId(Number(e.target.value))}
        >
          <option value={0}>Select player B</option>
          {players?.filter((p) => p.id !== playerAId).map((p) => <option key={p.id} value={p.id}>{p.name}</option>)}
        </select>
      </div>

      {/* Comparison */}
      {a && b ? (
        <div>
          <div className="grid grid-cols-[1fr_40px_1fr] gap-3 mb-4">
            <PlayerHeader player={a} />
            <div />
            <PlayerHeader player={b} align="right" />
          </div>
          <StatRow label="Avg Pts" a={a.averagePoints} b={b.averagePoints} />
          <StatRow label="Strike Rate" a={a.strikeRate} b={b.strikeRate} />
          <StatRow label="Bat Avg" a={a.battingAverage} b={b.battingAverage} />
          <StatRow label="Total Runs" a={a.totalRuns} b={b.totalRuns} format={(n) => String(n)} />
          <StatRow label="Wickets" a={a.totalWickets} b={b.totalWickets} format={(n) => String(n)} />
          <StatRow label="Credits" a={a.credits} b={b.credits} higherIsBetter={false} />
          <StatRow label="Selected %" a={a.selectionPercentage} b={b.selectionPercentage} />
        </div>
      ) : (
        <div className="text-center text-muted text-sm py-8">Select two players to compare</div>
      )}
    </div>
  )
}

function PlayerHeader({ player, align = 'left' }: { player: Player; align?: 'left' | 'right' }) {
  return (
    <div className={`bg-surface border border-border rounded-xl p-3 ${align === 'right' ? 'text-right' : ''}`}>
      <div className="font-semibold text-sm">{player.name}</div>
      <div className="text-xs text-muted mt-0.5">{player.team} · {player.role}</div>
    </div>
  )
}

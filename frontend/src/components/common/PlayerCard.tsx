import type { Player } from '../../types'
import { useTeamBuilderStore } from '../../store/teamBuilderStore'

const ROLE_COLORS: Record<string, string> = {
  BAT: 'text-orange-400 bg-orange-500/15',
  BOWL: 'text-blue-400 bg-blue-500/15',
  AR: 'text-green-400 bg-green-500/15',
  WK: 'text-purple-400 bg-purple-500/15',
}

interface PlayerCardProps {
  player: Player
  showActions?: boolean
}

export default function PlayerCard({ player, showActions = true }: PlayerCardProps) {
  const { addPlayer, removePlayer, isSelected } = useTeamBuilderStore()
  const selected = isSelected(player.id)

  function handleToggle() {
    if (selected) {
      removePlayer(player.id)
    } else {
      const error = addPlayer(player)
      if (error) alert(error)
    }
  }

  return (
    <div className={`flex items-center gap-3 p-3 rounded-xl border transition-colors cursor-pointer
      ${selected ? 'border-orange-500/60 bg-orange-500/5' : 'border-border bg-surface hover:border-border-strong'}`}
      onClick={showActions ? handleToggle : undefined}
    >
      {/* Avatar */}
      <div className="w-10 h-10 rounded-full bg-surface2 flex items-center justify-center font-bold text-sm text-orange-400 flex-shrink-0">
        {player.name.split(' ').map((n) => n[0]).slice(0, 2).join('')}
      </div>

      {/* Info */}
      <div className="flex-1 min-w-0">
        <div className="flex items-center gap-2">
          <span className="font-semibold text-sm truncate">{player.name}</span>
          <span className={`text-xs font-bold px-1.5 py-0.5 rounded ${ROLE_COLORS[player.role]}`}>
            {player.role}
          </span>
        </div>
        <div className="text-xs text-muted mt-0.5">
          {player.team} · Avg {player.averagePoints.toFixed(1)} pts
        </div>
      </div>

      {/* Credits */}
      <div className="text-right flex-shrink-0">
        <div className="font-bold text-amber-400 font-mono">{player.credits}</div>
        <div className="text-xs text-muted">cr</div>
      </div>

      {/* Add/Remove button */}
      {showActions && (
        <button className={`w-7 h-7 rounded-full flex items-center justify-center text-sm font-bold transition-colors
          ${selected ? 'bg-surface2 text-muted' : 'bg-orange-500 text-white'}`}>
          {selected ? '✓' : '+'}
        </button>
      )}
    </div>
  )
}

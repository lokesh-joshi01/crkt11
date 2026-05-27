import { useLeaderboard } from '../../hooks/useApi'

export default function Leaderboard() {
  const { data: entries, isLoading } = useLeaderboard()

  if (isLoading) return <div className="text-muted text-sm p-4">Loading leaderboard…</div>

  return (
    <div className="space-y-2">
      {entries?.map((entry) => (
        <div
          key={entry.rank}
          className={`flex items-center gap-3 p-3 rounded-xl border transition-colors
            ${entry.ownerUsername === 'me' ? 'border-orange-500/40 bg-orange-500/5' : 'border-border bg-surface'}`}
        >
          <div className={`font-black font-mono text-lg w-7 text-center
            ${entry.rank <= 3 ? 'text-amber-400' : 'text-muted'}`}>
            {entry.rank}
          </div>
          <div className="w-8 h-8 rounded-full bg-surface2 flex items-center justify-center text-xs font-bold text-orange-400">
            {entry.ownerUsername.slice(0, 2).toUpperCase()}
          </div>
          <div className="flex-1 min-w-0">
            <div className="font-semibold text-sm">{entry.ownerUsername}</div>
            <div className="text-xs text-muted truncate">C: {entry.captainName} · VC: {entry.viceCaptainName}</div>
          </div>
          <div className="text-right">
            <div className="font-black text-xl text-amber-400 font-mono">{entry.totalPoints}</div>
            {entry.rankChange !== 0 && (
              <div className={`text-xs font-semibold ${entry.rankChange > 0 ? 'text-green-400' : 'text-red-400'}`}>
                {entry.rankChange > 0 ? `▲ ${entry.rankChange}` : `▼ ${Math.abs(entry.rankChange)}`}
              </div>
            )}
          </div>
        </div>
      ))}
    </div>
  )
}

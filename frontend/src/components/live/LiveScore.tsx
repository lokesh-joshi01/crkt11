import { useLiveMatch } from '../../hooks/useApi'

const BALL_STYLES: Record<string, string> = {
  '0': 'bg-surface2 text-muted',
  '4': 'bg-blue-500/20 text-blue-400 border border-blue-500/40',
  '6': 'bg-green-500/20 text-green-400 border border-green-500/40',
  'W': 'bg-red-500/20 text-red-400 border border-red-500/40',
}

function BallChip({ ball }: { ball: string }) {
  const style = BALL_STYLES[ball] ?? 'bg-orange-500/20 text-orange-400 border border-orange-500/40'
  return (
    <span className={`inline-flex items-center justify-center w-7 h-7 rounded-full text-xs font-bold ${style}`}>
      {ball === '0' ? '·' : ball}
    </span>
  )
}

export default function LiveScore() {
  const { data: match, isLoading, isError } = useLiveMatch()

  if (isLoading) return <div className="text-muted text-sm p-4">Loading live match…</div>
  if (isError || !match) return <div className="text-muted text-sm p-4">No live match right now.</div>

  const batters = match.playerStats.filter((s) => s.balls > 0 && s.runs >= 0).slice(0, 3)

  return (
    <div className="space-y-4">
      {/* Hero score card */}
      <div className="bg-surface rounded-xl border border-border p-4 relative overflow-hidden">
        <div className="absolute top-0 left-0 right-0 h-0.5 bg-gradient-to-r from-orange-500 to-amber-400" />
        <div className="flex items-center gap-2 mb-3">
          <span className="flex items-center gap-1.5 text-xs font-semibold text-red-400 bg-red-500/10 border border-red-500/30 px-2 py-0.5 rounded">
            <span className="w-1.5 h-1.5 rounded-full bg-red-400 animate-pulse" />
            LIVE · {match.overs} OV
          </span>
        </div>
        <div className="flex items-center gap-3">
          <div className="flex-1">
            <div className="text-2xl font-black">{match.teamA}</div>
            <div className="text-4xl font-black text-amber-400">{match.runs}/{match.wickets}</div>
            <div className="text-xs text-muted mt-0.5">{match.overs} ov</div>
          </div>
          <div className="text-muted text-sm font-semibold">vs</div>
          <div className="flex-1 text-right">
            <div className="text-2xl font-black">{match.teamB}</div>
            <div className="text-4xl font-black text-muted">—</div>
            <div className="text-xs text-muted mt-0.5">yet to bat</div>
          </div>
        </div>
        <div className="flex gap-4 mt-3 pt-3 border-t border-border text-xs text-muted">
          <span><strong className="text-foreground">CRR</strong> {match.currentRunRate.toFixed(2)}</span>
          <span><strong className="text-foreground">Req RR</strong> {match.requiredRunRate.toFixed(2)}</span>
          <span><strong className="text-foreground">Venue</strong> {match.venue}</span>
        </div>
      </div>

      {/* Recent balls */}
      {match.recentBalls.length > 0 && (
        <div>
          <p className="text-xs font-bold tracking-widest text-muted uppercase mb-2">This Over</p>
          <div className="flex gap-1.5 items-center">
            {match.recentBalls.map((b, i) => <BallChip key={i} ball={b} />)}
          </div>
        </div>
      )}

      {/* Scorecard */}
      {batters.length > 0 && (
        <div>
          <p className="text-xs font-bold tracking-widest text-muted uppercase mb-2">Batting</p>
          <div className="bg-surface rounded-xl border border-border overflow-hidden">
            <div className="grid grid-cols-[1fr_40px_40px_60px_50px] gap-2 px-4 py-2 bg-surface2 text-xs text-muted font-semibold">
              <span>BATTER</span><span className="text-right">R</span>
              <span className="text-right">B</span><span className="text-right">4s/6s</span>
              <span className="text-right">SR</span>
            </div>
            {batters.map((s) => (
              <div key={s.playerId} className="grid grid-cols-[1fr_40px_40px_60px_50px] gap-2 px-4 py-2.5 border-t border-border text-sm items-center">
                <span className="font-medium">{s.playerName}</span>
                <span className="text-right font-mono">{s.runs}</span>
                <span className="text-right font-mono text-muted">{s.balls}</span>
                <span className="text-right font-mono text-muted">{s.fours}/{s.sixes}</span>
                <span className={`text-right font-mono font-bold ${s.strikeRate >= 130 ? 'text-green-400' : 'text-muted'}`}>
                  {s.strikeRate.toFixed(0)}
                </span>
              </div>
            ))}
          </div>
        </div>
      )}
    </div>
  )
}

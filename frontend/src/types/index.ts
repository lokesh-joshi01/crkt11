export type PlayerRole = 'BAT' | 'BOWL' | 'AR' | 'WK'

export type MatchStatus = 'UPCOMING' | 'LIVE' | 'COMPLETED'

export interface Player {
  id: number
  name: string
  team: string
  role: PlayerRole
  credits: number
  averagePoints: number
  strikeRate: number
  battingAverage: number
  totalRuns: number
  totalWickets: number
  matchesPlayed: number
  selectionPercentage: number
}

export interface PlayerMatchStat {
  playerId: number
  playerName: string
  team: string
  runs: number
  balls: number
  fours: number
  sixes: number
  strikeRate: number
  isNotOut: boolean
  oversBowled: number
  wickets: number
  runsConceded: number
  economy: number
  catches: number
  runOuts: number
  stumpings: number
  fantasyPoints: number
}

export interface Match {
  id: number
  teamA: string
  teamB: string
  venue: string
  matchTime: string
  status: MatchStatus
  battingTeam: string
  runs: number
  wickets: number
  overs: number
  currentRunRate: number
  requiredRunRate: number
  recentBalls: string[]
  playerStats: PlayerMatchStat[]
}

export interface FantasyTeam {
  id: number
  name: string
  ownerUsername: string
  players: Player[]
  captain: Player
  viceCaptain: Player
  totalPoints: number
  rank: number
  totalCreditsUsed: number
}

export interface FantasyTeamRequest {
  name: string
  playerIds: number[]
  captainId: number
  viceCaptainId: number
}

export interface LeaderboardEntry {
  rank: number
  previousRank: number
  ownerUsername: string
  teamName: string
  totalPoints: number
  captainName: string
  viceCaptainName: string
  rankChange: number
}

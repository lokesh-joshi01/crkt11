package com.crkt11.service;

import com.crkt11.dto.MatchDto;
import com.crkt11.dto.PlayerMatchStatDto;
import com.crkt11.exception.ResourceNotFoundException;
import com.crkt11.model.Match;
import com.crkt11.model.Match.MatchStatus;
import com.crkt11.repository.MatchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MatchService {

    private final MatchRepository matchRepository;

    public MatchDto getLiveMatch() {
        Match match = matchRepository.findFirstByStatusOrderByMatchTimeAsc(MatchStatus.LIVE)
                .orElseThrow(() -> new ResourceNotFoundException("No live match at the moment"));
        return toDto(match);
    }

    public MatchDto getMatchById(Long id) {
        Match match = matchRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Match not found: " + id));
        return toDto(match);
    }

    public List<MatchDto> getUpcomingMatches() {
        return matchRepository.findByStatus(MatchStatus.UPCOMING).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public List<MatchDto> getRecentMatches() {
        return matchRepository.findByStatus(MatchStatus.COMPLETED).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    private MatchDto toDto(Match match) {
        List<String> recentBalls = match.getRecentBalls() != null
                ? Arrays.asList(match.getRecentBalls().split(","))
                : List.of();

        List<PlayerMatchStatDto> stats = match.getPlayerStats() != null
                ? match.getPlayerStats().stream().map(stat -> PlayerMatchStatDto.builder()
                        .playerId(stat.getPlayer().getId())
                        .playerName(stat.getPlayer().getName())
                        .team(stat.getPlayer().getTeam())
                        .runs(stat.getRuns())
                        .balls(stat.getBalls())
                        .fours(stat.getFours())
                        .sixes(stat.getSixes())
                        .strikeRate(stat.getStrikeRate())
                        .isNotOut(stat.isNotOut())
                        .oversBowled(stat.getOversBowled())
                        .wickets(stat.getWickets())
                        .runsConceded(stat.getRunsConceded())
                        .economy(stat.getEconomy())
                        .catches(stat.getCatches())
                        .runOuts(stat.getRunOuts())
                        .stumpings(stat.getStumpings())
                        .fantasyPoints(stat.getFantasyPoints())
                        .build())
                .collect(Collectors.toList())
                : List.of();

        return MatchDto.builder()
                .id(match.getId())
                .teamA(match.getTeamA())
                .teamB(match.getTeamB())
                .venue(match.getVenue())
                .matchTime(match.getMatchTime())
                .status(match.getStatus())
                .battingTeam(match.getBattingTeam())
                .runs(match.getRuns())
                .wickets(match.getWickets())
                .overs(match.getOvers())
                .currentRunRate(match.getCurrentRunRate())
                .requiredRunRate(match.getRequiredRunRate())
                .recentBalls(recentBalls)
                .playerStats(stats)
                .build();
    }
}

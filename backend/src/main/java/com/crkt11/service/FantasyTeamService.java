package com.crkt11.service;

import com.crkt11.dto.FantasyTeamDto;
import com.crkt11.dto.LeaderboardEntryDto;
import com.crkt11.exception.BadRequestException;
import com.crkt11.exception.ResourceNotFoundException;
import com.crkt11.model.FantasyTeam;
import com.crkt11.model.Player;
import com.crkt11.repository.FantasyTeamRepository;
import com.crkt11.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FantasyTeamService {

    private static final double MAX_CREDITS = 100.0;
    private static final int MAX_PLAYERS_FROM_ONE_TEAM = 7;

    private final FantasyTeamRepository teamRepository;
    private final PlayerRepository playerRepository;
    private final PlayerService playerService;

    @Transactional
    public FantasyTeamDto.Response createTeam(FantasyTeamDto.Request request, String username) {
        List<Player> players = playerRepository.findAllById(request.getPlayerIds());

        if (players.size() != 11) {
            throw new BadRequestException("Could not find all 11 players");
        }

        validateTeamComposition(players);
        validateCredits(players);

        Player captain = playerRepository.findById(request.getCaptainId())
                .orElseThrow(() -> new ResourceNotFoundException("Captain not found"));
        Player viceCaptain = playerRepository.findById(request.getViceCaptainId())
                .orElseThrow(() -> new ResourceNotFoundException("Vice-captain not found"));

        FantasyTeam team = FantasyTeam.builder()
                .name(request.getName())
                .ownerUsername(username)
                .players(players)
                .captain(captain)
                .viceCaptain(viceCaptain)
                .totalCreditsUsed(players.stream().mapToDouble(Player::getCredits).sum())
                .build();

        return toDto(teamRepository.save(team));
    }

    public List<FantasyTeamDto.Response> getMyTeams(String username) {
        return teamRepository.findByOwnerUsername(username).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public List<LeaderboardEntryDto> getLeaderboard() {
        List<FantasyTeam> teams = teamRepository.findAllOrderByPoints();
        AtomicInteger rank = new AtomicInteger(1);

        return teams.stream()
                .map(team -> LeaderboardEntryDto.builder()
                        .rank(rank.getAndIncrement())
                        .ownerUsername(team.getOwnerUsername())
                        .teamName(team.getName())
                        .totalPoints(team.getTotalPoints())
                        .captainName(team.getCaptain() != null ? team.getCaptain().getName() : "—")
                        .viceCaptainName(team.getViceCaptain() != null ? team.getViceCaptain().getName() : "—")
                        .build())
                .collect(Collectors.toList());
    }

    private void validateTeamComposition(List<Player> players) {
        long batters = players.stream().filter(p -> p.getRole() == Player.PlayerRole.BAT).count();
        long bowlers = players.stream().filter(p -> p.getRole() == Player.PlayerRole.BOWL).count();
        long allRounders = players.stream().filter(p -> p.getRole() == Player.PlayerRole.AR).count();
        long wicketKeepers = players.stream().filter(p -> p.getRole() == Player.PlayerRole.WK).count();

        if (wicketKeepers < 1 || wicketKeepers > 4)
            throw new BadRequestException("Need 1–4 wicket-keepers");
        if (batters < 3 || batters > 6)
            throw new BadRequestException("Need 3–6 batters");
        if (bowlers < 3 || bowlers > 6)
            throw new BadRequestException("Need 3–6 bowlers");
        if (allRounders < 1 || allRounders > 4)
            throw new BadRequestException("Need 1–4 all-rounders");

        // Max 7 from one team
        players.stream()
                .collect(Collectors.groupingBy(Player::getTeam, Collectors.counting()))
                .forEach((team, count) -> {
                    if (count > MAX_PLAYERS_FROM_ONE_TEAM)
                        throw new BadRequestException("Max " + MAX_PLAYERS_FROM_ONE_TEAM + " players from " + team);
                });
    }

    private void validateCredits(List<Player> players) {
        double total = players.stream().mapToDouble(Player::getCredits).sum();
        if (total > MAX_CREDITS) {
            throw new BadRequestException("Team exceeds credit limit of " + MAX_CREDITS + " (used: " + total + ")");
        }
    }

    private FantasyTeamDto.Response toDto(FantasyTeam team) {
        return FantasyTeamDto.Response.builder()
                .id(team.getId())
                .name(team.getName())
                .ownerUsername(team.getOwnerUsername())
                .players(team.getPlayers().stream().map(playerService::toDto).collect(Collectors.toList()))
                .captain(team.getCaptain() != null ? playerService.toDto(team.getCaptain()) : null)
                .viceCaptain(team.getViceCaptain() != null ? playerService.toDto(team.getViceCaptain()) : null)
                .totalPoints(team.getTotalPoints())
                .rank(team.getRank())
                .totalCreditsUsed(team.getTotalCreditsUsed())
                .build();
    }
}

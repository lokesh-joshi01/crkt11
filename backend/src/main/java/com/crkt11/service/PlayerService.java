package com.crkt11.service;

import com.crkt11.dto.PlayerDto;
import com.crkt11.exception.ResourceNotFoundException;
import com.crkt11.model.Player;
import com.crkt11.model.Player.PlayerRole;
import com.crkt11.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlayerService {

    private final PlayerRepository playerRepository;

    public List<PlayerDto> getAllPlayers() {
        return playerRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public PlayerDto getPlayerById(Long id) {
        Player player = playerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Player not found: " + id));
        return toDto(player);
    }

    public List<PlayerDto> getPlayersByRole(PlayerRole role) {
        return playerRepository.findByRole(role).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public List<PlayerDto> getPlayersByTeam(String team) {
        return playerRepository.findByTeam(team).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public List<PlayerDto> getTopPlayers() {
        return playerRepository.findTopPlayersByAveragePoints().stream()
                .limit(20)
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public List<PlayerDto> comparePlayers(Long playerAId, Long playerBId) {
        Player playerA = playerRepository.findById(playerAId)
                .orElseThrow(() -> new ResourceNotFoundException("Player not found: " + playerAId));
        Player playerB = playerRepository.findById(playerBId)
                .orElseThrow(() -> new ResourceNotFoundException("Player not found: " + playerBId));
        return List.of(toDto(playerA), toDto(playerB));
    }

    public PlayerDto toDto(Player player) {
        return PlayerDto.builder()
                .id(player.getId())
                .name(player.getName())
                .team(player.getTeam())
                .role(player.getRole())
                .credits(player.getCredits())
                .averagePoints(player.getAveragePoints())
                .strikeRate(player.getStrikeRate())
                .battingAverage(player.getBattingAverage())
                .totalRuns(player.getTotalRuns())
                .totalWickets(player.getTotalWickets())
                .matchesPlayed(player.getMatchesPlayed())
                .selectionPercentage(player.getSelectionPercentage())
                .build();
    }
}

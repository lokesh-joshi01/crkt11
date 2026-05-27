package com.crkt11.dto;

import com.crkt11.model.Player.PlayerRole;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class PlayerDto {
    private Long id;
    private String name;
    private String team;
    private PlayerRole role;
    private double credits;
    private double averagePoints;
    private double strikeRate;
    private double battingAverage;
    private int totalRuns;
    private int totalWickets;
    private int matchesPlayed;
    private double selectionPercentage;
}

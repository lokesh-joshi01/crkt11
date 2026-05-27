package com.crkt11.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "players")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String team;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PlayerRole role;

    @Column(nullable = false)
    private double credits;

    private double averagePoints;
    private double strikeRate;
    private double battingAverage;
    private int totalRuns;
    private int totalWickets;
    private int matchesPlayed;

    // Percentage of fantasy teams that have selected this player
    private double selectionPercentage;

    public enum PlayerRole {
        BAT, BOWL, AR, WK
    }
}

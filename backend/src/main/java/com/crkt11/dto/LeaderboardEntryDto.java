package com.crkt11.dto;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class LeaderboardEntryDto {
    private int rank;
    private int previousRank;
    private String ownerUsername;
    private String teamName;
    private int totalPoints;
    private String captainName;
    private String viceCaptainName;
    private int rankChange; // positive = moved up, negative = moved down
}

package com.crkt11.dto;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class PlayerMatchStatDto {
    private Long playerId;
    private String playerName;
    private String team;
    private int runs;
    private int balls;
    private int fours;
    private int sixes;
    private double strikeRate;
    private boolean isNotOut;
    private double oversBowled;
    private int wickets;
    private int runsConceded;
    private double economy;
    private int catches;
    private int runOuts;
    private int stumpings;
    private int fantasyPoints;
}

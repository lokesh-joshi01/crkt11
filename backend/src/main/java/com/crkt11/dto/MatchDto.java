package com.crkt11.dto;

import com.crkt11.model.Match.MatchStatus;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class MatchDto {
    private Long id;
    private String teamA;
    private String teamB;
    private String venue;
    private LocalDateTime matchTime;
    private MatchStatus status;

    // Live score
    private String battingTeam;
    private int runs;
    private int wickets;
    private double overs;
    private double currentRunRate;
    private double requiredRunRate;
    private List<String> recentBalls;

    private List<PlayerMatchStatDto> playerStats;
}

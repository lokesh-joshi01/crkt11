package com.crkt11.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "matches")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String teamA;
    private String teamB;
    private String venue;
    private LocalDateTime matchTime;

    @Enumerated(EnumType.STRING)
    private MatchStatus status;

    // Live score fields
    private String battingTeam;
    private int runs;
    private int wickets;
    private double overs;
    private double currentRunRate;
    private double requiredRunRate;
    private String recentBalls; // e.g. "0,4,1,6,W,2"

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "match_id")
    private List<PlayerMatchStat> playerStats;

    public enum MatchStatus {
        UPCOMING, LIVE, COMPLETED
    }
}

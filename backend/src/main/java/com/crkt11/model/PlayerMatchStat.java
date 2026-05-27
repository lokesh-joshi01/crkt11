package com.crkt11.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "player_match_stats")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class PlayerMatchStat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "player_id")
    private Player player;

    // Batting
    private int runs;
    private int balls;
    private int fours;
    private int sixes;
    private double strikeRate;
    private boolean isNotOut;

    // Bowling
    private double oversBowled;
    private int wickets;
    private int runsConceded;
    private double economy;

    // Fielding
    private int catches;
    private int runOuts;
    private int stumpings;

    // Fantasy points
    private int fantasyPoints;
}

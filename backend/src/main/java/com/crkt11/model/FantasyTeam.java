package com.crkt11.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "fantasy_teams")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class FantasyTeam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String ownerUsername;

    @ManyToMany
    @JoinTable(
        name = "fantasy_team_players",
        joinColumns = @JoinColumn(name = "team_id"),
        inverseJoinColumns = @JoinColumn(name = "player_id")
    )
    private List<Player> players;

    @ManyToOne
    @JoinColumn(name = "captain_id")
    private Player captain;

    @ManyToOne
    @JoinColumn(name = "vice_captain_id")
    private Player viceCaptain;

    private int totalPoints;
    private int rank;
    private double totalCreditsUsed;
}

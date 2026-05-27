package com.crkt11.repository;

import com.crkt11.model.Player;
import com.crkt11.model.Player.PlayerRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {

    List<Player> findByTeam(String team);

    List<Player> findByRole(PlayerRole role);

    List<Player> findByTeamIn(List<String> teams);

    @Query("SELECT p FROM Player p WHERE p.credits BETWEEN :min AND :max ORDER BY p.averagePoints DESC")
    List<Player> findByCreditRange(double min, double max);

    @Query("SELECT p FROM Player p ORDER BY p.averagePoints DESC")
    List<Player> findTopPlayersByAveragePoints();
}

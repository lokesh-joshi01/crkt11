package com.crkt11.repository;

import com.crkt11.model.FantasyTeam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface FantasyTeamRepository extends JpaRepository<FantasyTeam, Long> {

    List<FantasyTeam> findByOwnerUsername(String ownerUsername);

    Optional<FantasyTeam> findByIdAndOwnerUsername(Long id, String ownerUsername);

    @Query("SELECT ft FROM FantasyTeam ft ORDER BY ft.totalPoints DESC")
    List<FantasyTeam> findAllOrderByPoints();

    long countByOwnerUsername(String ownerUsername);
}

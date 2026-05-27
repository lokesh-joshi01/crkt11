package com.crkt11.repository;

import com.crkt11.model.Match;
import com.crkt11.model.Match.MatchStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface MatchRepository extends JpaRepository<Match, Long> {

    List<Match> findByStatus(MatchStatus status);

    Optional<Match> findFirstByStatusOrderByMatchTimeAsc(MatchStatus status);

    List<Match> findByTeamAOrTeamBOrderByMatchTimeDesc(String teamA, String teamB);
}

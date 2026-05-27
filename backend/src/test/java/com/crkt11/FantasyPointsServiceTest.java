package com.crkt11;

import com.crkt11.model.PlayerMatchStat;
import com.crkt11.service.FantasyPointsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class FantasyPointsServiceTest {

    private FantasyPointsService service;

    @BeforeEach
    void setUp() {
        service = new FantasyPointsService();
    }

    @Test
    void shouldCalculateBasicBattingPoints() {
        PlayerMatchStat stat = new PlayerMatchStat();
        stat.setRuns(50);
        stat.setBalls(35);
        stat.setFours(4);
        stat.setSixes(2);
        stat.setStrikeRate(142.8);
        stat.setNotOut(true);

        int points = service.calculate(stat);

        // 4 (playing XI) + 50 (runs) + 4 (fours) + 4 (sixes) + 8 (50 bonus) = 70
        assertThat(points).isEqualTo(70);
    }

    @Test
    void shouldApplyCaptainMultiplier() {
        assertThat(service.applyMultiplier(100, true, false)).isEqualTo(200);
    }

    @Test
    void shouldApplyViceCaptainMultiplier() {
        assertThat(service.applyMultiplier(100, false, true)).isEqualTo(150);
    }

    @Test
    void shouldPenaliseDuck() {
        PlayerMatchStat stat = new PlayerMatchStat();
        stat.setRuns(0);
        stat.setBalls(3);
        stat.setNotOut(false);

        int points = service.calculate(stat);

        // 4 (playing XI) + 0 (runs) - 2 (duck penalty) = 2
        assertThat(points).isEqualTo(2);
    }
}

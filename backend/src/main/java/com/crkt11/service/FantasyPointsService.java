package com.crkt11.service;

import com.crkt11.model.PlayerMatchStat;
import org.springframework.stereotype.Service;

/**
 * Calculates fantasy points based on standard IPL fantasy scoring rules.
 */
@Service
public class FantasyPointsService {

    // Batting points
    private static final int POINTS_PER_RUN = 1;
    private static final int BONUS_BOUNDARY = 1;       // per four
    private static final int BONUS_SIX = 2;            // per six
    private static final int BONUS_HALF_CENTURY = 8;
    private static final int BONUS_CENTURY = 16;
    private static final int PENALTY_DUCK = -2;        // for batters/AR/WK

    // Strike rate bonus/penalty (min 10 balls faced)
    private static final int BONUS_SR_ABOVE_170 = 6;
    private static final int BONUS_SR_150_170 = 4;
    private static final int BONUS_SR_130_150 = 2;
    private static final int PENALTY_SR_BELOW_50 = -6;
    private static final int PENALTY_SR_50_60 = -4;
    private static final int PENALTY_SR_60_70 = -2;

    // Bowling points
    private static final int POINTS_PER_WICKET = 25;
    private static final int BONUS_LBW_BOWLED = 8;
    private static final int BONUS_4_WICKETS = 4;
    private static final int BONUS_5_WICKETS = 8;
    private static final int BONUS_MAIDEN_OVER = 12;

    // Economy rate bonus/penalty (min 2 overs bowled)
    private static final int BONUS_ECONOMY_BELOW_5 = 6;
    private static final int BONUS_ECONOMY_5_6 = 4;
    private static final int BONUS_ECONOMY_6_7 = 2;
    private static final int PENALTY_ECONOMY_10_11 = -2;
    private static final int PENALTY_ECONOMY_11_12 = -4;
    private static final int PENALTY_ECONOMY_ABOVE_12 = -6;

    // Fielding points
    private static final int POINTS_PER_CATCH = 8;
    private static final int BONUS_3_CATCHES = 4;
    private static final int POINTS_PER_STUMPING = 12;
    private static final int POINTS_PER_RUN_OUT_DIRECT = 12;
    private static final int POINTS_PER_RUN_OUT_THROW = 6;

    // Playing XI bonus
    private static final int POINTS_PLAYING_XI = 4;

    public int calculate(PlayerMatchStat stat) {
        int points = POINTS_PLAYING_XI;
        points += calculateBattingPoints(stat);
        points += calculateBowlingPoints(stat);
        points += calculateFieldingPoints(stat);
        return points;
    }

    private int calculateBattingPoints(PlayerMatchStat stat) {
        int points = 0;
        int runs = stat.getRuns();
        int balls = stat.getBalls();

        points += runs * POINTS_PER_RUN;
        points += stat.getFours() * BONUS_BOUNDARY;
        points += stat.getSixes() * BONUS_SIX;

        if (runs >= 100) points += BONUS_CENTURY;
        else if (runs >= 50) points += BONUS_HALF_CENTURY;
        else if (runs == 0 && !stat.isNotOut()) points += PENALTY_DUCK;

        if (balls >= 10) {
            double sr = stat.getStrikeRate();
            if (sr > 170) points += BONUS_SR_ABOVE_170;
            else if (sr >= 150) points += BONUS_SR_150_170;
            else if (sr >= 130) points += BONUS_SR_130_150;
            else if (sr < 50) points += PENALTY_SR_BELOW_50;
            else if (sr < 60) points += PENALTY_SR_50_60;
            else if (sr < 70) points += PENALTY_SR_60_70;
        }

        return points;
    }

    private int calculateBowlingPoints(PlayerMatchStat stat) {
        int points = 0;
        int wickets = stat.getWickets();
        double overs = stat.getOversBowled();

        points += wickets * POINTS_PER_WICKET;
        if (wickets >= 5) points += BONUS_5_WICKETS;
        else if (wickets >= 4) points += BONUS_4_WICKETS;

        if (overs >= 2) {
            double economy = stat.getEconomy();
            if (economy < 5) points += BONUS_ECONOMY_BELOW_5;
            else if (economy < 6) points += BONUS_ECONOMY_5_6;
            else if (economy < 7) points += BONUS_ECONOMY_6_7;
            else if (economy >= 11 && economy < 12) points += PENALTY_ECONOMY_10_11;
            else if (economy >= 12) points += PENALTY_ECONOMY_ABOVE_12;
        }

        return points;
    }

    private int calculateFieldingPoints(PlayerMatchStat stat) {
        int points = 0;
        int catches = stat.getCatches();

        points += catches * POINTS_PER_CATCH;
        if (catches >= 3) points += BONUS_3_CATCHES;
        points += stat.getStumpings() * POINTS_PER_STUMPING;
        points += stat.getRunOuts() * POINTS_PER_RUN_OUT_THROW;

        return points;
    }

    public int applyMultiplier(int basePoints, boolean isCaptain, boolean isViceCaptain) {
        if (isCaptain) return basePoints * 2;
        if (isViceCaptain) return (int) (basePoints * 1.5);
        return basePoints;
    }
}

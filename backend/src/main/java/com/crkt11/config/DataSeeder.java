package com.crkt11.config;

import com.crkt11.model.*;
import com.crkt11.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.time.LocalDateTime;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class DataSeeder {

    @Bean
    CommandLineRunner seedData(PlayerRepository playerRepo, MatchRepository matchRepo) {
        return args -> {
            if (playerRepo.count() > 0) return;

            // Seed players
            List<Player> players = List.of(
                Player.builder().name("Rohit Sharma").team("MI").role(Player.PlayerRole.BAT)
                    .credits(10.5).averagePoints(52.1).strikeRate(143.0).battingAverage(38.0)
                    .totalRuns(620).matchesPlayed(14).selectionPercentage(68.0).build(),
                Player.builder().name("Virat Kohli").team("RCB").role(Player.PlayerRole.BAT)
                    .credits(11.0).averagePoints(61.4).strikeRate(138.0).battingAverage(51.0)
                    .totalRuns(741).matchesPlayed(14).selectionPercentage(74.0).build(),
                Player.builder().name("MS Dhoni").team("CSK").role(Player.PlayerRole.WK)
                    .credits(9.5).averagePoints(44.2).strikeRate(165.0).battingAverage(31.0)
                    .totalRuns(312).matchesPlayed(14).selectionPercentage(58.0).build(),
                Player.builder().name("Hardik Pandya").team("MI").role(Player.PlayerRole.AR)
                    .credits(10.0).averagePoints(48.5).strikeRate(151.0).battingAverage(28.0)
                    .totalRuns(280).totalWickets(8).matchesPlayed(12).selectionPercentage(52.0).build(),
                Player.builder().name("Ravindra Jadeja").team("CSK").role(Player.PlayerRole.AR)
                    .credits(9.5).averagePoints(46.0).strikeRate(144.0).battingAverage(22.0)
                    .totalRuns(198).totalWickets(12).matchesPlayed(14).selectionPercentage(61.0).build(),
                Player.builder().name("Jasprit Bumrah").team("MI").role(Player.PlayerRole.BOWL)
                    .credits(10.0).averagePoints(50.2).totalWickets(18).matchesPlayed(13).selectionPercentage(71.0).build(),
                Player.builder().name("Yuzvendra Chahal").team("RR").role(Player.PlayerRole.BOWL)
                    .credits(9.0).averagePoints(43.1).totalWickets(16).matchesPlayed(14).selectionPercentage(49.0).build(),
                Player.builder().name("Shubman Gill").team("GT").role(Player.PlayerRole.BAT)
                    .credits(9.5).averagePoints(48.2).strikeRate(140.0).battingAverage(42.0)
                    .totalRuns(580).matchesPlayed(14).selectionPercentage(55.0).build(),
                Player.builder().name("Ruturaj Gaikwad").team("CSK").role(Player.PlayerRole.BAT)
                    .credits(9.0).averagePoints(42.0).strikeRate(135.0).battingAverage(38.5)
                    .totalRuns(510).matchesPlayed(14).selectionPercentage(47.0).build(),
                Player.builder().name("Kuldeep Yadav").team("DC").role(Player.PlayerRole.BOWL)
                    .credits(9.0).averagePoints(41.8).totalWickets(15).matchesPlayed(13).selectionPercentage(44.0).build(),
                Player.builder().name("Ishan Kishan").team("MI").role(Player.PlayerRole.WK)
                    .credits(9.0).averagePoints(39.5).strikeRate(148.0).battingAverage(29.0)
                    .totalRuns(360).matchesPlayed(14).selectionPercentage(42.0).build(),
                Player.builder().name("Devon Conway").team("CSK").role(Player.PlayerRole.BAT)
                    .credits(8.5).averagePoints(36.2).strikeRate(132.0).battingAverage(32.0)
                    .totalRuns(380).matchesPlayed(12).selectionPercentage(35.0).build()
            );
            playerRepo.saveAll(players);

            // Seed a live match
            Match liveMatch = Match.builder()
                    .teamA("MI")
                    .teamB("CSK")
                    .venue("Wankhede Stadium, Mumbai")
                    .matchTime(LocalDateTime.now().minusHours(1))
                    .status(Match.MatchStatus.LIVE)
                    .battingTeam("MI")
                    .runs(142)
                    .wickets(3)
                    .overs(14.3)
                    .currentRunRate(9.79)
                    .requiredRunRate(7.21)
                    .recentBalls("0,4,1,6,W,2")
                    .build();
            matchRepo.save(liveMatch);
        };
    }
}

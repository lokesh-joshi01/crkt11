package com.crkt11.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import java.util.List;

public class FantasyTeamDto {

    @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
    public static class Request {
        @NotBlank(message = "Team name is required")
        private String name;

        @NotNull @Size(min = 11, max = 11, message = "Must select exactly 11 players")
        private List<Long> playerIds;

        @NotNull(message = "Captain is required")
        private Long captainId;

        @NotNull(message = "Vice-captain is required")
        private Long viceCaptainId;
    }

    @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
    public static class Response {
        private Long id;
        private String name;
        private String ownerUsername;
        private List<PlayerDto> players;
        private PlayerDto captain;
        private PlayerDto viceCaptain;
        private int totalPoints;
        private int rank;
        private double totalCreditsUsed;
    }
}

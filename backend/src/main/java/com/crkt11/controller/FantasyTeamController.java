package com.crkt11.controller;

import com.crkt11.dto.FantasyTeamDto;
import com.crkt11.dto.LeaderboardEntryDto;
import com.crkt11.service.FantasyTeamService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/teams")
@RequiredArgsConstructor
public class FantasyTeamController {

    private final FantasyTeamService teamService;

    @PostMapping
    public ResponseEntity<FantasyTeamDto.Response> createTeam(
            @Valid @RequestBody FantasyTeamDto.Request request,
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(teamService.createTeam(request, userDetails.getUsername()));
    }

    @GetMapping("/my")
    public ResponseEntity<List<FantasyTeamDto.Response>> getMyTeams(
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(teamService.getMyTeams(userDetails.getUsername()));
    }

    @GetMapping("/leaderboard")
    public ResponseEntity<List<LeaderboardEntryDto>> getLeaderboard() {
        return ResponseEntity.ok(teamService.getLeaderboard());
    }
}

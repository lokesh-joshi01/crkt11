package com.crkt11.controller;

import com.crkt11.dto.MatchDto;
import com.crkt11.service.MatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/matches")
@RequiredArgsConstructor
public class MatchController {

    private final MatchService matchService;

    @GetMapping("/live")
    public ResponseEntity<MatchDto> getLiveMatch() {
        return ResponseEntity.ok(matchService.getLiveMatch());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MatchDto> getMatch(@PathVariable Long id) {
        return ResponseEntity.ok(matchService.getMatchById(id));
    }

    @GetMapping("/upcoming")
    public ResponseEntity<List<MatchDto>> getUpcomingMatches() {
        return ResponseEntity.ok(matchService.getUpcomingMatches());
    }

    @GetMapping("/recent")
    public ResponseEntity<List<MatchDto>> getRecentMatches() {
        return ResponseEntity.ok(matchService.getRecentMatches());
    }
}

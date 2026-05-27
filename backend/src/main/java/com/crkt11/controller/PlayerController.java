package com.crkt11.controller;

import com.crkt11.dto.PlayerDto;
import com.crkt11.model.Player.PlayerRole;
import com.crkt11.service.PlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/players")
@RequiredArgsConstructor
public class PlayerController {

    private final PlayerService playerService;

    @GetMapping
    public ResponseEntity<List<PlayerDto>> getAllPlayers(
            @RequestParam(required = false) PlayerRole role,
            @RequestParam(required = false) String team) {

        if (role != null) return ResponseEntity.ok(playerService.getPlayersByRole(role));
        if (team != null) return ResponseEntity.ok(playerService.getPlayersByTeam(team));
        return ResponseEntity.ok(playerService.getAllPlayers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlayerDto> getPlayer(@PathVariable Long id) {
        return ResponseEntity.ok(playerService.getPlayerById(id));
    }

    @GetMapping("/top")
    public ResponseEntity<List<PlayerDto>> getTopPlayers() {
        return ResponseEntity.ok(playerService.getTopPlayers());
    }

    @GetMapping("/compare")
    public ResponseEntity<List<PlayerDto>> comparePlayers(
            @RequestParam Long playerAId,
            @RequestParam Long playerBId) {
        return ResponseEntity.ok(playerService.comparePlayers(playerAId, playerBId));
    }
}

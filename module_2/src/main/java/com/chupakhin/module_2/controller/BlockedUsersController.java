package com.chupakhin.module_2.controller;

import com.chupakhin.module_2.service.BlockedUsersService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/blocked-users")
@AllArgsConstructor
public class BlockedUsersController {

    private final BlockedUsersService blockedUsersService;

    @GetMapping
    public ResponseEntity<Map<String, List<String>>> getAllBlockedUsers() {
        Map<String, List<String>> blockedUsers = blockedUsersService.getAllBlockedUsers();
        return ResponseEntity.ok(blockedUsers);
    }

    @GetMapping("/{recipientId}")
    public ResponseEntity<List<String>> getBlockedUsersByRecipientId(@PathVariable String recipientId) {
        List<String> blockedUsers = blockedUsersService.getBlockedUsersByRecipientId(recipientId);
        return ResponseEntity.ok(blockedUsers);
    }
}




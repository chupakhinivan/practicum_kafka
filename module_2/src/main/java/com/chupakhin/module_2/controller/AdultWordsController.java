package com.chupakhin.module_2.controller;

import com.chupakhin.module_2.service.AdultWordsService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/adult-words")
@AllArgsConstructor
public class AdultWordsController {

    private final AdultWordsService adultWordsService;

    @GetMapping
    public ResponseEntity<Set<String>> getAdultWords() {
        Set<String> adultWords = adultWordsService.getAdultWords();
        return ResponseEntity.ok(adultWords);
    }

    @PostMapping
    public ResponseEntity<String> addAdultWords(@RequestBody Set<String> adultWords) {
        try {
            adultWordsService.addAdultWords(adultWords);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
        return ResponseEntity.ok("Adult words added");
    }

    @DeleteMapping
    public ResponseEntity<String> removeAdultWords(@RequestBody Set<String> adultWords) {
        try {
            adultWordsService.removeAdultWords(adultWords);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
        return ResponseEntity.ok("Adult words removed");
    }
}




package com.leonardovaladao.spring.controllers;

import com.leonardovaladao.spring.domains.Anime;
import com.leonardovaladao.spring.services.AnimeService;
import com.leonardovaladao.spring.utilitaries.DateUtil;

import lombok.extern.log4j.Log4j2;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("anime")
@Log4j2
public class AnimeController {

    @Autowired
    private DateUtil dateUtil;

    @Autowired
    private AnimeService animeService;

    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<List<Anime>> getAnimes() {
        log.info(dateUtil.formatLocalDateTimeToDatabaseStyle(LocalDateTime.now()));
        return ResponseEntity.ok(animeService.getAnimes());
    }

    @GetMapping(path = "/{id}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<Anime> getAnime(@PathVariable Long id) {
        log.info(dateUtil.formatLocalDateTimeToDatabaseStyle(LocalDateTime.now()));
        return ResponseEntity.ok(animeService.getAnime(id));
    }

    @PostMapping
    public ResponseEntity<Anime> saveAnime(@RequestBody Anime anime) {
        return ResponseEntity.ok(animeService.saveAnime(anime));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deleteAnime(@PathVariable Long id) {
        animeService.deleteAnime(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping
    public ResponseEntity<Anime> updateAnime(@RequestBody Anime anime) {
        return ResponseEntity.ok(animeService.updateAnime(anime));
    }
}

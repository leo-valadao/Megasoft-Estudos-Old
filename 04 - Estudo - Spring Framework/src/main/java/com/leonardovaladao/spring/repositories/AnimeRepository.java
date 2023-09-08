package com.leonardovaladao.spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.leonardovaladao.spring.domains.Anime;

public interface AnimeRepository extends JpaRepository<Anime, Long> {
    
}

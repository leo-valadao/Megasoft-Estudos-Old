package com.leonardovaladao.spring.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.leonardovaladao.spring.domains.Anime;

@Service
public class AnimeService {

    private static List<Anime> animes;
    static {
        animes = new ArrayList<Anime>(
                List.of(new Anime(1L, "Naruto"), new Anime(2L, "Death Note"), new Anime(3L, "One Punch Man")));
    }

    public List<Anime> getAnimes() {
        return animes;
    }

    public Anime getAnime(Long id) {
        return animes.stream()
                .filter(anime -> anime.getId() == id)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Anime not Found!"));
    }

    public Anime saveAnime(Anime anime) {
        anime.setId(animes.get(animes.size() - 1).getId() + 1);
        animes.add(anime);
        return anime;
    }

    public void deleteAnime(Long id) {
        animes.removeIf(anime -> anime.getId() == id);
        System.out.println(animes);
    }

    public Anime updateAnime(Anime anime) {
        Anime animeAntigo = animes.parallelStream().filter(animeDaLista -> animeDaLista.getId() == anime.getId())
                .findFirst().get();

        animes.set(animes.indexOf(animeAntigo), anime);

        return anime;
    }
}

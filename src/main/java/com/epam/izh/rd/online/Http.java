package com.epam.izh.rd.online;

import com.epam.izh.rd.online.configuration.AppConfig;
import com.epam.izh.rd.online.entity.Pokemon;
import com.epam.izh.rd.online.service.PokemonFetchingService;
import com.epam.izh.rd.online.service.PokemonFightingClubService;
import com.epam.izh.rd.online.service.SimplePokemonFetchingService;
import com.epam.izh.rd.online.service.SimplePokemonFightingClubService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Http {

    public static void main(String[] args) {
        startTrialBattle("pikachu", "slowpoke");
    }

    public static void startTrialBattle(String pokemonNameFirst, String pokemonNameSecond) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
        PokemonFetchingService pokemonFetchingService = applicationContext.getBean(SimplePokemonFetchingService.class);
        PokemonFightingClubService pokemonFightingClubService = applicationContext.getBean(SimplePokemonFightingClubService.class);
        Pokemon pokemon1 = pokemonFetchingService.fetchByName(pokemonNameFirst);
        Pokemon pokemon2 = pokemonFetchingService.fetchByName(pokemonNameSecond);
        System.out.println(pokemon1.getPokemonName() + " and " + pokemon2.getPokemonName() + " are fighting.");
        Pokemon winner = pokemonFightingClubService.doBattle(pokemon1, pokemon2);
        System.out.println(winner.getPokemonName() + " wins!");
    }
}
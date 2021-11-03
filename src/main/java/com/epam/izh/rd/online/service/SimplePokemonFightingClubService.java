package com.epam.izh.rd.online.service;

import com.epam.izh.rd.online.entity.Pokemon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SimplePokemonFightingClubService implements PokemonFightingClubService {
    private final PokemonFetchingService pokemonFetchingService;

    @Autowired
    public SimplePokemonFightingClubService(PokemonFetchingService pokemonFetchingService) {
        this.pokemonFetchingService = pokemonFetchingService;
    }

    public Pokemon[] changePokemons(Pokemon[] pokemons) {
        Pokemon p = pokemons[0];
        pokemons[0] = pokemons[1];
        pokemons[1] = p;
        return pokemons;
    }

    @Override
    public Pokemon doBattle(Pokemon p1, Pokemon p2) {
        if (p1 == null || p2 == null) {
            throw new NullPointerException("There are null pokemon assigned to participate in the battle.");
        }
        Pokemon[] fighters = new Pokemon[]{p1, p2};
        if (fighters[0].getPokemonId() < fighters[1].getPokemonId()) {
            fighters = changePokemons(fighters);
        }
        while ((fighters[0].getHp() > 0) && (fighters[1].getHp() > 0)) {
            doDamage(fighters[0], fighters[1]);
            fighters = changePokemons(fighters);
        }
        showWinner(p1);
        return fighters[0].getHp() > 0 ? fighters[0] : fighters[1];
    }

    @Override
    public void showWinner(Pokemon winner) {
        pokemonFetchingService.getPokemonImage(winner.getImageUrl());
    }

    @Override
    public void doDamage(Pokemon from, Pokemon to) {
        to.setHp((short) (to.getHp() - from.getAttack() * (1 - to.getDefense() / 100.0)));
    }
}
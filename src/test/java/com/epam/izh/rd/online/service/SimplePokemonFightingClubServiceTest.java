package com.epam.izh.rd.online.service;

import com.epam.izh.rd.online.entity.Pokemon;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.hamcrest.CoreMatchers.*;
import static net.obvj.junit.utils.matchers.AdvancedMatchers.throwsException;
import static org.hamcrest.MatcherAssert.assertThat;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests for checking of SimplePokemonFightingClubService.class methods.")
class SimplePokemonFightingClubServiceTest {
    private final PokemonFetchingService mockPokemonFetchingService = Mockito.mock(SimplePokemonFetchingService.class);
    private final PokemonFightingClubService pokemonFightingClubService = new SimplePokemonFightingClubService(mockPokemonFetchingService);
    private static Pokemon PIKACHU;
    private static Pokemon SLOWPOKE;

    @BeforeEach
    void restorePokemons() {
        PIKACHU = new Pokemon(25L, "pikachu", (short) 35, (short) 55, (short) 40,
                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/25.png");
        SLOWPOKE = new Pokemon(79L, "slowpoke", (short) 90, (short) 65, (short) 65,
                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/79.png");
    }

    @Test
    void checkedChangePokemonsInObjects_whenChangePokemonsMethodCalled() {
        Pokemon[] pokemons = new Pokemon[]{PIKACHU, SLOWPOKE};
        pokemons = new SimplePokemonFightingClubService(mockPokemonFetchingService).changePokemons(pokemons);
        assertThat(PIKACHU, is(equalTo(pokemons[1])));
        assertThat(SLOWPOKE, is(equalTo(pokemons[0])));
    }

    @Test
    void returnExceptionDoBattleMethod_whenFirstPokemonIsNull() {
        assertThat(() -> pokemonFightingClubService.doBattle(null, SLOWPOKE),
                throwsException(NullPointerException.class));
    }

    @Test
    void returnExceptionDoBattleMethod_whenSecondPokemonIsNull() {
        assertThat(() -> pokemonFightingClubService.doBattle(PIKACHU, null),
                throwsException(NullPointerException.class));
    }

    @Test
    void returnPikachuFromDoBattleMethod_whenFightingPikachuAndSlowpoke() {
        Pokemon winner = pokemonFightingClubService.doBattle(PIKACHU, SLOWPOKE);
        assertThat(winner, is(equalTo(SLOWPOKE)));
    }

    @Test
    void returnPikachuFromDoDamageMethod_whenFightingPikachuAndSlowpoke() {
        pokemonFightingClubService.doDamage(PIKACHU, SLOWPOKE);
        int newSlowpokeHp = (int) SLOWPOKE.getHp();
        assertThat(newSlowpokeHp, is(equalTo(70)));
    }

    @Test
    void checkedShowWinnerMethod_whenBattleIsFinished() {
        pokemonFightingClubService.showWinner(PIKACHU);
        Mockito.verify(mockPokemonFetchingService, Mockito.times(1)).getPokemonImage(Mockito.any(String.class));
    }
}
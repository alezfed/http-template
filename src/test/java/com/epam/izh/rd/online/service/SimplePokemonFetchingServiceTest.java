package com.epam.izh.rd.online.service;

import com.epam.izh.rd.online.configuration.AppConfig;
import com.epam.izh.rd.online.entity.Pokemon;
import lombok.SneakyThrows;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.*;
import static net.obvj.junit.utils.matchers.AdvancedMatchers.throwsException;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AppConfig.class)
@DisplayName("Tests for checking of SimplePokemonFetchingService.class methods.")
class SimplePokemonFetchingServiceTest {
    private final PokemonFetchingService pokemonFetchingService;
    private final MockRestServiceServer mockServer;
    private final static String REST_URL = "https://pokeapi.co/api/v2/pokemon/";
    private final static Pokemon VERIFIED_POKEMON = new Pokemon(25L, "pikachu", (short) 35, (short) 55, (short) 40,
            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/25.png");
    private final static Pokemon UNKNOWN_POKEMON = new Pokemon(1L, "unknownPokemon", (short) 1, (short) 1, (short) 1,
            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/666666.png");

    @Autowired
    public SimplePokemonFetchingServiceTest(PokemonFetchingService pokemonFetchingService, RestTemplate restTemplate) {
        this.pokemonFetchingService = pokemonFetchingService;
        this.mockServer = MockRestServiceServer.createServer(restTemplate);
    }

    @BeforeEach
    void cleanTarget() {
        new File(Paths.get("target/winner.png").toUri()).deleteOnExit();
    }

    public byte[] readBytesFileFromResources(String filename) throws IOException {
        File file = new File(getClass().getClassLoader().getResource(filename).getFile());
        return Files.readAllBytes(Paths.get(file.getPath()));
    }

    @SneakyThrows
    @Test
    void returnPokemonItemFromServer_whenCalledFetchByNameMethod() {
        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI(REST_URL + VERIFIED_POKEMON.getPokemonName())))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(new String(readBytesFileFromResources(VERIFIED_POKEMON.getPokemonName() + ".json"))));
        Pokemon pokemonFromUrl = pokemonFetchingService.fetchByName(VERIFIED_POKEMON.getPokemonName());
        assertThat(pokemonFromUrl, is(equalTo(VERIFIED_POKEMON)));
        mockServer.verify();
    }

    @SneakyThrows
    @Test
    void returnException_whenCalledFetchByNameMethod() {
        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI(REST_URL + UNKNOWN_POKEMON.getPokemonName())))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.NOT_FOUND)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(""));
        assertThat(() -> pokemonFetchingService.fetchByName(UNKNOWN_POKEMON.getPokemonName()),
                throwsException(IllegalArgumentException.class));
        mockServer.verify();
    }

    @SneakyThrows
    @Test
    void returnPokemonImageFromServer_whenCalledGetPokemonImageMethod() {
        byte[] imageFromResourceInBytes = readBytesFileFromResources(VERIFIED_POKEMON.getPokemonName() + ".png");
        byte[] imageFromUrlInBytes = pokemonFetchingService.getPokemonImage(VERIFIED_POKEMON.getImageUrl());
        assertThat(imageFromUrlInBytes, is(imageFromResourceInBytes));
    }

    @Test
    void checkIsPredentPokemonImageInTargetFolder_whenCalledGetPokemonImageMethod() {
        File fileWinner = new File(Paths.get("target/winner.png").toUri());
        pokemonFetchingService.getPokemonImage(VERIFIED_POKEMON.getImageUrl());
        assertThat(fileWinner.exists(), is(true));
    }

    @Test
    void returnExceptionForPokemonImageGettingFromServer_whenCalledGetPokemonImageMethod() {
        assertThat(() -> pokemonFetchingService.getPokemonImage(UNKNOWN_POKEMON.getPokemonName() + ".png"),
                throwsException(IllegalArgumentException.class));
    }
}
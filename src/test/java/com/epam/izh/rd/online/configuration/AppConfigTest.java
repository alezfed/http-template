package com.epam.izh.rd.online.configuration;

import com.epam.izh.rd.online.Http;
import com.epam.izh.rd.online.factory.ObjectMapperFactory;
import com.epam.izh.rd.online.factory.SimpleObjectMapperFactory;
import com.epam.izh.rd.online.service.PokemonFetchingService;
import com.epam.izh.rd.online.service.PokemonFightingClubService;
import com.epam.izh.rd.online.service.SimplePokemonFetchingService;
import com.epam.izh.rd.online.service.SimplePokemonFightingClubService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AppConfig.class)
@DisplayName("Tests for checking beans and creating base context objects.")
class AppConfigTest {
    private final RestTemplate restTemplate;
    private final ObjectMapperFactory mapperFactory;
    private final PokemonFetchingService pokemonFetchingService;
    private final PokemonFightingClubService pokemonFightingClubService;

    @Autowired
    public AppConfigTest(RestTemplate restTemplate,
                         SimpleObjectMapperFactory mapperFactory,
                         SimplePokemonFetchingService pokemonFetchingService,
                         SimplePokemonFightingClubService pokemonFightingClubService) {
        this.restTemplate = restTemplate;
        this.mapperFactory = mapperFactory;
        this.pokemonFetchingService = pokemonFetchingService;
        this.pokemonFightingClubService = pokemonFightingClubService;
    }

    @Test
    void checkRestTemplate_whenBeanSuccessCreation() {
        assertThat(restTemplate, is(instanceOf(RestTemplate.class)));
    }

    @Test
    void checkObjectMapperFactory_whenBeanSuccessCreation() {
        assertThat(mapperFactory, is(instanceOf(SimpleObjectMapperFactory.class)));
    }

    @Test
    void checkPokemonFetchingService_whenBeanSuccessCreation() {
        assertThat(pokemonFetchingService, is(instanceOf(SimplePokemonFetchingService.class)));
    }

    @Test
    void checkPokemonFightingClubService_whenBeanSuccessCreation() {
        assertThat(pokemonFightingClubService, is(instanceOf(SimplePokemonFightingClubService.class)));
    }

    @Test
    void checkWorkingHttpClass_whenMainMethodCalled() {
        assertDoesNotThrow(() -> Http.startTrialBattle("pikachu", "slowpoke"));
    }
}
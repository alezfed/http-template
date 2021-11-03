package com.epam.izh.rd.online.factory;

import com.epam.izh.rd.online.entity.Pokemon;
import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@DisplayName("Tests for checking of SimpleObjectMapperFactory.class methods.")
class SimpleObjectMapperFactoryTest {
    private final static ObjectMapperFactory objectMapper = new SimpleObjectMapperFactory();
    private final static Pokemon PIKACHU = new Pokemon(25L, "pikachu", (short) 35, (short) 55, (short) 40,
            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/25.png");

    @SneakyThrows
    @Test
    void getObjectMapper() {
        InputStream inputStream = new FileInputStream(getClass().getClassLoader().getResource("pikachu.json").getFile());
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        Pokemon mappedPokemon = objectMapper.getObjectMapper().readValue(bufferedReader, Pokemon.class);
        assertThat(mappedPokemon, is(equalTo(PIKACHU)));
    }
}
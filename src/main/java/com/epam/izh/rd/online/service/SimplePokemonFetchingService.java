package com.epam.izh.rd.online.service;

import com.epam.izh.rd.online.entity.Pokemon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class SimplePokemonFetchingService implements PokemonFetchingService {
    private final RestTemplate restTemplate;

    @Autowired
    public SimplePokemonFetchingService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Pokemon fetchByName(String name) throws IllegalArgumentException {
        try {
            return restTemplate.getForObject("/" + name, Pokemon.class);
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Error downloading pokemon data.");
        }
    }

    @Override
    public byte[] getPokemonImage(String name) throws IllegalArgumentException {
        try (InputStream in = new URL(name).openStream()) {
            Files.createDirectories(Paths.get("target"));
            Files.copy(in, Paths.get("target/winner.png"), StandardCopyOption.REPLACE_EXISTING);
            return Files.readAllBytes(Paths.get("target/winner.png"));
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Error downloading image.");
        }
    }
}
package com.epam.izh.rd.online.service;

import com.epam.izh.rd.online.entity.Pokemon;

public interface PokemonFetchingService {

    Pokemon fetchByName(String name) throws IllegalArgumentException;

    byte[] getPokemonImage(String name) throws IllegalArgumentException;
}
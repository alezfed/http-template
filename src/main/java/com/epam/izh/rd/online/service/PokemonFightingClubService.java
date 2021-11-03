package com.epam.izh.rd.online.service;

import com.epam.izh.rd.online.entity.Pokemon;

public interface PokemonFightingClubService {

    Pokemon doBattle(Pokemon p1, Pokemon p2);

    void showWinner(Pokemon winner);

    void doDamage(Pokemon from, Pokemon to);
}
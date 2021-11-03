package com.epam.izh.rd.online.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Pokemon {
    private short hp;
    private short attack;
    private short defense;
    @JsonProperty("front_default")
    private String imageUrl;
    @JsonProperty("id")
    private long pokemonId;
    @JsonProperty("name")
    private String pokemonName;

    @JsonCreator
    public Pokemon(@JsonProperty("stats") Stat[] stats, @JsonProperty("sprites") Sprites sprites) {
        this.hp = stats[0].getStatValue();
        this.attack = stats[1].getStatValue();
        this.defense = stats[2].getStatValue();
        this.imageUrl = sprites.getImageUrl();
    }

    public Pokemon(long pokemonId, String pokemonName, short hp, short attack, short defense, String imageUrl) {
        this.pokemonId = pokemonId;
        this.pokemonName = pokemonName;
        this.hp = hp;
        this.attack = attack;
        this.defense = defense;
        this.imageUrl = imageUrl;
    }
}
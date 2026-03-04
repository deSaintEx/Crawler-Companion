package com.techelevator.model;

import java.util.Objects;

public class PlayerCharacter {

    private int characterId;
    private String characterName;
    private int characterLevel;
    private String species;
    private String characterClass;
    private String spells;
    private String weapons;
    private String description;
    private String stats;

    public PlayerCharacter() { }

    public PlayerCharacter(int characterId, String characterName, int characterLevel,
                           String species, String characterClass, String spells, String weapons,
                           String description, String stats) {
        this.characterId = characterId;
        this.characterName = characterName;
        this.characterLevel = characterLevel;
        this.species = species;
        this.characterClass = characterClass;
        this.spells = spells;
        this.weapons = weapons;
        this.description = description;
        this.stats = stats;
    }

    public int getCharacterId() {
        return characterId;
    }

    public void setCharacterId(int characterId) {
        this.characterId = characterId;
    }

    public String getCharacterName() {
        return characterName;
    }

    public void setCharacterName(String characterName) {
        this.characterName = characterName;
    }

    public int getCharacterLevel() {
        return characterLevel;
    }

    public void setCharacterLevel(int characterLevel) {
        this.characterLevel = characterLevel;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public String getCharacterClass() {
        return characterClass;
    }

    public void setCharacterClass(String characterClass) {
        this.characterClass = characterClass;
    }

    public String getSpells() {
        return spells;
    }

    public void setSpells(String spells) {
        this.spells = spells;
    }

    public String getWeapons() {
        return weapons;
    }

    public void setWeapons(String weapons) {
        this.weapons = weapons;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStats() {
        return stats;
    }

    public void setStats(String stats) {
        this.stats = stats;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlayerCharacter character = (PlayerCharacter) o;
        return characterId == character.characterId &&
                Objects.equals(characterName, character.characterName) &&
                Objects.equals(characterLevel, character.characterLevel) &&
                Objects.equals(species, character.species) &&
                Objects.equals(characterClass, character.characterClass) &&
                Objects.equals(spells, character.spells) &&
                Objects.equals(weapons, character.weapons) &&
                Objects.equals(description, character.description) &&
                Objects.equals(stats, character.stats);
    }

    @Override
    public int hashCode() {
        return Objects.hash(characterId, characterName, characterLevel, species, characterClass, spells, weapons, description, stats);
    }

    @Override
    public String toString() {
        return "PlayerCharacter{" +
                "characterId='" + characterId + '\'' +
                ", characterName='" + characterName + '\'' +
                ", characterLevel='" + characterLevel + '\'' +
                ", species='" + species + '\'' +
                ", characterClass='" + characterClass + '\'' +
                ", spells='" + spells + '\'' +
                ", weapons='" + weapons + '\'' +
                ", description='" + description + '\'' +
                ", stats='" + stats + '\'' +
                '}';
    }
}

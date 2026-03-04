package com.techelevator.model;
import java.util.List;
public class CreatePartyDTO {
    private String partyName;
    private List<Integer> characterIds;
    public String getPartyName() {
        return partyName;
    }
    public void setPartyName(String partyName) {
        this.partyName = partyName;
    }
    public List<Integer> getCharacterIds() {
        return characterIds;
    }
    public void setCharacterIds(List<Integer> characterIds) {
        this.characterIds = characterIds;
    }
}

package com.mgs.mazeGameserver;

import java.util.List;

public class GameInfoPacket {
    public List<List<Character>> mapVisibleAreaRepresentation;
    public List<Player> playerList;

    public GameInfoPacket(List<List<Character>> mapAreaResponse, List<Player> playerListResponse){
        mapVisibleAreaRepresentation = mapAreaResponse;
        playerList = playerListResponse;
    }

    public GameInfoPacket(){

    }

    public List<List<Character>> getMapVisibleAreaRepresentation() {
        return mapVisibleAreaRepresentation;
    }

    public List<Player> getPlayerList() {
        return playerList;
    }
}

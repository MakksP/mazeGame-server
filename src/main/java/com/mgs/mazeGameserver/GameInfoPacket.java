package com.mgs.mazeGameserver;

import java.util.List;

public class GameInfoPacket {
    public List<List<VisibleAreaMapPoint>> mapVisibleAreaRepresentation;
    public List<Player> playerList;

    public GameInfoPacket(List<List<VisibleAreaMapPoint>> mapAreaResponse, List<Player> playerListResponse){
        mapVisibleAreaRepresentation = mapAreaResponse;
        playerList = playerListResponse;
    }

    public GameInfoPacket(){

    }

    public List<List<VisibleAreaMapPoint>> getMapVisibleAreaRepresentation() {
        return mapVisibleAreaRepresentation;
    }

    public List<Player> getPlayerList() {
        return playerList;
    }
}

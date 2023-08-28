package com.mgs.mazeGameserver;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GameController {

    @PostMapping("/joinGame/{name}")
    public static int joinGame(@PathVariable String name){
        Player connectingPlayer = new Player(GameService.getRandomCords(), Game.getFirstFreePlayerNumber(), name);
        Game.getPlayerList().add(connectingPlayer);
        Cords connectingPlayerCords = connectingPlayer.getPlayerCords();
        addPlayerToMap(connectingPlayer, connectingPlayerCords);
        return connectingPlayer.getNumber();
    }

    @GetMapping("/getVisibleArea/{playerNumber}")
    public static GameInfoPacket getVisibleArea(@PathVariable int playerNumber){
        return new GameInfoPacket(GameService.getVisibleAreaByPlayerId(playerNumber), Game.getPlayerList());
    }

    @PostMapping("/move/up/{playerNumber}")
    public static void movePlayerUp(@PathVariable int playerNumber){

    }

    @PostMapping("/move/right/{playerNumber}")
    public static void movePlayerRight(@PathVariable int playerNumber){

    }

    @PostMapping("/move/down/{playerNumber}")
    public static void movePlayerDown(@PathVariable int playerNumber){

    }

    @PostMapping("/move/left/{playerNumber}")
    public static void movePlayerLeft(@PathVariable int playerNumber){

    }

    private static void addPlayerToMap(Player connectingPlayer, Cords connectingPlayerCords) {
        Game.getMapRepresentation().get(connectingPlayerCords.getY()).set(connectingPlayerCords.getX(), Character.forDigit(connectingPlayer.getNumber(), 10));
    }
}

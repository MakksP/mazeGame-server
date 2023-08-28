package com.mgs.mazeGameserver;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GameController {

    @PostMapping("/joinGame/{name}")
    public static void joinGame(@PathVariable String name){
        Player connectingPlayer = new Player(GameService.getRandomCords(), Game.getFirstFreePlayerNumber(), name);
        Game.getPlayerList().add(connectingPlayer);
        Cords connectingPlayerCords = connectingPlayer.getPlayerCords();
        addPlayerToMap(connectingPlayer, connectingPlayerCords);
    }

    private static void addPlayerToMap(Player connectingPlayer, Cords connectingPlayerCords) {
        Game.getMapRepresentation().get(connectingPlayerCords.getX()).set(connectingPlayerCords.getY(), Character.forDigit(connectingPlayer.getNumber(), 10));
    }
}

package com.mgs.mazeGameserver;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GameController {

    @PostMapping("/joinGame/{name}")
    public static void joinGame(@PathVariable String name){
        Game.getPlayerList().add(new Player(GameService.getRandomCords(), Game.getFirstFreePlayerNumber(), name));
    }
}

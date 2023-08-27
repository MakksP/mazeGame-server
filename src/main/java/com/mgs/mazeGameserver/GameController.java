package com.mgs.mazeGameserver;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GameController {

    @PostMapping("/joinGame")
    public static void joinGame(){
        Game.getPlayerList().add(new Player(GameService.getRandomCords(), Game.getFirstFreePlayerNumber()));
    }
}

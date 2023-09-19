package com.mgs.mazeGameserver;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

@RestController
public class GameController {

    private static final ReentrantLock joinLock = new ReentrantLock();

    @PostMapping("/joinGame/{name}")
    public static JoinInfo joinGame(@PathVariable String name){
        joinLock.lock();
        Player connectingPlayer = new Player(GameService.getRandomCords(), Game.getFirstFreePlayerNumber(), name);
        Game.getPlayerList().add(connectingPlayer);
        Player.addPlayerToMap(connectingPlayer);
        joinLock.unlock();
        return new JoinInfo(Game.getCampsiteLocation(), connectingPlayer.getNumber());
    }

    @PostMapping("/leaveGame/{playerNumber}")
    public static void leaveGame(@PathVariable int playerNumber){
        joinLock.lock();
        Player.deletePlayerById(playerNumber);
        joinLock.unlock();
    }

    @GetMapping("/getVisibleArea/{playerNumber}")
    public static GameInfoPacket getVisibleArea(@PathVariable int playerNumber){
        return new GameInfoPacket(GameService.getVisibleAreaByPlayerId(playerNumber), Game.getPlayerList());
    }

    @PostMapping("/move/up/{playerNumber}")
    public static void movePlayerUp(@PathVariable int playerNumber){
        TurnSystem.turnLock.lock();
        Player movingPlayer = Player.getPlayerById(playerNumber);
        Cords movingPlayerCords = movingPlayer.getPlayerCords();
        if (GameService.cordsOutOfBoundsAfterGoUp(movingPlayerCords) || GameService.elementAboveIsWall(movingPlayerCords)){
            TurnSystem.turnLock.unlock();
            return;
        }
        Player.clearPlayerFromMap(movingPlayer);
        movingPlayer.movePlayer(MoveDirection.UP);
        Player.addPlayerToMap(movingPlayer);
        TurnSystem.turnLock.unlock();
    }



    @PostMapping("/move/right/{playerNumber}")
    public static void movePlayerRight(@PathVariable int playerNumber){
        TurnSystem.turnLock.lock();
        Player movingPlayer = Player.getPlayerById(playerNumber);
        Cords movingPlayerCords = movingPlayer.getPlayerCords();
        if (GameService.cordsOutOfBoundsAfterGoRight(movingPlayerCords) || GameService.elementOnRightIsWall(movingPlayerCords)){
            TurnSystem.turnLock.unlock();
            return;
        }
        Player.clearPlayerFromMap(movingPlayer);
        movingPlayer.movePlayer(MoveDirection.RIGHT);
        Player.addPlayerToMap(movingPlayer);
        TurnSystem.turnLock.unlock();
    }


    @PostMapping("/move/down/{playerNumber}")
    public static void movePlayerDown(@PathVariable int playerNumber){
        TurnSystem.turnLock.lock();
        Player movingPlayer = Player.getPlayerById(playerNumber);
        Cords movingPlayerCords = movingPlayer.getPlayerCords();
        if (GameService.cordsOutOfBoundsAfterGoDown(movingPlayerCords) || GameService.elementBelowIsWall(movingPlayerCords)){
            TurnSystem.turnLock.unlock();
            return;
        }
        Player.clearPlayerFromMap(movingPlayer);
        movingPlayer.movePlayer(MoveDirection.DOWN);
        Player.addPlayerToMap(movingPlayer);
        TurnSystem.turnLock.unlock();
    }


    @PostMapping("/move/left/{playerNumber}")
    public static void movePlayerLeft(@PathVariable int playerNumber){
        TurnSystem.turnLock.lock();
        Player movingPlayer = Player.getPlayerById(playerNumber);
        Cords movingPlayerCords = movingPlayer.getPlayerCords();
        if (GameService.cordsOutOfBoundsAfterGoLeft(movingPlayerCords) || GameService.elementOnLeftIsWall(movingPlayerCords)){
            TurnSystem.turnLock.unlock();
            return;
        }
        Player.clearPlayerFromMap(movingPlayer);
        movingPlayer.movePlayer(MoveDirection.LEFT);
        Player.addPlayerToMap(movingPlayer);
        TurnSystem.turnLock.unlock();
    }


}

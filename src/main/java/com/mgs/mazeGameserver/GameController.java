package com.mgs.mazeGameserver;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GameController {

    @PostMapping("/joinGame/{name}")
    public static int joinGame(@PathVariable String name){
        Player connectingPlayer = new Player(GameService.getRandomCords(), Game.getFirstFreePlayerNumber(), name);
        Game.getPlayerList().add(connectingPlayer);
        addPlayerToMap(connectingPlayer);
        return connectingPlayer.getNumber();
    }

    @GetMapping("/getVisibleArea/{playerNumber}")
    public static GameInfoPacket getVisibleArea(@PathVariable int playerNumber){
        return new GameInfoPacket(GameService.getVisibleAreaByPlayerId(playerNumber), Game.getPlayerList());
    }

    @PostMapping("/move/up/{playerNumber}")
    public static void movePlayerUp(@PathVariable int playerNumber){
        Player movingPlayer = GameService.getPlayerById(playerNumber);
        Cords movingPlayerCords = movingPlayer.getPlayerCords();
        if (cordsOutOfBoundsAfterGoUp(movingPlayerCords) || elementAboveIsWall(movingPlayerCords)){
            return;
        }
        GameService.clearPlayerFromMap(movingPlayerCords);
        movingPlayerCords.setY(movingPlayerCords.getY() - 1);
        addPlayerToMap(movingPlayer);
    }


    @PostMapping("/move/right/{playerNumber}")
    public static void movePlayerRight(@PathVariable int playerNumber){
        Player movingPlayer = GameService.getPlayerById(playerNumber);
        Cords movingPlayerCords = movingPlayer.getPlayerCords();
        if (cordsOutOfBoundsAfterGoRight(movingPlayerCords) || elementOnRightIsWall(movingPlayerCords)){
            return;
        }
        GameService.clearPlayerFromMap(movingPlayerCords);
        movingPlayerCords.setX(movingPlayerCords.getX() + 1);
        addPlayerToMap(movingPlayer);
    }

    @PostMapping("/move/down/{playerNumber}")
    public static void movePlayerDown(@PathVariable int playerNumber){
        Player movingPlayer = GameService.getPlayerById(playerNumber);
        Cords movingPlayerCords = movingPlayer.getPlayerCords();
        if (cordsOutOfBoundsAfterGoDown(movingPlayerCords) || elementBelowIsWall(movingPlayerCords)){
            return;
        }
        GameService.clearPlayerFromMap(movingPlayerCords);
        movingPlayerCords.setY(movingPlayerCords.getY() + 1);
        addPlayerToMap(movingPlayer);
    }

    @PostMapping("/move/left/{playerNumber}")
    public static void movePlayerLeft(@PathVariable int playerNumber){
        Player movingPlayer = GameService.getPlayerById(playerNumber);
        Cords movingPlayerCords = movingPlayer.getPlayerCords();
        if (cordsOutOfBoundsAfterGoLeft(movingPlayerCords) || elementOnLeftIsWall(movingPlayerCords)){
            return;
        }
        GameService.clearPlayerFromMap(movingPlayerCords);
        movingPlayerCords.setX(movingPlayerCords.getX() - 1);
        addPlayerToMap(movingPlayer);
    }

    private static boolean elementOnRightIsWall(Cords movingPlayerCords) {
        return Game.getMapRepresentation().get(movingPlayerCords.getY()).get(movingPlayerCords.getX() + 1) == '#';
    }

    private static boolean cordsOutOfBoundsAfterGoRight(Cords movingPlayerCords) {
        return movingPlayerCords.getX() + 1 > GameService.MAP_WIDTH - 1;
    }

    private static boolean elementBelowIsWall(Cords movingPlayerCords) {
        return Game.getMapRepresentation().get(movingPlayerCords.getY() + 1).get(movingPlayerCords.getX()) == '#';
    }

    private static boolean cordsOutOfBoundsAfterGoDown(Cords movingPlayerCords) {
        return movingPlayerCords.getY() + 1 > GameService.MAP_HEIGHT - 1;

    }

    private static boolean elementOnLeftIsWall(Cords movingPlayerCords) {
        return Game.getMapRepresentation().get(movingPlayerCords.getY()).get(movingPlayerCords.getX() - 1) == '#';
    }

    private static boolean cordsOutOfBoundsAfterGoLeft(Cords movingPlayerCords) {
        return movingPlayerCords.getX() - 1 < 0;
    }

    private static boolean elementAboveIsWall(Cords movingPlayerCords) {
        return Game.getMapRepresentation().get(movingPlayerCords.getY() - 1).get(movingPlayerCords.getX()) == '#';
    }

    private static boolean cordsOutOfBoundsAfterGoUp(Cords movingPlayerCords) {
        return movingPlayerCords.getY() - 1 < 0;
    }

    private static void addPlayerToMap(Player player) {
        Game.getMapRepresentation().get(player.getPlayerCords().getY()).set(player.getPlayerCords().getX(), Character.forDigit(player.getNumber(), 10));
    }
}

package com.mgs.mazeGameserver;

import org.springframework.stereotype.Service;

@Service
public class GameService {
    public static int MAP_HEIGHT = 36;
    public static int MAP_WIDTH = 76;

    public static Cords getRandomCords(){
        int x = (int) (Math.random() * (MAP_HEIGHT - 1));
        int y = (int) (Math.random() * (MAP_WIDTH - 1));
        return new Cords(x, y);
    }
}

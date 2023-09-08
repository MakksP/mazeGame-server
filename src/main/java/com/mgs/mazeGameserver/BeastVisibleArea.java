package com.mgs.mazeGameserver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BeastVisibleArea {
    public static final Map<Cords, List<Cords>> invisibleFieldsInRelationToTheWalls = new HashMap<Cords, List<Cords>>() {{
        put(new Cords(0, -1), List.of(new Cords(-2, -3), new Cords(-1, -3), new Cords(0, -3), new Cords(1, -3), new Cords(2, -3), new Cords(-1, -2), new Cords(0, -2), new Cords(1, -2)));
        put(new Cords(0, -2), List.of(new Cords(-1, -3), new Cords(0, -3), new Cords(1, -3)));
        put(new Cords(1, 0), List.of(new Cords(3, -2), new Cords(3, -1), new Cords(3, 0), new Cords(3, 1), new Cords(3, 2), new Cords(2, -1), new Cords(2, 0), new Cords(2, 1)));
        put(new Cords(2, 0), List.of(new Cords(3, 1), new Cords(3, 0), new Cords(3, -1)));
        put(new Cords(0, 1), List.of(new Cords(-2, 3), new Cords(-1, 3), new Cords(0, 3), new Cords(1, 3), new Cords(2, 3), new Cords(-1, 2), new Cords(0, 2), new Cords(1, 2)));
        put(new Cords(0, 2), List.of(new Cords(-1, 3), new Cords(0, 3), new Cords(1, 3)));
        put(new Cords(-1, 0), List.of(new Cords(-3, -2), new Cords(-3, -1), new Cords(-3, 0), new Cords(-3, 1), new Cords(-3, 2), new Cords(-2, -1), new Cords(-2, 0), new Cords(-2, 1)));
        put(new Cords(-2, 0), List.of(new Cords(-2, -1), new Cords(-2, 0), new Cords(-2, 1)));

        put(new Cords(-1, -1), List.of(new Cords(-3, -3), new Cords(-2, -3), new Cords(-3, -2), new Cords(-2, -2), new Cords(-1, -2), new Cords(-2, -1)));
        put(new Cords(1, -1), List.of(new Cords(3, -3), new Cords(2, -3), new Cords(3, -2), new Cords(2, -2), new Cords(1, -2), new Cords(2, -1)));
        put(new Cords(1, 1), List.of(new Cords(3, 3), new Cords(2, 3), new Cords(3, 2), new Cords(2, 2), new Cords(1, 2), new Cords(2, 1)));
        put(new Cords(-1, 1), List.of(new Cords(-3, 3), new Cords(-2, 3), new Cords(-3, 2), new Cords(-2, 2), new Cords(-1, 2), new Cords(-2, 1)));

        put(new Cords(-2, -2), List.of(new Cords(-3, -3)));
        put(new Cords(2, -2), List.of(new Cords(3, -3)));
        put(new Cords(2, 2), List.of(new Cords(-3, 3)));
        put(new Cords(-2, 2), List.of(new Cords(-3, 3)));

        put(new Cords(-1, -2), List.of(new Cords(-2, -3), new Cords(-1, -3)));
        put(new Cords(1, -2), List.of(new Cords(2, -3), new Cords(1, -3)));
        put(new Cords(1, 2), List.of(new Cords(2, 3), new Cords(1, 3)));
        put(new Cords(-1, 2), List.of(new Cords(-2, 3), new Cords(-1, 3)));

        put(new Cords(-2, -1), List.of(new Cords(-3, -2), new Cords(-3, -1)));
        put(new Cords(-2, 1), List.of(new Cords(-3, 2), new Cords(-3, 1)));
        put(new Cords(2, -1), List.of(new Cords(3, -2), new Cords(3, -1)));
        put(new Cords(2, 1), List.of(new Cords(3, 2), new Cords(3, 1)));
    }};

    public static void makeActualBeastVisibleArea(List<List<VisibleAreaMapPoint>> beastVisibleArea, Cords beastCords){
        List<Cords> wallPositionsInRelationToPlayer = new ArrayList<>();
        makeWallPositionInRelationToBeast(beastVisibleArea, wallPositionsInRelationToPlayer, beastCords);
        List<Cords> invisibleElementsInRelationToBeast = new ArrayList<>();
        for (Cords wall : wallPositionsInRelationToPlayer){

        }
    }


    public static void makeWallPositionInRelationToBeast(List<List<VisibleAreaMapPoint>> beastVisibleArea, List<Cords> wallPositionsInRelationToBeast, Cords beastCords) {
        for (List<VisibleAreaMapPoint> currentRow : beastVisibleArea){
            for (VisibleAreaMapPoint currentElement : currentRow){
                if (elementIsWall(currentElement)){
                    wallPositionsInRelationToBeast.add(new Cords(currentElement.getElementCords().getX() - beastCords.getX(), currentElement.getElementCords().getY() - beastCords.getY()));
                }
            }
        }
    }

    private static boolean elementIsWall(VisibleAreaMapPoint currentElement) {
        return currentElement.getElement() == '#';
    }
}

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
        put(new Cords(2, 0), List.of(new Cords(3, -1), new Cords(3, 0), new Cords(3, 1)));
        put(new Cords(0, 1), List.of(new Cords(-2, 3), new Cords(-1, 3), new Cords(0, 3), new Cords(1, 3), new Cords(2, 3), new Cords(-1, 2), new Cords(0, 2), new Cords(1, 2)));
        put(new Cords(0, 2), List.of(new Cords(-1, 3), new Cords(0, 3), new Cords(1, 3)));
        put(new Cords(-1, 0), List.of(new Cords(-3, -2), new Cords(-3, -1), new Cords(-3, 0), new Cords(-3, 1), new Cords(-3, 2), new Cords(-2, -1), new Cords(-2, 0), new Cords(-2, 1)));
        put(new Cords(-2, 0), List.of(new Cords(-3, -1), new Cords(-3, 0), new Cords(-3, 1)));

        put(new Cords(-1, -1), List.of(new Cords(-3, -3), new Cords(-2, -3), new Cords(-3, -2), new Cords(-2, -2), new Cords(-1, -2), new Cords(-2, -1)));
        put(new Cords(1, -1), List.of(new Cords(3, -3), new Cords(2, -3), new Cords(3, -2), new Cords(2, -2), new Cords(1, -2), new Cords(2, -1)));
        put(new Cords(1, 1), List.of(new Cords(3, 3), new Cords(2, 3), new Cords(3, 2), new Cords(2, 2), new Cords(1, 2), new Cords(2, 1)));
        put(new Cords(-1, 1), List.of(new Cords(-3, 3), new Cords(-2, 3), new Cords(-3, 2), new Cords(-2, 2), new Cords(-1, 2), new Cords(-2, 1)));

        put(new Cords(-2, -2), List.of(new Cords(-3, -3)));
        put(new Cords(2, -2), List.of(new Cords(3, -3)));
        put(new Cords(2, 2), List.of(new Cords(3, 3)));
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

    public static List<VisibleAreaMapPoint> makeActualBeastVisibleArea(List<List<VisibleAreaMapPoint>> beastVisibleArea, Cords beastCords){
        List<Cords> wallPositionsInRelationToPlayer = new ArrayList<>();
        makeWallPositionInRelationToBeast(beastVisibleArea, wallPositionsInRelationToPlayer, beastCords);
        List<List<Cords>> invisibleElementsInRelationToBeast = new ArrayList<>();
        fillInvisibleElementsList(wallPositionsInRelationToPlayer, invisibleElementsInRelationToBeast);
        convertInvisibleElementsCordsToNatural(beastCords, invisibleElementsInRelationToBeast);
        List<VisibleAreaMapPoint> actualBeastVisibleArea = new ArrayList<>();
        fillActualBeastVisibleArea(beastVisibleArea, invisibleElementsInRelationToBeast, actualBeastVisibleArea);
        return actualBeastVisibleArea;
    }

    private static void fillActualBeastVisibleArea(List<List<VisibleAreaMapPoint>> beastVisibleArea, List<List<Cords>> invisibleElementsInRelationToBeast, List<VisibleAreaMapPoint> actualBeastVisibleArea) {
        for (List<VisibleAreaMapPoint> beastVisibleAreaRow : beastVisibleArea){
            for (VisibleAreaMapPoint beastVisibleAreaElement : beastVisibleAreaRow){
                checkInvisibleElementsList(invisibleElementsInRelationToBeast, actualBeastVisibleArea, beastVisibleAreaElement);
            }
        }
    }

    private static void checkInvisibleElementsList(List<List<Cords>> invisibleElementsInRelationToBeast, List<VisibleAreaMapPoint> actualBeastVisibleArea, VisibleAreaMapPoint beastVisibleAreaElement) {
        for (List<Cords> invisibleElementsRow : invisibleElementsInRelationToBeast){
            for (Cords invisibleElement : invisibleElementsRow){
                if (invisibleElement.cordsAreEqual(beastVisibleAreaElement.getElementCords())){
                    return;
                }
            }
        }
        actualBeastVisibleArea.add(beastVisibleAreaElement);
    }

    private static void fillInvisibleElementsList(List<Cords> wallPositionsInRelationToPlayer, List<List<Cords>> invisibleElementsInRelationToBeast) {
        int invisibleElementsInRelationToBeastCounter = 0;
        for (Cords wall : wallPositionsInRelationToPlayer){
            invisibleElementsInRelationToBeast.add(new ArrayList<>());
            if (invisibleFieldsInRelationToTheWalls.get(wall) == null){
                continue;
            }
            for (Cords hiddenFieldsCords : invisibleFieldsInRelationToTheWalls.get(wall)){
                invisibleElementsInRelationToBeast.get(invisibleElementsInRelationToBeastCounter).add(new Cords(hiddenFieldsCords.getX(), hiddenFieldsCords.getY()));
            }

            invisibleElementsInRelationToBeastCounter++;
        }
    }

    private static void convertInvisibleElementsCordsToNatural(Cords beastCords, List<List<Cords>> invisibleElementsInRelationToBeast) {
        for (List<Cords> invisibleElementsRow : invisibleElementsInRelationToBeast){
            for (Cords invisibleElement : invisibleElementsRow){
                invisibleElement.setX(invisibleElement.getX() + beastCords.getX());
                invisibleElement.setY(invisibleElement.getY() + beastCords.getY());
            }
        }
    }


    public static void makeWallPositionInRelationToBeast(List<List<VisibleAreaMapPoint>> beastVisibleArea, List<Cords> wallPositionsInRelationToBeast, Cords beastCords) {
        for (List<VisibleAreaMapPoint> currentRow : beastVisibleArea){
            for (VisibleAreaMapPoint currentElement : currentRow){
                Cords wallCordsInRelationToBeast = new Cords(currentElement.getElementCords().getX() - beastCords.getX(), currentElement.getElementCords().getY() - beastCords.getY());
                if (elementIsWall(currentElement) && !wallIsOnTheLastRing(wallCordsInRelationToBeast)){
                    wallPositionsInRelationToBeast.add(wallCordsInRelationToBeast);
                }
            }
        }
    }

    private static boolean wallIsOnTheLastRing(Cords currentElement){
        return Math.abs(currentElement.getX()) == 3 || Math.abs(currentElement.getY()) == 3;
    }

    private static boolean elementIsWall(VisibleAreaMapPoint currentElement) {
        return currentElement.getElement() == '#';
    }
}

package com.mgs.mazeGameserver;

import java.util.*;

public class BeastVisibleArea {
    public static final int FIRST_CORDS_INDEX = 0;

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

    private static boolean visitedPointsListHaveSpecificPoint(List<Cords> visitedPoints, Cords cords){
        for (Cords visitedPoint : visitedPoints){
            if (cords.cordsAreEqual(visitedPoint)){
                return true;
            }
        }
        return false;
    }

    private static Cords getNewCordsInMazeAlgorithm(Cords currentConsideredPoint, Cords direction) {
        return new Cords(currentConsideredPoint.getX() + direction.getX(), currentConsideredPoint.getY() + direction.getY());
    }

    private static boolean newCordsInMazeAlgorithmAreInsideMaze(Cords newCords){
        return newCords.getX() >= 0 && newCords.getX() < GameService.MAP_WIDTH && newCords.getY() >= 0 && newCords.getY() < GameService.MAP_HEIGHT;
    }

    private static boolean newCordsInMazeAlgorithmAreNotInWall(Cords newCords){
        return Game.getMapRepresentation().get(newCords.getY()).get(newCords.getX()) != '#';
    }

    private static boolean reachedDestination(Cords destination, Cords currentConsideredPoint) {
        return currentConsideredPoint.getX() == destination.getX() && currentConsideredPoint.getY() == destination.getY();
    }

    public static boolean searchMazeForPaths(Cords beastStartCords, Cords destination, Queue<Cords> pointsQueue, List<Cords> visitedPoints, List<Cords> directions) {
        pointsQueue.add(new Cords(beastStartCords.getX(), beastStartCords.getY()));
        while (!pointsQueue.isEmpty()){
            Cords currentConsideredPoint = pointsQueue.poll();
            for (Cords direction : directions){
                Cords newCords = getNewCordsInMazeAlgorithm(currentConsideredPoint, direction);
                if (newCordsInMazeAlgorithmAreInsideMaze(newCords) && newCordsInMazeAlgorithmAreNotInWall(newCords) && !visitedPointsListHaveSpecificPoint(visitedPoints, newCords)){
                    pointsQueue.add(new Cords(newCords.getX(), newCords.getY()));
                    visitedPoints.add(newCords);

                    if (reachedDestination(destination, newCords)){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static List<Cords> getBeastMovePath(List<Cords> visitedPoints) {
        Collections.reverse(visitedPoints);
        List<Cords> finalBeastPath = new ArrayList<>();
        Cords startCords = visitedPoints.get(FIRST_CORDS_INDEX);
        finalBeastPath.add(startCords);
        for (int visitedPointsIndex = 1; visitedPointsIndex < visitedPoints.size(); visitedPointsIndex++){
            Cords nextPoint = visitedPoints.get(visitedPointsIndex);
            if (startCords.pointsAreSideBySide(nextPoint)){
                finalBeastPath.add(nextPoint);
                startCords = nextPoint;
            }
        }
        Collections.reverse(finalBeastPath);
        return finalBeastPath;
    }
}


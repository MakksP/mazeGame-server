package com.mgs.mazeGameserver;

import java.util.*;

public class Beast extends MovingElement implements Runnable{

    public static final int BEAST_SLOW_DOWN_TIME_MS = 100;
    public static final int FIRST_CORDS_INDEX = 0;

    public Beast(){
        cords = GameService.getRandomCords();
        standsOn = ' ';
    }

    @Override
    public void run() {
        addBeastToMap();
        while (true){
            Cords destination = GameService.getRandomCords();
            Queue<Cords> pointsQueue = new LinkedList<>();
            List<Cords> visitedPoints = new ArrayList<>();
            List<Cords> directions = initPossibleDirectionsList();
            List<Cords> beastMovePath = new ArrayList<>();

            searchMazeForPaths(this.cords, destination, pointsQueue, visitedPoints, directions);
            beastMovePath = getBeastMovePath(visitedPoints);


            for (Cords movePoint : beastMovePath){
                try {
                    Thread.sleep(BEAST_SLOW_DOWN_TIME_MS);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                List<List<VisibleAreaMapPoint>> beastVisibleArea = GameService.getVisibleAreaByCords(cords);
                List<VisibleAreaMapPoint> beastView = BeastVisibleArea.makeActualBeastVisibleArea(beastVisibleArea, this.cords);
                testBeastVisible(beastView);
                TurnSystem.turnLock.lock();
                clearBeastFromMap();
                setNewLocation(movePoint);
                addBeastToMap();
                if (movePoint.cordsAreEqual(destination)){
                    TurnSystem.turnLock.unlock();
                    break;
                }
                TurnSystem.turnLock.unlock();
            }

        }
    }

    private static void testBeastVisible(List<VisibleAreaMapPoint> beastView) {
        for (int i = 0; i < Game.getMapRepresentation().size(); i++){
            for (int j = 0; j < Game.getMapRepresentation().get(i).size(); j++) {
                if (Game.getMapRepresentation().get(i).get(j) == '@'){
                    Game.getMapRepresentation().get(i).set(j, ' ');
                }
            }
        }

        for (VisibleAreaMapPoint singleViewPoint : beastView){
            if (Game.getMapRepresentation().get(singleViewPoint.getElementCords().getY()).get(singleViewPoint.getElementCords().getX()) == ' '){
                Game.getMapRepresentation().get(singleViewPoint.getElementCords().getY()).set(singleViewPoint.getElementCords().getX(), '@');
            }
        }
    }

    private static List<Cords> getBeastMovePath(List<Cords> visitedPoints) {
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

    private static boolean searchMazeForPaths(Cords beastStartCords, Cords destination, Queue<Cords> pointsQueue, List<Cords> visitedPoints, List<Cords> directions) {
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

    private static List<Cords> initPossibleDirectionsList() {
        List<Cords> directions = new ArrayList<>();
        directions.add(new Cords(0, 1));
        directions.add(new Cords(0, -1));
        directions.add(new Cords(1, 0));
        directions.add(new Cords(-1, 0));
        return directions;
    }

    private static boolean reachedDestination(Cords destination, Cords currentConsideredPoint) {
        return currentConsideredPoint.getX() == destination.getX() && currentConsideredPoint.getY() == destination.getY();
    }

    private void addBeastToMap() {
        standsOn = Game.getMapRepresentation().get(cords.getY()).get(cords.getX());
        Game.getMapRepresentation().get(cords.getY()).set(cords.getX(), '*');
    }

    private void clearBeastFromMap(){
        Game.getMapRepresentation().get(cords.getY()).set(cords.getX(), standsOn);
    }

    public char getStandsOn() {
        return standsOn;
    }

    public void setStandsOn(char standsOn) {
        this.standsOn = standsOn;
    }
}

package com.mgs.mazeGameserver;

import java.util.*;

public class Beast extends MovingElement implements Runnable{

    public static final int BEAST_SLOW_DOWN_TIME_MS = 100;

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

            BeastVisibleArea.searchMazeForPaths(this.cords, destination, pointsQueue, visitedPoints, directions);
            beastMovePath = BeastVisibleArea.getBeastMovePath(visitedPoints);


            for (Cords movePoint : beastMovePath){
                try {
                    Thread.sleep(BEAST_SLOW_DOWN_TIME_MS);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                List<VisibleAreaMapPoint> beastView = getBeastView();
                Cords playerCords = playerCordsSeenByBeast(beastView);

                if (beastSeePlayer(playerCords)){
                    actionAfterPlayerDetection(pointsQueue, visitedPoints, directions, playerCords);
                    break;
                }

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

    private void actionAfterPlayerDetection(Queue<Cords> pointsQueue, List<Cords> visitedPoints, List<Cords> directions, Cords playerCords) {
        pointsQueue.clear();
        visitedPoints.clear();
        serveBeastAttack(playerCords, pointsQueue, visitedPoints, directions);
    }

    private void serveBeastAttack(Cords destination, Queue<Cords> pointsQueue, List<Cords> visitedPoints, List<Cords> directions) {
        List<Cords> beastMovePath;
        BeastVisibleArea.searchMazeForPaths(this.cords, destination, pointsQueue, visitedPoints, directions);
        beastMovePath = BeastVisibleArea.getBeastMovePath(visitedPoints);
        for (Cords attackPoint : beastMovePath){
            try {
                Thread.sleep(BEAST_SLOW_DOWN_TIME_MS);
            } catch (Exception e){
                throw new RuntimeException(e);
            }
            TurnSystem.turnLock.lock();
            clearBeastFromMap();
            setNewLocation(attackPoint);
            addBeastToMap();
            if (attackPoint.cordsAreEqual(destination)){
                TurnSystem.turnLock.unlock();
                break;
            }
            TurnSystem.turnLock.unlock();
        }
    }

    private static boolean beastSeePlayer(Cords playerCords) {
        return playerCords.getY() != -1 && playerCords.getX() != -1;
    }

    private Cords playerCordsSeenByBeast(List<VisibleAreaMapPoint> beastView) {
        for (VisibleAreaMapPoint beastViewPoint : beastView){
            if (beastViewPoint.getElement() == '1'){
                return beastViewPoint.getElementCords();
            }
        }
        return new Cords(-1, -1);
    }

    private List<VisibleAreaMapPoint> getBeastView() {
        List<List<VisibleAreaMapPoint>> beastVisibleArea = GameService.getVisibleAreaByCords(cords);
        List<VisibleAreaMapPoint> beastView = BeastVisibleArea.makeActualBeastVisibleArea(beastVisibleArea, this.cords);
        return beastView;
    }

    private static void testBeastVisible(List<VisibleAreaMapPoint> beastView) {

        for (VisibleAreaMapPoint singleViewPoint : beastView){
            if (Game.getMapRepresentation().get(singleViewPoint.getElementCords().getY()).get(singleViewPoint.getElementCords().getX()) == ' '){
                Game.getMapRepresentation().get(singleViewPoint.getElementCords().getY()).set(singleViewPoint.getElementCords().getX(), '@');
            }
        }
    }

    private static void clearBeastVisible(){
        for (int i = 0; i < Game.getMapRepresentation().size(); i++){
            for (int j = 0; j < Game.getMapRepresentation().get(i).size(); j++) {
                if (Game.getMapRepresentation().get(i).get(j) == '@'){
                    Game.getMapRepresentation().get(i).set(j, ' ');
                }
            }
        }
    }


    private static List<Cords> initPossibleDirectionsList() {
        List<Cords> directions = new ArrayList<>();
        directions.add(new Cords(0, 1));
        directions.add(new Cords(0, -1));
        directions.add(new Cords(1, 0));
        directions.add(new Cords(-1, 0));
        return directions;
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

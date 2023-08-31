package com.mgs.mazeGameserver;

public class Beast extends MovingElement implements Runnable{

    public static final int BEAST_SLOW_DOWN_TIME_MS = 40;

    public Beast(){
        cords = GameService.getRandomCords();
        standsOn = ' ';
    }

    @Override
    public void run() {
        addBeastToMap();
        while (true){
            try {
                Thread.sleep(BEAST_SLOW_DOWN_TIME_MS);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            TurnSystem.turnLock.lock();
            int direction = (int) (Math.random() * 4);
            if (direction == 0){
                if (GameService.cordsOutOfBoundsAfterGoUp(cords) || GameService.elementAboveIsWall(cords)){
                    TurnSystem.turnLock.unlock();
                    continue;
                }
                clearBeastFromMap();
                GameService.moveElementUp(this);
                addBeastToMap();
            }else if (direction == 1){
                if (GameService.cordsOutOfBoundsAfterGoRight(cords) || GameService.elementOnRightIsWall(cords)){
                    TurnSystem.turnLock.unlock();
                    continue;
                }
                clearBeastFromMap();
                GameService.moveElementRight(this);
                addBeastToMap();
            }else if (direction == 2){
                if (GameService.cordsOutOfBoundsAfterGoDown(cords) || GameService.elementBelowIsWall(cords)){
                    TurnSystem.turnLock.unlock();
                    continue;
                }
                clearBeastFromMap();
                GameService.moveElementDown(this);
                addBeastToMap();
            } else if (direction == 3){
                if (GameService.cordsOutOfBoundsAfterGoLeft(cords) || GameService.elementOnLeftIsWall(cords)){
                    TurnSystem.turnLock.unlock();
                    continue;
                }
                clearBeastFromMap();
                GameService.moveElementLeft(this);
                addBeastToMap();
            }
            TurnSystem.turnLock.unlock();
        }
    }

    private void addBeastToMap() {
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

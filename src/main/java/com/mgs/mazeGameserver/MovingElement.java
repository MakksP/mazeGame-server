package com.mgs.mazeGameserver;

public class MovingElement {
    protected Cords cords;
    protected char standsOn;

    public void moveElementLeft() {
        int newPlayerX = this.cords.getX() - 1;
        this.standsOn = Game.getMapRepresentation().get(this.cords.getY()).get(newPlayerX);
        this.cords.setX(newPlayerX);
    }

    public void moveElementDown() {
        int newPlayerY = this.cords.getY() + 1;
        this.standsOn = Game.getMapRepresentation().get(newPlayerY).get(this.cords.getX());
        this.cords.setY(this.cords.getY() + 1);
    }

    public void moveElementRight() {
        int newPlayerX = this.cords.getX() + 1;
        this.standsOn = Game.getMapRepresentation().get(this.cords.getY()).get(newPlayerX);
        this.cords.setX(newPlayerX);
    }

    public void moveElementUp() {
        int newPlayerY = this.cords.getY() - 1;
        this.standsOn = Game.getMapRepresentation().get(newPlayerY).get(this.cords.getX());
        this.cords.setY(newPlayerY);
    }

    public void setNewLocation(Cords newCords){
        this.cords.setX(newCords.getX());
        this.cords.setY(newCords.getY());
    }
}

package com.mgs.mazeGameserver;

public class MovingElement {
    protected Cords cords;
    protected char standsOn;

    public void moveElementLeft() {
        int newElementX = this.cords.getX() - 1;
        this.standsOn = Game.getMapRepresentation().get(this.cords.getY()).get(newElementX);
        this.cords.setX(newElementX);
    }

    public void moveElementDown() {
        int newElementY = this.cords.getY() + 1;
        this.standsOn = Game.getMapRepresentation().get(newElementY).get(this.cords.getX());
        this.cords.setY(this.cords.getY() + 1);
    }

    public void moveElementRight() {
        int newElementX = this.cords.getX() + 1;
        this.standsOn = Game.getMapRepresentation().get(this.cords.getY()).get(newElementX);
        this.cords.setX(newElementX);
    }

    public void moveElementUp() {
        int newElementY = this.cords.getY() - 1;
        this.standsOn = Game.getMapRepresentation().get(newElementY).get(this.cords.getX());
        this.cords.setY(newElementY);
    }

    public void setNewLocation(Cords newCords){
        this.cords.setX(newCords.getX());
        this.cords.setY(newCords.getY());
    }

    public char getStandsOn(){
        return standsOn;
    }
}

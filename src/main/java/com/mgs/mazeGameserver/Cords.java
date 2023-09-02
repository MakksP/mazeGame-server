package com.mgs.mazeGameserver;

public class Cords {
    private int x;
    private int y;

    public Cords(int x, int y){
        this.x = x;
        this.y = y;
    }

    public Cords(){

    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean cordsAreEqual(Cords cords){
        return this.x == cords.x && this.y == cords.y;
    }

    public boolean pointsAreSideBySide(Cords cords){
        return (this.x == cords.x + 1 && this.y == cords.y)
                || (this.x == cords.x - 1 && this.y == cords.y)
                || (this.x == cords.x && this.y == cords.y - 1)
                || (this.x == cords.x && this.y == cords.y + 1);
    }
}

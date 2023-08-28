package com.mgs.mazeGameserver;

public class VisibleAreaMapPoint {
    private Cords elementCords;
    private char element;

    public VisibleAreaMapPoint(Cords elementCords, char element){
        this.elementCords = elementCords;
        this.element = element;
    }

    public VisibleAreaMapPoint(){

    }

    public Cords getElementCords() {
        return elementCords;
    }

    public void setElementCords(Cords elementCords) {
        this.elementCords = elementCords;
    }

    public char getElement() {
        return element;
    }

    public void setElement(char element) {
        this.element = element;
    }
}

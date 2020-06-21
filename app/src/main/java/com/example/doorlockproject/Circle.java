package com.example.doorlockproject;

public class Circle {
    private boolean active;

    public Circle(boolean b) {
        this.active = b;
    }

    public Circle() {
    }
    public boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
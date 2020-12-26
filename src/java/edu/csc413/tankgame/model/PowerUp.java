package edu.csc413.tankgame.model;

public class PowerUp extends Entity {
    public PowerUp(String id, double x, double y, double angle, double MOVEMENT_SPEED) {
        super(id, x, y, angle, MOVEMENT_SPEED);
    }

    @Override
    public void checkBounds(GameState gameState) {

    }

    @Override
    public void move(GameState gameState) {

    }
    @Override
    public double getXBound() {
        return getX() + 40.0;
    }

    @Override
    public double getYBound() {
        return getY() + 40.0;
    }

}

package edu.csc413.tankgame.model;

public class Walls extends Entity {
    private  int health;
    public Walls(String id, double x, double y, double angle, double MOVEMENT_SPEED) {
        super(id, x, y, angle, MOVEMENT_SPEED);
        health = 3;
    }

   public void decrementHealth() {
        health--;
    }

    public int getHealth() {
        return health;
    }


    @Override
    public void checkBounds(GameState gameState) {

    }

    @Override
    public double getXBound() {
        return getX() + 30.0;
    }

    @Override
    public double getYBound() {
        return getY() + 30.0;
    }



    @Override
    public void move(GameState gameState) {}

}

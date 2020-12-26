package edu.csc413.tankgame.model;

public abstract class Entity {

    private final String id;
    private double x;
    private double y;
    private double angle;
    private final double MOVEMENT_SPEED;
    private static final double TURN_SPEED = Math.toRadians(3.0);
    public Entity(
            String id,
            double x,
            double y,
            double angle ,
            double MOVEMENT_SPEED
        ) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.angle = angle;
       this.MOVEMENT_SPEED = MOVEMENT_SPEED;
    }


    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public String getId() {
        return id;
    }

    public double getX()
    {
        return x;
    }

    public  double getY() {
        return y;
    }

    public double getAngle() {
        return angle;
    }

    //    public double getXBound() {
//        return getX() + 55.0;
//    }
//    public double getYBound() {
//        return getY() + 24.0;
//    }
    public abstract double getXBound();
    public abstract double getYBound();


    public abstract void move(GameState gameState);

    // TODO: The methods below are provided so you don't have to do the math for movement. However, note that they are
    // protected. You should not be calling these methods directly from outside the Tank class hierarchy. Instead,
    // consider how to design your Tank class(es) so that a Tank can represent both a player-controlled tank and an AI
    // controlled tank.
//


    protected void moveForward() {
        x += MOVEMENT_SPEED * Math.cos(angle);
        y += MOVEMENT_SPEED * Math.sin(angle);
    }

    protected void moveBackward() {
        x -= MOVEMENT_SPEED * Math.cos(angle);
        y -= MOVEMENT_SPEED * Math.sin(angle);
    }

    protected void turnLeft() {
        angle -= TURN_SPEED;
    }

    protected void turnRight() {
        angle += TURN_SPEED;
    }

    public abstract void checkBounds(GameState gameState);
}

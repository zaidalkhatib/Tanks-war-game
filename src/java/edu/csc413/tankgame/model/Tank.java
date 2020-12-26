package edu.csc413.tankgame.model;

import edu.csc413.tankgame.view.RunGameView;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Model class representing a tank in the game. A tank has a position and an angle. It has a movement speed and a turn
 * speed, both represented below as constants.
 */
// TODO: Notice that Tank has a lot in common with Shell. For full credit, you will need to find a way to share code
// between the two classes so that the logic for e.g. moveForward, etc. are not duplicated.
public  abstract class Tank extends Entity {
    static final double TURN_SPEED = Math.toRadians(3.0);
    private int coolDown;
    private int health;

    public Tank(String id, double x, double y, double angle,double MOVEMENT_SPEED) {
        super(id, x, y, angle, MOVEMENT_SPEED);
        health = 4;
    }


    public void decrementHealth() {
        health--;
    }

    public int getHealth() {
        return health;
    }

    public void addHealth() {
        health = 4;
    }

    @Override
    public double getXBound() {
        return getX() + 55.0;
    }

    @Override
    public double getYBound() {
        return getY() + 55.0;
    }

    public void collidingMove(boolean isMoveX , boolean isMoveY, boolean subtraction , boolean addition, double val) {

            if (isMoveX) {
                if (addition) {
                    setX(val+getX());
                } else if (subtraction) {
                    setX(getX()-val);
                }
            }
            if (isMoveY)
            {
                if (addition) {
                    setY(val+getY());
                }
                else if (subtraction) {
                    setY( getY()-val);
                }
            }
        }

        @Override
        public void checkBounds( GameState gameState)
        {
            if (getX() < GameState.TANK_X_LOWER_BOUND) {
                setX(GameState.TANK_X_LOWER_BOUND);
            } if (getX() > GameState.TANK_X_UPPER_BOUND) {
                setX(GameState.TANK_X_UPPER_BOUND);
            } if (getY() < GameState.TANK_Y_LOWER_BOUND) {
           setY(GameState.TANK_Y_LOWER_BOUND);
            } if (getY() > GameState.TANK_Y_UPPER_BOUND) {
            setY(GameState.TANK_Y_UPPER_BOUND);
            }
        }

    @Override
    public void move(GameState gameState) {
        coolDown--;
        if (coolDown == -1) {
            coolDown = 200;
        }
    }
    // The following methods will be useful for determining where a shell should be spawned when it
    // is created by this tank. It needs a slight offset so it appears from the front of the tank,
    // even if the tank is rotated. The shell should have the same angle as the tank.


    public int getCoolDown() {
        return coolDown;
    }

    public void shoot(GameState gameState) {

            Entity playerShell = new Shell(
                    getShellX(),
                    getShellY(),
                    getAngle(),
                    RunGameView.SHELL_MOVEMENT_SPEED);
            gameState.setNewShells(playerShell);


    }


    private double getShellX() {
        return getX() + 30.0 * (Math.cos(getAngle()) + 0.5);
    }


    private double getShellY() {
        return getY() + 30.0 * (Math.sin(getAngle()) + 0.5);
    }
}

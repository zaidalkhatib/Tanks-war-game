package edu.csc413.tankgame.model;

/**
 * Model class representing a shell that has been fired by a tank. A shell has a position and an angle, as well as a
 * speed. Shells by default should be unable to turn and only move forward.
 */
// TODO: Notice that Shell has a lot in common with Tank. For full credit, you will need to find a way to share code
// between the two classes so that the logic for e.g. moveForward, etc. are not duplicated.
public class Shell  extends Entity{
    private static final String SHELL_ID_PREFIX = "shell-";
    private static long uniqueId = 0L;
    String callerClassName = new Exception().getStackTrace()[2].getClassName();


    public String getCallerClassName() {
        return callerClassName;
    }

    public Shell(
            double x,
            double y,
            double angle, double MOVEMENT_SPEED) {
        super(getUniqueId(), x, y, angle,MOVEMENT_SPEED);
        getUniqueId();
    }

    @Override
    public double getXBound() {
        return getX() + 24.0;
    }

    @Override
    public double getYBound() {
        return getY() + 24.0;
    }

    public static String getUniqueId() {
        return SHELL_ID_PREFIX + uniqueId++;
    }

    @Override
    public void checkBounds(GameState gameState) {
        for (Entity entity : gameState.getEntities()) {
            if (
                    entity.getX() < GameState.SHELL_X_LOWER_BOUND || entity.getX() > GameState.SHELL_X_UPPER_BOUND ||
                            entity.getY() < GameState.SHELL_Y_LOWER_BOUND || entity.getY() > GameState.SHELL_Y_UPPER_BOUND
            ) {
                gameState.setDeleteShell(entity);
            }
        }
    }



    @Override
    public void move(GameState gameState) {
        moveForward();
    }
}

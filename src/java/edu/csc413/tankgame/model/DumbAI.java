package edu.csc413.tankgame.model;

public class DumbAI extends Tank {
    @Override
    public void move(GameState gameState) {
        super.move(gameState);

        Entity playerTank = gameState.getEntity(GameState.PLAYER_TANK_ID);
        if (playerTank != null) {
        double dx = playerTank.getX() - getX();
        double dy = playerTank.getY() - getY() ;
        double angleToPlayer = Math.atan2(dy, dx);
        double angleDiff = getAngle() - angleToPlayer;
        angleDiff -= Math.floor(angleDiff / Math.toRadians(360.0) + 0.5) * Math.toRadians(360.0);
        if (angleDiff < -TURN_SPEED) {
            turnRight();

        } else if (angleDiff > TURN_SPEED) {

            turnLeft();
        }
            if (getCoolDown() == 0)
            {
                shoot(gameState);
            }
        }
    }

    public DumbAI(String id, double x, double y, double angle, double MOVEMENT_SPEED) {
        super(id, x, y, angle, MOVEMENT_SPEED);
    }

}

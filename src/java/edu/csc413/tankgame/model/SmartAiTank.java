package edu.csc413.tankgame.model;

public class SmartAiTank extends Tank {

    public SmartAiTank(String id, double x, double y, double angle, double MOVEMENT_SPEED) {
        super(id, x, y, angle, MOVEMENT_SPEED);
    }

    @Override
    public void move(GameState gameState) {
        super.move(gameState);
        Entity playerTank = gameState.getEntity(GameState.PLAYER_TANK_ID);
        if (playerTank != null) {
        double dx = playerTank.getX() - getX();
        double dy = playerTank.getY() - getY();
        double angleToPlayer = Math.atan2(dy, dx);
        double angleDiff = getAngle() - angleToPlayer;
        angleDiff -= Math.floor(angleDiff / Math.toRadians(360.0) + 0.5) * Math.toRadians(360.0);
        if (angleDiff < -TURN_SPEED) {
            turnRight();
        } else if (angleDiff > TURN_SPEED) {
            turnLeft();
        }

        double distance = Math.sqrt(dx * dx + dy * dy);

        if (distance>300.0) {
            moveForward();

        }
        if (distance < 110.0) {
            moveBackward();

        }

        if (getCoolDown() == 0)
        {
            shoot(gameState);
        }
        }
    }


}

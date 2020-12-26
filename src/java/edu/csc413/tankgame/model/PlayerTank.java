package edu.csc413.tankgame.model;

import edu.csc413.tankgame.view.MainView;
import edu.csc413.tankgame.view.RunGameView;

public class PlayerTank extends Tank {
    private MainView mainView;
    private int cool = 80;

    public PlayerTank(
            String id,
            double x,
            double y,
            double angle,
            double MOVEMENT_SPEED,
            MainView mainView
    ) {
        super(id, x, y, angle, MOVEMENT_SPEED);
        this.mainView = mainView;
    }


    RunGameView runGameView = new RunGameView();
    @Override
    public void move(GameState gameState) {
        cool--;
        if (gameState.isUpPressed()) {
            moveForward();
        }
        if (gameState.isDownPressed()) {
            moveBackward();
        }
        if (gameState.isRightPressed()) {
            turnRight();
        }
        if (gameState.isLeftPressed()) {
            turnLeft();
        }

        if (gameState.isSpacePressed()) {
            if (cool < 0) {
                shoot(gameState);
                cool=80;
            }
        }
        else if (gameState.isEscapePressed()) {

            mainView.setScreen(MainView.Screen.END_MENU_SCREEN);
            gameState.getEntities().clear();
            runGameView.reset();
        }

    }

}

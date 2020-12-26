package edu.csc413.tankgame.model;

import edu.csc413.tankgame.view.RunGameView;

import java.util.ArrayList;
import java.util.List;

/**
 * GameState represents the state of the game "world." The GameState object tracks all of the moving entities like tanks
 * and shells, and provides the controller of the program (i.e. the GameDriver) access to whatever information it needs
 * to run the game. Essentially, GameState is the "data context" needed for the rest of the program.
 */
public class GameState {

    public static final double TANK_X_LOWER_BOUND = 30.0;
    public static final double TANK_X_UPPER_BOUND = RunGameView.SCREEN_DIMENSIONS.width - 100.0;
    public static final double TANK_Y_LOWER_BOUND = 30.0;
    public static final double TANK_Y_UPPER_BOUND = RunGameView.SCREEN_DIMENSIONS.height - 120.0;

    public static final double SHELL_X_LOWER_BOUND = -10.0;
    public static final double SHELL_X_UPPER_BOUND = RunGameView.SCREEN_DIMENSIONS.width;
    public static final double SHELL_Y_LOWER_BOUND = -10.0;
    public static final double SHELL_Y_UPPER_BOUND = RunGameView.SCREEN_DIMENSIONS.height;

    public static final String PLAYER_TANK_ID = "player-tank";
    public static final String AI_TANK_ID = "ai-tank";
    public static final String DUMB_AI_ID = "dumb-tank";
    public static final String PowerUp_ID = "Power-Up";
    public static final String PowerUp_ID_2 = "Power-Up_2";

    private boolean upPressed=false;
    private boolean downPressed=false;
    private boolean leftPressed=false;
    private boolean rightPressed=false;
    private boolean spacePressed=false;
    private boolean escapePressed=false;
// TODO: Feel free to add more tank IDs if you want to support multiple AI tanks! Just make sure they're unique.

    // TODO: Implement.
    // There's a lot of information the GameState will need to store to provide contextual information. Add whatever
    // instance variables, constructors, and methods are needed.
    private final List<Entity> entities = new ArrayList<>();

    public void  addEntity(Entity entity) {
        entities.add(entity);
    }

    public List<Entity> getEntities() {
        return entities;
    }

    public void  removeEntity(Entity entity) {
                entities.remove(entity);
    }
    public Entity getEntity(String id) {
        Entity entity = null;
        for (Entity e : entities) {
            if (e.getId().equals(id)) {
                entity = e;
            }
        }
        return entity;
    }


    public List<Entity> tempAdditionList = new ArrayList<>();
    public void setNewShells(Entity entity) {
        tempAdditionList.add(entity);
    }
    public void clearAdditionTempEntities() { tempAdditionList.clear(); }

    public List<Entity> getTempAdditionListEntities() { return tempAdditionList; }
    public void clearDeletionTempEntities() {
        tempDeletionList.clear();
    }

    private List<Entity> tempDeletionList = new ArrayList<>();

    public void setDeleteShell(Entity entity) {
        tempDeletionList.add(entity);
    }

    public List<Entity> getTempDeletionList() {
        return tempDeletionList;
    }



    public boolean isUpPressed() {
        return upPressed;
    }

    public void setUpPressed(boolean upPressed) {
        this.upPressed = upPressed;
    }

    public boolean isDownPressed() {
        return downPressed;
    }

    public void setDownPressed(boolean downPressed) {
        this.downPressed = downPressed;
    }

    public boolean isLeftPressed() {

        return leftPressed;
    }

    public void setLeftPressed(boolean leftPressed) {
        this.leftPressed = leftPressed;
    }

    public boolean isRightPressed() {
        return rightPressed;
    }

    public void setRightPressed(boolean rightPressed) {
        this.rightPressed = rightPressed;
    }
    public boolean isSpacePressed() {
        return spacePressed;
    }

    public void setSpacePressed(boolean spacePressed) {
        this.spacePressed = spacePressed;
    }

    public boolean isEscapePressed() {
        return escapePressed;
    }

    public void setEscapePressed(boolean escapePressed) {
        this.escapePressed = escapePressed;
    }

}

package edu.csc413.tankgame;

import edu.csc413.tankgame.model.*;
import edu.csc413.tankgame.view.MainView;
import edu.csc413.tankgame.view.RunGameView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static edu.csc413.tankgame.view.StartMenuView.EXIT_BUTTON_ACTION_COMMAND;
import static edu.csc413.tankgame.view.StartMenuView.START_BUTTON_ACTION_COMMAND;

/**
 * GameDriver is the primary controller class for the tank game. The game is launched from GameDriver.main, and
 * GameDriver is responsible for running the game loop while coordinating the views and the data models.
 */
public class GameDriver implements ActionListener {

    // TODO: Implement.
    // Add the instance variables, constructors, and other methods needed for this class. GameDriver is the centerpiece
    // for the tank game, and should store and manage the other components (i.e. the views and the models). It also is
    // responsible for running the game loop.
    private final MainView mainView;
    private final RunGameView runGameView;
    private final GameState gameState;
    private GameDriver gameDriver = this;
    private int killedTanks = 0;
    private int id = 0;



    public GameDriver() {
        gameState = new GameState();
        GameKeyListener listener = new GameKeyListener(gameState);
        mainView = new MainView(listener, gameDriver);
        runGameView = mainView.getRunGameView();
    }


    @Override
    public void actionPerformed(ActionEvent e) {

        String actionCommand = e.getActionCommand();
        if (actionCommand.equals(START_BUTTON_ACTION_COMMAND)) {
            mainView.setScreen(MainView.Screen.RUN_GAME_SCREEN);
            runGame();
        } else if (actionCommand.equals(EXIT_BUTTON_ACTION_COMMAND)) {
            mainView.closeGame();
        }

    }

    public void start() {
        mainView.setScreen(MainView.Screen.START_MENU_SCREEN);
    }

    Entity playerTank = null;
    Entity dumbAI = null;
    Entity aiTank = null;
    Entity powerUp = null;
    Entity powerUp2 = null;
    private void runGame() {

        //create new tank
        playerTank = new PlayerTank(

                GameState.PLAYER_TANK_ID,
                RunGameView.PLAYER_TANK_INITIAL_X,
                RunGameView.PLAYER_TANK_INITIAL_Y,
                RunGameView.PLAYER_TANK_INITIAL_ANGLE,
                RunGameView.PLAYER_MOVEMENT_SPEED, mainView);

        //create new AI
        aiTank = new SmartAiTank(
                GameState.AI_TANK_ID,
                RunGameView.AI_TANK_INITIAL_X,
                RunGameView.AI_TANK_INITIAL_Y,
                RunGameView.AI_TANK_INITIAL_ANGLE,
                RunGameView.AI_MOVEMENT_SPEED);
        //create dumb AI
        dumbAI = new DumbAI(
                GameState.DUMB_AI_ID,
                RunGameView.AI_TANK_2_INITIAL_X,
                RunGameView.AI_TANK_2_INITIAL_Y,
                RunGameView.AI_TANK_2_INITIAL_ANGLE,
                RunGameView.DUMB_AI_MOVEMENT_SPEED);

         powerUp = new PowerUp(
                GameState.PowerUp_ID,
                RunGameView.POWER_UP_X,
                RunGameView.POWER_UP_Y,
                0,
                0);

        powerUp2 = new PowerUp(
                GameState.PowerUp_ID_2,
                RunGameView.POWER_UP_2_X,
                RunGameView.POWER_UP_2_Y,
                0,
                0);

        for (WallImageInfo readWall : WallImageInfo.readWalls()) {
            String wallId = "wall";
            Entity walls = new Walls(wallId + id, readWall.getX(), readWall.getY(), 0, 0);
            gameState.addEntity(walls);
            runGameView.addDrawableEntity(wallId + id, readWall.getImageFile(), walls.getX(), walls.getY(), 0);
            id++;
        }
        //add player to the UI
        runGameView.addDrawableEntity(
                GameState.PLAYER_TANK_ID,
                RunGameView.PLAYER_TANK_IMAGE_FILE,
                playerTank.getX(), playerTank.getY(),
                playerTank.getAngle()
        );

        //add AI  to the UI
        runGameView.addDrawableEntity(GameState.AI_TANK_ID,
                RunGameView.AI_TANK_IMAGE_FILE,
                aiTank.getX(),
                aiTank.getY(),
                aiTank.getAngle());


        runGameView.addDrawableEntity(GameState.DUMB_AI_ID,
                RunGameView.AI_TANK_IMAGE_FILE,
                dumbAI.getX(),
                dumbAI.getY(),
                dumbAI.getAngle());

        runGameView.addDrawableEntity(
                GameState.PowerUp_ID,
                RunGameView.POWER_UP_IMAGE_FILE,
                powerUp.getX(),
                powerUp.getY(),
                powerUp.getAngle());

        runGameView.addDrawableEntity(
                GameState.PowerUp_ID_2,
                RunGameView.POWER_UP_IMAGE_FILE,
                powerUp.getX(),
                powerUp.getY(),
                powerUp.getAngle());

        gameState.addEntity(playerTank);
        gameState.addEntity(aiTank);
        gameState.addEntity(dumbAI);
        gameState.addEntity(powerUp);
        gameState.addEntity(powerUp2);


        Runnable gameRunner = () -> {
            while (update()) {
                runGameView.repaint();
                try {
                    Thread.sleep(8L);
                } catch (InterruptedException exception) {
                    throw new RuntimeException(exception);
                }
            }
        };
        new Thread(gameRunner).start();


    }

    private boolean entitiesOverlap(Entity entity1, Entity entity2) {
        return entity1.getX() < entity2.getXBound()
                && entity1.getXBound() > entity2.getX()
                && entity1.getY() < entity2.getYBound()
                && entity1.getYBound() > entity2.getY();
    }
    // TODO: Implement.
    // update should handle one frame of gameplay. All tanks and shells move one step, and all drawn entities
    // should be updated accordingly. It should return true as long as the game continues.

    private boolean update() {


        for (Entity e : gameState.getEntities()) {

            e.move(gameState);
            e.checkBounds(gameState);
        }
        for (Entity entity : gameState.getTempAdditionListEntities()) {
            gameState.addEntity(entity);
            runGameView.addDrawableEntity(
                    entity.getId(),
                    RunGameView.SHELL_IMAGE_FILE,
                    entity.getX(),
                    entity.getY(),
                    entity.getAngle()
            );

        }

        gameState.clearAdditionTempEntities();

        for (Entity entity : gameState.getTempDeletionList()) {
            gameState.removeEntity(entity);
            runGameView.removeDrawableEntity(entity.getId());
        }
        gameState.clearDeletionTempEntities();

        for (Entity entity : gameState.getEntities()) {
            runGameView.setDrawableEntityLocationAndAngle(
                    entity.getId(),
                    entity.getX(),
                    entity.getY(),
                    entity.getAngle());
        }

        for (int i = 0; i < gameState.getEntities().size() - 1; i++) {

            for (int j = i + 1; j < gameState.getEntities().size(); j++) {

                if (gameState.getEntities().get(i).getId().equals("player-tank")) {
                    if (((Tank) gameState.getEntities().get(i)).getHealth() == 4) {
                        runGameView.addDrawableEntity("full-health", RunGameView.FULL_HEALTH, 30, 660, 0);
                    } else if (((Tank) gameState.getEntities().get(i)).getHealth() == 3) {
                        runGameView.removeDrawableEntity("full-health");
                        runGameView.addDrawableEntity("two-thirds-health", RunGameView.TWO_THIRDS_HEALTH, 30, 660, 0);
                    } else if (((Tank) gameState.getEntities().get(i)).getHealth() == 2) {
                        runGameView.removeDrawableEntity("two-thirds-health");
                        runGameView.addDrawableEntity("half-health", RunGameView.HALF_HEALTH, 30, 660, 0);
                    } else if (((Tank) gameState.getEntities().get(i)).getHealth() == 1) {
                        runGameView.removeDrawableEntity("half-health");
                        runGameView.addDrawableEntity("dead-health", RunGameView.DEAD_HEALTH, 30, 660, 0);
                    }
                }

                if (entitiesOverlap(gameState.getEntities().get(i), gameState.getEntities().get(j))) {
                    if (gameState.getEntities().get(i) instanceof Walls && gameState.getEntities().get(j) instanceof Walls) {

                    }  if (gameState.getEntities().get(i) instanceof Shell && gameState.getEntities().get(j) instanceof Shell) {
                        gameState.setDeleteShell(gameState.getEntities().get(j));
                        gameState.setDeleteShell(gameState.getEntities().get(i));
                        runGameView.removeDrawableEntity(gameState.getEntities().get(j).getId());
                        runGameView.removeDrawableEntity(gameState.getEntities().get(i).getId());
                        runGameView.addAnimation(RunGameView.BIG_EXPLOSION_ANIMATION, RunGameView.BIG_EXPLOSION_FRAME_DELAY,
                                gameState.getEntities().get(j).getX(), gameState.getEntities().get(j).getY());

                    }  if (gameState.getEntities().get(i) instanceof Tank && gameState.getEntities().get(j) instanceof Shell) {
                        if (!gameState.getEntities().get(i).getClass().getName().equals(((Shell) gameState.getEntities().get(j)).getCallerClassName())) {
                            gameState.setDeleteShell(gameState.getEntities().get(j));
                            runGameView.removeDrawableEntity(gameState.getEntities().get(j).getId());
                            ((Tank) gameState.getEntities().get(i)).decrementHealth();
                            runGameView.addAnimation(RunGameView.SHELL_EXPLOSION_ANIMATION, RunGameView.SHELL_EXPLOSION_FRAME_DELAY,
                                    gameState.getEntities().get(j).getX(), gameState.getEntities().get(j).getY());
                            if (((Tank) gameState.getEntities().get(i)).getHealth() == 0) {
                                killedTanks++;
                                gameState.setDeleteShell(gameState.getEntities().get(i));
                                runGameView.removeDrawableEntity(gameState.getEntities().get(i).getId());
                                runGameView.addAnimation(RunGameView.BIG_EXPLOSION_ANIMATION, RunGameView.BIG_EXPLOSION_FRAME_DELAY,
                                        gameState.getEntities().get(i).getX(), gameState.getEntities().get(i).getY());

                                if (gameState.getEntities().get(i).getId().equals("player-tank")) {
                                    runGameView.addDrawableEntity("GAME-OVER", RunGameView.LOSER_IMAGE, 250, 200, 0);
                                } else {
                                    if (killedTanks >= 2) {
                                        runGameView.addDrawableEntity("WINNER", RunGameView.WINNER_IMAGE, 350, 250, 0);
                                    }
                                }
                            }
                        }
                    }  if (gameState.getEntities().get(i) instanceof Tank && gameState.getEntities().get(j) instanceof Tank) {
                        double x1 = gameState.getEntities().get(i).getXBound() - gameState.getEntities().get(j).getX();
                        double x2 = gameState.getEntities().get(j).getXBound() - gameState.getEntities().get(i).getX();
                        double y1 = gameState.getEntities().get(i).getYBound() - gameState.getEntities().get(j).getY();
                        double y2 = gameState.getEntities().get(j).getYBound() - gameState.getEntities().get(i).getY();
                        double moveX = Math.min(x1, x2);
                        double moveY = Math.min(y1, y2);
                        if (moveX < moveY) {
                            if (moveX == x2) {
                                ((Tank) gameState.getEntities().get(i)).collidingMove(true, false, false, true, moveX / 2);
                                ((Tank) gameState.getEntities().get(j)).collidingMove(true, false, true, false, moveX / 2);

                            } else if (moveX == x1) {
                                ((Tank) gameState.getEntities().get(i)).collidingMove(true, false, false, true, moveX / 2);
                                ((Tank) gameState.getEntities().get(j)).collidingMove(true, false, true, false, moveX / 2);
                            }
                        } else {
                            if (moveY == y2) {
                                ((Tank) gameState.getEntities().get(i)).collidingMove(false, true, false, true, moveY / 2);
                                ((Tank) gameState.getEntities().get(j)).collidingMove(false, true, true, false, moveY / 2);

                            } else if (moveY == y1) {
                                ((Tank) gameState.getEntities().get(i)).collidingMove(false, true, false, true, moveY / 2);
                                ((Tank) gameState.getEntities().get(j)).collidingMove(false, true, true, false, moveY / 2);
                            }
                        }
                    }

                    if (gameState.getEntities().get(i) instanceof Walls && gameState.getEntities().get(j) instanceof Tank) {
                        double x1 = gameState.getEntities().get(i).getXBound() - gameState.getEntities().get(j).getX();
                        double x2 = gameState.getEntities().get(j).getXBound() - gameState.getEntities().get(i).getX();
                        double y1 = gameState.getEntities().get(i).getYBound() - gameState.getEntities().get(j).getY();
                        double y2 = gameState.getEntities().get(j).getYBound() - gameState.getEntities().get(i).getY();

                        double moveX = Math.min(x1, x2);
                        double moveY = Math.min(y1, y2); //if x1 is smallest we move to the left
                        if (moveX < moveY) {
                            if (moveX == x2) {
                                ((Tank) gameState.getEntities().get(j)).collidingMove(true, false, true, false, moveX);
                            } else if (moveX == x1) {
                                ((Tank) gameState.getEntities().get(j)).collidingMove(true, false, false, true, moveX);
                            }
                        } else {
                            if (moveY == y2) {
                                ((Tank) gameState.getEntities().get(j)).collidingMove(false, true, true, false, moveY);
                            } else if (moveY == y1) {
                                ((Tank) gameState.getEntities().get(j)).collidingMove(false, true, false, true, moveY);
                            }
                        }
                    }  if (gameState.getEntities().get(i) instanceof Walls && gameState.getEntities().get(j) instanceof Shell) {
                        ((Walls) gameState.getEntities().get(i)).decrementHealth();
                        gameState.setDeleteShell(gameState.getEntities().get(j));
                        runGameView.removeDrawableEntity(gameState.getEntities().get(j).getId());
                        runGameView.addAnimation(RunGameView.SHELL_EXPLOSION_ANIMATION, RunGameView.SHELL_EXPLOSION_FRAME_DELAY,
                                gameState.getEntities().get(j).getX(), gameState.getEntities().get(j).getY());
                        if (((Walls) gameState.getEntities().get(i)).getHealth() == 0) {
                            runGameView.addAnimation(RunGameView.BIG_EXPLOSION_ANIMATION, RunGameView.BIG_EXPLOSION_FRAME_DELAY,
                                    gameState.getEntities().get(i).getX(), gameState.getEntities().get(i).getY());
                            gameState.setDeleteShell(gameState.getEntities().get(i));
                            runGameView.removeDrawableEntity(gameState.getEntities().get(i).getId());
                        }
                    }
                    powerUpDeduction(i, j);
                    powerUpDeduction(j, i);

                    //tank //55*55
                   //shell //24*24
                   //wall //30*30

                }

            }
        }
        return true;

    }

    private void powerUpDeduction(int i, int j) {
        if (gameState.getEntities().get(i) instanceof PowerUp && gameState.getEntities().get(j) instanceof Tank) {
            if (gameState.getEntities().get(j).getId().equals("player-tank")) {
                ((Tank) gameState.getEntities().get(j)).addHealth();
                runGameView.removeDrawableEntity(gameState.getEntities().get(i).getId());
                gameState.setDeleteShell(gameState.getEntities().get(i));
            }
        }
    }


    public static void main(String[] args) {
        GameDriver gameDriver = new GameDriver();
        gameDriver.start();
    }


}

package edu.csc413.tankgame;

import edu.csc413.tankgame.model.GameState;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameKeyListener implements KeyListener {
    GameState gameState;
    public GameKeyListener(GameState gameState) {
        this.gameState = gameState;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        //useless
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_W) {
            gameState.setUpPressed(true);
        }
        if (keyCode == KeyEvent.VK_A) {
            gameState.setLeftPressed(true);


        }
        if (keyCode == KeyEvent.VK_D) {
            gameState.setRightPressed(true);
        }
        if (keyCode == KeyEvent.VK_S) {
            gameState.setDownPressed(true);
        }
        if (keyCode == KeyEvent.VK_ESCAPE) {
            gameState.setEscapePressed(true);
        }
        if (keyCode == KeyEvent.VK_SPACE) {
            gameState.setSpacePressed(true);
        }
    }


    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_W) {
            gameState.setUpPressed(false);
        }
        if (keyCode == KeyEvent.VK_A) {
            gameState.setLeftPressed(false);
        }
        if (keyCode == KeyEvent.VK_D) {
            gameState.setRightPressed(false);
        }
        if (keyCode == KeyEvent.VK_S) {
            gameState.setDownPressed(false);
        }
        if (keyCode == KeyEvent.VK_ESCAPE) {
            gameState.setEscapePressed(false);
        }
        if (keyCode == KeyEvent.VK_SPACE) {
            gameState.setSpacePressed(false);
        }
    }
}

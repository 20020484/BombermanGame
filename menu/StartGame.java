package menu;

import animation.BombermanFrame;
import floor.Floor;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.*;

public final class StartGame extends JComponent
{
    private static final int TIME_STEP = 30;
    private static int width = 18;
    private static int height = 10;
    private static int nrOfEnemies = 5;
    private static Timer clockTimer = null;
    private static GameOver gameOver = new GameOver();

    private StartGame() {}

    public static void main(String[] args) {

        startGame();
    }

    public static void startGame() {
        Floor floor = new Floor(width, height, nrOfEnemies);
        BombermanFrame frame = new BombermanFrame("Bomberman", floor);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        floor.addFloorListener(frame.getBombermanComponent());

        Action doOneStep = new AbstractAction()
        {
            public void actionPerformed(ActionEvent e) {
                gameOver.tick(frame, floor, clockTimer);
            }
        };
        clockTimer = new Timer(TIME_STEP, doOneStep);
        clockTimer.setCoalesce(true);
        clockTimer.start();
    }

}

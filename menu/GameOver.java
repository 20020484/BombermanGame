package menu;

import animation.BombermanFrame;
import floor.Floor;

import javax.sound.sampled.*;
import javax.swing.*;
import java.io.File;
import java.io.IOException;

public class GameOver {

    private void gameOver(BombermanFrame frame, Floor floor, Timer clockTimer) {
        File file = new File("audio/gameOver.wav");
        try {
            AudioInputStream audio = AudioSystem.getAudioInputStream(file);
            Clip clip = AudioSystem.getClip();
            clip.open(audio);

            clip.start();
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }

        clockTimer.stop();
        frame.dispose();
        //startGame();
    }

    public void tick(BombermanFrame frame, Floor floor, Timer clockTimer)  {
        if (floor.getIsGameOver()) {
            gameOver(frame, floor, clockTimer);

        } else {
            floor.moveEnemies();
            floor.bombCountdown();
            floor.explosionHandler();
            floor.characterInExplosion();
            floor.notifyListeners();
        }
    }

}

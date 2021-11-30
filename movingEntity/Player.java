package movingEntity;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

import floor.Floor;
import animation.BombermanAnimation;
import bomb.Bomb;


public class Player extends MovingEntity
{

    private final static int PLAYER_START_X = 60;
    private final static int PLAYER_START_Y = 60;
    private static int PLAYER_PIXELS_BY_STEP = 1;
    private int explosionRadius;
    private int bombCount;
    private Floor floor;
    private Move currentDirectionplayer;

    public Action up = new AbstractAction() {
        public void actionPerformed(ActionEvent e) {
            movePlayer(Move.UP);
            currentDirectionplayer=Move.UP;

        }
    };
    public Action still = new AbstractAction() {
        public void actionPerformed(ActionEvent e) {

            currentDirectionplayer=null;

        }
    };

    public Action right = new AbstractAction() {
        public void actionPerformed(ActionEvent e) {

            movePlayer(Move.RIGHT);
            currentDirectionplayer=Move.RIGHT;

        }
    };

    public Action down = new AbstractAction() {
        public void actionPerformed(ActionEvent e) {
            movePlayer(Move.DOWN);
            currentDirectionplayer=Move.DOWN;

        }
    };

    public Action left = new AbstractAction() {
        public void actionPerformed(ActionEvent e) {
            movePlayer(Move.LEFT);
            currentDirectionplayer=Move.LEFT;

        }
    };


    public Action dropBomb = new AbstractAction()
    {
        public void actionPerformed(ActionEvent e) {
            if(!floor.squareHasBomb(getRowIndex(), getColIndex()) && floor.getBombListSize() < getBombCount()){
                floor.addToBombList(new Bomb(getRowIndex(), getColIndex(), getExplosionRadius()));
            }
            floor.notifyListeners();
        }
    };

    public Player(BombermanAnimation bombermanAnimation, Floor floor) {
        super(PLAYER_START_X, PLAYER_START_Y, PLAYER_PIXELS_BY_STEP);
        explosionRadius = 1;
        bombCount = 1;
        this.floor = floor;
        setPlayerButtons(bombermanAnimation);

    }

    public void setPlayerButtons(BombermanAnimation bombermanAnimation){
        bombermanAnimation.getInputMap().put(KeyStroke.getKeyStroke("RIGHT"), "moveRight");
        bombermanAnimation.getInputMap().put(KeyStroke.getKeyStroke("LEFT"), "moveLeft");
        bombermanAnimation.getInputMap().put(KeyStroke.getKeyStroke("UP"), "moveUp");
        bombermanAnimation.getInputMap().put(KeyStroke.getKeyStroke("DOWN"), "moveDown");
        bombermanAnimation.getInputMap().put(KeyStroke.getKeyStroke("SPACE"), "dropBomb");
        bombermanAnimation.getInputMap().put(KeyStroke.getKeyStroke("released RIGHT"), "still");
        bombermanAnimation.getInputMap().put(KeyStroke.getKeyStroke("released LEFT"), "still");
        bombermanAnimation.getInputMap().put(KeyStroke.getKeyStroke("released UP"), "still");
        bombermanAnimation.getInputMap().put(KeyStroke.getKeyStroke("released DOWN"), "still");
        bombermanAnimation.getActionMap().put("moveRight", right);
        bombermanAnimation.getActionMap().put("moveLeft", left);
        bombermanAnimation.getActionMap().put("moveUp", up);
        bombermanAnimation.getActionMap().put("moveDown", down);
        bombermanAnimation.getActionMap().put("dropBomb", dropBomb);
        bombermanAnimation.getActionMap().put("still", still);

    }

    public int getBombCount() {
        return bombCount;
    }
    public Move getCurrentDirectionplayer(){return currentDirectionplayer;}

    public void setBombCount(int bombCount) {
        this.bombCount = bombCount;
    }

    public int getExplosionRadius() {
        return explosionRadius;
    }

    public void setExplosionRadius(int explosionRadius) {
        this.explosionRadius = explosionRadius;
    }

    private void movePlayer(Move move) {
        File file = new File("audio/feet.wav");
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
        move(move);
        if(floor.collisionWithBlock(this)){
            moveBack(move);
        }
        if(floor.collisionWithBombs(this)){
            moveBack(move);
        }
        if(floor.collisionWithEnemies()){
            floor.setIsGameOver(true);
        }

        floor.checkIfPlayerLeftBomb();
        floor.collisionWithPowerup();
        floor.notifyListeners();
    }
    public double getPLAYER_PIXELS_BY_STEP(){return PLAYER_PIXELS_BY_STEP;}
    public void setPlayerPixelsByStep(int speed){
        PLAYER_PIXELS_BY_STEP=speed;
    }

}


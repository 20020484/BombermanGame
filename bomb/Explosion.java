package bomb;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Explosion
{
    private int rowIndex;
    private int colIndex;
    private int duration = 5;

    public Explosion(int rowIndex, int colIndex)
    {
        File file = new File("audio/explosionBig.wav");
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
        this.rowIndex = rowIndex;
        this.colIndex = colIndex;
    }

    public int getRowIndex() {
        return rowIndex;
    }

    public int getColIndex() {
        return colIndex;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(final int duration) {
        this.duration = duration;
    }
}
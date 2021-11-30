package animation;

import bomb.Bomb;
import bomb.Explosion;
import floor.Floor;
import floor.FloorListener;
import floor.FloorTile;
import items.Item;
import movingEntity.Enemy;
import movingEntity.MovingEntity;
import movingEntity.Player;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.AbstractMap;
import java.util.EnumMap;


public class BombermanAnimation extends JComponent implements FloorListener, ActionListener {
    // Constants are static by definition.
    private final static int SQUARE_SIZE = 40;
    private final static int CHARACTER_ADJUSTMENT_FOR_PAINT = 15;
    private final static int SQUARE_MIDDLE = SQUARE_SIZE / 2;
    private final static int BOMB_ADJUSTMENT_1 = 5;

    private final Floor floor;
    private final AbstractMap<FloorTile, Color> colorMap;
    int i=0;



    public BombermanAnimation(Floor floor) {
        this.floor = floor;

        colorMap = new EnumMap<>(FloorTile.class);
        colorMap.put(FloorTile.FLOOR, Color.GREEN);
        colorMap.put(FloorTile.UNBREAKABLEBLOCK, Color.BLACK);
        colorMap.put(FloorTile.BREAKABLEBLOCK, Color.RED);
    }

    // This method is static since each square has the same size.
    public static int getSquareSize() {
        return SQUARE_SIZE;
    }

    // This method is static since each square has the same size.
    public static int getSquareMiddle() {
        return SQUARE_MIDDLE;
    }

    public Dimension getPreferredSize() {
        super.getPreferredSize();
        return new Dimension(this.floor.getWidth() * SQUARE_SIZE, this.floor.getHeight() * SQUARE_SIZE);
    }

    public void floorChanged() {
        repaint();
    }
    Timer timer=new Timer(100,this);


    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;

        i++;

        for(int rowIndex = 0; rowIndex<floor.getHeight();rowIndex++)

    {
        for (int colIndex = 0; colIndex < floor.getWidth(); colIndex++) {
            g2d.setColor(colorMap.get(this.floor.getFloorTile(rowIndex, colIndex)));
            if (floor.getFloorTile(rowIndex, colIndex) == FloorTile.BREAKABLEBLOCK) {
                paintBreakableBlock(rowIndex, colIndex, g2d);
            } else if (floor.getFloorTile(rowIndex, colIndex) == FloorTile.UNBREAKABLEBLOCK) {
                paintUnbreakableBlock(rowIndex, colIndex, g2d);
            } else {
                paintFloor(rowIndex, colIndex, g2d);
            }
        }
    }
    // Paint player:

        try{
        paintPlayer(floor.getPlayer(), g2d);
      }  catch(IOException e) {
        e.printStackTrace();
    }


    //Paint enemies
        for(Enemy ex:floor.getEnemyList()) {
        try {
            paintEnemy(ex,g2d);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Paint powerups
        for(Item p:floor.getPowerupList()) {
        if (p.getName().equals("items.BombCounter")) {
            BufferedImage[] BombCounter = new BufferedImage[1];
            try {
                BombCounter[0] = ImageIO.read(getClass().getResource("/imagine/powerup_bombs.png"));
                g2d.drawImage(BombCounter[0],p.getX() - CHARACTER_ADJUSTMENT_FOR_PAINT, p.getY() - CHARACTER_ADJUSTMENT_FOR_PAINT, 30, 30,null);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (p.getName().equals("items.BombRadius")) {
            BufferedImage[] BombRadius = new BufferedImage[1];
            try {
                BombRadius[0] = ImageIO.read(getClass().getResource("/imagine/powerup_flames.png"));
                g2d.drawImage(BombRadius[0],p.getX() - CHARACTER_ADJUSTMENT_FOR_PAINT, p.getY() - CHARACTER_ADJUSTMENT_FOR_PAINT, 30, 30,null);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if (p.getName().equals("items.SpeedUp")) {
            BufferedImage[] SpeedUp = new BufferedImage[1];
            try {
                SpeedUp[0] = ImageIO.read(getClass().getResource("/imagine/powerup_speed.png"));
                g2d.drawImage(SpeedUp[0],p.getX() - CHARACTER_ADJUSTMENT_FOR_PAINT, p.getY() - CHARACTER_ADJUSTMENT_FOR_PAINT, 30, 30,null);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    //Paint bombs
        for(Bomb b:floor.getBombList())

    {

        int j=i%4;

        int bombX = floor.squareToPixel(b.getColIndex());
        int bombY = floor.squareToPixel(b.getRowIndex());
        try {
            BufferedImage[] bom = new BufferedImage[3];
            bom[0] = ImageIO.read(getClass().getResource("/imagine/bom0.png"));
            bom[1] = ImageIO.read(getClass().getResource("/imagine/bom1.png"));
            bom[2] = ImageIO.read(getClass().getResource("/imagine/bom2.png"));


            g2d.drawImage(bom[j], bombX + BOMB_ADJUSTMENT_1, bombY + BOMB_ADJUSTMENT_1, 30, 30, null);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    //Paint explosions

        for(Explosion tup:floor.getExplosionCoords())

    {
        int tileSize = 25;

            BufferedImage [] flame = new BufferedImage[9];
            int a=i%9;
        try {
            flame[0] = ImageIO.read(getClass().getResource("/imagine/explo9.png"));
            flame[1] = ImageIO.read(getClass().getResource("/imagine/explo8.png"));
            flame[2] = ImageIO.read(getClass().getResource("/imagine/explo7.png"));
            flame[3] = ImageIO.read(getClass().getResource("/imagine/explo6.png"));
            flame[4] = ImageIO.read(getClass().getResource("/imagine/explo5.png"));
            flame[5] = ImageIO.read(getClass().getResource("/imagine/explo4.png"));
            flame[6] = ImageIO.read(getClass().getResource("/imagine/explo3.png"));
            flame[7] = ImageIO.read(getClass().getResource("/imagine/explo2.png"));
            flame[8] = ImageIO.read(getClass().getResource("/imagine/explo1.png"));
            g2d.drawImage(flame[a], floor.squareToPixel(tup.getColIndex()) + BOMB_ADJUSTMENT_1, floor.squareToPixel(tup.getRowIndex()) + BOMB_ADJUSTMENT_1, tileSize, tileSize, null);
    } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

    private void paintBreakableBlock(int rowIndex, int colIndex, Graphics g2d){

        try {
            BufferedImage bl = ImageIO.read(getClass().getResource("/imagine/bl.png"));
            g2d.drawImage(bl, colIndex * SQUARE_SIZE, rowIndex * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE, null);
            g2d.setColor(Color.DARK_GRAY);
            g2d.drawLine(colIndex* SQUARE_SIZE, rowIndex*SQUARE_SIZE, colIndex*SQUARE_SIZE+SQUARE_SIZE, rowIndex*SQUARE_SIZE);
            g2d.drawLine(colIndex* SQUARE_SIZE, rowIndex*SQUARE_SIZE+SQUARE_SIZE, colIndex*SQUARE_SIZE+SQUARE_SIZE, rowIndex*SQUARE_SIZE+SQUARE_SIZE);
            g2d.drawLine(colIndex* SQUARE_SIZE, rowIndex*SQUARE_SIZE, colIndex*SQUARE_SIZE, rowIndex*SQUARE_SIZE+SQUARE_SIZE);
            g2d.drawLine(colIndex* SQUARE_SIZE+SQUARE_SIZE, rowIndex*SQUARE_SIZE, colIndex*SQUARE_SIZE+SQUARE_SIZE, rowIndex*SQUARE_SIZE+SQUARE_SIZE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void paintUnbreakableBlock(int rowIndex, int colIndex, Graphics g2d){
        try {
            BufferedImage bl = ImageIO.read(getClass().getResource("/imagine/unbl.png"));
            g2d.drawImage(bl, colIndex * SQUARE_SIZE, rowIndex * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE, null);
            g2d.setColor(Color.DARK_GRAY);
            g2d.drawLine(colIndex* SQUARE_SIZE, rowIndex*SQUARE_SIZE, colIndex*SQUARE_SIZE+SQUARE_SIZE, rowIndex*SQUARE_SIZE);
            g2d.drawLine(colIndex* SQUARE_SIZE, rowIndex*SQUARE_SIZE+SQUARE_SIZE, colIndex*SQUARE_SIZE+SQUARE_SIZE, rowIndex*SQUARE_SIZE+SQUARE_SIZE);
            g2d.drawLine(colIndex* SQUARE_SIZE, rowIndex*SQUARE_SIZE, colIndex*SQUARE_SIZE, rowIndex*SQUARE_SIZE+SQUARE_SIZE);
            g2d.drawLine(colIndex* SQUARE_SIZE+SQUARE_SIZE, rowIndex*SQUARE_SIZE, colIndex*SQUARE_SIZE+SQUARE_SIZE, rowIndex*SQUARE_SIZE+SQUARE_SIZE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void paintFloor(int rowIndex, int colIndex, Graphics g2d){
        int tileSize = 30;
        try {
            BufferedImage floor = ImageIO.read(getClass().getResource("/imagine/floor.png"));
            g2d.drawImage(floor, colIndex * SQUARE_SIZE, rowIndex * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE, null);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void paintEnemy(Enemy ee, Graphics g2d)throws IOException{
        int tileSize = 30;
        BufferedImage [] enemy = new BufferedImage[6];
        int b=i%6;

            enemy[0] = ImageIO.read(getClass().getResource("/imagine/ballon.png"));
            enemy [1] = ImageIO.read(getClass().getResource("/imagine/ballon1.png"));
            enemy [2] = ImageIO.read(getClass().getResource("/imagine/ballon2.png"));
            enemy[3] = ImageIO.read(getClass().getResource("/imagine/ballon3.png"));
            enemy[4] = ImageIO.read(getClass().getResource("/imagine/ballon4.png"));
            enemy[5] = ImageIO.read(getClass().getResource("/imagine/ballon5.png"));

            g2d.drawImage(enemy[b], ee.getX()-CHARACTER_ADJUSTMENT_FOR_PAINT, ee.getY()-CHARACTER_ADJUSTMENT_FOR_PAINT, tileSize, tileSize, null);

    }

    private void paintPlayer(Player player, Graphics g2d) throws IOException  {
        int tileSize = 30;
        i++;
        int j=i%4;

        BufferedImage[] playermovementstill=new BufferedImage[1];

        BufferedImage[] playermovementup=new BufferedImage[3];

        BufferedImage[] playermovementdown=new BufferedImage[3];

        BufferedImage[] playermovementright=new BufferedImage[3];

        BufferedImage[] playermovementleft=new BufferedImage[3];

        if(player.getCurrentDirectionplayer()== null) {
            playermovementstill[0] = ImageIO.read(getClass().getResource("/imagine/Bomberman0.png"));
        }

        if(player.getCurrentDirectionplayer()== MovingEntity.Move.UP) {
            playermovementup[0] = ImageIO.read(getClass().getResource("/imagine/Bombermanup.png"));
            playermovementup[1] = ImageIO.read(getClass().getResource("/imagine/Bombermanup2.png"));
            playermovementup[2] = ImageIO.read(getClass().getResource("/imagine/Bombermanup3.png"));
        }else if(player.getCurrentDirectionplayer()== MovingEntity.Move.DOWN){
            playermovementdown[0] = ImageIO.read(getClass().getResource("/imagine/Bomberman0.png"));
            playermovementdown[1] = ImageIO.read(getClass().getResource("/imagine/Bombermandown.png"));
            playermovementdown[2] = ImageIO.read(getClass().getResource("/imagine/Bombermandown2.png"));
        }else if(player.getCurrentDirectionplayer()== MovingEntity.Move.RIGHT){
            playermovementright[0] = ImageIO.read(getClass().getResource("/imagine/Bomberman1.png"));
            playermovementright[1] = ImageIO.read(getClass().getResource("/imagine/Bomberman2.png"));
            playermovementright[2] = ImageIO.read(getClass().getResource("/imagine/Bomberman3.png"));
        }else if(player.getCurrentDirectionplayer()== MovingEntity.Move.LEFT){
            playermovementleft[0] = ImageIO.read(getClass().getResource("/imagine/Bombermanleft1.png"));
            playermovementleft[1] = ImageIO.read(getClass().getResource("/imagine/Bombermanleft2.png"));
            playermovementleft[2] = ImageIO.read(getClass().getResource("/imagine/Bombermanleft3.png"));
        }


        g2d.drawImage(playermovementstill[0], player.getX()-CHARACTER_ADJUSTMENT_FOR_PAINT, player.getY()-CHARACTER_ADJUSTMENT_FOR_PAINT, tileSize, tileSize, null);
        g2d.drawImage(playermovementup[j], player.getX()-CHARACTER_ADJUSTMENT_FOR_PAINT, player.getY()-CHARACTER_ADJUSTMENT_FOR_PAINT, tileSize, tileSize, null);

        g2d.drawImage(playermovementdown[j], player.getX()-CHARACTER_ADJUSTMENT_FOR_PAINT, player.getY()-CHARACTER_ADJUSTMENT_FOR_PAINT, tileSize, tileSize, null);

        g2d.drawImage(playermovementleft[j], player.getX()-CHARACTER_ADJUSTMENT_FOR_PAINT, player.getY()-CHARACTER_ADJUSTMENT_FOR_PAINT, tileSize, tileSize, null);

        g2d.drawImage(playermovementright[j], player.getX()-CHARACTER_ADJUSTMENT_FOR_PAINT, player.getY()-CHARACTER_ADJUSTMENT_FOR_PAINT, tileSize, tileSize, null);

    }


    @Override
    public void actionPerformed(ActionEvent e) {

     repaint();

    }
}
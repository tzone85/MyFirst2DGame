package main;

import entity.Player;
import tile.TileManager;

import javax.swing.JPanel;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable{
    // SCREEN SETTINGS

    final int originalTileSize = 16;    // 16x16 tile

    // scale it
    final int scale = 3;

    public final int tileSize = originalTileSize * scale;  // 48 x 48 tile
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol;    // 768 pixels
    public final int screenHeight = tileSize * maxScreenRow;   //562 pixels

    // FPS  (Frames per second)
    int FPS = 60;

    TileManager tileM = new TileManager(this);
    KeyHandler keyH = new KeyHandler();
    // game clock (Thread)
    Thread gameThread;

    Player player = new Player(this, keyH);

     // set players default position
//    int playerX = 100;
//    int playerY = 100;
//    int playerSpeed = 4;


    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true); // for better rendering performance

        // add keyHandler
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }


    /**
     *
     * called by the Thread
     * Sleep method
     */
    @Override
   /*
    public void run() {
        // create the game loop (game core)

        int i = 0;

        double drawInterval = 1000000000/FPS;
        double nextDrawTime = System.nanoTime() + drawInterval;     // System.nanoTime returns currentTime

        while (gameThread != null) {
            // System.out.println("Game loop is running: "+ i++);

            // check current Time
            long currentTime = System.nanoTime();           // 1 000 000 = 1 sec  (more precise)
            // long currentTime2 = System.currentTimeMillis(); // 1 000 = 1sec

            // 1    UPDATE: update information such as character positions
            update();

            // 2    DRAW: draw the screen with the updated information
            repaint();

            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime = remainingTime/1000000;  //coz we used nanoTime and the sleep method takes millisecs

                if (remainingTime < 0 ){
                    remainingTime = 0;
                }

                Thread.sleep((long) remainingTime);

                nextDrawTime += drawInterval;

            } catch (InterruptedException e){
                e.printStackTrace();
            }

        }
    }   */

    public void run() {

        double drawInterval = 1000000000/FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        // vars to count and show FPS
        long timer = 0;
        int drawCount = 0;


        while(gameThread != null) {

            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;

            // it's important for the timer to be btwn currentTime and lastTime
            timer += (currentTime - lastTime);
            // above position is important to desplay the FPS

            lastTime = currentTime;


            if (delta >= 1) {
                update();
                repaint();

                delta--;
                drawCount++;
            }

            if (timer >= 1000000000) {
                System.out.println("FPS: "+ drawCount);

                // reset vars again
                drawCount = 0;
                timer = 0;
            }

        }
    }

    public void update() {
        player.update();
    }

    // internal method
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g;

        // ensure you draw the tile before the player (it's like a layer)
        tileM.draw(g2);

        player.draw(g2);
        g2.dispose();

    }
}

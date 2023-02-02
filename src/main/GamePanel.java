package main;

import javax.swing.JPanel;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable{
    // SCREEN SETTINGS

    final int originalTileSize = 16;    // 16x16 tile

    // scale it
    final int scale = 3;

    final int tileSize = originalTileSize * scale;  // 48 x 48 tile
    final int maxScreenCol = 16;
    final int maxScreenRow = 12;
    final int screenWidth = tileSize * maxScreenCol;    // 768 pixels
    final int screenHeight = tileSize * maxScreenRow;   //562 pixels

    // FPS  (Frames per second)
    int FPS = 60;
    KeyHandler keyH = new KeyHandler();
    // game clock (Thread)
    Thread gameThread;

     // set players default position
    int playerX = 100;
    int playerY = 100;
    int playerSpeed = 4;
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
     */
    @Override
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
    }

    public void update() {

        /**
         * X values increase to the right
         * Y values increase as they go down
         */
        if (keyH.upPressed == true) {
            playerY -= playerSpeed;
        } else if (keyH.downPressed == true) {
            playerY += playerSpeed;
        } else if (keyH.leftPressed == true) {
            playerX -= playerSpeed;
        } else if (keyH.rightPressed == true) {
            playerX += playerSpeed;
        }

    }

    // internal method
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g;

        g2.setColor(Color.white);
        g2.fillRect(playerX, playerY, tileSize, tileSize);
        g2.dispose();

    }
}

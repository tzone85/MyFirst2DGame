package main;

import javax.swing.*;
import java.awt.Dimension;
import java.awt.Color;

public class GamePanel extends JPanel {
    // SCREEN SETTINGS

    final int originalTileSize = 16;    // 16x16 tile

    // scale it
    final int scale = 3;

    final int tileSize = originalTileSize * scale;  // 48 x 48 tile
    final int maxScreenCol = 16;
    final int maxScreenRow = 12;
    final int screenWidth = tileSize * maxScreenCol;    // 768 pixels
    final int screenHeight = tileSize * maxScreenRow;   //562 pixels

    // game clock (Thread)
    Thread gameThread;
    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true); // for better rendering performance
    }
}

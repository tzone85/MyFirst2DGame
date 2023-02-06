package tile;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TileManager {

    GamePanel gp;
    Tile[] tile;
    int mapTileNumber[][];

    public TileManager(GamePanel gp) {
        this.gp = gp;
        mapTileNumber = new int[gp.maxWorldCol][gp.maxWorldRow];
        tile = new Tile[10];
        getTileImage();

        loadMap("/maps/world01.txt");
    }

    public void getTileImage() {
        try{
            tile[0] = new Tile();
            tile[0].image = ImageIO.read(getClass().getResourceAsStream(("/tiles/grass.png")));

            tile[1] = new Tile();
            tile[1].image = ImageIO.read(getClass().getResourceAsStream(("/tiles/wall.png")));

            tile[2] = new Tile();
            tile[2].image = ImageIO.read(getClass().getResourceAsStream(("/tiles/water.png")));

            tile[3] = new Tile();
            tile[3].image = ImageIO.read(getClass().getResourceAsStream(("/tiles/earth.png")));

            tile[4] = new Tile();
            tile[4].image = ImageIO.read(getClass().getResourceAsStream(("/tiles/tree.png")));

            tile[5] = new Tile();
            tile[5].image = ImageIO.read(getClass().getResourceAsStream(("/tiles/sand.png")));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadMap(String filePath) {
        try {
            InputStream inputSt = getClass().getResourceAsStream(filePath);
            BufferedReader buffReader = new BufferedReader(new InputStreamReader(inputSt));

            int col = 0;
            int row = 0;

            while (col < gp.maxWorldCol && row < gp.maxWorldRow) {

                String line = buffReader.readLine();

                while (col < gp.maxWorldCol) {
                    String numbers[] = line.split(" ");

                    // changing the stream into a number
                    int num = Integer.parseInt(numbers[col]);

                    mapTileNumber[col][row] = num;
                    col++;
                }

                if (col == gp.maxWorldCol) {
                    col = 0;
                    row++;
                }
            }

            buffReader.close();
        } catch (Exception e) {

        }
    }
    public void draw(Graphics2D g2) {
        // practice drawing a tile

        int worldCol = 0;
        int  worldRow= 0;

        // implementation of camera function

        while (worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {

            int tileNum = mapTileNumber[worldCol][worldRow];

            // revisit
            int worldX = worldCol * gp.tileSize;
            int worldY = worldRow * gp.tileSize;
            int screenX = worldX - gp.player.worldX + gp.player.screenX;
            int screenY = worldY - gp.player.worldY + gp.player.screenY;

            // to only draw tiles that are visible within our screen
            if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                worldX  - gp.tileSize  < gp.player.worldX + gp.player.screenX &&
                worldY  + gp.tileSize  >gp.player.worldY - gp.player.screenY &&
                worldY -  gp.tileSize  < gp.player.worldY + gp.player.screenY)
            {
                g2.drawImage(tile[tileNum].image, screenX, screenY, gp.tileSize, gp.tileSize, null);
            }


            worldCol++;

            if (worldCol == gp.maxWorldCol) {
                worldCol = 0;
                worldRow++;
            }
        }
    }
}

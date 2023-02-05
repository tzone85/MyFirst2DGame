package tile;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.Buffer;

public class TileManager {

    GamePanel gp;
    Tile[] tile;
    int mapTileNumber[][];

    public TileManager(GamePanel gp) {
        this.gp = gp;
        mapTileNumber = new int[gp.maxScreenCol][gp.maxScreenRow];
        tile = new Tile[10];
        getTileImage();

        loadMap("/maps/gameMap2.txt");
    }

    public void getTileImage() {
        try{
            tile[0] = new Tile();
            tile[0].image = ImageIO.read(getClass().getResourceAsStream(("/tiles/grass.png")));

            tile[1] = new Tile();
            tile[1].image = ImageIO.read(getClass().getResourceAsStream(("/tiles/wall.png")));

            tile[2] = new Tile();
            tile[2].image = ImageIO.read(getClass().getResourceAsStream(("/tiles/water.png")));

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

            while (col < gp.maxScreenCol && row < gp.maxScreenRow) {

                String line = buffReader.readLine();

                while (col < gp.maxScreenCol) {
                    String numbers[] = line.split("");

                    // changing the stream into a number
                    int num = Integer.parseInt(numbers[col]);

                    mapTileNumber[col][row] = num;
                    col++;
                }

                if (col == gp.maxScreenCol) {
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

        int col = 0;
        int row = 0;
        int x = 0;
        int y = 0;

        while (col < gp.maxScreenCol && row < gp.maxScreenRow) {

            int tileNum = mapTileNumber[col][row];

            g2.drawImage(tile[tileNum].image, x, y, gp.tileSize, gp.tileSize, null);
            col++;
            x += gp.tileSize;

            if (col == gp.maxScreenCol) {
                col = 0;
                x = 0;
                row++;
                y += gp.tileSize;
            }
        }
    }
}

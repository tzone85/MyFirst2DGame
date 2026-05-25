package world;

/**
 * Pure 2D-grid math used by the collision checker. Extracted from
 * {@code CollisionChecker} so it can be unit-tested without instantiating a
 * Swing JPanel.
 */
public final class WorldGeometry {
    private WorldGeometry() {}

    /**
     * Tile-grid bounds an entity occupies. All values are in tile-space.
     */
    public record TileBounds(int leftCol, int rightCol, int topRow, int bottomRow) {}

    /**
     * Compute the tile-grid bounds covered by an entity at world coordinates
     * (worldX, worldY) with the given solid-area box, projected onto a grid
     * with `tileSize`-pixel tiles.
     */
    public static TileBounds tileBoundsOf(
        int worldX, int worldY,
        int solidAreaX, int solidAreaY, int solidAreaW, int solidAreaH,
        int tileSize
    ) {
        if (tileSize <= 0) throw new IllegalArgumentException("tileSize must be positive");
        int left = worldX + solidAreaX;
        int right = left + solidAreaW;
        int top = worldY + solidAreaY;
        int bottom = top + solidAreaH;
        return new TileBounds(left / tileSize, right / tileSize, top / tileSize, bottom / tileSize);
    }

    /**
     * After accounting for movement in the given `direction` at `speed` pixels,
     * which tile row/col index does the moving edge land on?
     */
    public static int movedEdgeIndex(Direction direction, TileBounds bounds, int speed, int worldEdgePx, int tileSize) {
        if (tileSize <= 0) throw new IllegalArgumentException("tileSize must be positive");
        return switch (direction) {
            case UP -> (worldEdgePx - speed) / tileSize;
            case DOWN -> (worldEdgePx + speed) / tileSize;
            case LEFT -> (worldEdgePx - speed) / tileSize;
            case RIGHT -> (worldEdgePx + speed) / tileSize;
        };
    }

    public enum Direction { UP, DOWN, LEFT, RIGHT }
}

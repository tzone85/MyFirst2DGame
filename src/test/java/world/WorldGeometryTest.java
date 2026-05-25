package world;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class WorldGeometryTest {

    @Test
    void tileBoundsOfAtOrigin() {
        // tileSize=48, entity at (0,0) with solid box (8,16,32,32) → left/top tiles
        WorldGeometry.TileBounds b =
            WorldGeometry.tileBoundsOf(0, 0, 8, 16, 32, 32, 48);
        assertEquals(0, b.leftCol());
        assertEquals(0, b.rightCol());
        assertEquals(0, b.topRow());
        assertEquals(1, b.bottomRow()); // top+height = 48 → 48/48 = 1
    }

    @Test
    void tileBoundsOfStraddlingTwoColumns() {
        WorldGeometry.TileBounds b =
            WorldGeometry.tileBoundsOf(40, 40, 0, 0, 16, 16, 48);
        assertEquals(0, b.leftCol());
        assertEquals(1, b.rightCol());
    }

    @Test
    void zeroTileSizeRejected() {
        assertThrows(IllegalArgumentException.class,
            () -> WorldGeometry.tileBoundsOf(0, 0, 0, 0, 16, 16, 0));
    }

    @Test
    void movedEdgeUpSubtracts() {
        // tileSize 48, edge at 100, speed 4 → (100-4)/48 = 2
        int idx = WorldGeometry.movedEdgeIndex(
            WorldGeometry.Direction.UP, null, 4, 100, 48);
        assertEquals(2, idx);
    }

    @Test
    void movedEdgeDownAdds() {
        int idx = WorldGeometry.movedEdgeIndex(
            WorldGeometry.Direction.DOWN, null, 4, 100, 48);
        assertEquals(2, idx); // (100+4)/48 = 2
    }

    @Test
    void movedEdgeLeftSubtracts() {
        int idx = WorldGeometry.movedEdgeIndex(
            WorldGeometry.Direction.LEFT, null, 4, 100, 48);
        assertEquals(2, idx);
    }

    @Test
    void movedEdgeRightAdds() {
        int idx = WorldGeometry.movedEdgeIndex(
            WorldGeometry.Direction.RIGHT, null, 4, 100, 48);
        assertEquals(2, idx);
    }

    @Test
    void zeroTileSizeRejectedForMovedEdge() {
        assertThrows(IllegalArgumentException.class,
            () -> WorldGeometry.movedEdgeIndex(
                WorldGeometry.Direction.UP, null, 0, 0, 0));
    }
}

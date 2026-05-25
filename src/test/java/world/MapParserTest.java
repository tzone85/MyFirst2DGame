package world;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MapParserTest {

    @Test
    void parsesARectangularGrid() {
        String text = String.join("\n",
            "0 1 2 3",
            "4 5 6 7",
            "8 9 0 1"
        );
        int[][] grid = MapParser.parse(text, 4, 3);
        assertEquals(0, grid[0][0]);
        assertEquals(3, grid[3][0]);
        assertEquals(5, grid[1][1]);
        assertEquals(1, grid[3][2]);
    }

    @Test
    void rejectsTooFewRows() {
        String text = "0 1 2 3\n4 5 6 7";
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
            () -> MapParser.parse(text, 4, 5));
        assertTrue(e.getMessage().contains("expected at least 5 rows"));
    }

    @Test
    void rejectsRowsWithTooFewCells() {
        String text = "0 1\n2 3\n4 5";
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
            () -> MapParser.parse(text, 4, 3));
        assertTrue(e.getMessage().contains("row 0 has only 2 tiles"));
    }

    @Test
    void rejectsNonNumericCells() {
        String text = "0 cat 2 3\n4 5 6 7\n8 9 0 1";
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
            () -> MapParser.parse(text, 4, 3));
        assertTrue(e.getMessage().contains("'cat'"));
    }

    @Test
    void rejectsZeroOrNegativeDimensions() {
        assertThrows(IllegalArgumentException.class, () -> MapParser.parse("", 0, 1));
        assertThrows(IllegalArgumentException.class, () -> MapParser.parse("", 1, 0));
        assertThrows(IllegalArgumentException.class, () -> MapParser.parse("", -1, 1));
    }

    @Test
    void rejectsNullText() {
        assertThrows(NullPointerException.class, () -> MapParser.parse(null, 1, 1));
    }

    @Test
    void readsResource() {
        String text = MapParser.readResource("/maps/world01.txt");
        assertNotNull(text);
        assertTrue(text.length() > 0);
        // can parse the bundled 50x50 map
        int[][] grid = MapParser.parse(text, 50, 50);
        assertEquals(50, grid.length);
        assertEquals(50, grid[0].length);
    }

    @Test
    void readResourceThrowsOnMissingPath() {
        assertThrows(IllegalArgumentException.class,
            () -> MapParser.readResource("/maps/does-not-exist.txt"));
    }
}

package world;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * Pure map-file parsing. The original {@code TileManager.loadMap} read a
 * resource via reflection-style streams and parsed inline; that mixed I/O,
 * parsing, and field mutation. This class only does parsing — testable.
 */
public final class MapParser {
    private MapParser() {}

    /**
     * Parse a map file's text into a {@code maxCol x maxRow} tile-id grid.
     * Rows are separated by newlines; tiles inside a row by single spaces.
     *
     * @throws IllegalArgumentException if the text does not contain enough
     *     rows or any row does not contain enough tiles.
     */
    public static int[][] parse(String text, int maxCol, int maxRow) {
        Objects.requireNonNull(text, "text");
        if (maxCol <= 0 || maxRow <= 0) {
            throw new IllegalArgumentException("maxCol and maxRow must be positive");
        }
        int[][] grid = new int[maxCol][maxRow];
        String[] lines = text.split("\\R", -1);
        if (lines.length < maxRow) {
            throw new IllegalArgumentException(
                "expected at least " + maxRow + " rows, got " + lines.length);
        }
        for (int row = 0; row < maxRow; row++) {
            String[] cells = lines[row].split(" ");
            if (cells.length < maxCol) {
                throw new IllegalArgumentException(
                    "row " + row + " has only " + cells.length + " tiles, expected " + maxCol);
            }
            for (int col = 0; col < maxCol; col++) {
                try {
                    grid[col][row] = Integer.parseInt(cells[col]);
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException(
                        "row " + row + " col " + col + ": '" + cells[col] + "' is not an int", e);
                }
            }
        }
        return grid;
    }

    /** Read a classpath resource fully as UTF-8 text. */
    public static String readResource(String path) {
        InputStream in = MapParser.class.getResourceAsStream(path);
        if (in == null) {
            throw new IllegalArgumentException("resource not found: " + path);
        }
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8))) {
            StringBuilder sb = new StringBuilder();
            String line;
            boolean first = true;
            while ((line = reader.readLine()) != null) {
                if (!first) sb.append('\n');
                sb.append(line);
                first = false;
            }
            return sb.toString();
        } catch (java.io.IOException e) {
            throw new RuntimeException("failed to read " + path, e);
        }
    }
}

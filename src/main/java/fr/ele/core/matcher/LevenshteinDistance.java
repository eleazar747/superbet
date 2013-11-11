package fr.ele.core.matcher;

import java.util.Arrays;

/**
 * This class provides a more elaborate implementation of Levenshtein. It allows
 * for retrieving edit type counts.
 */
public class LevenshteinDistance {

    public static class LevenshteinResults {
        private int distance;
        private int deleted;
        private int inserted;
        private int substituted;

        public int getDistance() {
            return distance;
        }

        public int getDeleted() {
            return deleted;
        }

        public int getInserted() {
            return inserted;
        }

        public int getSubstituted() {
            return substituted;
        }
    }

    private int[][] grid;

    public LevenshteinResults getLevenshteinDistance(CharSequence source, CharSequence target) {
    	int i;
        int j;
        int m = source.length();
        int n = target.length();
        int cost;
        int distance;

        if (grid == null || grid.length < m + 1) {
            grid = new int[m + 1][];
        }
        for (i = 0; i <= m; i++) {
            if (grid[i] == null || grid[i].length < n + 1) {
                grid[i] = new int[n + 1];
            } else {
                Arrays.fill(grid[i], 0);
            }
        }

        // initialization: fill top row and leftmost column
        for (i = 0; i <= m; i++)
            grid[i][0] = i;
        for (j = 0; j <= n; j++)
            grid[0][j] = j;

        // raster sweep the grid
        for (i = 1; i <= m; i++) {
            for (j = 1; j <= n; j++) {
                cost = (source.charAt(i - 1) == target.charAt(j - 1)) ? 0
                        : 1;

                grid[i][j] = Math.min(grid[i - 1][j] + 1, // deletion
                        Math.min(grid[i][j - 1] + 1, // insertion
                                grid[i - 1][j - 1] + cost)); // substitution
            }
        }

        distance = grid[m][n];

        LevenshteinResults results = new LevenshteinResults();

        i = source.length();
        j = target.length();
        int cur, left, up, leftUp;

        results.inserted = 0;
        results.deleted = 0;
        results.substituted = 0;
        results.distance = distance;

        while (i != 0 && j != 0) {
            cur = grid[i][j];
            left = grid[i][j - 1];
            up = grid[i - 1][j];
            leftUp = grid[i - 1][j - 1];

            // left & up is min
            if (leftUp <= left && leftUp <= up && leftUp == cur) {
                // match
                i--;
                j--;
            }
            // left is min, break ties by going left (insert in t)
            else if ((left < up && left < leftUp) || (left == up && left < cur)) {
                results.inserted++;
                j--;
            }
            // up is min, delete in s
            else if (up < left && up < leftUp) {
                results.deleted++;
                i--;
            }
            // left & up must be < cur, substitution
            else {
                results.substituted++;
                i--;
                j--;
            }
        }

        // pick up any leading edits
        while (j > 0) {
            results.inserted++;
            j--;
        }

        while (i > 0) {
            results.deleted++;
            i--;
        }
        return results;
    }

}

package uk.ac.cam.ajo41.prejava.ex2;

import uk.ac.cam.ajo41.prejava.ex2.PackedLong;

public class TinyLife {
    
    /**
     * The getCell method returns whether the cell at position (@param col, @param row) is alive in the world
     * 
     * @param world 64 bit long integer that represents an 8x8 world
     * @param col column index in the world (integer from 0 - 7)
     * @param row row index in the world (integer from 0 - 7)
     * @return whether the cell is alive
     */
    public static boolean getCell(long world, int col, int row) {
        if (col > 7 || row > 7 || col < 0 || row < 0) {
            return false;
        }

        return PackedLong.get(world, row * 8 + col);
    }

    /**
     * The setCell method returns an updated world where the cell at position (@param col, @param row) is set to value @param value.
     * 
     * @param world 64 bit long integer that represents an 8x8 world
     * @param col column index in the world (integer from 0 - 7)
     * @param row row index in the world (integer from 0 - 7)
     * @param value updated value of the cell
     * @return updated world
     */
    public static long setCell(long world, int col, int row, boolean value) {
        if (col > 7 || row > 7 || col < 0 || row < 0) {
            return world;
        }

        return PackedLong.set(world, row * 8 + col, value);
    }

    /**
     * This method counts the neighbours of a given cell
     * 
     * @param world 64 bit long integer that represents an 8x8 world
     * @param col column index in the world (integer from 0 - 7)
     * @param row row index in the world (integer from 0 - 7)
     * @return the number of alive neighbours
     */
    public static int countNeighbours(long world, int col, int row) {
        int neighbours = 0;

        // a list of (i, j) pairs representing positions of the neighbours
        int[][] neighbourPositions = new int[][]{
            {col - 1, row - 1}, {col - 1, row}, {col - 1, row + 1},
            {col, row - 1}, {col, row + 1},
            {col + 1, row - 1}, {col + 1, row}, {col + 1, row + 1}
        };

        for (int[] position : neighbourPositions) {
            neighbours += (getCell(world, position[0], position[1])) ? 1 : 0;
        }

        return neighbours;
    }

    /**
     * This method returns whether the cell should be alive or dead in the next generation
     * 
     * @param world 64 bit long integer that represents an 8x8 world
     * @param col column index in the world (integer from 0 - 7)
     * @param row row index in the world (integer from 0 - 7)
     * @return the state of the cell in the next generation
     */
    public static boolean computeCell(long world, int col, int row) {
        boolean liveCell = getCell(world, col, row);
        
        int neighbours = countNeighbours(world, col, row);
     
        boolean nextCell = false;
        
        if (neighbours < 2) {
           nextCell = false;
        }
      
        if (liveCell && (neighbours == 2 || neighbours == 3)) {
            nextCell = true;
        }

        if (neighbours > 3) {
            nextCell = false;
        }

        if (!liveCell && neighbours == 3) {
            nextCell = true;
        }
         
        return nextCell;
    }

    /**
     * This method returns the next generation of the world
     * 
     * @param world 64 bit long integer that represents an 8x8 world
     * @return the updated world
     */
    public static long nextGeneration(long world) {
        long newWorld = 0L;
        for (int j = 0; j < 8; j++) {
            for (int i = 0; i < 8; i++) {
                newWorld = setCell(newWorld, i, j, computeCell(world, i, j));
            }
        }
        return newWorld;
    }

    /**
     * This method prints the world
     * 
     * @param world 64 bit long integer that represents an 8x8 world
     */
    public static void print(long world) {
        System.out.println("-".repeat(8));
        for (int j = 0; j < 8; j++) {
            for (int i = 0; i < 8; i++) {
                System.out.print(getCell(world, i, j) ? "#" : "_");
            }
            System.out.println();
        }
        System.out.println("-".repeat(8));
    }

    public static void play(long world) throws Exception {
        int userResponse = 0;
        while (userResponse != 'q') {
            print(world);
            userResponse = System.in.read();
            world = nextGeneration(world);
        }
    }

    public static void main(String[] args) throws Exception {
        play(Long.decode(args[0]));
    }
}
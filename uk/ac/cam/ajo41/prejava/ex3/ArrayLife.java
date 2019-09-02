package uk.ac.cam.ajo41.prejava.ex3;


public class ArrayLife {

    /**
     * The getCell method returns whether the cell at position (@param col, @param row) is alive. 
     * If (@param col, @param row) doesn't exist in the world, then we return false.
     * 
     * @param world A two dimensional boolean array that represent ths world, where each element of world is a row and each element of a row is a cell
     * @param col column index in the world
     * @param row row index in the world
     * @return whether the cell is alive
     */
    public static boolean getCell(boolean[][] world, int col, int row) {
        if (row > world.length - 1 || row < 0 || col > world[row].length - 1 || col < 0) {
            return false;
        }

        return world[row][col];
    }

    /**
     * The setCell method updates the world where the cell at position (@param col, @param row) is set to the value @param value.
     * No world is returned due pass by reference instead of pass by value
     * If (@param col, @param row) doesn't exist in the world, then we do nothing.
     * 
     * @param world A two dimensional boolean array that represent ths world, where each element of world is a row and each element of a row is a cell
     * @param col column index in the world
     * @param row row index in the world
     * @param value updated value of the cell
     */
    public static void setCell(boolean[][] world, int col, int row, boolean value) {

        if (row > world.length - 1 || row < 0) {
            return;
        }

        if ( col > world[row].length - 1 || col < 0) {
            return;
        }

        world[row][col] = value;
    }

    /**
     * This method prints the world
     * 
     * @param world A two dimensional boolean array that represent ths world, where each element of world is a row and each element of a row is a cell
     */
    public static void print(boolean[][] world) {
        System.out.println("-".repeat(8));
        for (int j = 0; j < 8; j++) {
            for (int i = 0; i < 8; i++) {
                System.out.print(getCell(world, i, j) ? "#" : "_");
            }
            System.out.println();
        }
        System.out.println("-".repeat(8));
    }

    /**
     * This method counts the neighbours of a given cell
     * 
     * @param world A two dimensional boolean array that represent ths world, where each element of world is a row and each element of a row is a cell
     * @param col column index in the world
     * @param row row index in the world
     * @return the number of alive neighbours
     */
    public static int countNeighbours(boolean[][] world, int col, int row) {
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
     * @param world A two dimensional boolean array that represent ths world, where each element of world is a row and each element of a row is a cell
     * @param col column index in the world
     * @param row row index in the world
     * @return the state of the cell in the next generation
     */
    public static boolean computeCell(boolean[][] world, int col, int row) {
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
     * @param world A two dimensional boolean array that represent ths world, where each element of world is a row and each element of a row is a cell
     * @return the updated world
     */
    public static boolean[][] nextGeneration(boolean[][] world) {
        boolean[][] newWorld = new boolean[world.length][];
        for (int j = 0; j < world.length; j++) {
            newWorld[j] = new boolean[world[j].length];
            for (int i = 0; i < world[j].length; i++) {
                setCell(newWorld, i, j, computeCell(world, i, j));
            }
        }
        return newWorld;
    }


    public static void play(boolean[][] world) throws Exception {
        int userResponse = 0;
        while (userResponse != 'q') {
            print(world);
            userResponse = System.in.read();
            world = nextGeneration(world);
        }
    }
    public static boolean getFromPackedLong(long packed, int position) {
            return ((packed >>> position) & 1) == 1;
    }

    public static void main(String[] args) throws Exception {
        int size = Integer.parseInt(args[0]);
        long initial = Long.decode(args[1]);
        boolean[][] world = new boolean[size][size];
        //place the long representation of the game board in the centre of "world"
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                world[i+size/2-4][j+size/2-4] = getFromPackedLong(initial,i*8+j);
            }
        }
        play(world);
    }
}
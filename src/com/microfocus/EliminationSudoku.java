package com.microfocus;

public class EliminationSudoku extends Sudoku {


    private Region[] regions = new Region[BOARD_LENGTH];
    /**
     * Break the board into 9 3x3 regions
     *
     * @param board a 2d Sudoku board
     */
    EliminationSudoku(int[][] board) {
        super(board);
        this.extractRegions();
    }

    /***
     * Wrapper method for extractRegion loop.
     */
    private void extractRegions() {
        int regionNumber = 1;
        while (regionNumber <= BOARD_LENGTH) {
            this.regions[regionNumber - 1] = this.extractRegion(this.board, regionNumber);
            regionNumber++;
        }
    }

    /**
     * Extracts a region from the board given a region number
     *
     * @param board        the sudoku board
     * @param regionNumber the region's number horizontally counting
     * @return Region
     */
    private Region extractRegion(int[][] board, int regionNumber) {
        int regionIndex = this.calculateRegionIndex(regionNumber);
        int rowUpperBoundary = this.calculateRegionRowBoundary(regionIndex);
        int colUpperBoundary = this.calculateRegionColumnBoundary(regionNumber, regionIndex);

        int[][] region = new int[3][3];
        int regionRow = 0;
        for (int row = rowUpperBoundary - (REGION_LENGTH - 1); row <= rowUpperBoundary; row++) {
            int regionColumn = 0;
            for (int column = colUpperBoundary - (REGION_LENGTH - 1); column <= colUpperBoundary; column++) {
                region[regionRow][regionColumn] = board[row][column];
                regionColumn++;
            }
            regionRow++;
        }
        return new Region(colUpperBoundary, rowUpperBoundary, region);
    }

    /**
     * Calculate the upper row boundary of a region
     *
     * @param regionIndex the Sudoku's region index
     * @return upper row boundary
     */
    private int calculateRegionRowBoundary(int regionIndex) {
        return ((regionIndex + 1) * REGION_LENGTH) - 1;
    }

    /**
     * Calculate the upper column boundary of a region
     *
     * @param regionNumber the Sudoku's region number
     * @param regionIndex  the region index
     * @return upper column boundary
     */
    private int calculateRegionColumnBoundary(int regionNumber, int regionIndex) {
        return ((regionNumber * REGION_LENGTH) - (regionIndex * BOARD_LENGTH)) - 1;
    }

    /**
     * Calculate the region index from the row number.
     * A region index is essentially the row of the region but since indices start from 0 it's an index
     * E.g. regions (horizontally counting) 1,2,3 belong in the first region row, this is region index 0
     * Regions 4,5,6 belong to the second region row, this is region index 1 etc.
     *
     * @param region the region number
     * @return region index
     */
    private int calculateRegionIndex(int region) {
        if (region < 1 || region > BOARD_LENGTH) {
            throw new IllegalArgumentException("Regions can only be in range 1-9");
        }
        return (region - 1) / REGION_LENGTH;
    }

    /**
     * Attempt to solve the sudoku using set theory
     * Return the sudoku with the progress made if a dead end is reached
     */
    public boolean solve() {
        boolean hasNoSolution = true;
        for (Region region : this.regions) {
            if (region.isSolved()) {
                continue;
            }
            boolean changed = region.fill(this.getConflictRows(region), this.getConflictColumns(region));
            this.updateBoard(region);
            hasNoSolution = !changed && hasNoSolution;
        }
        if (hasNoSolution) {
            return false;
        }
        if (!this.isSolved()) {
            return this.solve();
        }
        return true;

    }

    /***
     * Get region's conflict rows
     * @param region the region to check
     * @return int[][] rows of conflict
     */
    private int[][] getConflictRows(Region region) {
        int[][] rows = new int[REGION_LENGTH][];
        int rowIndex = 0;
        for (int i = region.rowUpperIndex - (REGION_LENGTH - 1); i <= region.rowUpperIndex; i++) {
            rows[rowIndex] = this.board[i];
            rowIndex++;
        }
        return rows;
    }

    /**
     * Get region's conflict columns
     *
     * @param region the region to check
     * @return int[][] columns of conflict
     */
    private int[][] getConflictColumns(Region region) {
        int[][] columns = new int[REGION_LENGTH][];
        int index = 0;
        for (int col = region.colUpperIndex - (REGION_LENGTH - 1); col <= region.colUpperIndex; col++) {
            columns[index] = getConflictColumn(col);
            index++;
        }
        return columns;
    }

    /**
     * Get only one conflict column
     * @param col the column index
     * @return int[]
     */
    private int[] getConflictColumn(int col) {
        int[] column = new int[BOARD_LENGTH];
        for (int i = 0; i < column.length; i++) {
            column[i] = this.board[i][col];
        }
        return column;
    }

    /***
     * Update the board with the newly edited region
     * @param region the region that changed
     */
    private void updateBoard(Region region) {
        int upperColumn = region.colUpperIndex;
        int upperRow = region.rowUpperIndex;
        int[][] matrix = region.getRegion();
        int rowIndex = 0;
        for (int i = upperRow - (REGION_LENGTH - 1); i <= upperRow; i++) {
            int colIndex = 0;
            for (int j = upperColumn - (REGION_LENGTH - 1); j <= upperColumn; j++) {
                this.board[i][j] = matrix[rowIndex][colIndex];
                colIndex++;
            }
            rowIndex++;
        }
    }

    /**
     * A board is solved if all of its regions are solved
     *
     * @return boolean
     */
    public boolean isSolved() {
        for (Region region : this.regions) {
            if (!region.isSolved()) {
                return false;
            }
        }
        return true;
    }
}

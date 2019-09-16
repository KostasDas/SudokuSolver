package com.microfocus;


public class BackTrackingSudoku extends Sudoku {
    /**
     * Break the board into 9 3x3 regions
     *
     * @param board a 2d Sudoku board
     */
    BackTrackingSudoku(int[][] board) {
        super(board);
    }

    /**
     * Recursively solve the board using backtracking
     *
     * @return boolean
     */
    public boolean solve() {
        for (int row = 0; row < this.board.length; row++) {
            for (int col = 0; col < this.board[row].length; col++) {
                if (this.board[row][col] != 0) {
                    continue;
                }
                for (int number = 1; number <= 9; number++) {
                    if (isNumberLegal(row, col, number)) {
                        this.board[row][col] = number;
                        if (this.solve()) {
                            return true;
                        } else {
                            this.board[row][col] = 0;
                        }
                    }
                }
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean isSolved() {
        for (int[] rows : this.board) {
            for (int value : rows) {
                if (value == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * If the number is contained in a row
     *
     * @param row    row number
     * @param number the number to check
     * @return boolean
     */
    private boolean isContainedInRow(int row, int number) {
        for (int i = 0; i < BOARD_LENGTH; i++) {
            if (this.board[row][i] == number) {
                return true;
            }
        }
        return false;
    }

    /**
     * If the number is contained in a column
     *
     * @param col    column number
     * @param number the number to check
     * @return boolean
     */
    private boolean isContainedInColumn(int col, int number) {
        for (int i = 0; i < BOARD_LENGTH; i++) {
            if (this.board[i][col] == number) {
                return true;
            }
        }
        return false;
    }

    /**
     * Find the number's region
     *
     * @param row    row coordinate of the number
     * @param col    column coordinate fo the number
     * @param number the number
     * @return boolean
     */
    private boolean isContainedInRegion(int row, int col, int number) {
        int r = row - row % 3;
        int c = col - col % 3;
        for (int i = r; i < r + REGION_LENGTH; i++) {
            for (int j = c; j < c + REGION_LENGTH; j++) {
                if (this.board[i][j] == number) {
                    return true;
                }
            }

        }
        return false;
    }

    /***
     * If the number is legal to be in the row or the column or the 3x3 region contained so that it can be used in a cell
     * @param row the row to check against
     * @param col the column to check against
     * @param number the number
     * @return boolean
     */
    private boolean isNumberLegal(int row, int col, int number) {
        return !(isContainedInRow(row, number) || isContainedInColumn(col, number) || isContainedInRegion(row, col, number));
    }
}

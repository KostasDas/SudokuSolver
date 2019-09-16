package com.microfocus;

import java.util.Arrays;

public abstract class Sudoku implements Solvable {

    static final int REGION_LENGTH = 3;
    static final int BOARD_LENGTH = 9;
    protected int[][] board;

    /**
     * Break the board into 9 3x3 regions
     *
     * @param board a 2d Sudoku board
     */
    Sudoku(int[][] board) {
        this.board = board;
    }

    int[][] getBoard() {
        return board;
    }

    @Override
    public String toString() {
        StringBuilder matrix = new StringBuilder();
        for (int[] ints : this.board) {
            matrix.append(Arrays.toString(ints));
            matrix.append('\n');
        }
        return matrix.toString();
    }
}

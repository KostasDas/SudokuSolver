package com.microfocus;

public class SudokuSolver {


    public void solve(int[][] board) {
        System.out.println("Attempting to solve Sudoku using elimination process:");
        Sudoku sudoku = new EliminationSudoku(board);
        System.out.println(sudoku);
        boolean isSolved = this.solveSudoku(sudoku);

        if (isSolved) {
            return;
        }
        System.out.println("Reached dead-end, requires guessing");
        System.out.println("Attempting to solve Sudoku using backtracking:");

        sudoku = new BackTrackingSudoku(sudoku.getBoard());
        this.solveSudoku(sudoku);

    }
    private boolean solveSudoku(Sudoku s) {
        long startTime = System.nanoTime();
        boolean solved = s.solve();
        long endTime = System.nanoTime();
        if (solved) {
            this.rollCredits(s, (endTime-startTime)/1000);
        }
        return  solved;
    }
    private void rollCredits(Sudoku s, long duration) {
        System.out.println("Done - duration: " + duration + " milliseconds");
        System.out.println(s);
    }
}

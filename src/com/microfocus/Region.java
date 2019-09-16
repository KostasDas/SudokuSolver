package com.microfocus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

class Region {


    final int colUpperIndex;
    final int rowUpperIndex;
    private int[][] region;
    private HashSet<Integer> availableNumbers;

    Region(int colUpperIndex, int rowUpperIndex, int[][] region) {
        this.region = region;
        this.colUpperIndex = colUpperIndex;
        this.rowUpperIndex = rowUpperIndex;
        this.availableNumbers = SudokuSetHelper.getCandidateNumbers(this.getUsedNumbers());
    }

    /**
     * Getting region's used numbers to calculate the available numbers
     * @return HashSet
     */
    private HashSet<Integer> getUsedNumbers() {
        HashSet<Integer> usedNumbers = new HashSet<>();
        for (int[] rows : this.region) {
            for (int cols : rows) {
                if (cols != 0) {
                    usedNumbers.add(cols);
                }
            }
        }
        return usedNumbers;
    }

    /**
     * A region is solved if it contains no 0s
     *
     * @return boolean
     */
    boolean isSolved() {
        for (int[] rows : this.region) {
            for (int cols : rows) {
                if (cols == 0) {
                    return false;
                }
            }
        }
        return true;
    }



    /**
     * @param rows rows of conflict
     * @param cols columns of conflict
     * @return if the region changed or not
     */
    boolean fill(int[][] rows, int[][] cols) {
        boolean changed = false;
        for (int row = 0; row < this.region.length; row++) {
            HashSet<Integer> rowAvailableNumbers = SudokuSetHelper.getFromArrayCandidateNumbers(rows[row]);
            for (int col = 0; col < this.region[row].length; col++) {
                if (this.region[row][col] == 0) {
                    ArrayList<HashSet<Integer>> sets = new ArrayList<>();
                    sets.add(SudokuSetHelper.getFromArrayCandidateNumbers(cols[col]));
                    sets.add(this.availableNumbers);
                    sets.add(rowAvailableNumbers);
                    HashSet<Integer> cellAvailableNumbers = SudokuSetHelper.setIntersection(sets);
                    if (cellAvailableNumbers.size() == 1) {
                        this.region[row][col] = (int) cellAvailableNumbers.toArray()[0];
                        changed = true;
                    }
                }
            }
        }
        return changed;
    }

    @Override
    public String toString() {
        StringBuilder matrix = new StringBuilder();
        for (int[] ints : this.region) {
            matrix.append(Arrays.toString(ints));
            matrix.append('\n');
        }
        return matrix.toString();
    }

    /**
     * Get the current 2d board
     * @return int[][]
     */
    int[][] getRegion() {
        return this.region;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }

        final Region other = (Region) obj;
        return Arrays.deepEquals(other.region, this.region) && other.rowUpperIndex == this.rowUpperIndex && other.colUpperIndex == this.colUpperIndex;
    }
}

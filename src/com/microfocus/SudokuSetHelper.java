package com.microfocus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

public class SudokuSetHelper {


    /***
     * Convert a primitive int array to a boxed Integer Hash set to compare sets.
     * Normally this should be on a helper class but It's an overkill since there are no other methods at the moment
     * @param arr an array of ints
     * @return Integer HashSet
     */
    public static HashSet<Integer> arrayOfIntsToSet(int[] arr) {
        Integer[] row = Arrays.stream(arr).boxed().toArray(Integer[]::new);
        return new HashSet<>(Arrays.asList(row));
    }

    /**
     * Get which numbers can be used for a sudoku cell
     *
     * @param row HashSet of integers
     * @return Hashset difference from all numbers
     */
    public static HashSet<Integer> getCandidateNumbers(HashSet<Integer> row) {
        HashSet<Integer> availableNumbers = new HashSet<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9));
        availableNumbers.removeAll(row);

        return availableNumbers;
    }

    /**
     *
     * @param includedNumbers
     * @return
     */
    public static HashSet<Integer> getFromArrayCandidateNumbers(int[] includedNumbers) {
        return SudokuSetHelper.getCandidateNumbers(SudokuSetHelper.arrayOfIntsToSet(includedNumbers));
    }

    /**
     *
     * @param sets
     * @return
     */
    public static HashSet<Integer> setIntersection(ArrayList<HashSet<Integer>> sets) {
        if (sets.size() <= 1) {
            throw new IllegalArgumentException("Size of list must be more than 1");
        }
        HashSet<Integer> firstSet = new HashSet<>(sets.remove(0));
        for (HashSet<Integer> set: sets) {
            firstSet.retainAll(set);
        }
        return firstSet;
    }
}

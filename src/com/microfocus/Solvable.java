package com.microfocus;

public interface Solvable {

    /**
     * Any puzzle that can be solved implements this interface
     * @return if the puzzle is solved or not
     */
    boolean solve();

    /**
     * If the puzzle is currently solved
     * @return boolean
     */
    boolean isSolved();
}

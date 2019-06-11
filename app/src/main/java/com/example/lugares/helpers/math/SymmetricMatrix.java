package com.example.lugares.helpers.math;

public class SymmetricMatrix {
	/**
	 * Returns the 1D array size "of the up part of the symmetric matrix"
	 * @param diagonal_size
	 * @return
	 */
	public static int getArray1dSize( int diagonal_size ){
		return (diagonal_size * diagonal_size - diagonal_size)/2;
	}
	/**
	 * Returns the 1D array INDEX "of the up part of the symmetric matrix" (i.e. row != col)
	 * @param row
	 * @param col
	 * @param diagonal_size
	 * @return	index = (2*n - 3 - i)*i/2 + j - 1, with 0 <= i < j < n
	 */
	public static int getArray1dIndexRowCol( int row, int col, int diagonal_size ){
		return( row < col ? ((2*diagonal_size - 3 - row)*row/2 + col - 1) : ((2*diagonal_size - 3 - col)*col/2 + row - 1) );
	}
}

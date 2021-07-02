package com.tictactoe.tictactoe

import java.lang.IllegalArgumentException
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

class TicTacToe(private val n: Int) {
    private val board: Array<IntArray> = Array(n) { IntArray(n) }
    private val rowSum: IntArray = IntArray(n)
    private val colSum: IntArray = IntArray(n)
    private var diagSum = 0
    private var revDiagSum = 0
    var winner = 0

    /**
     * Makes a move on the board and returns the winner if the move is a winning move.
     *
     * @param player is either 0 or 1
     * @param row    is the move's row index
     * @param col    is the move's column index
     * @return winner +1 if first player wins, -1 if second player wins and 0 otherwise.
     * @throws IllegalArgumentException if the move is an illegal move.
     */
    @Throws(IllegalArgumentException::class)
    fun move(player: Int, row: Int, col: Int): Int {
        var player = player
        require(!(row < 0 || col < 0 || row >= n || col >= n)) { "Move out of board boundary!" }
        require(board[row][col] == 0) { "Square is already occupied!" }
        require(!(player != 0 && player != 1)) { "Invalid Player!" }
        require(winner == 0) { "Board is decided!" }
        player = if (player == 0) -1 else 1
        board[row][col] = player
        rowSum[row] += player
        colSum[col] += player
        if (row == col) {
            diagSum += player
        }
        if (row == n - 1 - col) {
            revDiagSum += player
        }
        if (abs(rowSum[row]) == n || abs(colSum[col]) == n || abs(diagSum) == n || abs(revDiagSum) == n) {
            winner = player
        }
        return winner
    }
}
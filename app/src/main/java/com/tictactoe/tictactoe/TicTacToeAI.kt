package com.tictactoe.tictactoe

import kotlin.math.max
import kotlin.math.min

class TicTacToeAI(private val n: Int, uPeg: Int) {
    val board: Array<IntArray> = Array(n) { IntArray(n) }
    private val userPeg = uPeg
    private val computerPeg = uPeg*-1
    val availableMoves: List<Pair<Int, Int>>
        get() {
            val cells = mutableListOf<Pair<Int, Int>>()
            for (i in board.indices) {
                for (j in board.indices) {
                    if (board[i][j] == 0) {
                        cells.add(Pair(i, j))
                    }
                }
            }
            return cells
        }

    // Boolean variable to check game status with an explicit getter.
    val isGameOver: Boolean get() = hasComputerWon() || hasPlayerWon() || availableMoves.isEmpty()

    fun hasComputerWon(): Boolean {
        if (board[0][0] == board[1][1] && board[0][0] == board[2][2] && board[0][0] == computerPeg ||
                board[0][2] == board[1][1] && board[0][2] == board[2][0] && board[0][2] == computerPeg) {
            return true
        }

        for (i in board.indices) {
            if (board[i][0] == board[i][1] && board[i][0] == board[i][2] && board[i][0] == computerPeg ||
                    board[0][i] == board[1][i] && board[0][i] == board[2][i] && board[0][i] == computerPeg) {
                return true
            }
        }

        return false
    }

    fun hasPlayerWon(): Boolean {
        if (board[0][0] == board[1][1] && board[0][0] == board[2][2] && board[0][0] == userPeg ||
                board[0][2] == board[1][1] && board[0][2] == board[2][0] && board[0][2] == userPeg) {
            return true
        }

        for (i in board.indices) {
            if (board[i][0] == board[i][1] && board[i][0] == board[i][2] && board[i][0] == userPeg ||
                    board[0][i] == board[1][i] && board[0][i] == board[2][i] && board[0][i] == userPeg) {
                return true
            }
        }

        return false
    }

    var computersMove: Pair<Int, Int>? = Pair(0, 0)
    
    fun minimax(depth: Int, player: Int): Int {
        if (hasComputerWon()) return +1
        if (hasPlayerWon()) return -1

        if (availableMoves.isEmpty()) return 0

        var minValue = Integer.MAX_VALUE
        var maxValue = Integer.MIN_VALUE

        for (i in availableMoves.indices) {
            val traversedMove = availableMoves[i]
            if (player == computerPeg) {
                move(computerPeg, traversedMove.first, traversedMove.second)
                val currentScore = minimax(depth + 1, userPeg)
                maxValue = max(currentScore, maxValue)

                if (currentScore >= 0) {
                    if (depth == 0) computersMove = traversedMove
                }

                if (currentScore == 1) {
                    board[traversedMove.first][traversedMove.second] = 0
                    break
                }

                if (i == availableMoves.size - 1 && maxValue < 0) {
                    if (depth == 0) computersMove = traversedMove
                }

            } else if (player == userPeg) {
                move(userPeg, traversedMove.first, traversedMove.second)
                val currentScore = minimax(depth + 1, computerPeg)
                minValue = min(currentScore, minValue)

                if (minValue == -1) {
                    board[traversedMove.first][traversedMove.second] = 0
                    break
                }
            }
            board[traversedMove.first][traversedMove.second] = 0
        }

        return if (player == computerPeg) maxValue else minValue
    }

    fun move(player: Int, row: Int, col: Int) {
        board[row][col] = player
    }
}
package com.tictactoe.tictactoe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.tictactoe.tictactoe.databinding.ActivityGameScreenAiBinding

class GameScreenAI : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityGameScreenAiBinding
    private lateinit var board: Array<Array<ImageButton>>
    private lateinit var tictactoe: TicTacToeAI
    private var gameResult = Integer.MIN_VALUE
    private var userPeg: Int = -1
    private var computerPeg: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameScreenAiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        board = arrayOf(
                arrayOf(binding.button1, binding.button2, binding.button3),
                arrayOf(binding.button4, binding.button5, binding.button6),
                arrayOf(binding.button7, binding.button8, binding.button9)
        )

        // Getting Intent Data (2) -> X and (-2) -> O
        userPeg = intent.getIntExtra("USER_PEG$2", 1)
        computerPeg = userPeg*-1

        tictactoe = TicTacToeAI(3, userPeg)

        // X: Makes the first move
        binding.playerTurn.text = "X's Turn"

        if (userPeg == -2) {
            val firstMove = tictactoe.availableMoves.random()
            tictactoe.move(computerPeg, firstMove.first, firstMove.second)
            if (!tictactoe.isGameOver) binding.playerTurn.text = if (userPeg == 2) "X's Turn" else "O's Turn"
            updateUI()
        }

        // Setting up the OnClickListeners
        for (i in board) {
            for (button in i) {
                button.setOnClickListener(this)
            }
        }

        binding.reset.setOnClickListener {
            // Reset the ImageButtons
            for (i in board) {
                for (button in i) {
                    button.apply {
                        setImageResource(0)
                        isEnabled = true
                    }
                }
            }

            // Reset the Game Setup
            userPeg = userPeg
            tictactoe = TicTacToeAI(3, userPeg)
            binding.playerTurn.text = "X's Turn"
            gameResult = Integer.MIN_VALUE
            if (userPeg == -2) {
                val firstMove = tictactoe.availableMoves.random()
                tictactoe.move(computerPeg, firstMove.first, firstMove.second)
                if (!tictactoe.isGameOver) binding.playerTurn.text = if (userPeg == 2) "X's Turn" else "O's Turn"
                updateUI()
            }
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.button1 -> press(0, 0)
            R.id.button2 -> press(0, 1)
            R.id.button3 -> press(0, 2)
            R.id.button4 -> press(1, 0)
            R.id.button5 -> press(1, 1)
            R.id.button6 -> press(1, 2)
            R.id.button7 -> press(2, 0)
            R.id.button8 -> press(2, 1)
            R.id.button9 -> press(2, 2)
        }
        if (!tictactoe.isGameOver) binding.playerTurn.text = if (userPeg == 2) "X's Turn" else "O's Turn"
        updateUI()
        checkResult(tictactoe)
    }

    private fun checkResult(tictactoe: TicTacToeAI) {
        when {
            tictactoe.hasPlayerWon() -> gameResult = 1
            tictactoe.hasComputerWon() -> gameResult = -1
            tictactoe.isGameOver -> gameResult = 0
        }

        if (gameResult == 1 || gameResult == -1) {
            // Disabling all buttons
            binding.playerTurn.text = "Game Over"
            for (i in board) {
                for (button in i) {
                    button.isEnabled = false
                }
            }

            val resultDialogView = LayoutInflater.from(this).inflate(R.layout.dialog_game_result, null);
            val builder = AlertDialog.Builder(this).setView(resultDialogView)
            val alertDialog = builder.show()

            // View Resource Setup
            var iRes = if (gameResult == 1) R.drawable.ic_won else R.drawable.ic_lost
            var tRes = if (gameResult == 1) "You Won" else "You Lost"
            resultDialogView.findViewById<ImageView>(R.id.dialog_image).setImageResource(iRes)
            resultDialogView.findViewById<TextView>(R.id.result).text = tRes

            //Setting the background of layout as transparent
            alertDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

            val exitButton = resultDialogView.findViewById<Button>(R.id.exit_button)
            exitButton.setOnClickListener {
                finishAffinity()
            }

            val resetButton = resultDialogView.findViewById<Button>(R.id.reset_button)
            resetButton.setOnClickListener {
                binding.reset.performClick()
                alertDialog.dismiss()
            }
        }
        else if (gameResult == 0 && tictactoe.availableMoves.isEmpty()) {
            binding.playerTurn.text = "It's a Draw"
        }
    }

    private fun press(row: Int, col: Int) {
        binding.playerTurn.text = if (computerPeg == 2) "X's Turn" else "O's Turn"
        if (!tictactoe.isGameOver) {
            tictactoe.move(userPeg, row, col)
            tictactoe.minimax(0, computerPeg)
            tictactoe.computersMove?.let {
                tictactoe.move(computerPeg, it.first, it.second)
            }
        }
    }

    private fun updateUI() {
        for (i in tictactoe.board.indices) {
            for (j in tictactoe.board.indices) {
                when {
                    tictactoe.board[i][j] == userPeg -> board[i][j].apply {
                        if (userPeg == 2) setImageResource(R.drawable.ic_x) else setImageResource(R.drawable.ic_o)
                        isEnabled = false
                    }
                    tictactoe.board[i][j] == computerPeg -> {
                        Handler(Looper.getMainLooper()).postDelayed({
                            board[i][j].apply {
                                if (computerPeg == 2) setImageResource(R.drawable.ic_x) else setImageResource(R.drawable.ic_o)
                                isEnabled = false
                            }
                        },500)
                    }
                    else -> board[i][j].apply {
                        setImageResource(0)
                        isEnabled = true
                    }
                }
            }
        }
    }
}




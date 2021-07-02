package com.tictactoe.tictactoe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.tictactoe.tictactoe.databinding.ActivityGameScreenBinding

class GameScreen : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityGameScreenBinding
    private lateinit var board: Array<Array<ImageButton>>
    private var tictactoe = TicTacToe(3)                            // 3x3 Tic Tac Toe Board
    private var player = 1                                             // 1-X and 0-O
    private var moveCount = 0
    private var gameResult = Integer.MIN_VALUE
    private var userPeg:Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        board = arrayOf(
                arrayOf(binding.button1, binding.button2, binding.button3),
                arrayOf(binding.button4, binding.button5, binding.button6),
                arrayOf(binding.button7, binding.button8, binding.button9)
        )

        // Getting Intent Data
        userPeg = intent.getIntExtra("USER_PEG$2",1)

        // X: Makes the first move
        binding.playerTurn.text = "X's Turn"

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
            tictactoe = TicTacToe(3)
            player = 1
            moveCount = 0
            gameResult = Integer.MIN_VALUE
            userPeg = userPeg
            binding.playerTurn.text = "X's Turn"
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.button1 -> {
                binding.button1.apply {
                    if (player == 1) setImageResource(R.drawable.ic_x) else setImageResource(R.drawable.ic_o)
                    isEnabled = false
                }
                moveCount++
                gameResult = tictactoe.move(player,0,0)
            }
            R.id.button2 -> {
                binding.button2.apply {
                    if (player == 1) setImageResource(R.drawable.ic_x) else setImageResource(R.drawable.ic_o)
                    isEnabled = false
                }
                moveCount++
                gameResult = tictactoe.move(player,0,1)
            }
            R.id.button3 -> {
                binding.button3.apply {
                    if (player == 1) setImageResource(R.drawable.ic_x) else setImageResource(R.drawable.ic_o)
                    isEnabled = false
                }
                moveCount++
                gameResult = tictactoe.move(player,0,2)
            }
            R.id.button4 -> {
                binding.button4.apply {
                    if (player == 1) setImageResource(R.drawable.ic_x) else setImageResource(R.drawable.ic_o)
                    isEnabled = false
                }
                moveCount++
                gameResult = tictactoe.move(player,1,0)
            }
            R.id.button5 -> {
                binding.button5.apply {
                    if (player == 1) setImageResource(R.drawable.ic_x) else setImageResource(R.drawable.ic_o)
                    isEnabled = false
                }
                moveCount++
                gameResult = tictactoe.move(player,1,1)
            }
            R.id.button6 -> {
                binding.button6.apply {
                    if (player == 1) setImageResource(R.drawable.ic_x) else setImageResource(R.drawable.ic_o)
                    isEnabled = false
                }
                moveCount++
                gameResult = tictactoe.move(player,1,2)
            }
            R.id.button7 -> {
                binding.button7.apply {
                    if (player == 1) setImageResource(R.drawable.ic_x) else setImageResource(R.drawable.ic_o)
                    isEnabled = false
                }
                moveCount++
                gameResult = tictactoe.move(player,2,0)
            }
            R.id.button8 -> {
                binding.button8.apply {
                    if (player == 1) setImageResource(R.drawable.ic_x) else setImageResource(R.drawable.ic_o)
                    isEnabled = false
                }
                moveCount++
                gameResult = tictactoe.move(player,2,1)
            }
            R.id.button9 -> {
                binding.button9.apply {
                    if (player == 1) setImageResource(R.drawable.ic_x) else setImageResource(R.drawable.ic_o)
                    isEnabled = false
                }
                moveCount++
                gameResult = tictactoe.move(player,2,2)
            }
        }

        // Switch the player after each turn: 0 ^ 1 = 1, 1 ^ 1 = 0
        player = player.xor(1)

        // Game State TextView
        if(moveCount == 9) binding.playerTurn.text = "It's a Draw"
        else binding.playerTurn.text = if (player == 1) "X's Turn" else "O's Turn"

        // Check for the result at each move
        checkResult(gameResult)
    }

    private fun checkResult(gameResult: Int) {
        // * gameResult: +1 if first player wins, -1 if second player wins and 0 otherwise.

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
            var iRes = if((gameResult > 0 && userPeg == 1)||(gameResult < 0 && userPeg == 0)) R.drawable.ic_won else R.drawable.ic_lost
            var tRes = if((gameResult > 0 && userPeg == 1)||(gameResult < 0 && userPeg == 0)) "You Won" else "You Lost"
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
    }
}
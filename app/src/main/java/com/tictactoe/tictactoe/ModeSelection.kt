package com.tictactoe.tictactoe

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ImageButton
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.tictactoe.tictactoe.databinding.ActivityModeSelectionBinding

class ModeSelection : AppCompatActivity() {

    private lateinit var binding: ActivityModeSelectionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityModeSelectionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Play With Takeo Button Listener
        binding.takeo.setOnClickListener {
            val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_pick_peg, null);
            val builder = AlertDialog.Builder(this).setView(dialogView)
            val alertDialog = builder.show()

            //Setting the background of layout as transparent
            alertDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

            val buttonO = dialogView.findViewById<ImageButton>(R.id.button_o)
            buttonO.setOnClickListener {
                val gameScreenIntent = Intent(this, GameScreenAI::class.java)
                gameScreenIntent.putExtra("USER_PEG$2", -2)
                startActivity(gameScreenIntent)
            }

            val buttonX = dialogView.findViewById<ImageButton>(R.id.button_x)
            buttonX.setOnClickListener {
                val gameScreenIntent = Intent(this, GameScreenAI::class.java)
                gameScreenIntent.putExtra("USER_PEG$2", 2)
                startActivity(gameScreenIntent)
            }
        }

        //Play With Friend Button Listener
        binding.friend.setOnClickListener {
            val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_pick_peg, null);
            val builder = AlertDialog.Builder(this).setView(dialogView)
            val alertDialog = builder.show()

            //Setting the background of layout as transparent
            alertDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

            val buttonO = dialogView.findViewById<ImageButton>(R.id.button_o)
            buttonO.setOnClickListener {
                val gameScreenIntent = Intent(this, GameScreen::class.java)
                gameScreenIntent.putExtra("USER_PEG$2", 0)
                startActivity(gameScreenIntent)
            }

            val buttonX = dialogView.findViewById<ImageButton>(R.id.button_x)
            buttonX.setOnClickListener {
                val gameScreenIntent = Intent(this, GameScreen::class.java)
                gameScreenIntent.putExtra("USER_PEG$2", 1)
                startActivity(gameScreenIntent)
            }
        }
    }
}
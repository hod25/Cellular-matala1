package com.example.tictactoe

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.GridLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private var currentPlayer = "X"
    private val board = Array(3) { arrayOfNulls<String>(3) }
    private lateinit var buttons: Array<Button>
    private lateinit var playAgainButton: Button
    private lateinit var statusTextView: TextView // Add reference to the TextView for the turn

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val gridLayout = findViewById<GridLayout>(R.id.gridLayout)
        buttons = Array(9) { index ->
            val button = gridLayout.getChildAt(index) as Button
            button.setOnClickListener { onCellClick(it as Button, index) }
            button
        }
        playAgainButton = findViewById(R.id.playAgainButton)
        playAgainButton.setOnClickListener { resetGame() }

        statusTextView = findViewById(R.id.statusTextView) // Initialize the TextView
        updateStatusText() // Update the text for the first turn
    }

    private fun onCellClick(button: Button, index: Int) {
        val row = index / 3
        val col = index % 3

        if (board[row][col] != null) return

        board[row][col] = currentPlayer
        button.text = currentPlayer

        if (currentPlayer == "X") {
            button.setTextColor(resources.getColor(R.color.player_x_color, theme))
        } else {
            button.setTextColor(resources.getColor(R.color.player_o_color, theme))
        }

        if (checkWinner()) {
            Toast.makeText(this, "$currentPlayer wins!", Toast.LENGTH_SHORT).show()
            playAgainButton.visibility = View.VISIBLE
        } else {
            currentPlayer = if (currentPlayer == "X") "O" else "X"
            updateStatusText() // עדכון התצוגה של תור השחקן
        }
    }


    private fun checkWinner(): Boolean {
        // Check rows, columns, and diagonals
        for (i in 0..2) {
            if (board[i][0] == currentPlayer && board[i][1] == currentPlayer && board[i][2] == currentPlayer) {
                return true
            }
            if (board[0][i] == currentPlayer && board[1][i] == currentPlayer && board[2][i] == currentPlayer) {
                return true
            }
        }
        if (board[0][0] == currentPlayer && board[1][1] == currentPlayer && board[2][2] == currentPlayer) {
            return true
        }
        if (board[0][2] == currentPlayer && board[1][1] == currentPlayer && board[2][0] == currentPlayer) {
            return true
        }
        return false
    }

    private fun resetGame() {
        // Clear board
        for (i in 0..2) {
            for (j in 0..2) {
                board[i][j] = null
            }
        }
        // Reset buttons
        buttons.forEach { it.text = "" }
        currentPlayer = "X"
        playAgainButton.visibility = View.GONE
        updateStatusText() // Reset the status text
    }

    private fun updateStatusText() {
        statusTextView.text = "Player $currentPlayer's Turn"
    }
}

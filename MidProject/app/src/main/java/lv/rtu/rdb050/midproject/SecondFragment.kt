// Player vs Player fragment
package lv.rtu.rdb050.midproject

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import lv.rtu.rdb050.midproject.databinding.FragmentSecondBinding


class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null

    private val binding get() = _binding!!

    //////// Used code from: https://github.com/usmaanz/Tic-Tac-Toe
    private lateinit var gameManager: GameManager
    private lateinit var one: TextView
    private lateinit var two: TextView
    private lateinit var three: TextView
    private lateinit var four: TextView
    private lateinit var five: TextView
    private lateinit var six: TextView
    private lateinit var seven: TextView
    private lateinit var eight: TextView
    private lateinit var nine: TextView
    private lateinit var startNewGameButton: Button
    private lateinit var player1Points: TextView
    private lateinit var player2Points: TextView
    ///////////////////////////////////////////////////////////////
    private lateinit var playerCurrent: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)

        ////////Used code from: https://github.com/usmaanz/Tic-Tac-Toe
        gameManager = GameManager()

        one = binding.one
        two = binding.two
        three = binding.three
        four = binding.four
        five = binding.five
        six = binding.six
        seven = binding.seven
        eight = binding.eight
        nine = binding.nine
        startNewGameButton = binding.buttonSecond
        player1Points = binding.playerOneScore
        player2Points = binding.playerTwoScore
        playerCurrent = binding.currentPlayer

        one.setOnClickListener { onBoxClicked(one, Position(0, 0)) }
        two.setOnClickListener { onBoxClicked(two, Position(0, 1)) }
        three.setOnClickListener { onBoxClicked(three, Position(0, 2)) }
        four.setOnClickListener { onBoxClicked(four, Position(1, 0)) }
        five.setOnClickListener { onBoxClicked(five, Position(1, 1)) }
        six.setOnClickListener { onBoxClicked(six, Position(1, 2)) }
        seven.setOnClickListener { onBoxClicked(seven, Position(2, 0)) }
        eight.setOnClickListener { onBoxClicked(eight, Position(2, 1)) }
        nine.setOnClickListener { onBoxClicked(nine, Position(2, 2)) }

        startNewGameButton.setOnClickListener {
            //startNewGameButton.visibility = View.GONE
            gameManager.reset()
            resetboxes()
            playerCurrent.text = "Current Player: X"
        }

        updatePoints()

        return binding.root

    }

    private fun updatePoints() {
        player1Points.text = "Player X Points: ${gameManager.player1Points}"
        player2Points.text = "Player O Points: ${gameManager.player2Points}"

    }

    private fun resetboxes() {
        one.text = ""
        two.text = ""
        three.text = ""
        four.text = ""
        five.text = ""
        six.text = ""
        seven.text = ""
        eight.text = ""
        nine.text = ""
        one.background = null
        two.background = null
        three.background = null
        four.background = null
        five.background = null
        six.background = null
        seven.background = null
        eight.background = null
        nine.background = null
        one.isEnabled = true
        two.isEnabled = true
        three.isEnabled = true
        four.isEnabled = true
        five.isEnabled = true
        six.isEnabled = true
        seven.isEnabled = true
        eight.isEnabled = true
        nine.isEnabled = true
    }

    private fun onBoxClicked(box: TextView, position: Position) {
        if (box.text.isEmpty()) {
            box.text = gameManager.currentPlayerMark

            if (gameManager.currentPlayer == 1){
                playerCurrent.text = "Current Player: O"
            } else {
                playerCurrent.text = "Current Player: X"
            }

            val winningLine = gameManager.makeMove(position)
            if (winningLine != null) {

                if (gameManager.currentPlayer == 1){
                    playerCurrent.text = "Player X wins!"
                } else {
                    playerCurrent.text = "Player O wins!"
                }


                updatePoints()
                disableBoxes()
                //startNewGameButton.visibility = View.VISIBLE
                showWinner(winningLine)
            }
            if (gameManager.isDraw()){
                playerCurrent.text = "It's a draw!"
            }
        }
    }

    private fun disableBoxes() {
        one.isEnabled = false
        two.isEnabled = false
        three.isEnabled = false
        four.isEnabled = false
        five.isEnabled = false
        six.isEnabled = false
        seven.isEnabled = false
        eight.isEnabled = false
        nine.isEnabled = false
    }

    private fun showWinner(winningLine: WinningLine) {
        val (winningBoxes, background) = when (winningLine) {
            WinningLine.ROW_0 -> Pair(listOf(one, two, three), R.drawable.horizontal_line)
            WinningLine.ROW_1 -> Pair(listOf(four, five, six), R.drawable.horizontal_line)
            WinningLine.ROW_2 -> Pair(listOf(seven, eight, nine), R.drawable.horizontal_line)
            WinningLine.COLUMN_0 -> Pair(listOf(one, four, seven), R.drawable.vertical_line)
            WinningLine.COLUMN_1 -> Pair(listOf(two, five, eight), R.drawable.vertical_line)
            WinningLine.COLUMN_2 -> Pair(listOf(three, six, nine), R.drawable.vertical_line)
            WinningLine.DIAGONAL_LEFT -> Pair(listOf(one, five, nine),
                R.drawable.left_diagonal_line
            )
            WinningLine.DIAGONAL_RIGHT -> Pair(listOf(three, five, seven),
                R.drawable.right_diagonal_line
            )
        }

        winningBoxes.forEach { box ->
            box.background =
                context?.let { ContextCompat.getDrawable(it.applicationContext, background) }
        }

    }

    /////////////////////////////////////////////////////////////////////////////////////////////


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
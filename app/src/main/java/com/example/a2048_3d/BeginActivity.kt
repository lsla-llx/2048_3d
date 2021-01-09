package com.example.a2048_3d

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.*
import com.example.a2048_3d.R
import java.lang.StringBuilder
import java.util.*
import kotlin.random.Random
import android.media.SoundPool
import kotlinx.android.synthetic.main.begin_main.*


var note : Int = 0

class BeginActivity : AppCompatActivity()
{

    private lateinit var gameScoreTextView : TextView
    private lateinit var swipeViewHolder : FrameLayout
    private lateinit var resultTextView : TextView
    private lateinit var recordTextView : TextView
    private lateinit var startGameButton : ImageButton
    private lateinit var toolButton : ImageButton

    private lateinit var b_1 : Button
    private lateinit var b_2 : Button
    private lateinit var b_3 : Button
    private lateinit var b_4 : Button
    private lateinit var b_5 : Button
    private lateinit var b_6 : Button
    private lateinit var b_7 : Button
    private lateinit var b_8 : Button
    private lateinit var b_9 : Button
    private lateinit var b_10 : Button
    private lateinit var b_11 : Button
    private lateinit var b_12 : Button
    private lateinit var b_13 : Button
    private lateinit var b_14 : Button
    private lateinit var b_15 : Button
    private lateinit var b_16 : Button





    private var emptyFields : Int = 16
    private var score : Int = 0
    private var isGameOver : Boolean = false

    private lateinit var gameField : Array<Array<Button>>

    private var gameFieldArray : Array<IntArray> = arrayOf(intArrayOf(0, 0, 0, 0),
        intArrayOf(0, 0, 0, 0),
        intArrayOf(0, 0, 0, 0),
        intArrayOf(0, 0, 0, 0))

    private val TAG = "___2048"

    private var countMusic = 0

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.begin_main)

        swipeViewHolder = findViewById(R.id.transparent_view)
        resultTextView = findViewById(R.id.result_tv)
        gameScoreTextView = findViewById(R.id.game_score_tv)
        recordTextView = findViewById(R.id.record_tv)

        gameField = Array(4) {row ->
            Array(4) {col ->
                Button(this)
            }
        }

        gameField[0][0] = findViewById(R.id.button_1_1)
        gameField[0][1] = findViewById(R.id.button_1_2)
        gameField[0][2] = findViewById(R.id.button_1_3)
        gameField[0][3] = findViewById(R.id.button_1_4)
        gameField[1][0] = findViewById(R.id.button_2_1)
        gameField[1][1] = findViewById(R.id.button_2_2)
        gameField[1][2] = findViewById(R.id.button_2_3)
        gameField[1][3] = findViewById(R.id.button_2_4)
        gameField[2][0] = findViewById(R.id.button_3_1)
        gameField[2][1] = findViewById(R.id.button_3_2)
        gameField[2][2] = findViewById(R.id.button_3_3)
        gameField[2][3] = findViewById(R.id.button_3_4)
        gameField[3][0] = findViewById(R.id.button_4_1)
        gameField[3][1] = findViewById(R.id.button_4_2)
        gameField[3][2] = findViewById(R.id.button_4_3)
        gameField[3][3] = findViewById(R.id.button_4_4)


         b_1 = findViewById(R.id.button_1_1)
        b_2 = findViewById(R.id.button_1_2)
         b_3 = findViewById(R.id.button_1_3)
        b_4 = findViewById(R.id.button_1_4)
         b_5 = findViewById(R.id.button_2_1)
         b_6 = findViewById(R.id.button_2_2)
         b_7 = findViewById(R.id.button_2_3)
        b_8 = findViewById(R.id.button_2_4)
         b_9 = findViewById(R.id.button_3_1)
         b_10 = findViewById(R.id.button_3_2)
         b_11= findViewById(R.id.button_3_3)
         b_12 = findViewById(R.id.button_3_4)
         b_13= findViewById(R.id.button_4_1)
         b_14 = findViewById(R.id.button_4_2)
         b_15 = findViewById(R.id.button_4_3)
         b_16 = findViewById(R.id.button_4_4)




            swipeViewHolder.setOnTouchListener(object : OnSwipeTouchListener(this@BeginActivity) {
            override fun onSwipeDown() {//下移
                super.onSwipeDown()

                for (col in 0..3){
                    var lastFoundFieldIndex = 4
                    var foundPair = false
                    for (row in 3 downTo 0) {
                        if(gameFieldArray[row][col] != 0) {
                            if (lastFoundFieldIndex == 4) {
                                lastFoundFieldIndex--
                                if (row != 3) {
                                    switchFieldsInColumn(col, row, lastFoundFieldIndex)
                                }
                            } else {
                                if (gameFieldArray[row][col] == gameFieldArray[lastFoundFieldIndex][col]) {
                                    if (!foundPair) {
                                        foundPair = true
                                        gameFieldArray[lastFoundFieldIndex][col] = gameFieldArray[lastFoundFieldIndex][col] * 2
                                        newField(gameFieldArray[lastFoundFieldIndex][col])
                                        gameFieldArray[row][col] = 0
                                    } else {
                                        foundPair = false
                                        lastFoundFieldIndex--
                                        switchFieldsInColumn(col, row, lastFoundFieldIndex)
                                    }
                                } else if(lastFoundFieldIndex - row > 1) {
                                    lastFoundFieldIndex--
                                    switchFieldsInColumn(col, row, lastFoundFieldIndex)
                                } else {
                                    lastFoundFieldIndex--
                                }
                            }
                        }
                    }
                }
                newFieldWithTwo()
                refreshField()


            }

            override fun onSwipeUp() {// 上移
                super.onSwipeUp()

                for (col in 0..3){
                    var lastFoundFieldIndex : Int = -1
                    var foundPair = false
                    for (row in 0..3) {
                        if(gameFieldArray[row][col] != 0) {
                            if (lastFoundFieldIndex == -1) {
                                lastFoundFieldIndex++
                                if (row != 0) {
                                    switchFieldsInColumn(col, row, lastFoundFieldIndex)
                                }
                            } else {
                                if (gameFieldArray[row][col] == gameFieldArray[lastFoundFieldIndex][col]) {
                                    if (!foundPair) {
                                        foundPair = true
                                        gameFieldArray[lastFoundFieldIndex][col] = gameFieldArray[lastFoundFieldIndex][col] * 2
                                        newField(gameFieldArray[lastFoundFieldIndex][col])
                                        gameFieldArray[row][col] = 0
                                    } else {
                                        foundPair = false
                                        lastFoundFieldIndex++
                                        switchFieldsInColumn(col, row, lastFoundFieldIndex)
                                    }
                                } else if(row - lastFoundFieldIndex > 1) {
                                    lastFoundFieldIndex++
                                    switchFieldsInColumn(col, row, lastFoundFieldIndex)
                                } else {
                                    lastFoundFieldIndex++
                                }
                            }
                        }
                    }
                }
                newFieldWithTwo()
                refreshField()
            }

            override fun onSwipeLeft() { //左移
                super.onSwipeLeft()

                for (row in gameFieldArray){
                    var lastFoundFieldIndex : Int = -1
                    var foundPair  = false
                    for (i in 0..3) {
                        if(row[i] != 0) {
                            if (lastFoundFieldIndex == -1) {
                                lastFoundFieldIndex++
                                if (i != 0) {
                                    switchFieldsInRow(gameFieldArray.indexOf(row), i, lastFoundFieldIndex)
                                }
                            } else {
                                if (row[i] == row[lastFoundFieldIndex]) {
                                    if (!foundPair) {
                                        foundPair = true
                                        row[lastFoundFieldIndex] = row[lastFoundFieldIndex] * 2
                                        newField(row[lastFoundFieldIndex])
                                        row[i] = 0
                                    } else {
                                        foundPair = false
                                        lastFoundFieldIndex++
                                        switchFieldsInRow(gameFieldArray.indexOf(row), i, lastFoundFieldIndex)
                                    }
                                } else if(i - lastFoundFieldIndex > 1) {
                                    lastFoundFieldIndex++
                                    switchFieldsInRow(gameFieldArray.indexOf(row), i, lastFoundFieldIndex)
                                } else {
                                    lastFoundFieldIndex++
                                }
                            }
                        }
                    }
                }
                newFieldWithTwo()
                refreshField()
            }

            override fun onSwipeRight() {//右移
                super.onSwipeRight()

                for (row in gameFieldArray){
                    var lastFoundFieldIndex  = 4
                    var foundPair  = false
                    for (i in 3 downTo 0) {
                        if(row[i] != 0) {
                            if (lastFoundFieldIndex == 4) {
                                lastFoundFieldIndex--
                                if (i != 3) {
                                    switchFieldsInRow(gameFieldArray.indexOf(row), i, lastFoundFieldIndex)
                                }
                            } else {
                                if (row[i] == row[lastFoundFieldIndex]) {
                                    if (!foundPair) {
                                        foundPair = true
                                        row[lastFoundFieldIndex] = row[lastFoundFieldIndex] * 2
                                        newField(row[lastFoundFieldIndex])
                                        row[i] = 0
                                    } else {
                                        foundPair = false
                                        lastFoundFieldIndex--
                                        switchFieldsInRow(gameFieldArray.indexOf(row), i, lastFoundFieldIndex)
                                    }
                                } else if(lastFoundFieldIndex - i > 1) {
                                    lastFoundFieldIndex--
                                    switchFieldsInRow(gameFieldArray.indexOf(row), i, lastFoundFieldIndex)
                                } else {
                                    lastFoundFieldIndex--
                                }
                            }
                        }
                    }
                }
                newFieldWithTwo()
                refreshField()
            }




            })

        goback_button.setOnClickListener {
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
            finish()
        }

        sound_button.setOnClickListener(){
            val intentMusic = Intent(this,MusicService::class.java)
            if(countMusic%2==0)
                stopService(intentMusic)
            else
                startService(intentMusic)
            countMusic++
        }

        startGameButton = findViewById(R.id.start_game_button)
        startGameButton.setOnClickListener { gameStart() }

        toolButton=findViewById(R.id.tool1)
        toolButton.setOnClickListener { note = 1
            Toast.makeText(this, "选择一个数字消除", Toast.LENGTH_SHORT).show();

        }
        b_1.setOnClickListener{
           if(note==1) {
               gameFieldArray[0][0] = 0
               refreshField()
               note = 0
           }
        }
        b_2.setOnClickListener{
            if(note==1) {
                gameFieldArray[0][1] = 0
                refreshField()
                note = 0
            }
        }
        b_3.setOnClickListener{
            if(note==1) {
                gameFieldArray[0][2] = 0
                refreshField()
                note = 0
            }
        }
        b_4.setOnClickListener{
            if(note==1) {
                gameFieldArray[0][3] = 0
                refreshField()
                note = 0
            }
        }
        b_5.setOnClickListener{
            if(note==1) {
                gameFieldArray[1][0] = 0
                refreshField()
                note = 0
            }
        }
        b_6.setOnClickListener{
            if(note==1) {
                gameFieldArray[1][1] = 0
                refreshField()
                note = 0
            }
        }
        b_7.setOnClickListener{
            if(note==1) {
                gameFieldArray[1][2] = 0
                refreshField()
                note = 0
            }
        }
        b_8.setOnClickListener{
            if(note==1) {
                gameFieldArray[1][3] = 0
                refreshField()
                note = 0
            }
        }
        b_9.setOnClickListener{
            if(note==1) {
                gameFieldArray[2][0] = 0
                refreshField()
                note = 0
            }
        }
        b_10.setOnClickListener{
            if(note==1) {
                gameFieldArray[2][1] = 0
                refreshField()
                note = 0
            }
        }
        b_11.setOnClickListener{
            if(note==1) {
                gameFieldArray[2][2] = 0
                refreshField()
                note = 0
            }
        }
        b_12.setOnClickListener{
            if(note==1) {
                gameFieldArray[2][3] = 0
                refreshField()
                note = 0
            }
        }
        b_13.setOnClickListener{
            if(note==1) {
                gameFieldArray[3][0] = 0
                refreshField()
                note = 0
            }
        }
        b_14.setOnClickListener{
            if(note==1) {
                gameFieldArray[3][1] = 0
                refreshField()
                note = 0
            }
        }
        b_15.setOnClickListener{
            if(note==1) {
                gameFieldArray[3][2] = 0
                refreshField()
                note = 0
            }
        }

        b_16.setOnClickListener{
            if(note==1) {
                gameFieldArray[3][3] = 0
                refreshField()
                note = 0
            }
        }




        setInitialScore()
    }

    override fun onStop() {//退出保存游戏

        super.onStop()
        if (!isGameOver) {
            val sharedPref = getSharedPreferences(getString(R.string.preference_file_key),Context.MODE_PRIVATE) ?: return
            val sb = StringBuilder()
            for (row in gameFieldArray) {
                for (col in row) {
                    sb.append(col).append(",")
                }
            }
            with(sharedPref.edit()) {
                putString(getString(R.string.saved_array_key), sb.toString())
                putInt(getString(R.string.saved_current_score_key), score)
                putInt(getString(R.string.saved_empty_fields_key), emptyFields)
                apply()
            }
        }

    }

    override fun onResume() {//恢复现场

        super.onResume()
        val sharedPref = getSharedPreferences(getString(R.string.preference_file_key),Context.MODE_PRIVATE) ?: return
        val savedArray = sharedPref.getString(getString(R.string.saved_array_key), "")
        if (savedArray != "") {
            gameFieldArray = arrayOf(intArrayOf(0, 0, 0, 0),
                intArrayOf(0, 0, 0, 0),
                intArrayOf(0, 0, 0, 0),
                intArrayOf(0, 0, 0, 0))

            val st = StringTokenizer(savedArray, ",")

            for (row in 0..3) {
                for (col in 0..3) {
                    gameFieldArray[row][col] = Integer.parseInt(st.nextToken())
                }
            }
            refreshField()
            swipeViewHolder.visibility = View.VISIBLE
          //  startGameButton.text = getString(R.string.reset_btn_text)
            emptyFields = sharedPref.getInt(getString(R.string.saved_empty_fields_key), 16)
            score = sharedPref.getInt(getString(R.string.saved_current_score_key), 0)
            gameScoreTextView.text = getString(R.string.game_score, score)
        }

    }



    fun switchFieldsInRow(row: Int, fromField: Int, toField: Int) {
        gameFieldArray[row][toField] = gameFieldArray[row][fromField]
        gameFieldArray[row][fromField] = 0
    }

    fun switchFieldsInColumn(col: Int, fromField: Int, toField: Int) {
        gameFieldArray[toField][col] = gameFieldArray[fromField][col]
        gameFieldArray[fromField][col] = 0
    }

    private fun randomIndex() : Int = Random.nextInt(0, 4)

    fun newFieldWithTwo() {//出现数字2
        if(emptyFields <= 0) {
            gameOver(false)
            return
        }
        val randomRow = randomIndex()
        val randomCol = randomIndex()
        val randomField = gameFieldArray[randomRow][randomCol]
        if(randomField != 0) {
            newFieldWithTwo()
        } else {
            gameFieldArray[randomRow][randomCol] = 2
            emptyFields--
            gameField[randomRow][randomCol].text = "2"
            gameField[randomRow][randomCol].setBackgroundResource(R.drawable.button_2)
        }
    }

    fun newField(newVal: Int)
    {
        emptyFields++
        score += newVal
        gameScoreTextView.text = getString(R.string.game_score, score)
        if (newVal == 2048) gameOver(true)
    }

    fun refreshField() {
        for (row in gameField) {
            for (col in row) when(val value = gameFieldArray[gameField.indexOf(row)][row.indexOf(col)]) {
                0 -> {
                    col.text = ""
                    col.setBackgroundResource(R.drawable.button_empty)
                }
                2 -> {
                    col.text = "2"
                    col.setTextColor(android.graphics.Color.BLACK)
                    col.setBackgroundResource(R.drawable.button_2)
                    col.animate().rotationYBy(360f)
                }
                4 -> {
                    col.text = "4"
                    col.setTextColor(android.graphics.Color.BLACK)
                    col.setBackgroundResource(R.drawable.button_4)
                    col.animate().rotationYBy(360f)
                }
                8 -> {
                    col.text = "8"
                    col.setTextColor(android.graphics.Color.BLACK)
                    col.setBackgroundResource(R.drawable.button_8)
                    col.animate().rotationYBy(360f)
                }
                16 -> {
                    col.text = "16"
                    col.setTextColor(android.graphics.Color.BLACK)
                    col.setBackgroundResource(R.drawable.button_16)
                    col.animate().rotationYBy(360f)
                }
                32 -> {
                    col.text = "32"
                    col.setTextColor(android.graphics.Color.BLACK)
                    col.setBackgroundResource(R.drawable.button_32)
                    col.animate().rotationYBy(360f)
                }
                64 -> {
                    col.text = "64"
                    col.setTextColor(android.graphics.Color.BLACK)
                    col.setBackgroundResource(R.drawable.button_64)
                    col.animate().rotationYBy(360f)
                }
                128 -> {
                    col.text = "128"
                    col.setTextColor(android.graphics.Color.BLACK)
                    col.setBackgroundResource(R.drawable.button_128)
                    col.animate().rotationYBy(360f)
                }
                256 -> {
                    col.text = "256"
                    col.setTextColor(android.graphics.Color.BLACK)
                    col.setBackgroundResource(R.drawable.button_256)
                    col.animate().rotationYBy(360f)
                }
                512 -> {
                    col.text = "512"
                    col.setTextColor(android.graphics.Color.BLACK)
                    col.setBackgroundResource(R.drawable.button_512)
                    col.animate().rotationYBy(360f)
                }
                1024 -> {
                    col.text = "1024"
                    col.setTextColor(android.graphics.Color.BLACK)
                    col.setBackgroundResource(R.drawable.button_1024)
                    col.animate().rotationYBy(360f)
                }
                2048 -> {
                    col.text = "2048"
                    col.setTextColor(android.graphics.Color.BLACK)
                    col.setBackgroundResource(R.drawable.button_2048)
                    col.animate().rotationYBy(360f)
                }
            }
        }
    }


    fun gameStart() {
        emptyFields = 16
        score = 0
        isGameOver = false

        gameFieldArray = arrayOf(intArrayOf(0, 0, 0, 0),
            intArrayOf(0, 0, 0, 0),
            intArrayOf(0, 0, 0, 0),
            intArrayOf(0, 0, 0, 0))

      //  startGameButton.text = getString(R.string.reset_btn_text)

        swipeViewHolder.visibility = View.VISIBLE
        resultTextView.visibility = View.INVISIBLE

        setInitialScore()

        newFieldWithTwo()
        newFieldWithTwo()

        refreshField()
    }





    private fun setInitialScore() {
        val initialScore = getString(R.string.game_score, score)
        gameScoreTextView.text = initialScore

        val sharedPref = getSharedPreferences(getString(R.string.preference_file_key),Context.MODE_PRIVATE) ?: return
        val highScore = sharedPref.getInt(getString(R.string.saved_high_score_key), 0)
        recordTextView.text = getString(R.string.record_score, highScore)
    }

    private fun gameOver(result: Boolean) {
        isGameOver = true
      //  startGameButton.text = getString(R.string.start_btn_text)

        if (result) {
            resultTextView.text = getString(R.string.game_win)
        } else {
            resultTextView.text = getString(R.string.game_lose)
        }

        val sharedPref = getSharedPreferences(getString(R.string.preference_file_key),Context.MODE_PRIVATE) ?: return
        val currentRecordScore = sharedPref.getInt(getString(R.string.saved_high_score_key), 0)

        with (sharedPref.edit()) {
            if (score > currentRecordScore) {
                putInt(getString(R.string.saved_high_score_key), score)
            }
            putString(getString(R.string.saved_array_key), "")
            putInt(getString(R.string.saved_current_score_key), 0)
            putInt(getString(R.string.saved_empty_fields_key), 0)
            commit()
        }
        if (score > currentRecordScore) {
            recordTextView.text = getString(R.string.record_score, score)
        }

        swipeViewHolder.visibility = View.INVISIBLE
        resultTextView.visibility = View.VISIBLE
    }
}

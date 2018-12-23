package dkfhui.catancounter

import android.app.Activity
import android.content.Intent
import android.icu.util.Calendar
import android.opengl.Visibility
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.FragmentTransaction
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.graph_layout.*
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class MainActivity : AppCompatActivity() {

    private val numberSet = hashMapOf<Int, Int>()
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
        numberSetText.text = numberSet.toString()

        rollPicker.setOnValueChangedListener { picker, oldVal, newVal -> run{
            removeButton.isEnabled = numberSet[newVal] != 0
        } }

        addButton.setOnClickListener{
//            updateRolls(rollInput.text.toString().toInt())
            updateRolls(rollPicker.value)
            removeButton.isEnabled = true
        }

        removeButton.setOnClickListener{
//            val roll = rollInput.text.toString().toInt()
            val roll = rollPicker.value
            updateRolls(roll, -1)
            if(numberSet[roll] == 0)
                removeButton.isEnabled = false
        }

        graphButton.setOnClickListener {
            val graphIntent = Intent(this, GraphActivity::class.java)
            graphIntent.putExtra("numberSet", numberSet)
            startActivityForResult(graphIntent, 1)
        }

        endGameButton.setOnClickListener { uploadGameData() }
    }

    private fun init(){
        for(i in 2..12) {
            numberSet[i] = 0
        }
        rollPicker.minValue = 2
        rollPicker.maxValue = 12
        removeButton.isEnabled = false
    }

    private fun validNumberToast() {Toast.makeText(this, "Please input a valid number from 2 to 12", Toast.LENGTH_LONG).show()}
    private fun disableBothButtons() {
        removeButton.isEnabled = false
        addButton.isEnabled = false
    }

    private fun updateRolls(roll: Int, change: Int = 1) {
        when(roll) {
            in 2..12 -> numberSet[roll] = numberSet[roll]!!+change
            else -> println("Wrong input in updateRolls()")
        }
        numberSetText.text = numberSet.toString()
    }

    fun uploadGameData() {
        val current = Timestamp.now()
        val date = current.toDate()
        db.collection("Games")
    }

//    fun getDateFromTimestamp(stamp:Timestamp): Date {
//        val formatter = DateTimeFormatter.ofPattern("yyyy/mm/dd hh:mm")
//        return formatter.
//    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode) {
            1 -> {
                if(resultCode == Activity.RESULT_OK)
                    println("It's all good")
                else
                    println("" + resultCode + " " + Activity.RESULT_CANCELED)
            }
        }
    }
}

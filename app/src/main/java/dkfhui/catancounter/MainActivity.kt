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
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.graph_layout.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.HashMap

class MainActivity : AppCompatActivity() {

    private val numberSet = hashMapOf<String, Int>()
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
        numberSetText.text = numberSet.toString()

        rollPicker.setOnValueChangedListener { picker, oldVal, newVal -> run{
            removeButton.isEnabled = numberSet[newVal.toString()] != 0
        } }

        addButton.setOnClickListener{
//            updateRolls(rollInput.text.toString().toInt())
            updateRolls(rollPicker.value.toString())
            removeButton.isEnabled = true
        }

        removeButton.setOnClickListener{
//            val roll = rollInput.text.toString().toInt()
            val roll = rollPicker.value.toString()
            updateRolls(roll, -1)
            if(numberSet[roll] == 0)
                removeButton.isEnabled = false
        }

        graphButton.setOnClickListener {
            val graphIntent = Intent(this, GraphActivity::class.java)
            graphIntent.putExtra("numberSet", numberSet)
            startActivityForResult(graphIntent, 1)
        }

        endGameButton.setOnClickListener {
            val gameInfoIntent = Intent(this, GameInfoActivity::class.java)
            startActivityForResult(gameInfoIntent, 2)
        }
    }

    private fun init(){
        for(i in 2..12) {
            numberSet[i.toString()] = 0
        }

        rollPicker.minValue = 2
        rollPicker.maxValue = 12
        removeButton.isEnabled = false

        val settings = FirebaseFirestoreSettings.Builder()
            .setTimestampsInSnapshotsEnabled(true)
            .build()
        db.firestoreSettings = settings
    }

    private fun validNumberToast() {Toast.makeText(this, "Please input a valid number from 2 to 12", Toast.LENGTH_LONG).show()}
    private fun disableBothButtons() {
        removeButton.isEnabled = false
        addButton.isEnabled = false
    }

    private fun updateRolls(roll: String, change: Int = 1) {
        when(roll.toInt()) {
            in 2..12 -> numberSet[roll] = numberSet[roll]!!+change
            else -> println("Wrong input in updateRolls()")
        }
        numberSetText.text = numberSet.toString()
    }

    private fun uploadGameData(data: Intent) {
        val format = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.FULL)
        val current = Timestamp.now()
        val date = format.format(current.toDate())
        println(date.toString())

        val keylist = ArrayList(numberSet.keys)
        val valuelist = ArrayList(numberSet.values)

        val doc = HashMap<String, Any>()
        doc["NumberSet"] = numberSet
        doc["Winner"] = data.getStringExtra("Winner")
        doc["WinnerVP"] = data.getStringExtra("WinnerVP")
        doc["Player2"] = data.getStringExtra("Player2")
        doc["Player2VP"] = data.getStringExtra("Player2VP")
        doc["Player3"] = data.getStringExtra("Player3")
        doc["Player3VP"] = data.getStringExtra("Player3VP")
        doc["Player4"] = data.getStringExtra("Player4")
        doc["Player4VP"] = data.getStringExtra("Player4VP")
        doc["Comments"] = data.getStringExtra("Comments")

        db.collection("Games")
            .document(date.toString())
            .set(doc)
            .addOnSuccessListener { Log.d("Firestore success", "Firestore upload success") }
            .addOnFailureListener { e -> Log.w("Firestore failure", e) }

        resetGame()
    }

    private fun resetGame() {
        for(i in 2..12) {
            numberSet[i.toString()] = 0
        }
        numberSetText.text = numberSet.toString()
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
            2 -> {
                if(resultCode == Activity.RESULT_OK)
                    uploadGameData(data!!)
            }
        }
    }
}

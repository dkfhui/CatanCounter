package dkfhui.catancounter

import android.content.Intent
import android.opengl.Visibility
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.FragmentTransaction
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.graph_layout.*

class MainActivity : AppCompatActivity() {

    val numberSet = hashMapOf<Int, Int>()

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
            startActivity(graphIntent)
        }
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
}

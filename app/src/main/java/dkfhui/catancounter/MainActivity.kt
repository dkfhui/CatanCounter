package dkfhui.catancounter

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val numberSet = hashMapOf<Int, Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
        numberSetText.text = numberSet.toString()

        rollInput.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val roll:Int? = rollInput.text.toString().toIntOrNull()
                if(roll == null){
                   disableBothButtons()
                }
                else
                    when(roll) {
                        in 2..12 -> {
                            removeButton.isEnabled = numberSet[roll] != 0
                            addButton.isEnabled = true
                        }
                        else -> {
                            disableBothButtons()
                            validNumberToast()
                        }
                    }
            }
        })

        addButton.setOnClickListener{
            updateRolls(rollInput.text.toString().toInt())
            removeButton.isEnabled = true;
        }

        removeButton.setOnClickListener{
            val roll = rollInput.text.toString().toInt()
            updateRolls(roll, -1)
            if(numberSet[roll] == 0)
                removeButton.isEnabled = false
        }
    }

    fun init(){
        for(i in 2..12) {
            numberSet[i] = 0
        }
    }

    fun validNumberToast() {Toast.makeText(this, "Please input a valid number from 2 to 12", Toast.LENGTH_LONG).show()}
    fun disableBothButtons() {
        removeButton.isEnabled = false
        addButton.isEnabled = false
    }

    fun updateRolls(roll: Int, change: Int = 1) {
        when(roll) {
            in 2..12 -> numberSet[roll] = numberSet[roll]!!+change
            else -> println("Wrong input in updateRolls()")
        }
        numberSetText.text = numberSet.toString()
    }
}

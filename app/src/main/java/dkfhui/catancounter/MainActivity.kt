package dkfhui.catancounter

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val numberSet = hashMapOf<Int, Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
        numberSetText.text = numberSet.toString()

        inputButton.setOnClickListener{
            addData(rollInput.text.toString().toInt())
        }
    }

    fun init(){
        for(i in 2..12) {
            numberSet[i] = 0
        }
    }

    fun addData(x: Int) {
        when(x) {
            in 2..12 -> numberSet[x] = numberSet[x]!!+1
            else -> Toast.makeText(this, "Please input a valid number from 2 to 12", Toast.LENGTH_LONG)
        }
        numberSetText.text = numberSet.toString()
    }
}

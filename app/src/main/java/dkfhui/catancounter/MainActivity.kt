package dkfhui.catancounter

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var input = 0;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        inputButton.setOnClickListener{
            input = rollInput.text.toString().toInt()
            Toast.makeText(this, input.toString(), Toast.LENGTH_LONG).show()
        }
    }
}

package dkfhui.catancounter

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.graph_layout.*

class GraphActivity : AppCompatActivity(), GraphFragment.OnGraphTouchedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_graph)

        val graphFragment = GraphFragment.newInstance()
        supportFragmentManager.beginTransaction()
            .add(R.id.graphFragment, graphFragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onGraphTouched() {
        println("onGraphTouched ")
        finish()
    }
}

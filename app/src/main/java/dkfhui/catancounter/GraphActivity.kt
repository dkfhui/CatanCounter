package dkfhui.catancounter

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.jjoe64.graphview.series.BarGraphSeries
import com.jjoe64.graphview.series.DataPoint
import kotlinx.android.synthetic.main.graph_layout.*

class GraphActivity : AppCompatActivity(), GraphFragment.OnGraphTouchedListener {
    private val numberSet = hashMapOf<Int, Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_graph)

        createGraph(intent.getSerializableExtra("numberSet") as HashMap<Int, Int>)

//        val graphFragment = GraphFragment.newInstance(intent.getSerializableExtra("numberSet") as HashMap<Int, Int>)
//        supportFragmentManager.beginTransaction()
//            .add(R.id.graphFragment, graphFragment)
//            .addToBackStack(null)
//            .commit()
    }

    override fun onGraphTouched() {
        println("onGraphTouched ")
        finish()
    }

    fun createGraph(numberSet:HashMap<Int, Int>) {
        val series = BarGraphSeries<DataPoint>(
            arrayOf<DataPoint>(
                DataPoint(2.0, numberSet[2]!!.toDouble()),
                DataPoint(3.0, numberSet[3]!!.toDouble()),
                DataPoint(4.0, numberSet[4]!!.toDouble()),
                DataPoint(5.0, numberSet[5]!!.toDouble()),
                DataPoint(6.0, numberSet[6]!!.toDouble()),
                DataPoint(7.0, numberSet[7]!!.toDouble()),
                DataPoint(8.0, numberSet[8]!!.toDouble()),
                DataPoint(9.0, numberSet[9]!!.toDouble()),
                DataPoint(10.0, numberSet[10]!!.toDouble()),
                DataPoint(11.0, numberSet[11]!!.toDouble()),
                DataPoint(12.0, numberSet[12]!!.toDouble())
            )
        )
        graph.addSeries(series)

        graph.viewport.isXAxisBoundsManual = true
        graph.viewport.isYAxisBoundsManual = true
        graph.viewport.maxXAxisSize = 13.25
        graph.viewport.setMinX(1.75)
        graph.viewport.setMaxY(series.highestValueY+1)
        graph.viewport.setMinY(0.0)
// styling
        series.setValueDependentColor { data ->
            Color.rgb(
                data.x.toInt() * 255 / 4,
                Math.abs(data.y * 255 / 6).toInt(),
                100
            )
        }

        series.spacing = 0
        series.dataWidth = 0.5
//        graph.gridLabelRenderer.padding = 32

// draw values on top
        series.isDrawValuesOnTop = true
        series.valuesOnTopColor = Color.RED
//series.setValuesOnTopSize(50);
    }
}

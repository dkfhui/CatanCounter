package dkfhui.catancounter

import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jjoe64.graphview.ValueDependentColor
import com.jjoe64.graphview.series.BarGraphSeries
import com.jjoe64.graphview.GraphView
import com.jjoe64.graphview.series.DataPoint
import kotlinx.android.synthetic.main.graph_layout.*



class GraphFragment : Fragment {

    constructor()

    companion object {
        internal fun newInstance() : GraphFragment{
            val graphFragment = GraphFragment()
            val b = Bundle()
            graphFragment.arguments = b
            return graphFragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
        ) : View {
        return inflater.inflate(R.layout.graph_layout, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val series = BarGraphSeries<DataPoint>(
            arrayOf<DataPoint>(
                DataPoint(0.0, 1.0),
                DataPoint(1.0, 5.0),
                DataPoint(2.0, 3.0),
                DataPoint(3.0, 2.0),
                DataPoint(4.0, 6.0)
            )
        )
        graph.addSeries(series)

// styling
        series.setValueDependentColor { data ->
            Color.rgb(
                data.x.toInt() * 255 / 4,
                Math.abs(data.y * 255 / 6).toInt(),
                100
            )
        }

        series.spacing = 50

// draw values on top
        series.isDrawValuesOnTop = true
        series.valuesOnTopColor = Color.RED
//series.setValuesOnTopSize(50);
    }

}
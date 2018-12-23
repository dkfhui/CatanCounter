package dkfhui.catancounter

import android.app.Activity
import android.content.Context
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
    private lateinit var onGraphTouchedListener: OnGraphTouchedListener

    interface OnGraphTouchedListener {
        fun onGraphTouched()
    }

    constructor()

    companion object {
        internal fun newInstance(numberSet:HashMap<Int, Int>) : GraphFragment{
            val graphFragment = GraphFragment()
            val b = Bundle()
            b.putSerializable("numberSet", numberSet)
            graphFragment.arguments = b
            return graphFragment
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if(context is OnGraphTouchedListener)
            onGraphTouchedListener = context
        else
            throw RuntimeException(context!!.toString() + " must implement OnGraphTouchedListener")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
        ) : View {
        val view = inflater.inflate(R.layout.graph_layout, container, false)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if(arguments != null)
            createGraph(arguments!!.getSerializable("numberSet") as HashMap<Int, Int>)
    }

    fun createGraph(numberSet:HashMap<Int, Int>) {
        val series = BarGraphSeries<DataPoint>(
            arrayOf<DataPoint>(
                DataPoint(0.2, numberSet[2]!!.toDouble()),
                DataPoint(1.0, numberSet[3]!!.toDouble()),
                DataPoint(1.8, numberSet[4]!!.toDouble()),
                DataPoint(2.6, numberSet[5]!!.toDouble()),
                DataPoint(3.4, numberSet[7]!!.toDouble()),
                DataPoint(4.2, numberSet[8]!!.toDouble()),
                DataPoint(5.0, numberSet[9]!!.toDouble()),
                DataPoint(5.8, numberSet[10]!!.toDouble()),
                DataPoint(6.6, numberSet[11]!!.toDouble()),
                DataPoint(7.4, numberSet[12]!!.toDouble())
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

        series.spacing = 15
        series.dataWidth = 0.5
        graph.gridLabelRenderer.padding = 32

// draw values on top
        series.isDrawValuesOnTop = true
        series.valuesOnTopColor = Color.RED
        series.setOnDataPointTapListener { series, dataPoint -> onGraphTouchedListener.onGraphTouched() }
//series.setValuesOnTopSize(50);
    }
}
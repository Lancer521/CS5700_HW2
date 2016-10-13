package Display;

import Data.Stock;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataItem;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by Ty on 10/9/2016 at 8:33 PM.
 * *
 */
public class GraphAttempt extends Display implements Observer{

    private XYDataset dataset;
    private XYSeries series;
    private JFreeChart chart;
    private ChartFrame frame;
    private int horizontalIndex = 0;

    public GraphAttempt(Stock stock){
        dataset = createDataSet();
        addStockToDisplay(stock);
        chart = ChartFactory.createXYLineChart("Graph", "Values", "Thingy", dataset, PlotOrientation.HORIZONTAL, false, false, false);
        frame = new ChartFrame("Title", chart);
    }

    @Override
    public void addStockToDisplay(Stock stock) {
        stock.addObserver(this);
    }

    @Override
    public void removeStockFromDisplay(Stock stock) {

    }

    @Override
    public void display() {
        frame.setVisible(true);
        frame.setSize(450, 500);
    }

    @Override
    public void update(Observable o, Object arg) {
        Stock stock = (Stock) o;
        if(series.getItemCount() < 60){
            series.add(stock.currentPrice, horizontalIndex++);
        } else {
            updateFullGraph(stock);
        }
    }

    private void updateFullGraph(Stock stock) {
        series.remove(0);
        List<XYDataItem> list = (List<XYDataItem>) series.getItems();
        for(XYDataItem item : list){
            item.setY(item.getYValue()-1);
        }
        series.add(stock.currentPrice, 60);
    }

    public XYDataset createDataSet(){
        series = new XYSeries("Test");
        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series);
        return dataset;
    }
}

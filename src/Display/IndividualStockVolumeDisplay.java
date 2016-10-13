package Display;

import Data.Stock;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
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
public class IndividualStockVolumeDisplay extends Display implements Observer {

    private XYDataset dataset;
    private XYSeries series;
    private JFreeChart chart;
    private ChartFrame frame;
    private XYPlot plot;
    private int minValue;
    private int maxValue;
    private int counter = 120;

    public IndividualStockVolumeDisplay(Stock stock) {
        dataset = createDataSet();
        addStockToDisplay(stock);
        chart = ChartFactory.createXYLineChart(stock.symbol + " - Volume", "", "", dataset, PlotOrientation.VERTICAL, false, false, false);
        frame = new ChartFrame("Title", chart);
        plot = chart.getXYPlot();
        plot.getRangeAxis().setVisible(false);
        plot.getDomainAxis().setVisible(false);
        minValue = stock.volumeSoldToday - 5;
        maxValue = stock.volumeSoldToday + 5;
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
        if (counter >= 120) {
            counter = 0;
            Stock stock = (Stock) o;
            if (series.getItemCount() < 60) {
                series.add(series.getItemCount(), stock.volumeSoldToday);
            } else {
                updateFullGraph(stock);
            }
            if (stock.volumeSoldToday < minValue) {
                minValue = stock.volumeSoldToday;
            } else if (stock.volumeSoldToday > maxValue) {
                maxValue = stock.volumeSoldToday;
            }
            plot.getRangeAxis().setRange(minValue, maxValue);
        }
        counter++;
    }

    private void updateFullGraph(Stock stock) {
        series.remove(0);
        List<XYDataItem> list = (List<XYDataItem>) series.getItems();
        for (XYDataItem item : list) {
            series.clear();
            series.add(item.getXValue() - 1, item.getYValue());
        }
        series.add(59, stock.volumeSoldToday);
    }

    public XYDataset createDataSet() {
        series = new XYSeries("Test");
        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series);
        return dataset;
    }
}

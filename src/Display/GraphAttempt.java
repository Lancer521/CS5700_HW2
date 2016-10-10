package Display;

import Data.Stock;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 * Created by Ty on 10/9/2016 at 8:33 PM.
 */
public class GraphAttempt extends Display{

    DefaultCategoryDataset dataset;
    JFreeChart chart;
    ChartFrame frame;

    public GraphAttempt(){
        dataset = new DefaultCategoryDataset();
        chart = ChartFactory.createLineChart("Graph", "Values", "Thingy", dataset, PlotOrientation.HORIZONTAL, true, true, true);
        frame = new ChartFrame("Title", chart);
    }

    public static void main(String[] args){
        GraphAttempt ga = new GraphAttempt();
        Stock s = new Stock();
        s.openingPrice = 25;
        s.closingPrice = 50;
        ga.addStockToDisplay(s);
        ga.display();
    }

    @Override
    public void addStockToDisplay(Stock stock) {
        dataset.setValue(stock.openingPrice, "Values", "Thingy");
        dataset.addValue(stock.closingPrice, "Values", "Thingy");
    }

    @Override
    public void removeStockFromDisplay(Stock stock) {

    }

    @Override
    public void display() {
        frame.setVisible(true);
        frame.setSize(450, 500);
    }
}

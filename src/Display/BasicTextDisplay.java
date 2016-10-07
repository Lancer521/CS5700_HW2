package Display;

import Data.Portfolio;
import Data.SortedListModel;
import Data.Stock;

import javax.swing.*;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by Ty on 10/6/2016 at 12:59 PM at 4:53 PM.
 */
public class BasicTextDisplay extends Display implements Observer {
    private JPanel panelMain;
    private JList symbolList;
    private JList closingPriceList;
    private JList openingPriceList;
    private JList currentPriceList;
    private JList bidPriceList;
    private JList askPriceList;
    private JList volumeTodayList;
    private JList averageVolumeList;

    private SortedListModel symbolListModel;
    private SortedListModel closingPriceListModel;
    private SortedListModel openingPriceListModel;
    private SortedListModel currentPriceListModel;
    private SortedListModel bidPriceListModel;
    private SortedListModel askPriceListModel;
    private SortedListModel volumeTodayListModel;
    private SortedListModel averageVolumeListModel;

    public BasicTextDisplay() {
        symbolListModel = new SortedListModel();
    }

    public void addStockToDisplay(Stock stock) {
        stock.addObserver(this);

        symbolListModel.add(stock.symbol);
        symbolList.setModel(symbolListModel);
    }

    public void removeStockFromDisplay(Observable observable) {
        observable.deleteObserver(this);
    }

    @Override
    public void update(Observable o, Object arg) {
        try {
            if (!(o instanceof Stock)) {
                throw new TypeNotPresentException("OBSERVABLE NOT A STOCK", new Throwable());
            }
            Stock stock = (Stock) o;
            // TODO: do stuff;
        } catch (TypeNotPresentException | ClassCastException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void display() {
        initializeWindow();
    }

    private void initializeWindow() {
        JFrame frame = new JFrame("Stock Monitoring System 5000");
        frame.setContentPane(panelMain);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setFocusable(true);
        frame.setVisible(true);
    }
}

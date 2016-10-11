package Display;

import Data.SortedListModel;
import Data.Stock;

import javax.swing.*;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by Ty on 10/8/2016 at 12:17 PM.
 */
public class PriceRangeDisplay extends Display implements Observer {

    private JPanel panelMain;
    private JRadioButton askRadioButton;
    private JRadioButton bidRadioButton;
    private JTextField textField1;
    private JTextField textField2;
    private JList symbolList;
    private JList priceList;

    private DefaultListModel<String> symbolListModel;
    private SortedListModel priceListModel;

    @Override
    public void display() {
        display(panelMain, "Price Range Display");
        askRadioButton.setSelected(true);
    }

    @Override
    public void addStockToDisplay(Stock stock) {

    }

    @Override
    public void removeStockFromDisplay(Stock stock) {

    }

    @Override
    public void update(Observable o, Object arg) {

    }
}

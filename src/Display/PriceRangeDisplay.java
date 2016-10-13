package Display;

import Data.Portfolio;
import Data.Stock;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by Ty on 10/8/2016 at 12:17 PM.
 * *
 */
public class PriceRangeDisplay extends Display implements Observer, ActionListener {

    private JPanel panelMain;
    private JRadioButton askRadioButton;
    private JRadioButton bidRadioButton;
    private JTextField minTextField;
    private JTextField maxTextField;
    private JList symbolList;
    private JList priceList;
    private JButton goButton;

    private DefaultListModel<String> symbolListModel;
    private DefaultListModel<Integer> priceListModel;

    private int minValue, maxValue;

    public PriceRangeDisplay(Portfolio portfolio) {

        for (Stock stock : portfolio.values()) {
            stock.addObserver(this);
        }

        symbolListModel = new DefaultListModel<>();
        priceListModel = new DefaultListModel<>();
        symbolList.setModel(symbolListModel);
        priceList.setModel(priceListModel);

        goButton.addActionListener(this);
        askRadioButton.addActionListener(this);
        bidRadioButton.addActionListener(this);
    }

    @Override
    public void display() {
        display(panelMain, "Price Range Display");
        askRadioButton.setSelected(true);
    }

    @Override
    public void addStockToDisplay(Stock stock) {
        //No-op
    }

    @Override
    public void removeStockFromDisplay(Stock stock) {
        //No-op
    }

    @Override
    public void update(Observable o, Object arg) {
        if (askRadioButton.isSelected()) {
            updateStockByRange(((Stock) o).askPrice, ((Stock) o).symbol);
        } else {
            updateStockByRange(((Stock) o).bidPrice, ((Stock) o).symbol);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == goButton) {
            goButtonPressed();
        } else if (e.getSource() == askRadioButton || e.getSource() == bidRadioButton) {
            symbolListModel.clear();
            priceListModel.clear();
        }
    }

    private void updateStockByRange(int value, String symbol) {
        if (value < minValue || value > maxValue) {
            if (symbolListModel.contains(symbol)) {
                int index = symbolListModel.indexOf(symbol);
                symbolListModel.removeElementAt(index);
                priceListModel.removeElementAt(index);
            }
        } else {
            if (symbolListModel.contains(symbol)) {
                int index = symbolListModel.indexOf(symbol);
                symbolListModel.set(index, symbol);
                priceListModel.set(index, value);
            } else {
                symbolListModel.addElement(symbol);
                priceListModel.addElement(value);
            }
        }
    }

    private void goButtonPressed() {
        String min = minTextField.getText();
        String max = maxTextField.getText();
        if (min != null && !min.isEmpty()) {
            minValue = Integer.parseInt(min);
        }
        if (max != null && !max.isEmpty()) {
            maxValue = Integer.parseInt(max);
        }
    }
}

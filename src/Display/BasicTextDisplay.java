package Display;

import Data.Stock;
import Utils.HelperUtils;

import javax.swing.*;
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
    private JLabel symbolLabel;
    private JLabel closingPriceLabel;
    private JLabel openingPriceLabel;
    private JLabel currentPriceLabel;
    private JLabel bidPriceLabel;
    private JLabel askPriceLabel;
    private JLabel volumeTodayLabel;
    private JLabel AverageVolumeLabel;

    private DefaultListModel<Object> symbolListModel;
    private DefaultListModel<Object> closingPriceListModel;
    private DefaultListModel<Object> openingPriceListModel;
    private DefaultListModel<Object> currentPriceListModel;
    private DefaultListModel<Object> bidPriceListModel;
    private DefaultListModel<Object> askPriceListModel;
    private DefaultListModel<Object> volumeTodayListModel;
    private DefaultListModel<Object> averageVolumeListModel;

    public BasicTextDisplay() {
        symbolListModel = new DefaultListModel<>();
        closingPriceListModel = new DefaultListModel<>();
        openingPriceListModel = new DefaultListModel<>();
        currentPriceListModel = new DefaultListModel<>();
        bidPriceListModel = new DefaultListModel<>();
        askPriceListModel = new DefaultListModel<>();
        volumeTodayListModel = new DefaultListModel<>();
        averageVolumeListModel = new DefaultListModel<>();

        symbolList.setModel(symbolListModel);
        closingPriceList.setModel(closingPriceListModel);
        openingPriceList.setModel(openingPriceListModel);
        currentPriceList.setModel(currentPriceListModel);
        bidPriceList.setModel(bidPriceListModel);
        askPriceList.setModel(askPriceListModel);
        volumeTodayList.setModel(volumeTodayListModel);
        averageVolumeList.setModel(averageVolumeListModel);
    }

    public void addStockToDisplay(Stock stock) {
        stock.addObserver(this);

        updateSymbolList(symbolListModel.getSize(), stock.symbol);
    }

    public void removeStockFromDisplay(Stock stock) {
        removeFromAllList(symbolListModel.indexOf(stock.symbol));
        stock.deleteObserver(this);
    }

    @Override
    public void update(Observable o, Object arg) {
        try {
            if (!(o instanceof Stock)) {
                throw new TypeNotPresentException("OBSERVABLE NOT A STOCK", new Throwable());
            }
            int index = symbolListModel.indexOf(((Stock) o).symbol);
            if(index >= 0){
                updateAllLists(index, (Stock) o);
            }
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

    private void updateAllLists(int index, Stock stock){
        updateOpeningPriceList(index, stock.openingPrice);
        updateClosingPriceList(index, stock.closingPrice);
        updateCurrentPriceList(index, stock.currentPrice);
        updateBidPriceList(index, stock.bidPrice);
        updateAskPriceList(index, stock.askPrice);
        updateVolumeTodayList(index, stock.volumeSoldToday);
        updateAverageVolumeList(index, stock.tenDayAverageVolume);
    }

    private void updateSymbolList(int index, String symbol) {
        if(symbol != null && !symbol.isEmpty()){
            updateList(symbolListModel, index, symbol);
        }
    }

    private void updateOpeningPriceList(int index, int openingPrice) {
        if(openingPrice != HelperUtils.NOT_INITIALIZED){
            updateList(openingPriceListModel, index, openingPrice);
        }
    }

    private void updateClosingPriceList(int index, int closingPrice) {
        if(closingPrice != HelperUtils.NOT_INITIALIZED){
            updateList(closingPriceListModel, index, closingPrice);
        }
    }

    private void updateCurrentPriceList(int index, int currentPrice) {
        if(currentPrice != HelperUtils.NOT_INITIALIZED){
            updateList(currentPriceListModel, index, currentPrice);
        }
    }

    private void updateBidPriceList(int index, int bidPrice) {
        if(bidPrice != HelperUtils.NOT_INITIALIZED){
            updateList(bidPriceListModel, index, bidPrice);
        }
    }

    private void updateAskPriceList(int index, int askPrice) {
        if(askPrice != HelperUtils.NOT_INITIALIZED){
            updateList(askPriceListModel, index, askPrice);
        }
    }

    private void updateVolumeTodayList(int index, int volumeToday) {
        if(volumeToday != HelperUtils.NOT_INITIALIZED){
            updateList(volumeTodayListModel, index, volumeToday);
        }
    }

    private void updateAverageVolumeList(int index, int averageVolume) {
        if(averageVolume != HelperUtils.NOT_INITIALIZED){
            updateList(averageVolumeListModel, index, averageVolume);
        }
    }

    private void updateList(DefaultListModel<Object> listModel, int index, Object object) {
        if(listModel.size() > index){
            listModel.set(index, object);
        } else {
            listModel.addElement(object);
        }
    }

    private void removeFromAllList(int index){
        symbolListModel.removeElementAt(index);
        openingPriceListModel.removeElementAt(index);
        closingPriceListModel.removeElementAt(index);
        currentPriceListModel.removeElementAt(index);
        bidPriceListModel.removeElementAt(index);
        askPriceListModel.removeElementAt(index);
        volumeTodayListModel.removeElementAt(index);
        averageVolumeListModel.removeElementAt(index);
    }
}

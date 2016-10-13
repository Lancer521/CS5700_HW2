package Display;

import Data.Stock;
import Utils.HelperUtils;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by Ty on 10/6/2016 at 12:59 PM at 4:53 PM.
 *
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
    private JList directionList;

    private DefaultListModel<Object> symbolListModel;
    private DefaultListModel<Object> closingPriceListModel;
    private DefaultListModel<Object> openingPriceListModel;
    private DefaultListModel<Object> currentPriceListModel;
    private DefaultListModel<Object> directionListModel;
    private DefaultListModel<Object> bidPriceListModel;
    private DefaultListModel<Object> askPriceListModel;
    private DefaultListModel<Object> volumeTodayListModel;
    private DefaultListModel<Object> averageVolumeListModel;

    boolean openingRB;
    boolean isDirectionUp;

    @SuppressWarnings("all")
    public BasicTextDisplay(boolean openingRB) {
        this.openingRB = openingRB;
        if(openingRB){
            closingPriceList.setVisible(false);
            closingPriceLabel.setVisible(false);
            openingPriceListModel = new DefaultListModel<>();
            openingPriceList.setModel(openingPriceListModel);
        } else {
            openingPriceList.setVisible(false);
            openingPriceLabel.setVisible(false);
            closingPriceListModel = new DefaultListModel<>();
            closingPriceList.setModel(closingPriceListModel);
        }

        symbolListModel = new DefaultListModel<>();
        currentPriceListModel = new DefaultListModel<>();
        directionListModel = new DefaultListModel<>();
        bidPriceListModel = new DefaultListModel<>();
        askPriceListModel = new DefaultListModel<>();
        volumeTodayListModel = new DefaultListModel<>();
        averageVolumeListModel = new DefaultListModel<>();

        symbolList.setModel(symbolListModel);
        currentPriceList.setModel(currentPriceListModel);
        directionList.setModel(directionListModel);
        bidPriceList.setModel(bidPriceListModel);
        askPriceList.setModel(askPriceListModel);
        volumeTodayList.setModel(volumeTodayListModel);
        averageVolumeList.setModel(averageVolumeListModel);

//        directionList.setCellRenderer();
    }

    @Override
    public void display() {
        display(panelMain, "Basic Text Display");
    }

    @Override
    public void addStockToDisplay(Stock stock) {
        stock.addObserver(this);

        updateSymbolList(symbolListModel.getSize(), stock.symbol);
    }

    @Override
    public void removeStockFromDisplay(Stock stock) {
        removeFromAllLists(symbolListModel.indexOf(stock.symbol));
        stock.deleteObserver(this);
    }

    @Override
    public void update(Observable o, Object arg) {
        try {
            if (!(o instanceof Stock)) {
                throw new TypeNotPresentException("OBSERVABLE NOT A STOCK", new Throwable());
            }
            int index = symbolListModel.indexOf(((Stock) o).symbol);
            isDirectionUp = getDirection((Stock) o);
            if(index >= 0){
                updateAllLists(index, (Stock) o);
            }
        } catch (TypeNotPresentException | ClassCastException e) {
            e.printStackTrace();
        }

    }

    private boolean getDirection(Stock stock){
        if(openingRB){
            return stock.currentPrice > stock.openingPrice;
        }
        return stock.currentPrice > stock.closingPrice;
    }

    private void updateAllLists(int index, Stock stock){
        if(openingRB){
            updateOpeningPriceList(index, stock.openingPrice);
        } else {
            updateClosingPriceList(index, stock.closingPrice);
        }
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

    private void removeFromAllLists(int index){
        symbolListModel.removeElementAt(index);
        if(openingRB){
            openingPriceListModel.removeElementAt(index);
        } else {
            closingPriceListModel.removeElementAt(index);
        }
        currentPriceListModel.removeElementAt(index);
        bidPriceListModel.removeElementAt(index);
        askPriceListModel.removeElementAt(index);
        volumeTodayListModel.removeElementAt(index);
        averageVolumeListModel.removeElementAt(index);
    }

    private class ImageCellRenderer extends DefaultListCellRenderer{
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            Icon icon = getIcon();
            label.setIcon(icon);
            return label;
        }

        @Override
        public Icon getIcon() {
            if(isDirectionUp){
                //return green arrow
            }
            //return red arrow
            return super.getIcon();
        }
    }
}

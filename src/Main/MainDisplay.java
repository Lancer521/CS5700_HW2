package Main;

import Display.Display;
import Display.PortfolioStockDisplay;
import Display.SortedListModel;
import Display.DisplayFactory;
import Utils.CompanyListUtil;
import Data.Portfolio;
import Data.Stock;
import Messages.Communicator;
import Utils.FileManager;
import Utils.JsonFileManager;
import org.jfree.ui.RefineryUtilities;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Ty on 10/3/2016 at 9:20 PM.
 * *
 */
public class MainDisplay implements ActionListener {

    private JList portfolioList;
    private JList companyList;
    private JButton addButton;
    private JButton removeButton;
    private JPanel panelMain;
    private JButton loadPortfolioButton;
    private JButton savePortfolioButton;
    private JPanel portfolioButtonsPanel;
    private JPanel displayButtonsPanel;
    private JPanel buttonsPanel;
    private JPanel portfolioPanel;
    private JPanel displayPanel;
    private JButton displayButton;
    private JCheckBox basicTextDisplayCheckBox;
    private JRadioButton basicTextClosingRB;
    private JRadioButton basicTextOpeningRB;
    private JCheckBox priceRangeDisplayCheckBox;
    private JComboBox stockPriceComboBox;
    private JCheckBox stockVolumeCheckBox;
    private JCheckBox stockPriceCheckBox;
    private JComboBox stockVolumeComboBox;

    private Portfolio portfolio;
    private Map<String, String> companyMap;
    private SortedListModel companyListModel;
    private SortedListModel portfolioListModel;
    private Communicator communicator;
    private FileManager fileManager;

    private List<Display> displays;
    private DefaultComboBoxModel<String> priceComboBoxModel;
    private DefaultComboBoxModel<String> volumeComboBoxModel;

    public static void main(String[] args) {
        new MainDisplay();
    }

    public MainDisplay() {
        JFrame frame = new JFrame("Stock Monitoring System 5000");
        frame.setContentPane(panelMain);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        RefineryUtilities.centerFrameOnScreen(frame);

        CompanyListUtil companyListUtil = new CompanyListUtil();
        companyMap = companyListUtil.getMap();
        initializeLists();

        portfolio = new Portfolio();
        communicator = new Communicator(portfolio);
        fileManager = new JsonFileManager();

        loadPortfolioButton.addActionListener(this);
        savePortfolioButton.addActionListener(this);
        addButton.addActionListener(this);
        removeButton.addActionListener(this);
        displayButton.addActionListener(this);

        priceComboBoxModel = new DefaultComboBoxModel<>();
        volumeComboBoxModel = new DefaultComboBoxModel<>();
        stockPriceComboBox.setModel(priceComboBoxModel);
        stockVolumeComboBox.setModel(volumeComboBoxModel);

        basicTextOpeningRB.setSelected(true);

        frame.setVisible(true);
    }

    private void initializeLists() {
        companyListModel = new SortedListModel();
        portfolioListModel = new SortedListModel();

        companyListModel.addAll(companyMap.keySet().toArray());

        companyList.setModel(companyListModel);
        portfolioList.setModel(portfolioListModel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loadPortfolioButton) {
            portfolio = fileManager.loadPortfolio();
            for (Stock s : portfolio.values()) {
                List<String> cList = new ArrayList<>(companyMap.values());
                int index = cList.indexOf(s.symbol);
                List<String> pList = new ArrayList<>(companyMap.keySet());
                String name = pList.get(index);

                portfolioListModel.add(name);
                companyListModel.removeElement(name);
            }
        } else if (e.getSource() == savePortfolioButton) {
            if (portfolio != null && !portfolio.isEmpty()) {
                fileManager.savePortfolio(portfolio);
            }
        } else if (e.getSource() == addButton) {
            addToPortfolio();
        } else if (e.getSource() == removeButton) {
            removeFromPortfolio();
        } else if (e.getSource() == displayButton) {
            launchDisplays();
        }
    }

    private void addToPortfolio() {
        if (companyListModel.getSize() > 0) {
            String key = (String) companyList.getSelectedValue();
            if (key == null) {
                System.out.println("No item was selected to be added");
                return;
            }

            Stock stock = new Stock();
            stock.symbol = companyMap.get(key);
            portfolio.put(stock.symbol, stock);
            if (displays != null && !displays.isEmpty()) {
                addStockToDisplays(stock);
            }

            communicator.updatePortfolio(portfolio);
            communicator.beginTransfer();

            companyListModel.removeElement(key);
            portfolioListModel.add(key);
            priceComboBoxModel.addElement(stock.symbol);
            volumeComboBoxModel.addElement(stock.symbol);
        }
    }

    private void removeFromPortfolio() {
        if (portfolioListModel.getSize() > 0) {
            String key = (String) portfolioList.getSelectedValue();

            if (key == null) {
                System.out.println("No item was selected to be removed");
                return;
            }

            String symbol = companyMap.get(key);
            Stock stock = portfolio.remove(symbol);
            removeStockFromDisplays(stock);

            communicator.updatePortfolio(portfolio);
            if (portfolio.size() == 0) {
                communicator.endTransfer();
            }

            portfolioListModel.removeElement(key);
            companyListModel.add(key);
            priceComboBoxModel.removeElement(stock.symbol);
            volumeComboBoxModel.removeElement(stock.symbol);
        }
    }

    private void launchDisplays() {
        displays = getDisplays();
        for (Display display : displays) {
            portfolio.values().forEach(display::addStockToDisplay);
            display.display();
        }
        if (!communicator.isMonitoring()) {
            communicator.beginTransfer();
        }
    }

    private List<Display> getDisplays() {
        Stock priceStock = portfolio.get((String) stockPriceComboBox.getSelectedItem());
        Stock volumeStock = portfolio.get((String) stockVolumeComboBox.getSelectedItem());
        return DisplayFactory.createDisplays(basicTextDisplayCheckBox.isSelected(), basicTextOpeningRB.isSelected(),
                priceRangeDisplayCheckBox.isSelected(), portfolio,
                stockPriceCheckBox.isSelected(), priceStock,
                stockVolumeCheckBox.isSelected(), volumeStock);
    }

    private void addStockToDisplays(Stock stock) {
        for (Display display : displays) {
            if (display instanceof PortfolioStockDisplay) {
                display.addStockToDisplay(stock);
            }
        }
    }

    private void removeStockFromDisplays(Stock stock) {
        if (displays == null) return;
        for (Display display : displays) {
            if (display instanceof PortfolioStockDisplay) {
                display.removeStockFromDisplay(stock);
            }
        }
    }
}

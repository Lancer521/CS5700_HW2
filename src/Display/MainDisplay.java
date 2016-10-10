package Display;

import Data.CompanyListUtil;
import Data.Portfolio;
import Data.Stock;
import Data.SortedListModel;
import Messages.Communicator;
import Utils.FileManager;
import Utils.JsonFileManager;
import org.jfree.ui.RefineryUtilities;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;

/**
 * Created by Ty on 10/3/2016 at 9:20 PM.
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
    private JRadioButton priceRangeOpeningRB;
    private JRadioButton priceRangeClosingRB;

    private Portfolio portfolio;
    private Map<String, String> companyMap;
    private SortedListModel companyListModel;
    private SortedListModel portfolioListModel;
    private Communicator communicator;
    private FileManager fileManager;

    private List<Display> displays;

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
            portfolio = fileManager.loadPortfolio("NOT_YET_NEEDED");
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
        }
    }

    private void launchDisplays() {
        if (displays == null || displays.isEmpty()) {
            displays = getDisplays();
            for (Display display : displays) {
                portfolio.values().forEach(display::addStockToDisplay);
                display.display();
            }
        } else {
            for (Display display : displays) {
                display.display();
            }
        }
    }

    private List<Display> getDisplays() {
        return DisplayFactory.createDisplays(basicTextDisplayCheckBox.isSelected(), priceRangeDisplayCheckBox.isSelected());
    }

    private void addStockToDisplays(Stock stock) {
        for (Display display : displays) {
            if (display instanceof BasicTextDisplay) {
                display.addStockToDisplay(stock);
            }
        }
    }

    private void removeStockFromDisplays(Stock stock) {
        if (displays == null) return;
        for (Display display : displays) {
            if (display instanceof BasicTextDisplay) {
                display.removeStockFromDisplay(stock);
            }
        }
    }
}

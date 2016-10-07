package Display;

import Data.CompanyListUtil;
import Data.Portfolio;
import Data.Stock;
import Data.SortedListModel;
import Messages.Communicator;
import Utils.FileManager;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

/**
 * Created by Ty on 10/3/2016 at 9:20 PM.
 *
 */
public class MainDisplay implements ActionListener {

    private JList portfolioList;
    private JList companyList;
    private JButton addButton;
    private JButton removeButton;
    private JPanel panelMain;
    private JButton loadPortfolioButton;
    private JButton savePortfolioButton;
    private JButton stopMonitoringButton;
    private JPanel portfolioButtonsPanel;
    private JPanel displayButtonsPanel;
    private JPanel buttonsPanel;
    private JPanel portfolioPanel;
    private JPanel displayPanel;
    private JButton displayButton;

    private Portfolio portfolio;
    private Map<String, String> companyMap;
    private SortedListModel companyListModel;
    private SortedListModel portfolioListModel;
    private Communicator communicator;
    private FileManager fileManager;

    public MainDisplay(){
        JFrame frame = new JFrame("Stock Monitoring System 5000");
        frame.setContentPane(panelMain);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();

        CompanyListUtil companyListUtil = new CompanyListUtil();
        companyMap = companyListUtil.getMap();
        initializeLists();

        portfolio = new Portfolio();
        communicator = new Communicator(portfolio);
        fileManager = new FileManager();

        loadPortfolioButton.addActionListener(this);
        savePortfolioButton.addActionListener(this);
        addButton.addActionListener(this);
        removeButton.addActionListener(this);
        displayButton.addActionListener(this);
        stopMonitoringButton.addActionListener(this);

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
        if(e.getSource() == loadPortfolioButton){
            loadPortfolio();
        } else if(e.getSource() == savePortfolioButton){
            savePortfolio();
        } else if(e.getSource() == addButton){
            add();
        } else if(e.getSource() == removeButton){
            remove();
        } else if(e.getSource() == displayButton){
            launchDisplays();
        } else if(e.getSource() == stopMonitoringButton){
            stopMonitoring();
        }
    }

    private void loadPortfolio() {

    }

    private void savePortfolio() {

    }

    private void add() {
        if(companyListModel.getSize() > 0) {
            String key = (String) companyList.getSelectedValue();
            if (key == null) {
                System.out.println("No item was selected to be added");
                return;
            }
            String symbol = companyMap.get(key);
            portfolio.put(symbol, new Stock());

            communicator.updatePortfolio(portfolio);
            communicator.beginTransfer();

            companyListModel.removeElement(key);
            portfolioListModel.add(key);
            //portfolioListModel.add(key + " [" + symbol + "]");
        }
    }

    private void remove() {
        if(portfolioListModel.getSize() > 0) {
            String key = (String) portfolioList.getSelectedValue();

            if (key == null) {
                System.out.println("No item was selected to be removed");
                return;
            }
            String symbol = companyMap.get(key);
            portfolio.remove(symbol);

            portfolioListModel.removeElement(key);
            companyListModel.add(key);
        }
    }

    private void stopMonitoring() {
        if(communicator != null && communicator.isMonitoring()){
            communicator.endTransfer();
        }
    }

    private void launchDisplays() {
        BasicTextDisplay display = new BasicTextDisplay();
        for(Stock stock : portfolio.values()){
            if(stock.isHydrated){
                display.addStockToDisplay(stock);
            }
        }
        display.display();
    }

    private void closeDisplays(){

    }
}

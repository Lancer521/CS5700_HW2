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
    private JButton selectDisplaysButton;
    private JButton addButton;
    private JButton removeButton;
    private JPanel panelMain;
    private JButton startMonitoringButton;
    private JButton loadPortfolioButton;
    private JButton savePortfolioButton;
    private JButton stopMonitoringButton;

    private Portfolio portfolio;
    private Map<String, String> companyMap;
    private SortedListModel companyListModel;
    private SortedListModel portfolioListModel;
    private Communicator communicator;
    private FileManager fileManager;


    public void configure(){
        JFrame frame = new JFrame("Stock Monitoring System 5000");
        frame.setContentPane(panelMain);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();

        CompanyListUtil companyListUtil = new CompanyListUtil();
        companyMap = companyListUtil.getMap();
        initializeLists();

        portfolio = new Portfolio();
        fileManager = new FileManager();

        loadPortfolioButton.addActionListener(this);
        savePortfolioButton.addActionListener(this);
        addButton.addActionListener(this);
        removeButton.addActionListener(this);
        selectDisplaysButton.addActionListener(this);
        startMonitoringButton.addActionListener(this);
        stopMonitoringButton.addActionListener(this);

        frame.setVisible(true);
    }

    private void initializeLists() {
        companyListModel = new SortedListModel();
        portfolioListModel = new SortedListModel();

        for(String key : companyMap.keySet()){
            companyListModel.add(key);
        }

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
        } else if(e.getSource() == selectDisplaysButton){
            selectDisplays();
        } else if(e.getSource() == startMonitoringButton){
            startMonitoring();
        } else if(e.getSource() == stopMonitoringButton){
            stopMonitoring();
        }
    }

    private void loadPortfolio() {

    }

    private void savePortfolio() {

    }

    private void add() {
        String key = (String) companyList.getSelectedValue();
        String symbol = companyMap.get(key);
        portfolio.put(symbol, new Stock());

        companyListModel.removeElement(key);
        portfolioListModel.add(key + " [" + symbol + "]");
    }

    private void remove() {
        String key = (String) portfolioList.getSelectedValue();
        String symbol = companyMap.get(key);
        portfolio.remove(symbol);

        portfolioListModel.removeElement(key);
        companyListModel.add(key);
    }

    private void selectDisplays() {
        System.out.println();
    }

    private void startMonitoring() {
        if(communicator == null) {
            communicator = new Communicator(portfolio);
            communicator.beginTransfer();
        } else if(communicator.isMonitoring()){
            communicator.endTransfer();
            communicator.updatePortfolio(portfolio);
            communicator.beginTransfer();
        } else {
            communicator.updatePortfolio(portfolio);
            communicator.beginTransfer();
        }
    }

    private void stopMonitoring() {
        if(communicator != null && communicator.isMonitoring()){
            communicator.endTransfer();
        }
    }
}

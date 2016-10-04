package Display;

import Data.CompanyListUtil;

import javax.swing.*;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by Ty on 10/3/2016 at 9:20 PM.
 *
 */
public class MainDisplay implements Observer {

    private JList portfolioList;
    private JList companyList;
    private JButton selectDisplaySButton;
    private JButton addButton;
    private JButton removeButton;
    private JPanel panelMain;
    private JButton startMonitoringButton;

    private List<String> list;


    public void configure(){
        JFrame frame = new JFrame("Stock Monitoring System 5000");
        frame.setContentPane(panelMain);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();

        CompanyListUtil companyListUtil = new CompanyListUtil();
        list = companyListUtil.getList();
        companyList.setListData(list.toArray());
        populateCompanyList();

        frame.setVisible(true);
    }

    private void populateCompanyList() {
//        companyList.
    }

    @Override
    public void update(Observable o, Object arg) {

    }
}

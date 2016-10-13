package Display;


import Data.Stock;

import javax.swing.*;

/**
 * Created by Ty on 10/4/2016 at 11:10 PM.
 *
 */
public abstract class Display {

    public abstract void addStockToDisplay(Stock stock);

    public abstract void removeStockFromDisplay(Stock stock);

    public abstract void display();

    protected void display(JPanel panelMain, String title) {
        JFrame frame = new JFrame(title);
        frame.setContentPane(panelMain);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}

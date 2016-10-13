package Test;

import Data.Portfolio;
import Data.Stock;
import Messages.Communicator;
import Utils.FileManager;
import Utils.JsonFileManager;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Ty on 10/11/2016 at 10:37 PM.
 * *
 */
public class TestSuite {

    private Portfolio portfolio;
    private Stock stock;

    private void setUp(){
        portfolio = new Portfolio();
        stock = new Stock();
        stock.symbol = "GOOGL";
        portfolio.put(stock.symbol, stock);
    }

    @Test
    public void CommunicatorIsMonitoring(){
        setUp();
        Communicator communicator = new Communicator(portfolio);
        communicator.beginTransfer();
        Assert.assertTrue(communicator.isMonitoring());
    }

    @Test
    public void PortfolioSavingCorrectly(){
        setUp();
        FileManager manager = new JsonFileManager();
        manager.savePortfolio(portfolio);
        Portfolio loadedPortfolio = manager.loadPortfolio();
        String symbol1 = portfolio.values().iterator().next().symbol;
        String symbol2 = loadedPortfolio.values().iterator().next().symbol;
        Assert.assertTrue(symbol1.equals(symbol2));
    }
}

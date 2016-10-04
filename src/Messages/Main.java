package Messages;

import Display.MainDisplay;

/**
 * Created by Ty on 9/28/2016 at 5:52 PM.
 *
 */
public class Main {

    public static void main(String[] args){
        Communicator communicator = new Communicator();
        communicator.addTestData();
        communicator.beginTransfer();
        MainDisplay display = new MainDisplay();
        display.configure();
        System.out.println();
    }
}

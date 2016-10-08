package Display;

import java.util.ArrayList;

/**
 * Created by Ty on 10/8/2016 at 11:33 AM.
 *
 * Returns a list of new Display objects as specified by parameters
 */
public class DisplayFactory {

    public static ArrayList<Display> createDisplays(boolean basicText){
        ArrayList<Display> displays = new ArrayList<>();
        if(basicText){
            displays.add(new BasicTextDisplay());
        }
        return displays;
    }
}

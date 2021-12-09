

import java.awt.EventQueue;
import controller.GameController;



/**
 * StartApplication class to run and play the brick game.
 */
public class StartApplication {

    public static void main(String[] args){
        EventQueue.invokeLater(() -> new GameController().initialize());
    }

}

package main;

import ui.Terminal;

/**
 *
 * @author Gabriel Bressane
 */
public class PageSimulator {

    public static void main(String[] args) {
        
        Terminal ui = new Terminal();
        
        String tipoAlgoritmo = args[0];
        
        
        ui.showMessage(tipoAlgoritmo);
        
        System.out.println("Hello World!");
    }
}


package main;

import ui.Terminal;

/**
 *
 * @author Gabriel Bressane
 */
public class PageSimulator {

    public static void main(String[] args) {
        
        Terminal ui = new Terminal();
        
        String diretório_das_páginas = args[0];
        String algoritmo_de_substituição_de_páginas = args[1];
        String número_de_frames_de_memória = args[2];
        String quantidade_de_páginas_únicas = args[3];
        String quantidades_de_páginas_requeridas = args[4];


        ui.showMessage(diretório_das_páginas);
        ui.showMessage(algoritmo_de_substituição_de_páginas);
        ui.showMessage(número_de_frames_de_memória);
        ui.showMessage(quantidade_de_páginas_únicas);
        ui.showMessage(quantidades_de_páginas_requeridas);
        
    }
}


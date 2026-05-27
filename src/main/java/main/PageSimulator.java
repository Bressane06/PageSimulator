package main;

import ui.Terminal;
import simulacao.ConfiguracaoSimulacao;
import simulacao.Simulacao;
/**
 *
 * @author Gabriel Bressane
 */
public class PageSimulator {

    public static void main(String[] args) {
        
        Terminal ui = new Terminal();

        PageSimuladorOperacoes ops = new PageSimuladorOperacoes(ui, args);
        ConfiguracaoSimulacao cfg = ops.configuracao;

        Simulacao simulacao = new Simulacao(cfg);
        simulacao.executarSimulacao(cfg);
        
    }
}


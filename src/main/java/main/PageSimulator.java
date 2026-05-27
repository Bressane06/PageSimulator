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


        ui.showMessage(cfg.getDiretorio_das_paginas());
        ui.showMessage(cfg.getAlgoritmo_de_substituicao_de_paginas());
        ui.showMessage(cfg.getNumero_de_frames_de_memoria());
        ui.showMessage(cfg.getQuantidade_de_paginas_unicas());
        ui.showMessage(cfg.getQuantidades_de_paginas_requeridas());
        
    }
}


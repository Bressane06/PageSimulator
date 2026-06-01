package main;
import ui.Terminal;
import simulacao.ConfiguracaoSimulacao;

/**
 * Simula operações de gerenciamento de páginas.
 *
 * @author Gabriel Bressane
 */
public class PageSimuladorOperacoes {
 
    public Terminal ui;
    public ConfiguracaoSimulacao configuracao;

    public PageSimuladorOperacoes(Terminal ui) {
        this.ui = ui;
        this.configuracao = null;
    }

    public PageSimuladorOperacoes(Terminal ui, ConfiguracaoSimulacao configuracao) {
        this.ui = ui;
        this.configuracao = configuracao;
    }

    public PageSimuladorOperacoes(Terminal ui, String[] args) {
        this.ui = ui;
        if (args == null || args.length < 5) {
            throw new IllegalArgumentException("Esperado 5 argumentos: diretorio algoritmo numFrames qtdUnicas qtdRequeridas");
        }
        this.configuracao = new ConfiguracaoSimulacao(args[0], args[1], args[2], args[3], args[4]);
    }

    
}
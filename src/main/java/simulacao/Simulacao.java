package simulacao;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import ui.Terminal;

public class Simulacao {

    ConfiguracaoSimulacao config;
    Terminal ui = new Terminal();

    public Simulacao(ConfiguracaoSimulacao config) {
        this.config = config;
    }

    // Método para executar a simulação
    public void executarSimulacao(ConfiguracaoSimulacao config) {

        Path dir = Paths.get(config.getDiretorio_das_paginas());
        int qtd = Integer.parseInt(config.getQuantidade_de_paginas_unicas());
    
        GeradorPaginas gerador = new GeradorPaginas();
        List<Pagina> paginas = null;
        
        try {
            paginas = gerador.gerarPaginas(dir, qtd);
        } catch (Exception e) {
            System.err.println("Erro ao gerar páginas: " + e.getMessage());
            return;
        }
        
        ui.showMessage("Geradas " + paginas.size() + " paginas em: " + dir.toAbsolutePath());
        for (Pagina p : paginas) {
            ui.showMessage("Pagina " + p.getNumero() + " -> " + p.getConteudo());
        }
        

        // Aqui você pode implementar a lógica para executar a simulação usando a configuração fornecida.
        // Isso pode incluir carregar as páginas, aplicar o algoritmo de substituição, contar falhas de página, etc.
    }
}

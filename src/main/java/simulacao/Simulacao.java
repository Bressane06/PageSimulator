package simulacao;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import arquivos.FilePageStore;
import arquivos.PageStore;
import memoria.Pagina;
import ui.Terminal;

public class Simulacao{

    private final ConfiguracaoSimulacao config;
    private final Terminal ui;
    private final SimulacaoAlgoritmos simulacaoAlgoritmos;

    public Simulacao(ConfiguracaoSimulacao config){
        this.config = config;
        this.ui = new Terminal();
        this.simulacaoAlgoritmos = new SimulacaoAlgoritmos(config, ui);
    }

    public void executarSimulacao(){
        executarSimulacao(this.config);
    }

    public void executarSimulacao(ConfiguracaoSimulacao config){
        
        // fluxo:

        // Passo 1: gerar páginas e quantidade de páginas a partir do diretório e quantidade configurados
        Path dir = Paths.get(config.getDiretorio_das_paginas());
        int quantidadePaginas = Integer.parseInt(config.getQuantidade_de_paginas_unicas());
        PageStore store = new FilePageStore();
        List<Pagina> paginas;

        try{
            paginas = store.generatePages(dir, quantidadePaginas);
        }
        catch (Exception e){
            ui.showError("Erro ao gerar páginas: " + e.getMessage());
            return;
        }

        //ui.showMessage("Geradas " + paginas.size() + " paginas em: " + dir.toAbsolutePath());
        //for (Pagina pagina : paginas)
        
        //    ui.showMessage("Pagina " + pagina.getNumero() + " -> " + pagina.getConteudo());

        // Passo 2: escolher o algoritmo de substituição e simular
        simulacaoAlgoritmos.escolherAlgoritmo(paginas);
    }
}

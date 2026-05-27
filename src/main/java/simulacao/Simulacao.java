package simulacao;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

import algoritmos.AlgoritmoSubstituicao;
import algoritmos.FIFO;
import ui.Terminal;
import arquivos.FilePageStore;
import arquivos.PageStore;
import memoria.Frame;
import memoria.Pagina;

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

        PageStore store = new FilePageStore();
        List<Pagina> paginas = null;

        try {
            paginas = store.generatePages(dir, qtd);
        } catch (Exception e) {
            System.err.println("Erro ao gerar páginas: " + e.getMessage());
            return;
        }

        ui.showMessage("Geradas " + paginas.size() + " paginas em: " + dir.toAbsolutePath());
        for (Pagina p : paginas) {
            ui.showMessage("Pagina " + p.getNumero() + " -> " + p.getConteudo());
        }

        escolherAlgoritmo(paginas);

    }

    public void simularFIFO(List<Pagina> paginas) {
        ui.showMessage("Simulando FIFO...");

        int numeroDeFrames = Integer.parseInt(config.getNumero_de_frames_de_memoria());
        int quantidadeUnicas = Integer.parseInt(config.getQuantidade_de_paginas_unicas());
        int quantidadeRequeridas = Integer.parseInt(config.getQuantidades_de_paginas_requeridas());

        Frame[] frames = criarFrames(numeroDeFrames);
        AlgoritmoSubstituicao fifo = new FIFO();

        List<Integer> requisicoes = gerarSequenciaRequisicoes(quantidadeRequeridas, quantidadeUnicas);
        int totalFalhas = 0;

        for (int numeroPagina : requisicoes) {
            Pagina pagina = paginas.get(numeroPagina);

            ui.showMessage("Página Requerida: " + pagina.getNumero());

            if (paginaEstaNaMemoria(frames, pagina.getNumero())) {
                imprimirFrames(frames);
                continue;
            }

            totalFalhas++;
            int frameLivre = procurarFrameVazio(frames);

            if (frameLivre != -1) {
                frames[frameLivre].setPagina(pagina);
                fifo.registrarEntrada(frameLivre);
            } else {
                int frameSubstituir = fifo.escolherFrameParaSubstituir(frames);
                if (frameSubstituir != -1) {
                    frames[frameSubstituir].setPagina(pagina);
                }
            }

            imprimirFrames(frames);
        }

        ui.showMessage("Algoritmo de Substituição de Páginas: FIFO");
        ui.showMessage("Sequência de Requisição: " + formatarSequencia(requisicoes));
        ui.showMessage("Total de Falhas de Página: " + totalFalhas);
    }
    
    public void simularLRU() {
        ui.showMessage("Simulando LRU...");
        // Implementação do algoritmo LRU
    }

    private void escolherAlgoritmo(List<Pagina> paginas) {
        String algoritmo = config.getAlgoritmo_de_substituicao_de_paginas();
        switch (algoritmo) {
            case "LRU":
                simularLRU();
                break;
            case "FIFO":
                simularFIFO(paginas);
                break;
            default:
                ui.showMessage("Algoritmo desconhecido.");
        }
    }

    private Frame[] criarFrames(int quantidade) {
        Frame[] frames = new Frame[quantidade];
        for (int i = 0; i < quantidade; i++) {
            frames[i] = new Frame();
        }
        return frames;
    }

    private int procurarFrameVazio(Frame[] frames) {
        for (int i = 0; i < frames.length; i++) {
            if (frames[i].estaVazio()) {
                return i;
            }
        }
        return -1;
    }

    private boolean paginaEstaNaMemoria(Frame[] frames, int numeroPagina) {
        for (Frame frame : frames) {
            if (!frame.estaVazio() && frame.getPagina().getNumero() == numeroPagina) {
                return true;
            }
        }
        return false;
    }

    private List<Integer> gerarSequenciaRequisicoes(int quantidade, int limite) {
        List<Integer> requisicoes = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < quantidade; i++) {
            requisicoes.add(random.nextInt(limite));
        }
        return requisicoes;
    }

    private void imprimirFrames(Frame[] frames) {
        ui.showMessage("Frame Página Conteúdo");
        for (int i = 0; i < frames.length; i++) {
            if (frames[i].estaVazio()) {
                ui.showMessage(i + " - -");
            } else {
                Pagina pagina = frames[i].getPagina();
                ui.showMessage(i + " " + pagina.getNumero() + " " + pagina.getConteudo());
            }
        }
    }

    private String formatarSequencia(List<Integer> requisicoes) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < requisicoes.size(); i++) {
            if (i > 0) {
                builder.append(" ");
            }
            builder.append(requisicoes.get(i));
        }
        return builder.toString();
    }
}

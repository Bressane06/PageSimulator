package simulacao;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

import algoritmos.AlgoritmoSubstituicao;
import algoritmos.FIFO;
import algoritmos.LFU;
import algoritmos.LRU;
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
            ui.showError("Erro ao gerar páginas: " + e.getMessage());
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
                fifo.registrarAcesso(obterIndicePagina(frames, pagina.getNumero()));
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
                    fifo.registrarEntrada(frameSubstituir);
                }
                
            }

            imprimirFrames(frames);
        }

        ui.showResults("FIFO", formatarSequencia(requisicoes), totalFalhas);
    }

    public void simularLFU(List<Pagina> paginas) {
        ui.showMessage("Simulando LFU...");

        int numeroDeFrames = Integer.parseInt(config.getNumero_de_frames_de_memoria());
        int quantidadeUnicas = Integer.parseInt(config.getQuantidade_de_paginas_unicas());
        int quantidadeRequeridas = Integer.parseInt(config.getQuantidades_de_paginas_requeridas());

        Frame[] frames = criarFrames(numeroDeFrames);
        AlgoritmoSubstituicao lfu = new LFU(numeroDeFrames);

        List<Integer> requisicoes = gerarSequenciaRequisicoes(quantidadeRequeridas, quantidadeUnicas);
        int totalFalhas = 0;

        for (int numeroPagina : requisicoes) {
            Pagina pagina = paginas.get(numeroPagina);

            ui.showMessage("Página Requerida: " + pagina.getNumero());

            int frameHit = obterIndicePagina(frames, pagina.getNumero());
            if (frameHit != -1) {
                lfu.registrarAcesso(frameHit);
                imprimirFrames(frames);
                continue;
            }

            totalFalhas++;
            int frameLivre = procurarFrameVazio(frames);

            if (frameLivre != -1) {
                frames[frameLivre].setPagina(pagina);
                lfu.registrarEntrada(frameLivre);
            } else {
                int frameSubstituir = lfu.escolherFrameParaSubstituir(frames);
                if (frameSubstituir != -1) {
                    frames[frameSubstituir].setPagina(pagina);
                    lfu.registrarEntrada(frameSubstituir);
                }
            }

            imprimirFrames(frames);
        }

        ui.showResults("LFU", formatarSequencia(requisicoes), totalFalhas);
    }
    
    public void simularLRU(List<Pagina> paginas) {
        ui.showMessage("Simulando LRU...");

        int numeroDeFrames = Integer.parseInt(config.getNumero_de_frames_de_memoria());
        int quantidadeUnicas = Integer.parseInt(config.getQuantidade_de_paginas_unicas());
        int quantidadeRequeridas = Integer.parseInt(config.getQuantidades_de_paginas_requeridas());

        Frame[] frames = criarFrames(numeroDeFrames);
        AlgoritmoSubstituicao lru = new LRU(numeroDeFrames);

        List<Integer> requisicoes = gerarSequenciaRequisicoes(quantidadeRequeridas, quantidadeUnicas);
        int totalFalhas = 0;

        for (int numeroPagina : requisicoes) {
            Pagina pagina = paginas.get(numeroPagina);

            ui.showMessage("Página Requerida: " + pagina.getNumero());

            // verificar se página está na memória e obter o índice do frame
            int frameHit = -1;
            for (int i = 0; i < frames.length; i++) {
                if (!frames[i].estaVazio() && frames[i].getPagina().getNumero() == numeroPagina) {
                    frameHit = i;
                    break;
                }
            }

            if (frameHit != -1) {
                // hit: atualizar timestamp via registrarEntrada
                lru.registrarEntrada(frameHit);
                imprimirFrames(frames);
                continue;
            }

            // falta de página
            totalFalhas++;
            int frameLivre = procurarFrameVazio(frames);

            if (frameLivre != -1) {
                frames[frameLivre].setPagina(pagina);
                lru.registrarEntrada(frameLivre);
            } else {
                int frameSubstituir = lru.escolherFrameParaSubstituir(frames);
                if (frameSubstituir != -1) {
                    frames[frameSubstituir].setPagina(pagina);
                    lru.registrarEntrada(frameSubstituir);
                }
            }

            imprimirFrames(frames);
        }

        ui.showResults("LRU", formatarSequencia(requisicoes), totalFalhas);
    }

    private void escolherAlgoritmo(List<Pagina> paginas) {
        String algoritmo = config.getAlgoritmo_de_substituicao_de_paginas();
        switch (algoritmo) {
            case "LRU":
                simularLRU(paginas);
                break;
            case "FIFO":
                simularFIFO(paginas);
                break;
            case "LFU":
                simularLFU(paginas);
                break;
            default:
                ui.showMessage("Algoritmo desconhecido.");
        }
    }

    private Frame[] criarFrames(int quantidade) {
        Frame[] frames = new Frame[quantidade];
        for (int i = 0; i < quantidade; i++) 
            frames[i] = new Frame();
        
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

    private int obterIndicePagina(Frame[] frames, int numeroPagina) {
        for (int i = 0; i < frames.length; i++) {
            if (!frames[i].estaVazio() && frames[i].getPagina().getNumero() == numeroPagina) {
                return i;
            }
        }
        return -1;
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

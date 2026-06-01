package simulacao;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import algoritmos.AlgoritmoSubstituicao;
import algoritmos.FIFO;
import algoritmos.LFU;
import algoritmos.LRU;
import memoria.Frame;
import memoria.Pagina;
import ui.Terminal;

public class SimulacaoAlgoritmos {

    private final ConfiguracaoSimulacao config;
    private final Terminal ui;

    public SimulacaoAlgoritmos(ConfiguracaoSimulacao config, Terminal ui) {
        this.config = config;
        this.ui = ui;
    }

    public void escolherAlgoritmo(List<Pagina> paginas) {
        String algoritmo = config.getAlgoritmo_de_substituicao_de_paginas();
        int numeroDeFrames = Integer.parseInt(config.getNumero_de_frames_de_memoria());

        switch (algoritmo) {
            case "LRU":
                simularLRU(paginas, numeroDeFrames);
                break;

            case "FIFO":
                simularFIFO(paginas, numeroDeFrames);
                break;

            case "LFU":
                simularLFU(paginas, numeroDeFrames);
                break;

            default:
                ui.showMessage("Algoritmo desconhecido.");
        }
    }

    public void simularFIFO(List<Pagina> paginas, int numeroDeFrames) {
        executarSimulacao("FIFO", paginas, new FIFO(), numeroDeFrames);
    }

    public void simularLFU(List<Pagina> paginas, int numeroDeFrames) {
        executarSimulacao("LFU", paginas, new LFU(numeroDeFrames), numeroDeFrames);
    }

    public void simularLRU(List<Pagina> paginas, int numeroDeFrames) {
        executarSimulacao("LRU", paginas, new LRU(numeroDeFrames), numeroDeFrames);
    }

    private void executarSimulacao(
            String nomeAlgoritmo,
            List<Pagina> paginas,
            AlgoritmoSubstituicao algoritmo,
            int numeroDeFrames) {

        ui.showMessage("Simulando " + nomeAlgoritmo + "...");

        int quantidadeUnicas =
                Integer.parseInt(config.getQuantidade_de_paginas_unicas());

        int quantidadeRequeridas =
                Integer.parseInt(config.getQuantidades_de_paginas_requeridas());

        Frame[] frames = criarFrames(numeroDeFrames);

        List<Integer> requisicoes =
                gerarSequenciaRequisicoes(
                        quantidadeRequeridas,
                        quantidadeUnicas);

        int totalFalhas = 0;

        for (int numeroPagina : requisicoes) {
            Pagina pagina = paginas.get(numeroPagina);

            // ui.showMessage("Página Requerida: " + pagina.getNumero());

            int frameHit =
                    obterIndicePagina(frames, pagina.getNumero());

            if (frameHit != -1) {
                algoritmo.registrarAcesso(frameHit);
                // imprimirFrames(frames);
                continue;
            }

            totalFalhas++;

            int frameLivre = procurarFrameVazio(frames);

            if (frameLivre != -1) {
                frames[frameLivre].setPagina(pagina);
                algoritmo.registrarEntrada(frameLivre);
            } else {
                int frameSubstituir =
                        algoritmo.escolherFrameParaSubstituir(frames);

                if (frameSubstituir != -1) {
                    frames[frameSubstituir].setPagina(pagina);
                    algoritmo.registrarEntrada(frameSubstituir);
                }
            }

            // imprimirFrames(frames);
        }

        ui.showResults(
                nomeAlgoritmo,
                formatarSequencia(requisicoes),
                totalFalhas);
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
            if (frames[i].estaVazio()) 
                return i;
            
        }

        return -1;
    }

    private int obterIndicePagina(Frame[] frames, int numeroPagina) {
        for (int i = 0; i < frames.length; i++) {
            if (!frames[i].estaVazio() && frames[i].getPagina().getNumero() == numeroPagina) 
                return i;
            
        }

        return -1;
    }

    private List<Integer> gerarSequenciaRequisicoes(int quantidade, int limite) {

        List<Integer> requisicoes = new ArrayList<>();
        Random random = new Random();

        for(int i = 0; i < quantidade; i++)
            requisicoes.add(random.nextInt(limite));
        
        return requisicoes;
    }

    private void imprimirFrames(Frame[] frames) {
        ui.showMessage(String.format("%-6s %-8s %-10s", "Frame", "Página", "Conteúdo"));

        for (int i = 0; i < frames.length; i++) {
            if (frames[i].estaVazio()) {
                ui.showMessage(String.format("%-6d %-8s %-10s", i, "-", "-"));
            } else {
                Pagina pagina = frames[i].getPagina();

                ui.showMessage(String.format(
                        "%-6d %-8d %-10s",
                        i,
                        pagina.getNumero(),
                        pagina.getConteudo()));
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
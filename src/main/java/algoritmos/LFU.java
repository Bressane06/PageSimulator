package algoritmos;

import memoria.Frame;

/**
 * LFU - Least Frequently Used
 *
 * Substitui a página com menor frequência de uso. Em caso de empate,
 * usa o timestamp da última utilização como desempate, escolhendo a
 * página menos recente.
 */
public class LFU implements AlgoritmoSubstituicao {

    private final int[] frequencias;
    private final long[] lastUsed;
    private long tick;

    public LFU(int numFrames) {
        this.frequencias = new int[numFrames];
        this.lastUsed = new long[numFrames];
        this.tick = 0L;
    }

    @Override
    public int escolherFrameParaSubstituir(Frame[] frames) {
        int escolha = -1;
        int menorFrequencia = Integer.MAX_VALUE;
        long maisAntigo = Long.MAX_VALUE;

        for (int i = 0; i < frames.length; i++) {
            Frame frame = frames[i];
            if (frame == null || frame.estaVazio()) {
                continue;
            }

            int frequenciaAtual = frequencias[i];
            long ultimoUso = lastUsed[i];

            if (frequenciaAtual < menorFrequencia
                    || (frequenciaAtual == menorFrequencia && ultimoUso < maisAntigo)) {
                menorFrequencia = frequenciaAtual;
                maisAntigo = ultimoUso;
                escolha = i;
            }
        }

        return escolha;
    }

    @Override
    public void registrarEntrada(int frameIndex) {
        tick++;
        frequencias[frameIndex] = 1;
        lastUsed[frameIndex] = tick;
    }

    @Override
    public void registrarAcesso(int frameIndex) {
        tick++;
        frequencias[frameIndex]++;
        lastUsed[frameIndex] = tick;
    }
}
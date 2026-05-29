package algoritmos;

import memoria.Frame;

/**
 * LRU - Least Recently Used
 *
 * Implementação simples de LRU que mantém um timestamp (tick) de última
 * utilização por índice de frame. Ao escolher um frame para substituir,
 * seleciona o frame cuja última utilização é a mais antiga.
 *
 * A interface `AlgoritmoSubstituicao` fornece `registrarEntrada(int)`.
 * - `registrarEntrada`: chamado quando uma nova página é carregada no frame
 *   (inicializa/atualiza o timestamp);
 */
public class LRU implements AlgoritmoSubstituicao {
    private final long[] lastUsed;
    private long tick;

    /**
     * Constrói um LRU para um número fixo de frames.
     * lastUsed é inicializado com o tamanho dos frames e não é redimensionado.
     */
    public LRU(int numFrames) {
        this.lastUsed = new long[numFrames];
        this.tick = 0L;
    }

    // funciona assim: 
        // - cada vez que um frame é acessado (hit) ou recebe uma nova 
        //      página (miss), o tick é incrementado e o timestamp do frame é atualizado
        // - ao escolher um frame para substituir, o algoritmo percorre os frames e selecionados 
        //      aquele com o timestamp mais antigo (menor valor de lastUsed)
    @Override
    public int escolherFrameParaSubstituir(Frame[] frames) {
        
        int escolha = -1;
        long maisVelho = Long.MAX_VALUE;

        for (int i = 0; i < frames.length; i++) {
            Frame f = frames[i];
            
            if (f == null || f.estaVazio()) 
                continue;

            long ultimoUsado = lastUsed[i];
            
            if (ultimoUsado < maisVelho) {
                maisVelho = ultimoUsado;
                escolha = i;
            }
        }

        return escolha;
    }

    @Override
    public void registrarEntrada(int frameIndex) {
        tick++;
        lastUsed[frameIndex] = tick;
    }

    @Override
    public void registrarAcesso(int frameIndex) {
        registrarEntrada(frameIndex);
    }

}

package algoritmos;

import memoria.Frame;

/**
 *
 * @author Gabriel Bressane
 */

public interface AlgoritmoSubstituicao {
    
    int escolherFrameParaSubstituir(Frame[] frames);

    void registrarEntrada(int frameIndex);

    default void registrarAcesso(int frameIndex) {}


}

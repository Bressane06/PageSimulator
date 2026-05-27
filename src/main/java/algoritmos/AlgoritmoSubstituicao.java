package algoritmos;

import memoria.Frame;

public interface AlgoritmoSubstituicao {
    
    int escolherFrameParaSubstituir(Frame[] frames);

    void registrarEntrada(int frameIndex);

}

package algoritmos;

import java.util.LinkedList;
import java.util.Queue;
import memoria.Frame;

public class FIFO implements AlgoritmoSubstituicao {

	private final Queue<Integer> fila;

	public FIFO() {
		this.fila = new LinkedList<>();
	}

	@Override
	public int escolherFrameParaSubstituir(Frame[] frames) {
		if (fila.isEmpty()) {
			return -1;
		}

        // .poll() remove e retorna o elemento mais antigo da fila
		int frameMaisAntigo = fila.poll();
		fila.add(frameMaisAntigo);
		return frameMaisAntigo;
	}

	@Override
	public void registrarEntrada(int frameIndex) {
		fila.add(frameIndex);
	}
}

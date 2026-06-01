package algoritmos;

import java.util.LinkedList;
import java.util.Queue;
import memoria.Frame;

/**
 *
 * @author Gabriel Bressane
 */

public class FIFO implements AlgoritmoSubstituicao {

	private final Queue<Integer> fila;

	public FIFO() {
		this.fila = new LinkedList<>();
	}

	@Override
	public int escolherFrameParaSubstituir(Frame[] frames) {

		while (!fila.isEmpty()) {
			int candidato = fila.poll();

			if (!frames[candidato].estaVazio()) 
				return candidato;
			
		}

		return -1;
	}

	@Override
	public void registrarEntrada(int frameIndex) {
		fila.add(frameIndex);
	}
}

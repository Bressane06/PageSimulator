package arquivos;

import java.nio.file.Path;
import java.io.IOException;
import java.util.List;

import memoria.Pagina;

/**
 *
 * @author Gabriel Bressane
 */

public interface PageStore {

    List<Pagina> generatePages(Path directory, int qtd) throws IOException;
    Pagina loadPage(Path directory, int index) throws IOException;
    List<Pagina> loadAll(Path directory) throws IOException;

}

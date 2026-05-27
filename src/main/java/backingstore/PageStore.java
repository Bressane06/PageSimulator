package backingstore;

import simulacao.Pagina;
import java.nio.file.Path;
import java.io.IOException;
import java.util.List;

public interface PageStore {

    List<Pagina> generatePages(Path directory, int qtd) throws IOException;
    Pagina loadPage(Path directory, int index) throws IOException;
    List<Pagina> loadAll(Path directory) throws IOException;

}

package arquivos;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import memoria.Pagina;

public class FilePageStore implements PageStore {

    @Override
    public List<Pagina> generatePages(Path directory, int qtd) throws IOException {
        
        Files.createDirectories(directory);
        List<Pagina> pages = new ArrayList<>();
        
        for (int i = 0; i < qtd; i++) {
            String content = generateContentForPage(i);
            Path file = directory.resolve(i + ".pag");
            Files.writeString(file, content, StandardCharsets.UTF_8);
            Pagina page = new Pagina(i, content);
            
            pages.add(page);
        }
        return pages;
    }

    @Override
    public Pagina loadPage(Path directory, int index) throws IOException {
        
        Path file = directory.resolve(index + ".pag");
        String content = Files.readString(file, StandardCharsets.UTF_8);
        return new Pagina(index, content);
    
    }

    @Override
    public List<Pagina> loadAll(Path directory) throws IOException {
        
        List<Pagina> pages = new ArrayList<>();

        // uso do lambda para ler todos os arquivos .pag e criar objetos Pagina
        Files.list(directory).filter(p -> p.getFileName().toString().endsWith(".pag")).forEach(p -> {
            try {
                String name = p.getFileName().toString();
                int idx = Integer.parseInt(name.substring(0, name.indexOf('.')));
                String content = Files.readString(p, StandardCharsets.UTF_8);
                
                Pagina page = new Pagina(idx, content);
                pages.add(page);
            
            } catch (IOException e) {
                // ignorar, por simplicidade
            }
        });
        return pages;
    }

    private String generateContentForPage(int pageNumber) {
        
        char base = (char) ('a' + (pageNumber % 26));
        StringBuilder string = new StringBuilder(10);
        
        for (int i = 0; i < 10; i++) 
            string.append(base);
        
        return string.toString();
    }
}

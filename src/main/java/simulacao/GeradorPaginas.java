package simulacao;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class GeradorPaginas {

    // Usei a lib do java.nio para criar os arquivos das páginas, porque achei mais simples do que usar 
    // FileWriter ou BufferedWriter. O método gerarPaginas cria os arquivos das páginas no diretório especificado 
    // e retorna uma lista de objetos Pagina com o número e o conteúdo de cada página. O conteúdo é gerado 
    // usando um padrão simples, onde cada página tem um caractere repetido 10 vezes, começando com 'a' para a página 0, 
    // 'b' para a página 1, e assim por diante.
    public List<Pagina> gerarPaginas(Path diretorio, int quantidadeDePaginasUnicas) throws IOException {
        Files.createDirectories(diretorio);

        List<Pagina> paginas = new ArrayList<>();
        for (int i = 0; i < quantidadeDePaginasUnicas; i++) {
            String conteudo = gerarConteudoParaPagina(i);
            Path arquivo = diretorio.resolve(i + ".pag");
            Files.writeString(arquivo, conteudo, StandardCharsets.UTF_8);
            paginas.add(new Pagina(i, conteudo));
        }
        return paginas;
    }

    private String gerarConteudoParaPagina(int numeroPagina) {
        char caractereBase = (char) ('a' + (numeroPagina % 26));
        StringBuilder conteudo = new StringBuilder(10);
        for (int i = 0; i < 10; i++) {
            conteudo.append(caractereBase);
        }
        return conteudo.toString();
    }
}
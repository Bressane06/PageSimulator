package memoria;

public class Pagina {

    private final int numero;
    private final String conteudo;

    public Pagina(int numero, String conteudo) {
        this.numero = numero;
        this.conteudo = conteudo;
    }

    public int getNumero() {
        return numero;
    }

    public String getConteudo() {
        return conteudo;
    }
}


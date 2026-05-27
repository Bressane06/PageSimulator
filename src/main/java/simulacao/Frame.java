package simulacao;

public class Frame {

    private Pagina pagina;

    public Frame() {
        this.pagina = null;
    }

    public Pagina getPagina() {
        return pagina;
    }

    public void setPagina(Pagina pagina) {
        this.pagina = pagina;
    }

    public boolean estaLivre() {
        return pagina == null;
    }
}
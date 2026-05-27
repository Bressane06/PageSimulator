package memoria;

public class Frame {

    private Pagina pagina;

    public Frame() {
        this.pagina = null;
    }

    public boolean estaVazio() {
        return pagina == null;
    }

    public Pagina getPagina() {
        return pagina;
    }

    public void setPagina(Pagina pagina) {
        this.pagina = pagina;
    }

}
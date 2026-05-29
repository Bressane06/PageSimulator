
package ui;

/**
 *
 * @author Gabriel Bressane
 */
public class Terminal implements UserInterface{
    
    public void showMessage(String message){
    
        System.out.println(message);
        
    }

    public void showResults(String algoritmo, String sequencia, int totalFalhas) {
        System.out.println("Algoritmo de Substituição de Páginas: " + algoritmo);
        System.out.println("Sequência de Requisição: " + sequencia);
        System.out.println("Total de Falhas de Página: " + totalFalhas);
    }

    public void showError(String error) {
        System.err.println("Erro: " + error);
    }
    
}

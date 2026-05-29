package ui;
/**
 *
 * @author Gabriel Bressnae
 */
public interface UserInterface {
    
    public void showMessage(String message);
    public void showResults(String algoritmo, String sequencia, int totalFalhas);
    public void showError(String error);
    
}

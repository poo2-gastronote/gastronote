//Netbeans IDE23
//Isadora Costa Baía - RA2614685
import javax.swing.JOptionPane;

public class NomeException extends Exception {
    public void nomeInvalido() {
        JOptionPane.showMessageDialog(null, "O nome da receita deve ter pelo menos 3 caracteres.", "Erro", 0);
    }
}
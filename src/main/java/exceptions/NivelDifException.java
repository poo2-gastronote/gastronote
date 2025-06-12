package exceptions;

import javax.swing.JOptionPane;

public class NivelDifException extends Exception {
    public void nivelInvalido() {
        JOptionPane.showMessageDialog(null, "O valor deve estar entre 1 e 5.", "Erro", 0);

    }
}

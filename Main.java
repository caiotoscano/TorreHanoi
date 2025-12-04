import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        // SwingUtilities garante que a interface gráfica seja criada
        // na Thread correta de eventos do Java
        SwingUtilities.invokeLater(() -> {
            // Instancia a janela principal (ProjetoTorreHanoi) e a torna visível
            // Como Main e ProjetoTorreHanoi estão na mesma pasta
            // vão se enxergar automaticamente
            new ProjetoTorreHanoi().setVisible(true);
        });
    }
}

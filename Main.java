import javax.swing.JPanel;
import javax.swing.SwingUtilities;;

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

    class PainelHanoi extends JPanel {
        // Usei array para guardar as três pilhas (as torres)
        private Pilha[] torres;
        // Guardando número total de discos da simulação
        private int totalDiscos;
        // Usei um array de cores para colorir os discos de forma diferente
        private final Color[] coresDiscos = {
                new Color(255, 87, 34), new Color(255, 193, 7), new Color(76, 175, 80),
                new Color(33, 150, 243), new Color(156, 39, 176), new Color(233, 30, 99),
                new Color(121, 85, 72), new Color(96, 125, 139), new Color(0, 0, 0),
        };

        // Construtor do painel
        public PainelHanoi() {
            // Inicializando o array de 3 posições
            torres = new Pilha[3];
            // Loop para instanciar uma nova pilha customizada em cada posição
            for (int i = 0; i < 3; i++) {
                torres[i] = new Pilha();
            }
            // Define a cor de fundo do painel como branco
            setBackground(Color.WHITE);
        }
    }
}

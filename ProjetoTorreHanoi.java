import javax.swing.*; //classe necessária para criar interface gráfica
import java.awt.*; // classe para manipulação de cores, gráficos e layout

//Declaração da classe principal que herda JFrame (janela padrão)
public class ProjetoTorreHanoi extends JFrame {
    // Declaração do painel onde as torres e discos serão desenhados (a classe
    // interna foi definida mais abaixo)
    private PainelHanoi painel;
    // Declaração dos botões para selecionar quantidade de disco
    private JButton btn3Discos, btn6Discos, btn9Discos, btnParar;
    // Declaração do label que mostrará status da simulação
    private JLabel lblStatus;

    // Declaração de uma Thread para rodar o algoritmo sem travar a tela
    private ThreadResolucao;
    //Construtor, delcarando a classe onde abre a janela principal
    public ProjetoHanoi() {
        //Chamando o construtor da classe mãe o JFrame e definindo título da janela
        super("Projeto Torre de Hanoi");
        //Aqui estamos setando para que o programa sempre encerre ao fechar a janela
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //aqui estamos setando o tamanho da janela: 800 pixels de largura por 500 de altura
        setSize(800, 500);
        //Aqui estou setando uma localização vazia para que a janela fique centralizada na tela dinamicamente
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        // Instanciando o painel de desenho customizado
        painel = new PainelHanoi();
        //Adiciona o painel no centro da janela para ocupar maior espaço
        add(painel, BorderLayout.CENTER);
    }
}

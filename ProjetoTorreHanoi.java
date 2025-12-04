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
    private Thread threadResolucao;

    // Construtor, declarando a classe onde abre a janela principal
    public ProjetoTorreHanoi() {
        // Chamando o construtor da classe mãe o JFrame e definindo título da janela
        super("Projeto Torre de Hanoi");
        // Aqui estamos setando para que o programa sempre encerre ao fechar a janela
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // aqui estamos setando o tamanho da janela: 800 pixels de largura por 500 de
        // altura
        setSize(800, 500);
        // Aqui estou setando uma localização vazia para que a janela fique centralizada
        // na tela dinamicamente
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        // Instanciando o painel de desenho customizado
        painel = new PainelHanoi();
        // Adiciona o painel no centro da janela para ocupar maior espaço
        add(painel, BorderLayout.CENTER);
        // Cria um painel secundário para organizar os botões
        JPanel painelControles = new JPanel();
        // Instancia os botões com os textos
        btn3Discos = new JButton("3 Discos");
        btn6Discos = new JButton("6 Discos");
        btn9Discos = new JButton("9 Discos");
        btnParar = new JButton("Parar / Resetar");
        // Instanciando texto de status inicial do programa
        lblStatus = new JLabel("Selecione a quantidade de discos.");
        // Adiciona os botões ao painel de controles
        painelControles.add(btn3Discos);
        painelControles.add(btn6Discos);
        painelControles.add(btn9Discos);
        // Adicionando padding de 20px para separar o botão de parar
        painelControles.add(Box.createHorizontalStrut(20));
        painelControles.add(btnParar);
        // Cria um painel inferior que vai conter os botões e os textos de status
        JPanel painelInferior = new JPanel(new BorderLayout());
        // Setando os botões no centro do painel inferior
        painelInferior.add(painelControles, BorderLayout.CENTER);
        // Adiciona o texto de status na parte de baixo do painel
        painelInferior.add(lblStatus, BorderLayout.SOUTH);
        // Centralizando texto do status
        lblStatus.setHorizontalAlignment(SwingConstants.CENTER);
        // Adiciona um padding no centro do texto para não ficar colado
        lblStatus.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        // Adiciona todo esse painel inferior na parte de baixo da janela principal
        add(painelInferior, BorderLayout.SOUTH);
        // Configuração das ações dos botões (Setando Listeners)
        // Quando clicar em "3 Discos", chama o método iniciarSimulacao passando 3
        btn3Discos.addActionListener(e -> iniciarSimulacao(3));
        // Quando clicar em "6 Discos", chama o método iniciarSimulacao passando 3
        btn6Discos.addActionListener(e -> iniciarSimulacao(6));
        // Quando clicar em "9 Discos", chama o método iniciarSimulacao passando 3
        btn9Discos.addActionListener(e -> iniciarSimulacao(9));
        // Quando clica em "Parar", chama o método pararSimulacao
        btnParar.addActionListener(e -> pararSimulacao());
    }

    // Criando método responsável por preparar e começar a execução do algoritmo
    private void iniciarSimulacao(int n) {
        // Garantindo que qualquer simulação anterior seja parada antes de começar uma
        // nova
        pararSimulacao();
        // Chamando método do painel que cria pilhas e enche a primeira torre
        painel.inicializarTorres(n);
        // Desativando botões interativos para usuário não clicar durante execução
        alternarBotoes(false);
        // Criando uma nova Thread. Importante! Se o loop da resolução da torre fosse
        // na thread principal a interface gráfica congelaria e não seria possível
        // visualizar a animação
        threadResolucao = new Thread(() -> {
            try {
                // Atualiza texto na tela informando que começou
                lblStatus.setText("Resolvendo iterativamente (utilizando classe Pilha.java)...");
                // Resolvi colocar uma pausa de 1 segundo para o usuário ver o estado inicial
                Thread.sleep(1000);
                // Chamando algoritmo principal (Iterativo)
                resolverHanoiIterativo(n);
                // Se o método ThreadResolucao terminar sem erro, avisa que concluiu
                lblStatus.setText("Concluído, torre movida com sucesso!");
            } catch (InterruptedException e) {
                // Se a thread for interrompida (botão parar)
                lblStatus.setText("Simulação interrompida.");
            } finally {
                // Bloco finally sempre executa: reativa os botões ao terminar de rodar
                alternarBotoes(true);
            }
        });
        // Iniciando a execução da thread que acabei de criar
        threadResolucao.start();
    }

    // Método para interromper a execução e limpar a tela
    private void pararSimulacao() {
        // verifica se existe uma thread rodando e se ela já está ativa
        if (threadResolucao != null && threadResolucao.isAlive()) {
            // Envia um sinal de interrupção para a thread parar o loop
            threadResolucao.interrupt();
        }
        // Limpa as torres visualmente (esvazia a pilha)
        painel.limpar();
        // reativa os botões
        alternarBotoes(true);
        // atualiza o texto de status
        lblStatus.setText("Simulação parada");
    }

    // método auxiliar para ativar (true) ou desativar (false)os botões de seleção
    private void alternarBotoes(boolean estado) {
        btn3Discos.setEnabled(estado);
        btn6Discos.setEnabled(estado);
        btn9Discos.setEnabled(estado);
    }

    // Implementando a lógica iterativa
    private void resolverHanoiIterativo(int n) throws InterruptedException {
        // Calcula o total de movimentos necessários: fórmula 2 elevado a N, menos 1
        long totalMovimentos = (long) Math.pow(2, n) - 1;
        // Define os índices das torres no vetor: 0 = Origem, 1 = Auxiliar, 2 = Destino
        int origem = 0;
        int auxiliar = 1;
        int destino = 2;
        // aqui foi um truque que eu implementei enquanto estava construindo o código
        // se o número de discos for par, troquei o destino com auxiliar
        // isso garante que a torre acabe SEMPRE no pino C
        if (n % 2 == 0) {
            auxiliar = 2;
            destino = 1;
        }
        // Definindo a velocidade da animação. Por exemplo 9 discos são muitos
        // movimentos, acelerei 50ms
        // Se for 3 ou 6, coloquei mais devagar para ser mais visual e didático
        int delay = (n >= 9) ? 50 : 500;
        // Aqui o loop é construído indo de 1 até o total de movimentos necessários
        for (int i = 1; i <= totalMovimentos; i++) {
            // verificando se usuário apertou "parar"
            if (Thread.interrupted())
                throw new InterruptedException();
            // Essa lógica funciona em ciclos de 3 movimentos:
            // i % 3 == 1:Movimento exato entre Origem e Destino
            if (i % 3 == 1) {
                moverDiscoLegal(origem, destino);
            }
            // i % 3 == 2: Movimento entre origem e auxiliar
            else if (i % 3 == 2) {
                moverDiscoLegal(origem, auxiliar);
            }
            // i % 3 == 2: Movimento entre auxiliar e destino
            else if (i % 3 == 0) {
                moverDiscoLegal(auxiliar, destino);
            }
            // Pausando a execução pelo tempo definido no delay para criar a animação
            Thread.sleep(delay);
        }
    }

    // método que decide quem move para quem entre os dois pinos
    private void moverDiscoLegal(int indexA, int indexB) {
        // pega referência da Pilha A no painel
        Pilha pilhaA = painel.getPilha(indexA);
        // pega referência da Pilha B no painel
        Pilha pilhaB = painel.getPilha(indexB);
        // Agora vou começar a usar os métodos da classe pilha
        Object objetoA = pilhaA.pop();
        Object objetoB = pilhaB.pop();
        // converti o objeto para inteiro. Se for null a pilha está vazia
        int valA = (objetoA == null) ? Integer.MAX_VALUE : (Integer) objetoA;
        int valB = (objetoB == null) ? Integer.MAX_VALUE : (Integer) objetoB;
        // Comparando os tamanhos dos discos
        if (valA < valB) {
            // Se o disco A é menor que o disco B precisamos mover A para B
            // Primeiro, devolvemos o B para o lugar dele (se ele existia), pois o disco não
            // se moveu
            if (objetoB != null)
                pilhaB.push(objetoB);
            // Segundo, colocar o A no topo da pilha B
            pilhaB.push(objetoA);
            // Aqui estou atualizando o frame da tela com a nova configuração
            painel.repaint();
        } else {
            // Se o disco B é menor que o A, devemos mover B para A
            // Primeiro, devolvemos para o lugar onde estava (se existia)
            if (objetoA != null)
                pilhaA.push(objetoA);
            // Segundo, colocamos B no topo da pilha A
            pilhaA.push(objetoB);
            // Atualizando o frame da tela principal de novo.
            painel.repaint();
        }
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

        // Método para resetar e colocar os discos na primeira torre
        public void inicializarTorres(int n) {
            totalDiscos = n;
            limpar(); // garantindo que tudo está vazio
            // loop do maior (n) até 1: empilha os discos na torre de origem
            for (int i = n; i >= 1; i--) {
                // o comando Integer.valueOf converte int para Objeto Integer
                torres[0].push(Integer.valueOf(i));
            }
            // Redesenhando a tela
            repaint();
        }

        // Criando um método para esvaziar todas as pilhas
        public void limpar() {
            for (int i = 0; i < 3; i++) {
                // Cria novas instâncias de pilha, jogando fora as antigas
                torres[i] = new Pilha();
            }
            repaint();
        }

        // Método público para acessar uma pilha específica (usei no método
        // moverDiscoLegal)
        public Pilha getPilha(int indice) {
            return torres[indice];
        }

        // Aqui vamos usar o override do JPanel onde é desenhado a animação
        @Override
        protected void paintComponent(Graphics g) {
            // Chamando método da superclasse para limpar a tela e pintar o fundo
            super.paintComponent(g);
            // Converti o objeto g para Graphics2d (tem mais recursos)
            Graphics2D g2d = (Graphics2D) g;
            // Dica que vi enquanto pesquisava essa linha serve para deixar as linhas e
            // círculos suaves sem estar serrilhado
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            // pegando dimensões atuais do painel
            int larguraPainel = getWidth();
            int alturaPainel = getHeight();
            // Dividi a largura por três para saber a posição de cada pino
            int larguraBase = larguraPainel / 3;
            // Definindo a posição Y da base das torres
            int centroPinoY = alturaPainel - 50;
            // Definindo a altura da haste vertical
            int alturaHaste = 300;
            // Nome dos pinos para exibir na tela
            String[] nomes = { "1 - Origem", "2 - Auxiliar", "3 - Destino" };
            g2d.setColor(Color.DARK_GRAY); // Cinza escuro para a haste das torres
            // loop para desenhar as 3 torres
            for (int i = 0; i < 3; i++) {
                // Calculando centro X da torre iterada
                int centroX = (i * larguraBase) + (larguraBase / 2);
                // Configurando a espessura da linha para 5 px;
                g2d.setStroke(new BasicStroke(5));
                // Desenhando a haste
                g2d.drawLine(centroX, centroPinoY, centroX, centroPinoY - alturaHaste);
                // Desenhando a base retangular
                g2d.fillRect(centroX - 80, centroPinoY, 160, 10);
                // Configurando a fonte e cor para escrever o nome da torre
                g2d.setColor(Color.BLACK);
                g2d.setFont(new Font("Arial", Font.BOLD, 14));
                g2d.drawString(nomes[i], centroX - 30, centroPinoY + 30);
                g2d.setColor(Color.DARK_GRAY);
                // Desenhando os discos
                Pilha p = torres[i];
                // Encontrei um problema ao acessar os itenda da pilha
                // Agora precisamos desenhar todos os discos mas a pilha só mostra o topo
                // Para resolver sem mudar a estrutura da classe Pilha acessei pela classe lista
                // Para iterar sobre todos os elementos sem precisar remover
                Lista listaInterna = p.lista;
                // Iterando sobre a lista que acabei de criar para desenhar todos os discos
                for (int c = 0; c < listaInterna.tamanho(); c++) {
                    // Pegando o objeto na posição C (de Caio hehe) da lista
                    Object obj = listaInterna.pega(c);
                    // Essa linha não é necessária implementei só por boas práticas
                    if (obj instanceof Integer) {
                        // Converte Object para Int
                        int tamanhoDisco = (Integer) obj;
                        // Cálculos geométricos para desenhar o disco agora
                        // Largura aumenta conforme o número do disco
                        int larguraDisco = 40 + (tamanhoDisco * 20);
                        int alturaDisco = 20;
                        // Centralizando disco no pino
                        int xDisco = centroX - (larguraDisco / 2);
                        // Calcula o Y empilhado um sobre o outro
                        int yDisco = centroPinoY - ((c + 1) * alturaDisco);
                        // definindo a cor baseando no tamanho do disco para diferenciar
                        g2d.setColor(coresDiscos[(tamanhoDisco - 1) % coresDiscos.length]);
                        // Desenha retângulo preenchido com bordas arredondadas
                        g2d.fillRoundRect(xDisco, yDisco, larguraDisco, alturaDisco, 5, 5);
                        // Desenhando borda preta em volta do disco (ficou mais bonito nos testes que
                        // fiz)
                        g2d.setColor(Color.BLACK);
                        g2d.setStroke(new BasicStroke(1));
                        g2d.drawRoundRect(xDisco, yDisco, larguraDisco, alturaDisco, 5, 5);
                    }
                }
                // Reseta as cores para a próxima iteração do loop das torres
                g2d.setColor(Color.DARK_GRAY);
            }
        }
    }
}
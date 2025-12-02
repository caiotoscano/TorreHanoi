public class Lista {
    private Object[] dados = new Object[100];
    private int total = 0;
    StringBuilder builder;

    public void adiciona(Object elem) {
        liberaEspaco();
        this.dados[this.total] = elem;
        this.total++;
    }

    public String toString() {
        if (this.total == 0) {
            return "[]";
        }
        builder = new StringBuilder();
        builder.append("[");
        for (int i = 0; i < this.total - 1; i++) {
            builder.append(this.dados[i]);
            builder.append(", ");
        }
        builder.append(this.dados[this.total - 1]);
        builder.append("]");
        return builder.toString();
    }

    private void liberaEspaco() {
        if (total == dados.length) {
            Object[] novaArray = new Object[this.dados.length * 2];
            for (int i = 0; i < dados.length; i++) {
                novaArray[i] = this.dados[i];
            }
            dados = novaArray;
        }
    }

    public Object pega(int posicao) {
        if (!posicaoOcupada(posicao)) {
            throw new IllegalArgumentException("** Posição inválida ***");
        }
        return dados[posicao];
    }

    public boolean vazia() {
        return this.total == 0;
    }

    private boolean posicaoValidaParaAdicionar(int posicao) {
        return posicao >= 0 && posicao <= total;
    }

    public void adiciona(int posicao, Object elem) {
        liberaEspaco();
        if (total >= dados.length) {
            throw new IllegalStateException("Lista cheia");
        }
        if (!posicaoValidaParaAdicionar(posicao)) {
            throw new IllegalArgumentException("Posição inválida");
        }

        for (int i = total - 1; i >= posicao; i--) {
            this.dados[i + 1] = this.dados[i];
        }

        this.dados[posicao] = elem;
        total++;
    }

    private boolean posicaoOcupada(int posicao) {
        return posicao >= 0 && posicao < total;
    }

    public void remove(int posicao) {
        if (!posicaoOcupada(posicao)) {
            throw new IllegalArgumentException("Posição inválida");
        }
        for (int i = posicao; i < total - 1; i++) {
            this.dados[i] = this.dados[i + 1];
        }
        this.dados[total - 1] = null;
        total--;
    }

    public int tamanho() {
        return this.total;
    }

    public boolean contem(String dados) {
        this.toString();
        return builder.indexOf(dados.toString()) >= 0 ? true : false; // ou remover "? true : false" isso
    }

}
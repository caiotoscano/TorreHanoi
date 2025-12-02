public class Pilha {
    Lista lista = new Lista();
    
    public void push(Object objeto) {
        lista.adiciona(objeto);
    }
    
    public boolean pEmpty() {
        return lista.vazia();
    }
    
    public Object pop() {
        if(!pEmpty()) {
        Object objeto = lista.pega(lista.tamanho()-1);
        lista.remove(lista.tamanho()-1);
        return objeto;
        }
        return null;
    }
}

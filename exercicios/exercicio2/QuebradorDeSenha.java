package exercicios.exercicio2;

public class QuebradorDeSenha extends Thread {
    private int id;
    private long inicio;
    private long fim;
    private String senha_a_encontrar;

    public QuebradorDeSenha(int id, long inicio, long fim, String senha_a_encontrar) {
        this.id = id;
        this.inicio = inicio;
        this.fim = fim;
        this.senha_a_encontrar = senha_a_encontrar;
    }

    public void run() {
        for(long i = inicio; (i < this.fim) && !Main.encontrada; i++) {
            String tentativa = String.format("%010d", i);
            if(tentativa.equals(senha_a_encontrar)) {
                Main.encontrada = true;
                System.out.println("Senha econtrada: " + tentativa);
                System.out.println("Thread Encontradora: " + id);
            }
        }
    }
}

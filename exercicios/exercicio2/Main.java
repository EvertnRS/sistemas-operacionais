package exercicios.exercicio2;

public class Main {
    private final static String senha = "0000000089"; 
    private final static int num_threads = 20;
    private final static long max = (long) Math.pow(10, 10);
    static volatile boolean encontrada = false;

    public static void main(String[] args) {
        long intervalo = max/num_threads;

        for(int i = 0; i < num_threads; i++) {
            long inicio = i * intervalo;
            long fim = (i == num_threads - 1) ? max : inicio + intervalo;

            new Thread(new QuebradorDeSenha(i, inicio, fim, senha)).start();;
        } 
    }
    
}

package exercicios.exercicio4;

public class PhilosopherDinner {
    public static final int NUM_FILOSOFOS = 5;
    public static Estado[] estados = new Estado[NUM_FILOSOFOS];
    public static final Object monitor = new Object();

    public static void main(String[] args) {
        Garfo[] garfos = new Garfo[NUM_FILOSOFOS];
        Filosofo[] filosofos = new Filosofo[NUM_FILOSOFOS];

        for (int i = 0; i < NUM_FILOSOFOS; i++) {
            garfos[i] = new Garfo(i);
            estados[i] = Estado.PENSANDO;
        }

        for (int i = 0; i < NUM_FILOSOFOS; i++) {
            Garfo garfoEsquerdo = garfos[i];
            Garfo garfoDireito = garfos[(i + 1) % NUM_FILOSOFOS];
            filosofos[i] = new Filosofo(i, garfoEsquerdo, garfoDireito);
            filosofos[i].start();
        }
    }
}
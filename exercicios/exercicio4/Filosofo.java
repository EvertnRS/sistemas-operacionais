package exercicios.exercicio4;

public class Filosofo extends Thread {
    private final int id;
    private final Garfo esquerdo;
    private final Garfo direito;

    public Filosofo(int id, Garfo esquerdo, Garfo direito) {
        this.id = id;
        this.esquerdo = esquerdo;
        this.direito = direito;
    }

    private void comer() throws InterruptedException {
        System.out.println("Filósofo " + id + " está comendo!");
        Thread.sleep((long) (Math.random() * 1000));
    }

    private int esquerda(int i) {
        return (i + PhilosopherDinner.NUM_FILOSOFOS - 1) % PhilosopherDinner.NUM_FILOSOFOS;
    }

    private int direita(int i) {
        return (i + 1) % PhilosopherDinner.NUM_FILOSOFOS;
    }

    private void tentarComer() throws InterruptedException {
        synchronized (PhilosopherDinner.monitor) {
            PhilosopherDinner.estados[id] = Estado.FAMINTO;
            while (PhilosopherDinner.estados[esquerda(id)] == Estado.COMENDO ||
                    PhilosopherDinner.estados[direita(id)] == Estado.COMENDO) {
                PhilosopherDinner.monitor.wait();
            }

            PhilosopherDinner.estados[id] = Estado.COMENDO;
            System.out.println("Filósofo " + id + " pegou os garfos " + esquerdo.getId() + " e " + direito.getId());
        }
    }

    private void terminarDeComer() {
        synchronized (PhilosopherDinner.monitor) {
            PhilosopherDinner.estados[id] = Estado.PENSANDO;
            System.out.println("Filósofo " + id + " largou os garfos " + esquerdo.getId() + " e " + direito.getId());
            PhilosopherDinner.monitor.notifyAll();
        }
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < 3; i++) {
                tentarComer();
                comer();
                terminarDeComer();
                Thread.sleep((long) (Math.random() * 1000));
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

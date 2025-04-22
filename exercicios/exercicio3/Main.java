package exercicios.exercicio3;

public class Main {
    private static final int num_pontos = 3;
    private static final int num_equipes = 2;
    private static final int num_corredores_por_equipe = 3;
    private static int prontosParaLargar = 0;
    private static boolean podeComecar = false;

    public static int getNumPontos() {
        return num_pontos;
    }
    
    public static int getNumEquipes() {
        return num_equipes;
    }

    public static int getNumCorredoresPorEquipe() {
        return num_corredores_por_equipe;
    }

    public static void setProntosParaLargar(int i) {
        prontosParaLargar += i;
    }

    public static void setPodeComecar(boolean x) {
        podeComecar = x;
    }

    public static boolean getPodeComecar() {
        return podeComecar;
    }

    public static void main(String[] args) {
        Object largadaLock = new Object();
        Object[] barreiras = new Object[num_equipes];
        for(int i = 0; i < num_equipes; i++) {
            barreiras[i] = new Object();
            for(int j = 0; j < num_corredores_por_equipe; j++) {
                new Corredor(i, j + 1, largadaLock, barreiras[i]).start();;
            }
        }

        while (true) {
            synchronized (largadaLock) {
                if(prontosParaLargar == num_corredores_por_equipe * num_equipes) {
                    podeComecar = true;
                    largadaLock.notifyAll();
                    break;
                }
            }
        }
    }
}

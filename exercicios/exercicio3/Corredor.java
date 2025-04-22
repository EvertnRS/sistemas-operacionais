package exercicios.exercicio3;

import java.util.Random;

public class Corredor extends Thread {
    private int equipe;
    private int numero;
    private Object largadaLock;
    private static int[][] pontoPorEquipe = new int[Main.getNumEquipes()][Main.getNumCorredoresPorEquipe()];
    private Object barreira;
    
    public Corredor(int equipe, int numero, Object largadaLock, Object barreira) {
        this.equipe = equipe;
        this.numero = numero;
        this.largadaLock = largadaLock;
        this.barreira = barreira;
    }

    public void run() {
        synchronized(largadaLock) {
            Main.setProntosParaLargar(1);
            while(!Main.getPodeComecar()) {
                try {
                    largadaLock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        System.out.println("Equipe " + this.equipe + " - Corredor " + this.numero + " começou!");

        for (int ponto = 0; ponto < Main.getNumPontos(); ponto++) {
            try {
                char letra = (char) ('A' + ponto);
                correrAtePonto(letra);
                esperarMembros(ponto);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Equipe " + equipe + " - Corredor " + numero + " terminou!");
    }

    public void correrAtePonto(char ponto) throws InterruptedException {
        int tempo = 2000 + new Random().nextInt(3001); //simula tempo de corrida ate o ponto
        Thread.sleep(tempo);
        System.out.println("Equipe " + this.equipe + " - Corredor " + this.numero + " chegou ao ponto " + ponto);
    }

    public void esperarMembros(int ponto) throws InterruptedException {
        synchronized(barreira) {
            pontoPorEquipe[equipe][ponto]++;
            if(pontoPorEquipe[equipe][ponto] < Main.getNumCorredoresPorEquipe()) {
                barreira.wait();
            } else {
                barreira.notifyAll();
                System.out.println("Partindo para o proximo ponto");
            }
        }
    }

}

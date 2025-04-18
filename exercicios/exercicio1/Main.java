package exercicios.exercicio1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    private static int numeroPalavras = 0;
    static final Map<String, Integer> palavrasIguais = new HashMap<>();


    public static void main(String[] args) {
        long inicio = System.currentTimeMillis();
        String filePath = "D:\\projetos\\jorge\\exercicios\\exercicio1\\teste.txt";
        ArrayList<String> content = new ArrayList<>();
        ArrayList<Thread> threads = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.add(line);
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo: " + e.getMessage());
        }

        for (String line : content) {
            Thread contadorPalavras = new Thread(() -> {
                String[] words = line.trim().split("\s+");
                synchronized (Main.class) {
                    numeroPalavras += words.length;
                }
            });

            Thread contadorPalavrasIguais = new Thread(() -> {
                String[] words = line.trim().toLowerCase().split("\\s+");

                for (String word : words) {
                    synchronized (palavrasIguais) {
                        palavrasIguais.put(word, palavrasIguais.getOrDefault(word, 0) + 1);
                    }
                }
            });
            contadorPalavras.start();
            threads.add(contadorPalavras);
            contadorPalavrasIguais.start();
            threads.add(contadorPalavrasIguais);
        }

        for (Thread t : threads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                System.err.println("Thread interrompida: " + e.getMessage());
            }
        }

        System.out.println("Total de palavras: " + numeroPalavras);


        for (Map.Entry<String, Integer> entry : palavrasIguais.entrySet()) {
            System.out.println("Palavra: " + entry.getKey() + " - Contagem: " + entry.getValue());

        }

        // Encontra todas as palavras mais frequentes
        List<String> maisFrequentes = new ArrayList<>();
        int maiorContagem = 0;

        for (Map.Entry<String, Integer> entry : palavrasIguais.entrySet()) {
            int count = entry.getValue();
            if (count > maiorContagem) {
                maisFrequentes.clear();
                maisFrequentes.add(entry.getKey());
                maiorContagem = count;
            } else if (count == maiorContagem) {
                maisFrequentes.add(entry.getKey());
            }
        }

        // Mostra o resultado
        System.out.println("\nPalavra(s) mais frequente(s):");
        for (String palavra : maisFrequentes) {
            System.out.println("'" + palavra + "' apareceu " + maiorContagem + " vezes.");
        }

        long fim = System.currentTimeMillis();
        System.out.println("Tempo total: " + (fim - inicio)/1000.0 + "segundos");
        System.out.println("Total de Threads: " + threads.size());
    }
}


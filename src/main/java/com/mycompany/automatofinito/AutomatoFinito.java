/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.automatofinito;

import com.mycompany.automatofinito.classes.AFD;
import com.mycompany.automatofinito.classes.Transicao;
import com.mycompany.automatofinito.serializadorCSV.FilePersistence;
import com.mycompany.automatofinito.serializadorCSV.SerializadorCSVAFD;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author igorxf
 */
public class AutomatoFinito {

    public static void main(String[] args) {
        try {
            Scanner entrada = new Scanner(System.in);
            
            // Menu para escolher o AFD
            System.out.println("Escolha o AFD que deseja utilizar:");
            System.out.println(" 1 - L = {w | w possua aa ou bb como sub palavra}");
            System.out.println(" 2 - L = {w | entre dois a's de w, há quantidade par de b's ");
            System.out.println(" 3 - L = {w | w tenha aa ou aba, como subpalavra } ");
            System.out.println(" 4 - L = {w | entre dois b's de w, há quantidade impar de a's }");
            System.out.print("Digite sua opção: ");
            int opcao = entrada.nextInt();
            entrada.nextLine(); // Limpar o buffer
            
            String arquivoCSV;
            switch(opcao) {
                case 1:
                    arquivoCSV = "afd1.csv";
                    break;
                case 2:
                    arquivoCSV = "afd2.csv";
                    break;
                case 3:
                    arquivoCSV = "afd3.csv";
                    break;
                case 4:
                    arquivoCSV = "afd4.csv";
                    break;
                default:
                    System.out.println("Opção inválida. Usando AFD padrão (afd1.csv).");
                    arquivoCSV = "afd1.csv";
            }
            
            System.out.println("\nCarregando AFD do arquivo: " + arquivoCSV);
            
            FilePersistence filePersistence = new FilePersistence();
            String csv = filePersistence.loadFromFile(arquivoCSV);

            SerializadorCSVAFD serializador = new SerializadorCSVAFD();
            List<Transicao> transicoes = serializador.fromCSV(csv);
            List<Integer> finais = serializador.estadosFinais;

            AFD afd = new AFD(0, finais, transicoes);

            while (true) {
                System.out.print("\nDigite uma palavra para testar (ou 'sair' para encerrar): ");
                String palavra = entrada.nextLine();

                if (palavra.equalsIgnoreCase("sair")) break;

                if (afd.verifica(palavra)) {
                    System.out.println("Palavra ACEITA pelo AFD!");
                } else {
                    System.out.println("Palavra REJEITADA pelo AFD.");
                }
            }
            
            System.out.println("Programa encerrado.");

        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
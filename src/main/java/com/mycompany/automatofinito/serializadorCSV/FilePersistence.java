package com.mycompany.automatofinito.serializadorCSV;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author igorxf
 */
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Scanner;

public class FilePersistence {

    public void saveToFile(String texto, String filePath) throws Exception {
        FileWriter arq = new FileWriter(filePath);
        PrintWriter gravarArq = new PrintWriter(arq);
        gravarArq.print(texto);
        arq.close();
    }

    public String loadFromFile(String filePath) throws Exception {
        StringBuilder conteudoLido = new StringBuilder();
        File file = new File(filePath);
        Scanner scanner = new Scanner(file);

        scanner.useDelimiter("\\Z"); // Lê até o final do arquivo
        while (scanner.hasNext()) {
            conteudoLido.append(scanner.next());
        }

        return conteudoLido.toString();
    }
}


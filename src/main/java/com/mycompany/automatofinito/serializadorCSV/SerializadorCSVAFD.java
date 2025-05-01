package com.mycompany.automatofinito.serializadorCSV;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author igorxf
 */
import com.mycompany.automatofinito.classes.Transicao;
import java.util.ArrayList;
import java.util.List;

public class SerializadorCSVAFD {

    public List<Integer> estadosFinais = new ArrayList<>();

    public List<Transicao> fromCSV(String data) {
        List<Transicao> transicoes = new ArrayList<>();
        String[] linhas = data.split("\n");

        for (String linha : linhas) {
            linha = linha.trim();
            if (linha.startsWith("finais;")) {
                String[] finais = linha.substring(7).split(";");
                for (String estado : finais) {
                    estadosFinais.add(Integer.parseInt(estado));
                }
            } else {
                String[] partes = linha.split(";");
                if (partes.length == 3) {
                    int origem = Integer.parseInt(partes[0]);
                    char simbolo = partes[1].charAt(0);
                    int destino = Integer.parseInt(partes[2]);
                    transicoes.add(new Transicao(origem, simbolo, destino));
                }
            }
        }

        return transicoes;
    }
}


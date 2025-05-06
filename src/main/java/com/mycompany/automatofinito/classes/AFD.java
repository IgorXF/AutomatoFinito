/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.automatofinito.classes;

/**
 *
 * @author igorxf
 */
import java.util.List;

public class AFD {
    private int estadoInicial;
    private List<Integer> estadosFinais;
    private List<Transicao> transicoes;

    public AFD(int estadoInicial, List<Integer> estadosFinais, List<Transicao> transicoes) {
        this.estadoInicial = estadoInicial;
        this.estadosFinais = estadosFinais;
        this.transicoes = transicoes;
    }

    public boolean verifica(String palavra) {
        int estadoAtual = estadoInicial;

        for (char simbolo : palavra.toCharArray()) {
            boolean transicaoEncontrada = false;

            for (Transicao t : transicoes) {
                if (t.origem == estadoAtual && t.simbolo == simbolo) {
                    estadoAtual = t.destino;
                    transicaoEncontrada = true;
                    break;
                }
            }

            if (!transicaoEncontrada) {
                return false;
            }
        }

        // Verificando se o estado final é válido após processar toda a palavra
        return estadosFinais.contains(estadoAtual);
    }

    // Método para retornar o erro na palavra (índice do erro)
    public int getIndiceErro(String palavra) {
        int estadoAtual = estadoInicial;

        for (int i = 0; i < palavra.length(); i++) {
            char simbolo = palavra.charAt(i);
            boolean transicaoEncontrada = false;

            for (Transicao t : transicoes) {
                if (t.origem == estadoAtual && t.simbolo == simbolo) {
                    estadoAtual = t.destino;
                    transicaoEncontrada = true;
                    break;
                }
            }

            if (!transicaoEncontrada) {
                return i; // Retorna o índice do erro na palavra
            }
        }

        // Se não for final ao término da palavra, retorna o estado final não atingido
        if (!estadosFinais.contains(estadoAtual)) {
            return palavra.length(); // Retorna o comprimento da palavra se não terminou em estado final
        }

        return -1; // Se a palavra foi completamente aceita
    }
}

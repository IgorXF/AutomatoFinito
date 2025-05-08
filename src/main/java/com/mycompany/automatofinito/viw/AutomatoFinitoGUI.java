package com.mycompany.automatofinito.viw;

import com.mycompany.automatofinito.classes.AFD;
import com.mycompany.automatofinito.classes.Transicao;
import com.mycompany.automatofinito.serializadorCSV.FilePersistence;
import com.mycompany.automatofinito.serializadorCSV.SerializadorCSVAFD;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class AutomatoFinitoGUI {

    public void createAndShowGUI() {
        // Criando a janela
        JFrame frame = new JFrame("Autômato Finito Determinístico");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 500); // um pouco maior
        frame.setLocationRelativeTo(null); // Centraliza

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        String[] opcoes = {
            "1 - L = {w | w possua aa ou bb como sub palavra}",
            "2 - L = {w | entre dois a's de w, há quantidade par de b's}",
            "3 - L = {w | w tenha aa ou aba, como subpalavra}",
            "4 - L = {w | entre dois b's de w, há quantidade impar de a's}",
        };
        JComboBox<String> comboBox = new JComboBox<>(opcoes);

        JTextField inputField = new JTextField();
        inputField.setFont(new Font("Arial", Font.PLAIN, 20));

        JButton verificarButton = new JButton("Verificar Palavra");
        verificarButton.setEnabled(true);

        // JTextArea com scroll para mostrar resultado
        JTextArea resultadoArea = new JTextArea(5, 50);
        resultadoArea.setFont(new Font("Monospaced", Font.PLAIN, 18));
        resultadoArea.setEditable(false);
        resultadoArea.setLineWrap(false); // desativa quebra de linha
        JScrollPane scrollPane = new JScrollPane(
            resultadoArea,
            JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
            JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED
        );

        panel.add(new JLabel("Escolha o AFD:"));
        panel.add(comboBox);
        panel.add(new JLabel("Digite a palavra:"));
        panel.add(inputField);
        panel.add(verificarButton);
        panel.add(scrollPane);

        frame.add(panel);
        frame.setVisible(true);

        comboBox.addActionListener(e -> resultadoArea.setText(""));

        inputField.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) {
                
                resultadoArea.setText("");
            }

            public void removeUpdate(DocumentEvent e) {
                resultadoArea.setText("");
            }

            public void changedUpdate(DocumentEvent e) {     
                resultadoArea.setText("");
            }
        });

        verificarButton.addActionListener((ActionEvent e) -> {
            int index = comboBox.getSelectedIndex() + 1;
            String arquivoCSV = "afd" + index + ".csv";

            try {
                FilePersistence filePersistence = new FilePersistence();
                String csv = filePersistence.loadFromFile(arquivoCSV);
                SerializadorCSVAFD serializador = new SerializadorCSVAFD();
                List<Transicao> transicoes = serializador.fromCSV(csv);
                List<Integer> finais = serializador.estadosFinais; /// herdar do serializador e modificar la

                AFD afd = new AFD(0, finais, transicoes);
                String palavra = inputField.getText();

                int erro = afd.getIndiceErro(palavra);
                if (erro == -1) {
                    resultadoArea.setForeground(Color.GREEN.darker());
                    resultadoArea.setText("Palavra ACEITA!");
                } else if (erro == palavra.length()) {
                    resultadoArea.setForeground(Color.RED);
                    resultadoArea.setText("Palavra REJEITADA! A palavra terminou sem atingir a condição da linguagem.");
                } else {
                    // Erro no meio da palavra
                    StringBuilder linhaPalavra = new StringBuilder();
                    for (int i = 0; i < palavra.length(); i++) {
                        linhaPalavra.append(palavra.charAt(i));
                    }

                    StringBuilder linhaSeta = new StringBuilder();
                    for (int i = 0; i < erro; i++) {
                        linhaSeta.append(" ");
                    }
                    linhaSeta.append("↑ erro");

                    resultadoArea.setForeground(Color.RED);
                    resultadoArea.setText("Palavra: " + linhaPalavra + "\n         " + linhaSeta);
                }

            } catch (Exception ex) {
                resultadoArea.setForeground(Color.RED);
                resultadoArea.setText("Erro: " + ex.getMessage());
                ex.printStackTrace();
            }
        });
    }
}

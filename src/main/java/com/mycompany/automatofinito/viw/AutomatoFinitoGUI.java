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
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null); // Centraliza a tela

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
        verificarButton.setEnabled(false); // Inicialmente desabilitado

        JLabel resultadoLabel = new JLabel("Digite uma palavra para verificar.");
        resultadoLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        resultadoLabel.setVerticalAlignment(SwingConstants.TOP);

        panel.add(new JLabel("Escolha o AFD:"));
        panel.add(comboBox);
        panel.add(new JLabel("Digite a palavra:"));
        panel.add(inputField);
        panel.add(verificarButton);
        panel.add(resultadoLabel);

        frame.add(panel);
        frame.setVisible(true);

        // Função auxiliar para atualizar o botão com base no texto
        Runnable atualizarEstadoDoBotao = () -> {
            String texto = inputField.getText();
            boolean habilitar = texto != null && !texto.trim().isEmpty();
            verificarButton.setEnabled(habilitar);
            if (!habilitar) {
                resultadoLabel.setText(""); // Apaga a mensagem se o campo estiver vazio
            }
        };

        // Limpa mensagem ao alterar AFD
        comboBox.addActionListener(e -> {
            resultadoLabel.setText("");
        });

        // Monitorar alterações no campo de texto
        inputField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                atualizarEstadoDoBotao.run();
                resultadoLabel.setText("");
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                atualizarEstadoDoBotao.run();
                resultadoLabel.setText("");
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                atualizarEstadoDoBotao.run();
                resultadoLabel.setText("");
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
                List<Integer> finais = serializador.estadosFinais;

                AFD afd = new AFD(0, finais, transicoes);
                String palavra = inputField.getText();

                int erro = afd.getIndiceErro(palavra);
                if (erro == -1) {
                    resultadoLabel.setText("<html><b>Palavra ACEITA!</b></html>");
                    resultadoLabel.setForeground(Color.GREEN);
                    resultadoLabel.setFont(new Font("Arial", Font.BOLD, 20));
                } else if (erro == palavra.length()) {
                    resultadoLabel.setText("<html><b>Palavra REJEITADA! A palavra não atingiu as condições</b></html>");
                    resultadoLabel.setForeground(Color.RED);
                    resultadoLabel.setFont(new Font("Arial", Font.PLAIN, 18));
                } else {
                    // Mostrando erro com alinhamento correto usando <pre>
                    StringBuilder palavraFormatada = new StringBuilder();
                    for (int i = 0; i < palavra.length(); i++) {
                        if (i == erro) {
                            palavraFormatada.append("<b><font color='red'>").append(palavra.charAt(i)).append("</font></b>");
                        } else {
                            palavraFormatada.append(palavra.charAt(i));
                        }
                    }

                    StringBuilder setaErro = new StringBuilder();
                    for (int i = 0; i < erro; i++) {
                        setaErro.append(" ");
                    }
                    setaErro.append("↑ erro");

                    StringBuilder html = new StringBuilder("<html>");
                    html.append("<pre style='font-family: monospace;'>");
                    html.append("Palavra: ").append(palavraFormatada).append("\n");
                    html.append("         ").append(setaErro);
                    html.append("</pre>");
                    html.append("</html>");

                    resultadoLabel.setText(html.toString());
                    resultadoLabel.setForeground(Color.RED);
                    resultadoLabel.setFont(new Font("Arial", Font.PLAIN, 18));
                }

            } catch (Exception ex) {
                resultadoLabel.setText("Erro: " + ex.getMessage());
                resultadoLabel.setForeground(Color.RED);
                ex.printStackTrace();
            }
        });
    }
}

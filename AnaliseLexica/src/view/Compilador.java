/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import entidades.Arquivo;
import gals.LexicalError;
import gals.Lexico;
import gals.SemanticError;
import gals.Sintatico;
import gals.SyntaticError;
import gals.Token;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author BOSS
 */
public class Compilador extends javax.swing.JFrame {

    Lexico analisadorLexico;

    /**
     * Creates new form Compilador
     */
    public Compilador() {
        initComponents();


    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuBar2 = new javax.swing.JMenuBar();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();
        jMenuItem5 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jMenu1.setText("Arquivo");

        jMenuItem2.setText("Salvar");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalvarArquivo(evt);
            }
        });
        jMenu1.add(jMenuItem2);
        jMenuItem2.getAccessibleContext().setAccessibleParent(jMenu1);

        jMenuItem1.setText("Abrir");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AbrirArquivo(evt);
            }
        });
        jMenu1.add(jMenuItem1);
        jMenuItem1.getAccessibleContext().setAccessibleParent(jMenu1);

        jMenuItem3.setText("Sair");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Sair(evt);
            }
        });
        jMenu1.add(jMenuItem3);
        jMenuItem3.getAccessibleContext().setAccessibleParent(jMenu1);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Léxico");

        jMenuItem4.setText("Analisar");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                analiseLexica(evt);
            }
        });
        jMenu2.add(jMenuItem4);
        jMenuItem4.getAccessibleContext().setAccessibleParent(jMenu2);

        jMenuBar1.add(jMenu2);

        jMenu4.setText("Sintático");

        jMenuItem5.setText("Analisar");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                analiseSintatica(evt);
            }
        });
        jMenu4.add(jMenuItem5);

        jMenuBar1.add(jMenu4);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 668, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(57, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(44, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void AbrirArquivo(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AbrirArquivo

        JFileChooser chooser = new JFileChooser();
        String caminho = "";
        int retorno = chooser.showOpenDialog(null);
        if (retorno == JFileChooser.APPROVE_OPTION) {
            caminho = chooser.getSelectedFile().getAbsolutePath();  // o getSelectedFile pega o arquivo e o getAbsolutePath retorna uma string contendo o endereço.
            jTextArea1.setText(new Arquivo().abrirArquivo(caminho));
        }
    }//GEN-LAST:event_AbrirArquivo

    private void Sair(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Sair
        System.exit(0);
    }//GEN-LAST:event_Sair

    private void SalvarArquivo(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalvarArquivo

        JFileChooser chooser = new JFileChooser();
        String caminho = "";
        FileWriter file = null;
        int retorno = chooser.showSaveDialog(null); // showSaveDialog retorna um inteiro , e ele ira determinar que o chooser será para salvar.

        if (retorno == JFileChooser.APPROVE_OPTION) {
            caminho = chooser.getSelectedFile().getAbsolutePath();  // o getSelectedFile pega o arquivo e o getAbsolutePath retorna uma string contendo o endereço.
            try {
                if (!(caminho.endsWith(".txt") || caminho.endsWith(".lsi"))) {
                    file = new FileWriter(caminho + ".txt");
                } else {
                    file = new FileWriter(caminho);
                }

            } catch (Exception e) {
                System.out.println("Erro ao salvar arquivo. " + e.getMessage());
            }

            new Arquivo(jTextArea1.getText()).gravarArquivo(file);
        }
    }//GEN-LAST:event_SalvarArquivo

    private void analiseLexica(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_analiseLexica

        if (jTextArea1.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "É necessário informar pelo menos um caractere para ser analisado!");
            return;
        }

        analisadorLexico = new Lexico(new StringReader(jTextArea1.getText()));

        try {
            Token t = null;
            while ((t = analisadorLexico.nextToken()) != null) {
                System.out.println(t.getLexeme());
            }
            JOptionPane.showMessageDialog(null, "Análise Léxica efetuada com sucesso");
        } catch (LexicalError e) {
            JOptionPane.showMessageDialog(null, "Erro: " + e.getMessage() + ",  Posição: " + e.getPosition());
            System.out.println("Erro: " + e.getMessage() + "Posição: " + e.getPosition());
        }





    }//GEN-LAST:event_analiseLexica

    private void analiseSintatica(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_analiseSintatica
        if (analisadorLexico == null) {
            JOptionPane.showMessageDialog(null, "É necessário realizar primeiramente a análise léxica.");
            return;
        }
        Sintatico analisadorSintatico = new Sintatico();
        try {
            analisadorSintatico.parse(analisadorLexico, null);
            JOptionPane.showMessageDialog(null, "Análise Sintática efetuada com sucesso");
        } catch (LexicalError ex) {
            JOptionPane.showMessageDialog(null, "Erro: " + ex.getMessage() + ",  Posição: " + ex.getPosition());
        } catch (SyntaticError ex) {
            JOptionPane.showMessageDialog(null, "Erro: " + ex.getMessage() + ",  Posição: " + ex.getPosition());
            Logger.getLogger(Compilador.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SemanticError ex) {
            JOptionPane.showMessageDialog(null, "Erro: " + ex.getMessage() + ",  Posição: " + ex.getPosition());

        }


    }//GEN-LAST:event_analiseSintatica

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Compilador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Compilador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Compilador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Compilador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Compilador().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuBar jMenuBar2;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    // End of variables declaration//GEN-END:variables
}

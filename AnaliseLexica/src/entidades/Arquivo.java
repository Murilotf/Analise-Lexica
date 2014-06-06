/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 *
 * @author BOSS
 */
public class Arquivo {

    private String texto;

    public Arquivo(String texto) {
        this.texto = texto;
    }

    public String getTxt() {
        return texto;
    }

    public void setTxt(String texto) {
        this.texto = texto;
    }

    public void gravarArquivo(FileWriter arq) {

         PrintWriter gravarArq = null;
                
        try {
            
            
            gravarArq = new PrintWriter(new BufferedWriter(arq));
            gravarArq.println(texto);
            gravarArq.close();
        } finally{
            gravarArq.close();
        }
    }

    public String abrirArquivo(String caminho) {
        String arquivo = null;
        Scanner leitor = null;
        try {
            
            leitor = new Scanner(new File(caminho));
            arquivo = leitor.useDelimiter("\\|\\n").next();           

        } catch (IOException e) {
            System.out.println("erro ao abrir arquivo. Detalhes:" + e.getMessage());
        }finally{
            leitor.close();
        }
        return arquivo;

    }
}

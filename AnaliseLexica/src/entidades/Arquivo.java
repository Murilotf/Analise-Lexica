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

    public Arquivo() {
    }
    
    

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
                
        try (PrintWriter gravarArq = new PrintWriter(new BufferedWriter(arq))) {
            gravarArq.println(texto);
            gravarArq.close();
        }catch(Exception e){
            System.out.println("erro ao salvar o arquivo. Detalhes: "+e.getMessage());
        }
    }

    public String abrirArquivo(String caminho) {
        String arquivo = null;
        try (Scanner leitor = new Scanner(new File(caminho))) {
            arquivo = leitor.useDelimiter("\\|\\n").next();           
            leitor.close();
        } catch (IOException e) {
            System.out.println("erro ao abrir arquivo. Detalhes: " + e.getMessage());
        }
        return arquivo;

    }
}

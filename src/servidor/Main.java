/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import servidor.Servidor;

/**
 *
 * @author Carlos
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static Banca banca = new Banca();

    public static void main(String[] args) {

        ServerSocket ss;
        Socket sc;  
        
        
        try {

            ss = new ServerSocket(6060);

            while (true) {//bucle infinito para que espere conexiones
                sc = ss.accept();//aceptamos la conexion y guardamos el socket

//                //ejemplo de hilo
//                Thread hilo = new Thread(new Servidor(sc));
//                hilo.start();
                //ejemplo de hilo
                new Thread(new Servidor(sc)).start();
            }

        } catch (IOException ex) {

            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);

        }

    }

}

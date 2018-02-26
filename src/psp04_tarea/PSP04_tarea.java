/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package psp04_tarea;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import servidor.servidor;

/**
 *
 * @author Carlos
 */
public class PSP04_tarea {

    /**
     * @param args the command line arguments
     */
    
    
    public static  int saldoBanca = 0;
    
    
    public static void main(String[] args) {
        
        
            ServerSocket ss;
            Socket sc;
            
        try {            
            
            ss = new ServerSocket(6060);
            sc = ss.accept();//aceptamos la conexion y guardamos el socket
            
            //ejemplo de hilo
            Thread hilo = new Thread(new servidor(sc));
            hilo.start();
            
        } catch (IOException ex) {
            
            Logger.getLogger(servidor.class.getName()).log(Level.SEVERE, null, ex);
            
        } 
        
        
        
        
        
        
        
        
        
        
        
        
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
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

        Scanner entrada = new Scanner(System.in);
        System.out.println("Por favor, introduzca el saldo inicial del que dispondrá el servidor:");

        int importe = 0;
        //validamos el input
        boolean valid = false;
        while (!valid) {            
            try {
                importe = Integer.parseInt(entrada.nextLine());
                if (importe >= 0) {
                    valid = true;
                }
            } catch (NumberFormatException e) {
                //error
                System.out.println("No has introducido un valor numerico, por favor vuelve a intentarlo.");
            }
        }
        
        //metemos el dinero en la banca
        banca.meterDinero(importe);

        try {

            ss = new ServerSocket(6060);

            while (true) {//bucle infinito para que espere conexiones
                sc = ss.accept();//aceptamos la conexion y guardamos el socket

                new Thread(new Servidor(sc)).start();
            }

        } catch (IOException ex) {

            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);

        }

    }

}

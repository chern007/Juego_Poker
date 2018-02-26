/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidor;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import psp04_tarea.PSP04_tarea;

/**
 *
 * @author Carlos
 */
public class servidor implements Runnable {

    Socket sc;
    String[] corazonesServer = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "AS"};
    String[] rombosServer = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "AS"};
    String[] trebolesServer = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "AS"};
    String[] picasServer = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "AS"};

    String[][] barajaServer = {corazonesServer, rombosServer, trebolesServer, picasServer};

//    int saldoBanca = 0;
    boolean finJuego = false;

    public servidor(Socket sc) {

        this.sc = sc;

    }

    @Override
    public void run() {

        DataInputStream loQueEntra = null;
        DataOutputStream loQueSale = null;

        String entrada;
        int apuestaCliente;
        int[] carta = new int[2];

        try {

            loQueEntra = new DataInputStream(sc.getInputStream());
            loQueSale = new DataOutputStream(sc.getOutputStream());

            entrada = loQueEntra.readUTF();//R1

            loQueSale.writeUTF("Hola " + entrada + ", bienvenido al juego de cartas.");//W1  

            loQueSale.writeUTF("¿Cuánto dinero quieres apostar?");//W2

            apuestaCliente = loQueEntra.readInt();//R2

            if (apuestaCliente > PSP04_tarea.saldoBanca) {

                //se termina el juego porque no tienme dinero la banca
                loQueSale.writeUTF("No se ha aceptado tu apuesta porque no hay dinero suficiente en la banca.\nPrueba mas tarde.");//W3

            } else {

                loQueSale.writeUTF("Se ha aceptado tu apuesta.");//W3

            }

            while (!finJuego) {

                //entrada = loQueEntra.readUTF();            
                carta[0] = loQueEntra.readInt();//R3
                carta[1] = loQueEntra.readInt();//R4

                int indiceCartaCliente = carta[1];

                int indiceCartaServer = sacaCarta()[1];

                if (indiceCartaServer >= indiceCartaCliente) {

                    loQueSale.writeUTF("Ohh, has perdido la apuesta de " + apuestaCliente);//W3

                } else {

                    loQueSale.writeUTF("Enhorabuena has ganado " + apuestaCliente);//W3
                    PSP04_tarea.saldoBanca -= apuestaCliente;
                }

            }

        } catch (IOException ex) {

            Logger.getLogger(servidor.class.getName()).log(Level.SEVERE, null, ex);

        }
    }

    private int[] sacaCarta() {

        int paloRandom;
        int numeroRandom;
        String carta = "";
        int[] posicion = new int[2];

        while (carta.equals("")) {

            posicion[0] = new Random().nextInt(4);
            posicion[1] = new Random().nextInt(13);

            carta = barajaServer[posicion[0]][posicion[1]];
            barajaServer[posicion[0]][posicion[1]] = "";//lo vaciamos, ya que hemos sacado la carta
        }

        return posicion;
    }

    private String traduceCarta(int[] indicesCarta) {

        String palo = "";
        String numero = "";

        switch (indicesCarta[0]) {

            case 0:

                palo = "Corazones";

                break;

            case 1:

                palo = "Rombos";

                break;

            case 2:

                palo = "Treboles";

                break;

            case 3:

                palo = "Picas";

                break;

            default:
                
                palo = "";

                break;
        }
        
        //----------------------------------------------------------------------
        
                switch (indicesCarta[1]) {

            case 0:

                numero = "2";

                break;

            case 1:

                numero = "3";

                break;

            case 2:

                numero = "4";

                break;

            case 3:

                numero = "5";

                break;

            case 4:

                numero = "6";

                break;
                
            case 5:

                numero = "7";

                break;
                
            case 6:

                numero = "8";

                break;
                
            case 7:

                numero = "9";

                break;
                
            case 8:

                numero = "10";

                break;
                
            case 9:

                numero = "J";

                break;
                
            case 10:

                numero = "Q";

                break;
                
            case 11:

                numero = "K";

                break;
                
            case 12:

                numero = "AS";

                break;                
            
            default:
                
                numero = "";

                break;
        }
                
                if (palo != "" && numero != "") {
                    
                    return numero + " de " + palo;
            
        }else{
                    return "";
                }

    }

//    public static void main(String[] args) {
//        
//        
//            ServerSocket ss;
//            Socket sc;
//            
//        try {            
//            
//            ss = new ServerSocket(6060);
//            sc = ss.accept();//aceptamos la conexion y guardamos el socket
//            
//            //ejemplo de hilo
//            Thread hilo = new Thread(new servidor(sc));
//            hilo.start();
//            
//        } catch (IOException ex) {
//            
//            Logger.getLogger(servidor.class.getName()).log(Level.SEVERE, null, ex);
//            
//        }
//        
//    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cliente;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author chern007
 */
public class Cliente {

    String[] corazonesCliente = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "AS"};
    String[] rombosCliente = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "AS"};
    String[] trebolesCliente = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "AS"};
    String[] picasCliente = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "AS"};

    String[][] barajaCliente = {corazonesCliente, rombosCliente, trebolesCliente, picasCliente};

    private static Scanner entrada = new Scanner(System.in);

    public static void main(String[] args) {

        String nombreSaludo;
        String respuesta;
        String continuamos;
        int importe;
        int[] cartasElegidas = new int[2];
        boolean finJuego = false;

        try {

            //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
            Socket sc = new Socket("localhost", 6060);

            DataInputStream loQueEntra = new DataInputStream(sc.getInputStream());
            DataOutputStream loQueSale = new DataOutputStream(sc.getOutputStream());
            //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::

            System.out.println("Introduce tu nombre:");

            nombreSaludo = entrada.nextLine();
            loQueSale.writeUTF(nombreSaludo);//W1 enviamos el nombre

            System.out.println(loQueEntra.readUTF());//R1 recivimos el saludo del server           
            
            while(!finJuego){

            System.out.println(loQueEntra.readUTF());//R2 nos dice cuanto dinero queremos apostar

            importe = entrada.nextInt();entrada.nextLine();//para finalizar la linea
            loQueSale.writeInt(importe);//W2 enviamos el importe a apostar           

            respuesta = loQueEntra.readUTF();//R3-->apuesta ACEPTADA o NO

            System.out.println(respuesta);
            if (respuesta.contains("no hay dinero")) {
                return;
            }

   

            respuesta = loQueEntra.readUTF();//R1-->RESULTADO Perdido o ganado
            System.out.println(respuesta);
            
            if (respuesta.contains("perdido")) {
                
                System.out.println(loQueEntra.readUTF());//R2 vuelves a jugar?                
                
                do{
                continuamos = entrada.nextLine().toLowerCase();                
                }while(!continuamos.equals("si") && !continuamos.equals("no"));
                loQueSale.writeUTF(continuamos);//W1 le enviamos la respuesta

                if (continuamos.contains("no")) {

                    respuesta = loQueEntra.readUTF();//R3
                    System.out.println(respuesta);//imprimimos la despedida
                    finJuego = true;
                    return;

                }

            } else {

                System.out.println(loQueEntra.readUTF());//R2 vuelves a jugar?
                    
                do{
                continuamos = entrada.nextLine().toLowerCase();
                }while(!continuamos.equals("si") && !continuamos.equals("no"));
                loQueSale.writeUTF(continuamos);//W1 le enviamos la respuesta

                if (continuamos.contains("no")) {

                    respuesta = loQueEntra.readUTF();//R3
                    System.out.println(respuesta);//imprimimos la despedida
                    finJuego = true;
                    return;
                }
            }            
        }

        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private static int[] eligeCarta() {

        int[] carta = new int[2];
        String palo = "*";
        String numero = "*";

        System.out.println("Elige tu carta:");
        System.out.println("\"Corazones(C), Rombos(R), Tréboles(T), Picas(P)\"");
        
        do {
            if (!palo.equals("*")) {
                System.out.println("No has introducido un valor permitido, prueba otra vez.");
            }
            palo = entrada.nextLine().toLowerCase();
        } while (!palo.equals("c") && !palo.equals("r") && !palo.equals("t") && !palo.equals("p"));

        System.out.println("Elige el número de la carta:\n\"2, 3, 4, 5, 6, 7, 8, 9, 10, J, Q, K, AS\"");

        do {
            if (!numero.equals("*")) {
                System.out.println("No has introducido un valor permitido, prueba otra vez.");
            }
            numero = entrada.nextLine().toLowerCase();
        } while (!numero.equals("2") && !numero.equals("3") && !numero.equals("4") && !numero.equals("5") && !numero.equals("6") && !numero.equals("7") && !numero.equals("8") && !numero.equals("9") && !numero.equals("10") && !numero.equals("j") && !numero.equals("q") && !numero.equals("k") && !numero.equals("as"));

        switch (palo) {
            case "c":

                carta[0] = 0;

                break;
            case "r":

                carta[0] = 1;

                break;
            case "t":

                carta[0] = 2;

                break;
            case "p":

                carta[0] = 3;

                break;

            default:
                System.out.println("NO HAS INTRODUCIDO CARACTERES PERMITIDOS.");
                break;
        }
        
        ////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////
        
        switch (numero) {
            case "2":

                carta[1] = 0;

                break;
            case "3":

                carta[1] = 1;

                break;
            case "4":

                carta[1] = 2;

                break;
            case "5":

                carta[1] = 3;

                break;
            case "6":

                carta[1] = 4;

                break;
            case "7":

                carta[1] = 5;

                break;
            case "8":

                carta[1] = 6;

                break;
            case "9":

                carta[1] = 7;

                break;
            case "10":

                carta[1] = 8;

                break;
            case "j":

                carta[1] = 9;

                break;
            case "q":

                carta[1] = 10;

                break;
            case "k":

                carta[1] = 11;

                break;
            case "as":

                carta[1] = 12;

                break;

            default:
                System.out.println("NO HAS INTRODUCIDO CARACTERES PERMITIDOS.");
                break;
        }

        return carta;
    }

}

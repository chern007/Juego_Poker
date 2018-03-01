/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidor;

/**
 *
 * @author chern007
 */
public class Banca {

    int dinero = 1000;//empezamos con 1000 de saldo

    public synchronized void sacarDinero(int cantidad) {
        this.dinero -= cantidad;
    }

    public synchronized void meterDinero(int cantidad) {
        this.dinero += cantidad;
    }

    public synchronized int saldo() {
        return this.dinero;
    }

}

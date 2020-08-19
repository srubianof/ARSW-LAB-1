/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.primefinder;

import java.util.Scanner;
import java.util.Timer;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 */
public class Control extends Thread {

    private final static int NTHREADS = 3;
    private final static int MAXVALUE = 30000000;
    private final static int TMILISECONDS = 5000;

    private final int NDATA = MAXVALUE / NTHREADS;
    private long t;
    private PrimeFinderThread pft[];
    private AtomicInteger counter = new AtomicInteger(0);
    private Control(long t) {
        super();
        this.pft = new PrimeFinderThread[NTHREADS];
        this.t = t;
        int i;
        for (i = 0; i < NTHREADS - 1; i++) {
            PrimeFinderThread elem = new PrimeFinderThread(i * NDATA, (i + 1) * NDATA,counter);
            pft[i] = elem;
        }
        pft[i] = new PrimeFinderThread(i * NDATA, MAXVALUE + 1,counter);

    }

    public static Control newControl(long t) {

        return new Control(t);
    }

    @Override
    public void run() {
        for (int i = 0; i < NTHREADS; i++) {
            pft[i].start();
        }
        long startTime = System.currentTimeMillis();
        boolean areAlive=true;
        while (areAlive) {
            if (System.currentTimeMillis() - startTime >= t) {
                areAlive=false;
                for (int i = 0; i < NTHREADS; i++) {
                    pft[i].notRunningThread();
                }
                System.out.println("Primos encontrados hasta el momento: "+counter.toString());
                System.out.println("Presiona enter para continuar.");
                Scanner entrada = new Scanner(System.in);
                entrada.nextLine();
                for (int i = 0; i < NTHREADS; i++) {
                    pft[i].runningThread();
                    if (pft[i].isAlive() && !areAlive){
                        areAlive=true;
                    }
                }

                startTime = System.currentTimeMillis();
                //System.out.println("hola");

            }

        }


    }

}



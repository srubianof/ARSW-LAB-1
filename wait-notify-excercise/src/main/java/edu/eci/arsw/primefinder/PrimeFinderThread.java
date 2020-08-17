package edu.eci.arsw.primefinder;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class PrimeFinderThread extends Thread{


    int a,b;
    AtomicInteger c;
    private List<Integer> primes;
    private boolean running;
    private Object lock;

    public PrimeFinderThread(int a, int b, AtomicInteger c) {
        super();
        this.primes = new LinkedList<>();
        this.a = a;
        this.b = b;
        this.c = c;
        this.running=false;
        this.lock = new Object();
    }

    @Override
    public void run(){
        this.running=true;
        for (int i= a;i < b;i++){
            synchronized (this){
                while(!running){
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }

            }
            if (isPrime(i)){
                primes.add(i);
                c.getAndIncrement();
                System.out.println(i);
            }

        }
    }

    boolean isPrime(int n) {
        boolean ans;
        if (n > 2) {
            ans = n%2 != 0;
            for(int i = 3;ans && i*i <= n; i+=2 ) {
                ans = n % i != 0;
            }
        } else {
            ans = n == 2;
        }
        return ans;
    }

    public List<Integer> getPrimes() {
        return primes;
    }

    public synchronized void runningThread(){
        this.running=true;
        notify();
    }

    public void notRunningThread(){
        this.running=false;

    }
}
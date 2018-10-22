package edu.temple.cis.c3238.banksim;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;


class TransferThread extends Thread {

    private final Bank bank;
    private final int fromAccount;
    private final int maxAmount;
    Semaphore semaphore = null;
    Semaphore signal = null;

    public TransferThread(Bank b, int from, int max, Semaphore semaphore) {
        bank = b;
        fromAccount = from;
        maxAmount = max;
        this.semaphore = semaphore;
    }

    @Override
    public void run() {
        while(bank.isOpen()){
        for (int i = 0; i <= 10000; i++)  {
        
            try {
            semaphore.acquire();

            } catch (InterruptedException ex){
            }
            int toAccount = (int) (bank.size() * Math.random());
            int amount = (int) (maxAmount * Math.random());
            
            bank.transfer(fromAccount, toAccount, amount);
            
            semaphore.release();
//            try {
//                sleep(2);
//            } catch (InterruptedException ex) {
//                
//            }
        
        }
        }
        bank.closeBank();
    }
}
//    public void run() {
//
//        while (bank.isOpen()) {
//            //increment semaphore
//            try {
//                semaphore.acquire(10);
//            } catch (InterruptedException ex) {
//                //Do nothing
//            }
//            //System.out.println("TEST THREAD: " + semaphore.toString()); 
//            if (bank.shouldTest()) {
//                bank.test();
//            }
//            //decrement semaphore
//            semaphore.release(10);
//
//            try {
//                sleep(1);
//            } catch (InterruptedException ex) {
//
//            }
//        }
//        bank.closeBank();
//    }

    

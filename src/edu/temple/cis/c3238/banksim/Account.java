package edu.temple.cis.c3238.banksim;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class Account {

    private volatile int balance;
    private final int id;
    private final Bank myBank;
    private final ReentrantLock r_lock;
    
    public Account(Bank myBank, int id, int initialBalance, ReentrantLock lock) {
        this.myBank = myBank;
        this.id = id;
        balance = initialBalance;
        this.r_lock = lock;
    }

    public int getBalance() {
        return balance;
    }

    public synchronized boolean withdraw(int amount) {
        
        if (amount <= balance) {
            r_lock.lock();
            int currentBalance = balance;
            Thread.yield(); // Try to force collision
            int newBalance = currentBalance - amount;
            balance = newBalance;
            r_lock.unlock();
            return true;
        } else {
            return false;
        }
        
    }
    
    public synchronized void waitForSufficientFunds(int amount){
                  
        boolean flag = false;
        while (amount >= balance){
            flag = true;
            try{
                wait();
            }catch (InterruptedException ex) {
            }
        }
        

    }

    public synchronized void deposit(int amount) {
        r_lock.lock();
        
        int currentBalance = balance;
        Thread.yield();   // Try to force collision
        int newBalance = currentBalance + amount;
        balance = newBalance;
        
        notifyAll();
        r_lock.unlock();
    }
    
    @Override
    public String toString() {
        return String.format("Account[%d] balance %d", id, balance);
    }
}

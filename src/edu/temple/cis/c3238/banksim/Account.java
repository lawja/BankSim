package edu.temple.cis.c3238.banksim;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class Account {

    private volatile int balance;
    private final int id;
    private final Bank myBank;
    private final ReentrantLock r_lock = new ReentrantLock();
    public Account(Bank myBank, int id, int initialBalance) {
        this.myBank = myBank;
        this.id = id;
        balance = initialBalance;
    }

    public int getBalance() {
        return balance;
    }

    public synchronized boolean withdraw(int amount) {
        if (amount <= balance) {
            int currentBalance = balance;
            Thread.yield(); // Try to force collision
            int newBalance = currentBalance - amount;
            balance = newBalance;
            return true;
        } else {
            return false;
        }
    }
    
    public synchronized void waitForSufficientFunds(int amount){
        r_lock.lock();            
       
        while (amount >= balance){
            try{
                wait();
            }catch (InterruptedException ex) {
            }
        }

    }

    public synchronized void deposit(int amount) {
        int currentBalance = balance;
        Thread.yield();   // Try to force collision
        int newBalance = currentBalance + amount;
        balance = newBalance;
        notifyAll();
    }
    
    @Override
    public String toString() {
        return String.format("Account[%d] balance %d", id, balance);
    }
}

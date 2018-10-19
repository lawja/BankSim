package edu.temple.cis.c3238.banksim;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Cay Horstmann
 * @author Modified by Paul Wolfgang
 * @author Modified by Charles Wang
 */
class TransferThread extends Thread {

    private final Bank bank;
    private final int fromAccount;
    private final int maxAmount;
    private final ReentrantLock r_lock;

    public TransferThread(Bank b, int from, int max, ReentrantLock lock) {
        bank = b;
        fromAccount = from;
        maxAmount = max;
        r_lock = lock;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10000; i++) {
            int toAccount = (int) (bank.size() * Math.random());
            int amount = (int) (maxAmount * Math.random());
            r_lock.lock();
            bank.transfer(fromAccount, toAccount, amount);
            r_lock.unlock();
        }
    }
}

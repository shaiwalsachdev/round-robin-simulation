/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.osproject.roundrobinsimulation;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author yasas
 */
public class Processor {

    private ReadyQueue readyQueue;
    private boolean finished;
    private Job runningProcess;
    private Job prevProcess;
    private int speed;
    private int count;

    public Processor(int Speed) {
        this.speed = speed;
        count =0;
    }

    public Job executeProcess(Job process) {
        prevProcess = runningProcess;
        runningProcess = process;
        return prevProcess;
    }

    public void changeSpeed(int speed) {
        this.speed = speed;
    }

    public synchronized void pause() {
        finished = true;
    }
    
    public int getCount(){
        return count;
    }

    public synchronized void start() {
        finished = false;
        Runnable r = new Runnable() {
            @Override
            public void run() {
                count++;
                while (!finished) {
                    if (runningProcess != null) {
                        runningProcess.burst();
                        if (runningProcess.isCompleted()) {
                            runningProcess = null;
                        }
                    }
                    System.out.println("Processor Thread!!!");
                    try {
                        Thread.sleep(700 - speed * 4);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Simulator.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
            }
        };
        new Thread(r).start();
    }

}

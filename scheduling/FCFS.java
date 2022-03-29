/*
	Author : Kaiser Sakhi
	Date : 28-03-2022

*/


import java.lang.*;


class FCFS{


    public static void main(String[] args){
        MyOS os = new MyOS();
        os.runCPU();
        os.computeCompletionTime();
        os.printProcessTable();
        System.out.println("Average Turn Around Time :  " + os.getAverageTurnAroundTime());
        System.out.println("Average Waiting Time : "+ os.getAverageWaitingTime());
    }
}

class MyOS{
    Process[] processes;
    MyOS(){
        processes = new Process[10];
        // init 10 processes
        for (int i = 0; i < 10; ++i){
            // add sleep thread here, after every 1 second
            try{
                Thread.sleep(1000); // make main thread sleep to have random arrival time of the process
                processes[i] = new Process(i * 2); // assinging (i * 2) burst time to each process
                System.out.println("Process_"+processes[i].getPid() + " " + "Arrived!");
            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }

    public void runCPU(){ // this method will act as a cpu
        processes[0].setWaitingTime(0);
        for (int i = 1; i < processes.length; ++i){
            processes[i].setWaitingTime(processes[i-1].getWaitingTime() + processes[i-1].getBurstTime());
        }
    }

    public void printProcessTable(){
        System.out.println("Process ID |\t Arrival Time |\t Burst Time |\t Waiting Time");
        for (Process x : processes){
            System.out.println(x.getPid() +"\t |\t "+ x.getArrivalTime()+"\t |\t\t "+ x.getBurstTime() +" |\t\t "+ x.getWaitingTime());
        }
    }

    public void computeCompletionTime(){
        for (int i = 0; i < processes.length -1 ; ++i){
            processes[i].setCompeletionTime(processes[i + 1].getWaitingTime());
        }
    }

    public float getAverageWaitingTime(){
        float avgWaitTime = 0;
        for (int i = 0; i < processes.length; ++i){
            // waiting time = turn around time - burst time
            avgWaitTime += (processes[i].getCompeletionTime() - processes[i].getArrivalTime()) - processes[i].getBurstTime();
        }

        return avgWaitTime/processes.length;
    }
    public float getAverageTurnAroundTime(){
        float avgTime = 0f;

        for (int i =0 ; i < processes.length; ++i){
            avgTime += processes[i].getCompeletionTime() - processes[i].getArrivalTime();
        }
        return avgTime / 10;
    }
}

class Process{
    static int pid_gen;
    int pid;
    int burstTime;
    int arrivalTime;
    int waitingTime;
    int compeletionTime;
//    int turnAroundTime;
    static long os_startTime;

    static{
        Process.pid_gen = 0;
        Process.os_startTime = System.currentTimeMillis() / 1000l;
    }


    Process(int burstTime){
        this.burstTime = burstTime;
        this.pid = ++pid_gen;
        long currentTime =  ((System.currentTimeMillis() / 1000l));
        this.arrivalTime = (int)(currentTime - Process.os_startTime); // arrival time is in milliseconds
        // this.arrivalTime = this.arrivalTime / 1000;
    }
//
//    public int getTurnAroundTime(){
//        // calculate TurnAroundTIme
//        return this.turnAroundTime;
//    }
//
//    public void setTurnAroundTime(int time){
//        this.turnAroundTime = time;
//    }

    public int getWaitingTime(){
        return this.waitingTime;
    }

    public int getPid(){
        return this.pid;
    }

    public int getArrivalTime(){
        return this.arrivalTime;
    }

    public int getBurstTime(){
        return this.burstTime;
    }
    public void setWaitingTime(int time){
        this.waitingTime = time;
    }
    public void setCompeletionTime(int time){
        this.compeletionTime = time;
    }
    public int getCompeletionTime(){
        return this.compeletionTime;
    }



}

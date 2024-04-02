/*
Name: put your name here if you want credit
CSCI 4401/5401
Spring 2024
Assignment 4

Due: Monday, 4/8 @ 11:59pm

- This assignment contains a total of 50 points.
- The problems you will solve all have ToDo: notes. 
- You can create new variables, but you cannot hardcode values. 
- The arguments passed to the methods are example test values. You code should not be built to work for these specific values. I.e., it should still work if other test values were used. 
- Do not modify the current print statements. If you add print statements for testing & debugging, please remove them before submitting.
- Submit: this modified file containing your solutions.
*/

import java.util.*;
import java.io.*;
import java.lang.Math;

public class HW4{ //don't rename

	public static void main(String args[]){
		part1test();
		part2test();
	}

	/********************************************************************************************
	Part I: Process Scheduling
		- firstComeFirsServe()
		- shortestJobFirst()
		- roundRobin()
	********************************************************************************************/

	public static void part1test(){
		/*	This is for testing your code. 
			DO NOT modify this method
		*/
		
		printSectionHeader("Part I: Process Scheduling");

		//Test firstComeFirsServe()
		System.out.println("firstComeFirsServe() [8 points]");
		System.out.println(String.format("\t%1$8s%2$20s", "Process", "Turnaround Time(ms)"));
		int[] fcfsJobs = {12, 8, 10, 4, 2};
		firstComeFirstServe(fcfsJobs);
	
		//Test shortestJobFirst()
		int[] sjfJobs = {12, 8, 10, 4, 2};
		System.out.println("\nshortestJobFirst() [8 points]");
		System.out.println(String.format("\t%1$8s%2$20s", "Process", "Turnaround Time(ms)"));
		shortestJobFirst(sjfJobs);

		//Test roundRobin()
		int[] rrJobs = {12, 8, 10, 4, 2};
		System.out.println("\nroundRobin() [14 points]");
		System.out.println(String.format("\t%1$8s%2$20s", "Process", "Turnaround Time(ms)"));
		roundRobin(rrJobs);
	}

	public static void firstComeFirstServe(int[] jobs) {
		/* Consider a system using first-come, first-serve process scheduling algorithm. 
		   Write code to determine the turnaround times @turnaroundTimes for each process in @jobs, and 
			compute the average turnaround @avgTurnaroundTime.
		   You can assume jobs will have a unique runtime, making them safe to use as keys in @turnaroundTimes.
		*/

		float avgTurnaroundTime = 0;
		float turnaroundTime = 0;
		HashMap<Integer, Integer> turnaroundTimes = new HashMap<Integer, Integer>();

		for (int job : jobs) {
			turnaroundTimes.put(job, 0);
		}

		//ToDo: add your code to calculate @turnaroundTimes and @avgTurnaroundTime.

		for(int job : jobs){
			turnaroundTime = job + turnaroundTime;
			turnaroundTimes.replace(job, (int)turnaroundTime);
			avgTurnaroundTime += turnaroundTime;
		}
		avgTurnaroundTime /= jobs.length;


		for (Integer job : 	turnaroundTimes.keySet()) { //Do not modify		
			System.out.println(String.format("\t%1$8d%2$20d", job, turnaroundTimes.get(job)) ); //Do not modify		
		}
		System.out.println("\tAverage Time: " +  avgTurnaroundTime); //Do not modify		
	}
	
	public static void shortestJobFirst(int[] jobs) {
		/* Consider a system using shortest-job first process scheduling algorithm. 
		   Write code to determine the turnaround times @turnaroundTimes for each process in @jobs, and 
			compute the average turnaround @avgTurnaroundTime.
		   You can assume jobs will have a unique runtime, makng them safe to use as keys in @turnaroundTimes.
		*/

		float avgTurnaroundTime = 0;
		float turnaroundTime = 0;
		HashMap<Integer, Integer> turnaroundTimes = new HashMap<Integer, Integer>();

		for (int job : jobs) {
			turnaroundTimes.put(job, 0);				
		}

		//ToDo: add your code to calculate @turnaroundTimes and @avgTurnaroundTime.
		int[] sortJobs = jobs.clone();
		
		for(int i = 0; i < sortJobs.length-1; i++){
			int min = sortJobs[i];
			int minIndex = i;
			for(int j = i+1; j< sortJobs.length; j++){
				if(min > sortJobs[j]){
					min = sortJobs[j];
					minIndex = j;
				}
			}
			int temp = sortJobs[i];
			sortJobs[i] = sortJobs[minIndex];
			sortJobs[minIndex] = temp;
		}

		for(int job : sortJobs){
			turnaroundTime = job + turnaroundTime;
			turnaroundTimes.replace(job, (int)turnaroundTime);
			avgTurnaroundTime += turnaroundTime;
		}
		avgTurnaroundTime /= jobs.length;



		for (Integer job : 	turnaroundTimes.keySet()) { //Do not modify		
			System.out.println(String.format("\t%1$8d%2$20d", job, turnaroundTimes.get(job)) ); //Do not modify		
		}

		System.out.println("\tAverage Time: " +  avgTurnaroundTime); //Do not modify	
	}

	public static void roundRobin(int[] jobs) {
		/* Consider a system using round robin process scheduling algorithm (assume a quantum = 1). 
		   Write code to determine the turnaround times @turnaroundTimes for each process in @jobs, and 
			compute the average turnaround @avgTurnaroundTime.
		   You can assume jobs will have a unique runtime, makng them safe to use as keys in @turnaroundTimes.
		*/

		float avgTurnaroundTime = 0;
		HashMap<Integer, Integer> turnaroundTimes = new HashMap<Integer, Integer>();

		for (int job : jobs) {
			turnaroundTimes.put(job, 0);				
		}

		//ToDo: add your code to calculate @turnaroundTimes and @avgTurnaroundTime.
		int[] decrementJobs = jobs.clone();
		int index = -1;
		int turnaround = 0;
		boolean exec = true;

		while(exec){
			index = (index + 1) % jobs.length;
			while(decrementJobs[index] == 0){
				index = (index + 1) % jobs.length;
			}

			decrementJobs[index] -= 1;
			turnaround++;
			if(decrementJobs[index] == 0){
				turnaroundTimes.replace(jobs[index], turnaround);
				avgTurnaroundTime += turnaround;
			}

			exec = false;
			for(int i = 0; i < decrementJobs.length; i++){
				if(decrementJobs[i] != 0){
					exec = true;
					break;
				}
			}
		}

		avgTurnaroundTime /= jobs.length;


		for (Integer job : 	turnaroundTimes.keySet()) { //Do not modify		
			System.out.println(String.format("\t%1$8d%2$20d", job, turnaroundTimes.get(job)) ); //Do not modify		
		}
		System.out.println("\tAverage Time: " +  avgTurnaroundTime); //Do not modify		
	}
	

	/********************************************************************************************
	Part II: Deadlocks
		- safeState()
		- deadlockDetection()
	********************************************************************************************/

	public static void part2test(){
		/*	This is for testing your code. 
			DO NOT modify this method
		*/

		printSectionHeader("Part II: Deadlocks");

		//Test safeState()
		System.out.println("safeState() [10 points]");
		int[][] allocation1 = 	{{0, 1, 0}, {2, 0, 0}, {3, 0, 3}, {2, 1, 1}, {0, 0, 2}};
		int[][] need1 = 		{{0, 0, 0}, {2, 0, 2}, {0, 0, 0}, {1, 0, 0}, {0, 0, 2}};
		int[] available1 = {0, 0, 0};		
		safeState(allocation1, need1, available1);
		int[][] allocation2 = 	{{0, 1, 0}, {2, 0, 0}, {3, 0, 3}, {2, 1, 1}, {0, 0, 2}};
		int[][] need2 = 		{{0, 2, 0}, {2, 0, 2}, {0, 0, 3}, {1, 0, 0}, {0, 0, 2}};
		int[] available2 = {0, 0, 0};		
		safeState(allocation2, need2, available2);


		System.out.println("\ndeadlockDetection() [10 points]");
		int[][] allocation3 = 	{{0, 1, 0}, {2, 0, 0}, {3, 0, 3}, {2, 1, 1}, {0, 0, 2}};
		int[][] need3 = 		{{0, 0, 0}, {2, 0, 2}, {0, 0, 0}, {1, 0, 0}, {0, 0, 2}};
		int[] available3 = {0, 0, 0};		
		deadlockDetection(allocation3, need3, available3);
		int[][] allocation4 = 	{{0, 1, 0}, {2, 0, 0}, {1, 0, 0}, {2, 1, 1}, {0, 0, 2}};
		int[][] need4 = 		{{0, 0, 0}, {2, 0, 2}, {0, 0, 0}, {1, 0, 0}, {0, 0, 2}};
		int[] available4 = {0, 0, 0};		
		deadlockDetection(allocation4, need4, available4);
	}

	public static void safeState(int[][] allocation, int[][] need, int[] available) {
		/* Consider a system using deadlock avoidance (e.g., Banker's algorithm). 
		   Write code to determine if all the processes can finish @finish (a boolean array 
			where true means the process can finish), and if a safe sequence exists, determine one
			of the safe sequences @sequence. You only need to return one possible safe sequence (not 
			all possible safe sequences). For the sequence, use the index position in @need or @allocation, 
			e.g., [1, 0, 2, 4, 3]
		*/

		boolean[] finish = new boolean[allocation.length]; //true means a process can finish, false means a process cannot finish.
		ArrayList<Integer> sequence = new ArrayList<Integer>();
		

		//ToDo: add your code to determine @finish and @sequence.
		boolean proceed = true;	// Boolean that determines if there are still processes that can be run out of the entire set
		boolean able = false;	// Boolean that determines if an individual process can be run
		int index = 0; // Process index that can be run

		for(int i = 0; i < finish.length; i++){
			finish[i] = false;
		}

		while(proceed){
			proceed = false;	// Assumes that there are no more available processes (updated to true if there is at least one change)
			able = false;		// Assumes that there isn't an availble process 

			// Loops through all of the processes
			for(int i = 0; i < need.length; i++){
				if(finish[i]){ // If a process has already been run, ignore it
					continue;
				}

				// Assume that the process can be run
				able = true;
				index = i;
				for(int j = 0; j < need[i].length; j++){
					if(need[i][j] > available[j]){ 			// If there isn't enough of any resource, the process can't be run
						able = false;
						break;
					}
				}

				// If the process can be run, break out of the for loop
				if(able){
					break;
				}
			}

			// If the process can be run, update available resources and update the proceed boolean
			if(able){
				for(int j = 0; j < allocation[index].length; j++){
					available[j] += allocation[index][j];
				}
				finish[index] = true;
				sequence.add(index);
				proceed = true;
			}
		}


		for (boolean process: finish) { //Do not modify
			if (!process) { //Do not modify
				System.out.println("\tNo safe sequence exists."); //Do not modify
				return; //Do not modify
			}
		}
		System.out.println("\tSafe sequence: " + sequence); //Do not modify
	}



	public static void deadlockDetection(int[][] allocation, int[][] need, int[] available) {
		/* Consider a system using deadlock detection. 
		   Write code to determine if a deadlock is present. If a deadlock is present, store the deadlocked
			process in @deadlockedProcess. For the deadlocked processes, use the index position in @need 
			or @allocation, e.g., [1, 0, 2, 4, 3]
		*/

		ArrayList<Integer> deadlockedProcesses = new ArrayList<Integer>();
		
		//ToDo: add your code to determine @deadlockedProcesses.
		boolean proceed = true;	// Boolean that determines if there are still processes that can be run out of the entire set
		boolean able = false;	// Boolean that determines if an individual process can be run
		int index = 0; // Process index that can be run
		boolean[] finish = new boolean[allocation.length]; //true means a process can finish, false means a process cannot finish.

		for(int i = 0; i < finish.length; i++){
			finish[i] = false;
		}

		while(proceed){
			proceed = false;	// Assumes that there are no more available processes (updated to true if there is at least one change)
			able = false;		// Assumes that there isn't an availble process 

			// Loops through all of the processes
			for(int i = 0; i < need.length; i++){
				if(finish[i]){ // If a process has already been run, ignore it
					continue;
				}

				// Assume that the process can be run
				able = true;
				index = i;
				for(int j = 0; j < need[i].length; j++){
					if(need[i][j] > available[j]){ 			// If there isn't enough of any resource, the process can't be run
						able = false;
						break;
					}
				}

				// If the process can be run, break out of the for loop
				if(able){
					break;
				}
			}

			// If the process can be run, update available resources and update the proceed boolean
			if(able){
				for(int j = 0; j < allocation[index].length; j++){
					available[j] += allocation[index][j];
				}
				finish[index] = true;
				proceed = true;
			}
		}

		for(int i = 0; i < finish.length; i++){
			if(!finish[i]){
				deadlockedProcesses.add(i);
			}
		}


		
		if (deadlockedProcesses.size() > 0) {
			System.out.println("\tDeadlocked processes: " + deadlockedProcesses);
		}
		else {
			System.out.println("\tNo deadlock exists");
		}
	}


	public static void printSectionHeader(String sectionName) {
		//Don't modify this
		System.out.println("\n" + "-".repeat(30));
		System.out.println(String.format("%1$-30s", sectionName));
		System.out.println("-".repeat(30));
	}

}

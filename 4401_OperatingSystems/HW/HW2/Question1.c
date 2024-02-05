#include <stdio.h>
#include <stdlib.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <unistd.h>



int main(int args, char* argv[]){


	int pid;

	FILE *fileReset = fopen("Question2.txt", "w");
	fprintf(fileReset, "");
	fclose(fileReset);



	for(int i = 0; i < atoi(argv[1]); i++){
		fork();
	}


	FILE *fileOut = fopen("Question2.txt", "a");
	fprintf(fileOut, "Process ID: %d,\t Parent ID: %d\n", getpid(), getppid());
	fclose(fileOut);

	sleep(1);

}

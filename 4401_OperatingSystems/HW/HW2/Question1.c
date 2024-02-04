#include <stdio.h>
#include <stdlib.h>
#include <sys/types.h>
#include <unistd.h>



int main(int args, char* argv[]){

	int pid;

	for(int i = 0; i < atoi(argv[1]); i++){
		fork();
	}

	FILE *fileReset = fopen("Question2.txt", "w");
	fprintf(fileReset, "");
	fclose(fileReset);

	FILE *fileOut = fopen("Question2.txt", "a");
	fprintf(fileOut, "Process ID: %d,\t Parent ID: %d\n", getpid(), getppid());
	fclose(fileOut);

	sleep(1);

}

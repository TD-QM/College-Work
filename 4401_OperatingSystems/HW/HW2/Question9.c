#include <sys/types.h>
#include <sys/ipc.h>
#include <sys/msg.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>


struct msg_buffer {
	long type;
	char text[50];
};


int main(){
	struct msg_buffer buff;
	key_t key;
	int msgid;
	int pid;

	key = ftok(".", 'x');

	pid = fork();

	if(pid != 0){
		msgid = msgget(key, 0666 | IPC_CREAT);
		buff.type = 1;

		printf("I'm parent %d! I need the following chore done:\n", getpid());
		fgets(buff.text, sizeof buff.text, stdin);
		if(msgsnd(msgid, &buff, sizeof(buff), 0 & IPC_NOWAIT) == 0){
			printf("I sent my chore \n\n");
		}

		memset(buff.text, '\0', sizeof(buff.text));

		msgrcv(msgid, &buff, sizeof(buff), 1, 0 & IPC_NOWAIT);
		printf("I'm parent %d! Again!\n", getpid());
		printf("My child says: %s", buff.text);

		return 0;

	} else {
		msgid = msgget(key, 0666 | IPC_CREAT);
		buff.type = 1;

		msgrcv(msgid, &buff, sizeof(buff), 1, 0 & IPC_NOWAIT);
		printf("I'm child %d! My chore is: %s", getpid(), buff.text);

		printf("I've finished my chore! Sending my parent:\n");
		fgets(buff.text, sizeof buff.text, stdin);

		if (msgsnd(msgid, &buff, sizeof(buff), 0 & IPC_NOWAIT) == 0){
			printf("Sent my parent a reply!\n\n");
		}

		return 0;
	}

}










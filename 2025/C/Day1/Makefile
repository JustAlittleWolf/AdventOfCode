CC = gcc
DEFS = -D_DEFAULT_SOURCE -D_BSD_SOURCE -D_SVID_SOURCE -D_POSIX_C_SOURCE=200809L
CCFLAGS = -std=c99 -Wall -pedantic -g $(DEFS)
OBJECTS_CLIENT = main.o

.PHONY: all clean

all: day1

day1: $(OBJECTS_CLIENT)
	$(CC) $(CCFLAGS) -o $@ $^

%.o: %.c
	$(CC) $(CCFLAGS) -c -o $@ $<

clean:
	rm -rf *.o day1
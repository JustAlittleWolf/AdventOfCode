#include <getopt.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <errno.h>
#include <math.h>

int position = 50;
int times_visited_zero = 0;
int times_passed_zero = 0;

static void exit_failure_with_message(char *message)
{
    if (errno != 0)
        fprintf(stderr, "errno: %s\n", strerror(errno));
    fprintf(stderr, "%s\n", message);
    exit(EXIT_FAILURE);
}

static int parse_args(int argc, char **argv, FILE **instream)
{
    char *in_file_path = NULL;
    int opt;
    while ((opt = getopt(argc, argv, "i:")) != -1)
    {
        switch (opt)
        {
        case 'i':
            in_file_path = optarg;
            if (in_file_path == NULL || strlen(in_file_path) < 1)
                return -1;
            break;
        default:
            return -1;
        }
    }

    if (optind != argc)
        return -1;
    if (in_file_path == NULL)
        return -1;

    *instream = fopen(in_file_path, "r");
    if (*instream == NULL)
        return -2;
    return 0;
}

static int for_each_line(FILE *instream, int (*callback)(char *))
{
    char *line = NULL;
    size_t len = 0;
    ssize_t nread;
    while ((nread = getline(&line, &len, instream)) != -1)
    {
        int status = callback(line);
        if (status != 0)
        {
            free(line);
            return status;
        }
    }

    free(line);
    return 0;
}

static int parse_line(char *line)
{
    if (strlen(line) < 2)
        return -1;
    int sign = line[0] == 'R' ? 1 : (line[0] == 'L' ? -1 : 0);
    if (sign == 0)
        return -1;
    char *endptr;
    int steps = strtol(line + 1, &endptr, 10);
    if (endptr == NULL || !(*endptr == '\0' || *endptr == '\n' || *endptr == '\r'))
        return -1;
    int prev_position = position;
    position += (sign * steps);
    int position_before_mod = position;
    position %= 100;
    if (position < 0)
        position += 100;

    if (position == 0)
        times_visited_zero++;

    if (position != position_before_mod)
    {
        if (position_before_mod > 99)
            times_passed_zero += position_before_mod / 100;
        else if (position_before_mod < 0)
        {
            times_passed_zero += 1 + (-position_before_mod / 100);
            if (prev_position == 0)
                times_passed_zero--;
        }
    }
    else if (position == 0)
        times_passed_zero++;

    return 0;
}

int main(int argc, char **argv)
{
    FILE *instream = NULL;
    switch (parse_args(argc, argv, &instream))
    {
    case 0:
        break;
    case -2:
        exit_failure_with_message("Failed to read input file");
        break;
    default:
        exit_failure_with_message("Usage: day1 -i file");
        break;
    }

    switch (for_each_line(instream, parse_line))
    {
    case 0:
        break;
    default:
        exit_failure_with_message("Failed to read the next line from input file");
        break;
    }

    fprintf(stdout, "%d\n", times_visited_zero);
    fprintf(stdout, "%d\n", times_passed_zero);
    return 0;
}
#include <stdio.h>
#include <cs50.h>
#include <stdint.h> // for uint8_t integer type

#define BLOCK 512                                                         // CS50 2017 recover

bool already = false;   // check if JPEG is already open
int count = 0;          // track JPEG filenames

int main(int argc, char *argv[])
{
	// check command line arguments
	if (argc != 2)
    {
        fprintf(stderr, "Usage: ./recover datafile\n");
        return 1;
    }

    // open input file 
    FILE *file = fopen(argv[1], "r");
    if (file == NULL)
    {
        fprintf(stderr, "Could not open %s.\n", (argv[1]));
        return 2;
    }
    
    // allocate heap memory space
    uint8_t *buffer = malloc(BLOCK);
    char *filename = malloc(8);
    
    // loop until EOF
    while (fread(buffer, 1, BLOCK, file) == BLOCK)
    {
        // if start of a JPEG is found
        if (buffer[0] == 0xff &&
            buffer[1] == 0xd8 &&
            buffer[2] == 0xff &&
            (buffer[3] & 0xf0) == 0xe0)
            {
            // if new JPEG found and another JPEG already open
            if (already == true)
            {
                sprintf(filename, "%03i.jpg", count);
                count++;
                FILE *img = fopen(filename, "w");
                fwrite(buffer, 1, BLOCK, img);
                fclose(img);
            }
            // else, begin recovering first JPEG
            else if (already == false)
            {
                already = true;
                sprintf(filename, "%03i.jpg", count);
                count++;
                FILE *img = fopen(filename, "w");
                fwrite(buffer, 1, BLOCK, img);
                fclose(img);
            }
        }
        // if no new JPEG found and JPEG already open, copy the next block
        else if (already == true)
        {
            FILE *img = fopen(filename, "a+");
            fwrite(buffer, 1, BLOCK, img);
            fclose(img);
        }
    }
    
    // close infile
    fclose(file);
    
    // free heap memory
    free(buffer);
    free(filename);
    
    return 0;
}
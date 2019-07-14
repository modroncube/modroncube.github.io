#include <stdio.h>
#include <cs50.h>                                           // CS50 2017 vigenere
#include <stdlib.h>
#include <ctype.h>
#include <string.h>

int i;                                                  // initialise variables
int j;
int change;
int mod;
string plaintext;
string keyword;
string ciphertext;

int main(int argc, string argv[])
{
    keyword = argv[1];
    
    if (argc != 2 || argv[1] == NULL)
    {                   // if command line argument count is NULL or != 2 in total, quit
        printf("usage: ./vigenere [keyword]\n");
        return 1;
    }
    else
    {
        for (int c = 0; c < strlen(keyword); c++)
        {                      // loop through keyword to check it's all alphabetical
            if (!isalpha(keyword[c])){
            printf("keyword must be alphabetical characters only\n");
            return 1;
            }
            else
            {
            keyword[c] = tolower(keyword[c]);                       // if so, set keyword to lowercase
            }                                                       // (for the sake of the formula below)
        }
    }
    
    printf("plaintext: ");                              // otherwise, get plaintext string
    plaintext = get_string();
    
    printf("ciphertext: ");
    
    i = 0;
    j = 0;
    mod = 26;
    
    while (i < strlen(plaintext))
    {                                                   // loop for length of plaintext string
        if (isalpha(plaintext[i]))
        {
            keyword[j] = tolower(keyword[j]);
            if (isupper(plaintext[i]))
            {                                           // if [i] is upper case, change variable for for calculation according
                change = 65;                            // to alphabetical index
            }
            else
            {
                change = 97;                            // likewise if [i] is lower case
            }
        }
        
        else
        {
            change = plaintext[i];                      // if [i] is not alphabetical, leave it
            j--;                                        // and don't proceed on keyword[j]
            mod = 1;
        }
        
        printf("%c", ((((plaintext[i] - change) + keyword[j % strlen(keyword)]) - 97) % mod) + change);  // where the magic happens
        mod = 26;
        i++;
        j++;
    }
    
    printf("\n");
    return 0;
}
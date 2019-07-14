import sys
import crypt
import string                                           # CS50 pset6 crack (python)

def main():
    if len(sys.argv) != 2:                      # if more than two command line arguments, quit
        print("usage: python crack.py [hash]")
        return 1
    else:
        in_hash = sys.argv[1]                   # else, capture hash
     
    salt = in_hash[:2]                          # capture first two characters of hash
    alphabet = get_alphabet()
    scan(alphabet, in_hash, salt)
    return 0
    
def get_alphabet():                             # same as in my C program, two loops to capture A-Z + a-z
    a = ""
    for i in range(65, 65 + 26):
        a += chr(i)
    for j in range(97, 97 + 26):
        a += chr(j)
    return a

def scan(alpha, h, s):
    p = ""                                      # declare password variable
    for i in range(0, len(alpha)):              # check all the characters in alphabet variable
        if match(alpha[i], s, h):
            return
    pos = 0
    for i in range(1, len(alpha) ** 2):         # loop through two-character combinations
        p = alpha[pos] + alpha[i % len(alpha)]
        if match(p, s, h):
            return
        if i != 1 and (i % len(alpha)) == 0:
            pos += 1
    pos = 0
    pos_two = 0
    for i in range(1, len(alpha) ** 3):         # loop through three-character combinations
        p = alpha[pos % len(alpha)] + alpha[pos_two % len(alpha)] + alpha[i % len(alpha)]
        if match(p, s, h):
            return
        if i != 1 and (i % (len(alpha) ** 2)) == 0:
            pos += 1
        if i != 1 and (i % len(alpha)) == 0:
            pos_two += 1
        
    pos = 0
    pos_two = 0
    pos_three = 0
    for i in range(1, len(alpha) ** 4):         # loop through four-character combinations
        p = alpha[pos % len(alpha)] + alpha[pos_two % len(alpha)] + alpha[pos_three % len(alpha)] + alpha[i % len(alpha)]
        if match(p, s, h):
            return
        if i != 1 and i % (len(alpha) ** 3) == 0:
            pos += 1
        if i != 1 and i % (len(alpha) ** 2) == 0:
            pos_two += 1
        if i != 1 and i % len(alpha) == 0:
            pos_three += 1
    return

def match(p, s, h):                             # generate hash, compare with input hash
    out_hash = crypt.crypt(p, s)
    if out_hash == h:
        print("{}".format(p))
        return True

if __name__ == "__main__":
    main()
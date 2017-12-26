#!/usr/bin/env python3

def is_prime(n):
    """
    Decide whether a number is prime or not.
    """
    if n < 2:
        return False
    if n == 2:
        return True
    if n % 2 == 0:
        return False

    i = 3
    maxi = n**0.5 + 1
    while i <= maxi:
        if n % i == 0:
            return False
        i += 2

    return True


def main():
    b = 105_700
    c = 122_700
    h = 0

    for n in range(b, c + 1, 17):
        if not is_prime(n):
            h += 1

    print(h)

##############################################################################

if __name__ == "__main__":
    main()

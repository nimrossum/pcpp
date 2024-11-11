import math


def main():
    n = 1_000_000
    n2 = 10_000_000
    print(est_16(n))
    print(est_32(n))
    print(est_16(n2))
    print(est_32(n2))


def est_16(n):
    return (n * math.log2(n)) / (
        n + n / 2 + n / 4 + n / 8 + math.log2(n / 16) * (n / 16)
    )


def est_32(n):
    return (n * math.log2(n)) / (
        n + n / 2 + n / 4 + n / 8 + n / 16 + math.log2(n / 32) * (n / 32)
    )


if __name__ == "__main__":
    main()

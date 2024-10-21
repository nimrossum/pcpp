# Exercise 8.1

## 1

We did not observe any "strangeness". The results made sense, the more repetitions, the lower standard deviation.


## 2

We changed the test to use Mark7 and ran it using:

```bash
gradlew -PmainClass=exercises08.TestTimeThreads run > 8.1.2.txt
```

See 8.1.2.txt for system information

## 3

Creating a thread takes on this computer average 1140.3 ns +- 8.51 ns.

# Exercise 8.2

Results are reported in 8.2.txt

We expected that using a volatile would be slower, as marking a variable as volatile forces the thread to read the variable from memory every time it is accessed.

However, this is not what we observed. We observed exactly the same times, even across 134217728 runs.

Therefore, we would argue that the results are not plausible as they do not match our expectations. We were very surprised, but we have a theory the the JIT compiler of Java tries to optimize, since it might realize that the volatile variable is not being changed by any other thread and therefore can be kept in higher levels of memory.

## Exercise 8.3

### 1





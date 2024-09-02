## Exercise 2.1

### 1

We identified that one of the two threads used its own lock, while the other thread used a shared lock. This caused the threads to interleave, as the shared lock was not used to protect the critical section.

The solution was to ensure they both used the method that used the same lock. We did this by using the static method instead of the instance method, as the synchronized keyword locks the class object. If it is a static method, the static class is locked, while if it's a non-static method, the instance object is locked, which is local to the thread.

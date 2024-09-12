## Exercise 3.1
### 1
The bounded buffer has been implemented in the BoundedBuffer class

### 2

In order for the bounded buffer to be thread safe it must live up to three criteria:
- No fields can be modified after publication
  - This holds true. All fields in bounded buffer are marked as final.
  - The contents of the queue and semaphores can only be modified using the intended methods because they are private.
- Objects are safely published
  - fields are marked as final and so are safely published.
- Access to the object’s state does not escape
  - All fields are marked as private and the underlying data structure is never accessed

### 3
We do not believe it is possible. Barriers serve a different purpose compared to semaphores,
that is not applicable to this problem. Barriers wait for several threads to reach some state
before proceeding. Here we instead need to dynamically and independently let them interact


## Exercise 3.2
### 1
The Person class has been implemented

### 2
Person is thread safe because every method is using intrinsic locks with the synchronized keyword.
In the constructor we are using a synchronized block with a shared static lock. This is to avoid
multiple objects ending up with the same id.

### 3
A main method has been added to Person.java which creates instances of and uses the Person class

### 4
No errors were found, but this is not enough to confirm that the class is thread safe.
Concurrency issues may happen very rarely so should be arrived at analytically to say anything
with certainty.

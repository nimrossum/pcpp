## Exercise 3.1
### 1
The bounded buffer has been implemented in the BoundedBuffer class

### 2

In order for the bounded buffer to be thread safe it must live up to three criteria:
- No fields can be modified after publication
  - This holds true. All fields in bounded buffer are marked as final.
  - The contents of the queue and semaphores can only be modified using the intended methods
- Objects are safely published
  - fields are marked as final and so are safely published.
- Access to the object’s state does not escape
  - All fields are marked as private and the underlying data structure is never accessed

### 3
We do not believe it is possible. Barriers serve a different purpose compared to semaphores, 
that is not applicable to this problem. Barriers wait for several threads to reach some state
before proceeding. Here we instead need to dynamically and independently let them interact
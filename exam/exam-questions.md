# Exam questions for the examination of Practical Concurrent and Parallel Programming (PCPP) F2023

Below is a list of 10 questions for the exam. The exam will start with a question that you draw (at random) from the list below. As the exam proceeds, teachers and examiners may transition to any of the other questions, or ask questions about any of the topics in the syllabus. All questions have the same structure. First, you are supposed to start by defining, motivating and explaining the concepts introduced in a particular week of the course. After this, you should show some samples of the code you made for the exercises in that week. Teachers and examiners may also show code examples related to the question for you to discuss. While you are answering a question, teachers and examiners might ask about specific details of the question you are answering.

1. **Intro to concurrency and the mutual exclusion problem**: Define and motivate concurrency and mutual exclusion. Explain race conditions, critical sections and the happens-before relation. Show some examples of code from your solutions to the exercises in week 1.

2. **Shared Memory I**: Explain and motivate how locks, monitors and semaphores can be used to address the challenges caused by concurrent access to shared memory. Show some examples of code from your solutions to the exercises in week 2 and 3.

3. **Shared Memory II**: Define and explain what makes a class thread-safe. Explain the issues that may make classes not thread-safe. Show some examples of code from your solutions to the exercises in week 3.

4. **Testing**: Explain the challenges in ensuring the correctness of concurrent programs. Describe different testing strategies for concurrent programs, and their advantages and disadvantages. Show some examples of code from your solutions to the exercises in week 4.

5. **Performance measurements**: Motivate and explain how to measure the performance of Java code. Illustrate some of the pitfalls there are in doing such measurements. Show some examples of code from your solutions to the exercises in week 8.

6. **Performance and Scalability**: Explain how to increase the performance of Java code using threading. Illustrate some of the pitfalls there are in doing this. Show some examples of code from your solutions to the exercises in week 9.

7. **Lock-free Data Structures**: Define and motivate lock-free data structures. Explain how *compare-and-swap* (CAS) operations can be used to solve concurrency problems. Show some examples of code from your solutions to the exercises in week 5.

8. **Linearizability**: Explain and motivate linearizability. Explain for what type of concurrent objects linearizability is specially useful, and describe how it can be applied to reason about their correctness. Show some examples of code in your solutions to the exercises in week 6 where you used linearizability to reason about correctness.

9. **Streams**: Explain and motivate the use of streams to parallelize computation. Discuss issues that arise in operations executed by parallel streams. Show some examples of code from your solutions to the exercises in week 10.

10. **Message Passing**: Explain and motivate the actor model of concurrent computation. Discuss advantages and disadvantages of approaches to distribute computation in actor systems. Show some examples of code from your solutions to the exercises in week 12 and 13.

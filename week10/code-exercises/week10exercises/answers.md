# 10.4

## 1.
Both examples filter out words that have a length greater than 5.

The main thing to understand is that Java streams are pull-based and RxJava is push-based.

This meanst that RxJava can react to changing lists, for instance if the user is typing words in a text field. New pushes are processed in an asynchronous manner. 


# 2.
Since Java Streams are (generally) single-use, 2 streams are created here. The first stream filters words with a length greater than 5, and the second stream filters words with a length greater than 10 independently.

For RxJava, both subscriptions listen to the same source, but with independent filtering criteria. They both process the same source, so the first line does not optimize the second line.
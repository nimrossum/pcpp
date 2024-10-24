## 1
Ensure that class does not escape and is safe for publication. This is achieved by using final and private on the counts array. This is the only state for the class.

The atomic integers can only be accessed and modified through the exposed interface so it is safe for publication and does not escape.

## 2
Yes it does. The scheduler might switch the thread after the do-while loop
and before the return statement, however the correct value will still be
returned once the thread runs again.

## 3

# Phone book

Project that contains `PhoneBookFileSearcher`, `Timer` and `Manager` classes.  
`Main` class only used to run the `Manager.work()` method.

Can be converted to DB searcher if needed

Searching methods list:

+ **Linear search**  
  _Time complexity_: O(n^2)  
  _Space complexity_: O(1)
+ **Bubble sort + jump search**  
  _Time complexity_: O(n^2) + O(sqrt(n))  
  _Space complexity_: O(1)
+ **Quick sort + binary search**  
  _Time complexity_: O(n log n) + O(log n)  
  _Space complexity_: O(1)
+ **Hash table creation + hashtable search**  
  _Time complexity_: O(n) + O(1)  
  _Space complexity_: O(n)
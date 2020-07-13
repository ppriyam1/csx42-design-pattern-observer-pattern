# CSX42: Assignment 3
## Name: Preeti Priyam

-----------------------------------------------------------------------

Following are the commands and the instructions to run ANT on your project.


Note: build.xml is present in [studentskills/src](./studentskills/src/) folder.

Instruction to clean:
####Command: ant -buildfile studentskills/src/build.xml clean

Description: It cleans up all the .class files that were generated when compiled the code.

Instruction to compile:
####Command: ant -buildfile studentskills/src/build.xml all

Description: Compiles code and generates .class files inside the BUILD folder.

Instruction to run:
####Command: ant -buildfile studentskills/src/build.xml run -Dinput="input.txt" -Dmodify="modify.txt" -Dout1="out1.txt" -Dout2="out2.txt" -Dout3="out3.txt" -Derror="error.txt" -Ddebug="2"

Note: Arguments accept the absolute path of the files.

# Justification for the Data Structures used in the assignment in terms of time and/or space complexity.
List: ArrayList
I have used List implementation ArrayList because it is a dynamic size array which is better in terms of space and time complexity as we don't need to define a static size for the array. It's better to store objects in the ArrayList as it provides type checking through generics. Moreover, when compare to other data structure such as LinkedList, Stack, Queue, etc., in terms of time complexity for inserting, reading a value, ArrayList is better.

Time complexity for adding an element into an arbitrary indices of the List : O(n)
Time complexity for adding last element into the List : O(1)
Time complexity for reading an element from List : O(1)
Time complexity for searching an element from List : O(n)

Map: HashMap
I have used Map implementation HashMap. HashMap access element faster when compared to other data structure due to its hashing technique. HashMap provides constant time complexity for basic operations such as get and put if the hash function is properly implemented.

Time complexity for adding an element into Map : O(1)
Time complexity for reading an element from Map : O(1)
Time complexity for searching an element from Map : O(n)

Tree: Binary Search Tree(BST)
I have used the simple Tree implementation of BST. In BST, the lookups can be done in logarithmic time which matters a lot when input is large. We can always keep the cost of insertion, deletion and traversal to O(logN).

[Reference](https://www.geeksforgeeks.org/binary-search-tree-set-1-search-and-insertion)
[Reference](https://www.geeksforgeeks.org/search-a-node-in-binary-tree)

# Observer Pattern Implementation Detail [Reference](https://www.youtube.com/watch?v=_BpmfnqjgzQ)
A node is chosen as a subject which consist a notifyAll method and it takes an argument of Enum Operation which has two values INSERT and MODIFY to depict whether it is an insertion flow or modification.

Similar nodes i.e. matching bNumber are treated as observers to the subject node.

So when a subject calls its notifyAll method then it iterates over its list of observers and let each observer call its own update method, the update method takes an argument of node which is then used by the current observer to set/update its properties.

# Value Add
I have spent major time on the scalability and support to higher number of inputs of the program as I have handled the scenario where this application can support even more than three trees. So now for `n` argument inputs of output file name "out1.txt".."outN.txt" my application will be able to support it and it will create subject and observers respectively.

# Slack day used: 2

## Academic Honesty statement:

"I have done this assignment completely on my own. I have not copied
it, nor have I given my solution to anyone else. I understand that if
I am involved in plagiarism or cheating an official form will be
submitted to the Academic Honesty Committee of the Watson School to
determine the action that needs to be taken. "

Date: [07/12/2020]

# frequent-itemset-mining
A Priori Algorithm

The a priori algorithm finds frequent itemsets in a large amount of data.  It begins by 
finding frequent individual items and then expanding the sets containing those frequent
items.  Such an algorithm is useful in analyzing customers' "baskets" on any website 
selling products or services.  

In order to run the program, type:
javac AprioriAlgorithm.java
java AprioriAlgorithm

An input TXT file must be in the same directory and written as the file to read in the
main method.  In the given input file, the items are simply labeled as integer id's.

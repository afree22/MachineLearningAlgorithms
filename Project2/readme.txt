Project 2
Use the files from Project 1 to read in the .mff files and populate the datasets.
Project 2 adds in classifiers for nominal attributes -- k-nearest neighbors and naive bayes.



KNN is implemented in O(k) space.The command-line switch -k lets users specify the value of k (it defaults to 3).
Naive Bayes using the arg max formulation and uses add-one smoothing in the probabilities. 
P2 also implements routines for evaluating classifiers using a k-fold cross-validation. The command-line option -x to let users specify the number of folds, with 10 as the default. Use the option -s to let users specify a seed for the random-number generator.

The program outputs the accuracy and the standard deviation of the accuracy.

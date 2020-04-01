# Coronavirus HPP project

The following project is inspired by the ongoing events around the coronavirus. 

## Context

Disclaimer: The rules of virus propagation and the generated data are **fictional** for the sake of the pedagogical activity.

The coronavirus hit many european countries. 
These countries generated CSV files to help us to understand the virus propagation. 
The good news for the data analysts is that we have a list of hundred of thousands reported infected cases, and for most of the case we know from whom the case was contracted (some cases are though reported to be contracted from "unknown" person). 
That means that for a person `p` , we known that either that person contracted the virus from `p_j` where `j` in the population of the country reporting contamination cases.  


In our program, we want to identify the chain of contamination with the highest importance. 
Especially, we are asked by the european union parliament to report the top-3 more important chain of contaminated persons we can find in the provided datasets any time a new case is reported. 

### Computing the importance of a contamination chain

We do not know really how long people are contagious. Scientists have provided you a rough model to estimate the importance of a chain of contaminations.  

Basically, a case is considered important with a score of 10 in its 7 first days (168 exactly, inclusive).
Between 7 days (168 hours, exclusive) and 14 days (336 days, inclusive), the case is considered with an importance score of 4.
After 14 days (336 days, exclusive), that case is considered 0 (although it can still participate to a chain of contamination if the overall score of that chain is not 0).
_This model is not perfect, nor pretend to model or even approximate what happened in real life_.


### Input data

Speaking of data, we have the following information about a case :

```
person_id : identifier of the person in his/her country.
person_name : surname and name of the infected person.
person_age : age in years of that person (floored, e.g. 23 years, 10 months 3 days old person will be recorded as 23 years old) 
diagnosed_ts : diagnosed time of coronavirus infection (supposed datetime when the person was supposed to be infected).
contaminated_by : the `person_id` of the person who contaminated this person. The string `unknown` means that this person is the root of a chain of contagions.
```  

_Remark_: Yes, the dataset include names (see `person_name` field). Yes, this goes against all good privacy practices. 
Why include it then ? Remember that these are fictional data: this field is added in this fictional toy project for you to more easily read and debug your program.


### Input streams

We focus on the datasets that we received from three countries: Italy, Spain and France.

The input format for a country is :
```
<person_id, person_surname, person_family_name, date_of_birth, diagnosed_ts, contaminated_by>
```

### Output streams

At any new recorded cases, we want to output the top-3 longest contagious chain as follows:

```
top1_country_origin, top1_chain_root_person_id; top2_country_origin, top2_chain_root_person_id; top3_country_origin, top3_chain_root_person_id

```

Where `topx_country_origin` is the country code where this chain started, and `top1_chain_root_person_id` is the root of that contagious chain (so that we can later reconstruct the chain).   
The actual score of the contagious chain, as well as the content of that chain, is not supposed to be part of the output.


### An example

Consider the *French* dataset as follows :
```
1, "Cerise", "Dupond", "21/01/1963", 1584540000, "unknown"
2, "Hervé", "Renoir", "11/03/1971", 1584540000, "unknown"
```
Notes:  
1584540000 : 03/18/2020 @ 2:00pm (UTC)  
1584712800 : 03/20/2020 @ 2:00pm (UTC)  

Consider the *Italian* dataset as follows :
```
3, "Valentina", "Rossi", "21/01/1963", 1584558000, "1"
4, "Marco", "Guili", "06/01/1956", 1585324800, "unknown"
5, "Stella", "Capelli", "21/01/1949", 1587312000, "4"
```
Notes:  
1584558000 : 03/19/2020 @ 7:00pm (UTC)  
1585324800 : 03/27/2020 @ 4:00pm (UTC)  
1587312000 : 04/19/2020 @ 4:00pm (UTC)  


Consider the *Spanish* dataset as follows :
```
6, "Ricardo", "Rodriguez", "03/10/1964", 1587052800, "4"
```
Notes:  
1587052800 : 04/16/2020 @ 4:00pm (UTC)  

What should happens is what follows :  

1- Process event 1 :   
The program parses each file concurrently. The first event to be processed is the first from France (oldest one - case `person_id` 1)
After this event is processed, the output file contains :
```
France, 1
```

2- Process event second oldest event :  
The program pops the first event from Italy (second oldest event). 
It is linked to the chain started by person Cerise Dupont, which is less than 7 days old: that chain got a score of `10+10=20`. 
no other chain exists.   
Therefore, after this event is processed, the output file contains :
```
France, 1 <-- that was the previous output that we still have in the output file
France, 1 <-- this is what we generate as top 10
```

3- Process next event:  
The program pops the second event from France. It is an event being the root of a new chain of contamination.   
The previous chain still got a score of importance of `20`, and the new one starting with user 2 has the starting score of `10`.
Therefore, after this event is processed, the output file contains :
```
France, 1 <-- that was the previous output that we still have in the output file (event 1)
France, 1 <-- that was the previous output that we still have in the output file (event 2)
France, 1; France, 2 <-- this is what we generate as top 10 for this event
```

4- Process next event  
This is the second event from Italy.   
It is a new chain of contamination (it starts at score 10).  
All three previous event falls into the after 7 days and before 14 days case : they count as 4 each and no longer 10.  
As a consequence, chain starting by `France, 1` got a score of 8, the chain starting by `France, 2` got a score of 4.   
Therefore, after this event is processed, the output file contains :
```
France, 1
France, 1
France, 1; France, 2
Italy, 2; France, 1; France, 2 <-- this is what we generate as top 10 for this event
```

5- Process next event
This is the first event from Spain that must be popped out of the remaining events.
Its timestamp is `04/16/2020 @ 4:00pm (UTC)`. 
All previous cases falls to score 0. Only that case remains. 
Therefore, after this event is processed, the output file contains :
```
France, 1
France, 1
France, 1; France, 2
Italy, 2; France, 1; France, 2 
Spain, 1<-- this is what we generate as top 10 for this event
```

5- Process last event  
We have next to consider the last event (3rd case in Italy).   
That event is supposed to be contamination from `person_id` 4, which no longer concern an active chain of contaminations. 
Consequently, it shall be considered as a root case for a new contamination chain (illustration of Rule 4).
This event happens three days after the last Italian case therefore they both remain at a score of 10. 
When there is a draw, the oldest chain comes first (illustration of Rule 6).
Therefore, after this event is processed, the output file contains :  
```
France, 1
France, 1
France, 1; France, 2
Italy, 2; France, 1; France, 2 
Spain, 1
Spain, 1; Italy 3<-- this is what we generate as top 10 for this event
```

You can see that it is not trivial to be sure that your program works as expected.   
Your first duty (see below) will be to create MORE unit tests and CHECK them on PAPER.   
I insist, this must not be underestimated. It can be trivial to write a lightning fast solution that solves only half of the cases. 
In such cases you would be optimising an algorithm that would be useless !


## Additional rules and edge cases

Some rules to make it more accessible:  
`Rule 0` - Team of 3 persons and the delivery must be a maven Java project with JVM compatibility set to version 8.   
`Rule 1` - It is FORBIDDEN to make a program that will first read all files from all countries, store all the information in memory, and then starts to process it. We do not know in advance the number of cases, it is likely that it may not fits into memory (the real data contains many more informations about cases, and as the number of cases follow an exponential growth, we have to make sure that our program will not easily run out of memory).  
`Rule 2` - Consequently, your program is not allowed to run more than 5 threads (using all 5 threads is part of the ultimate solution : it is just a upper bound given in the project), and you are not allowed to use more than 4Gb of RAM. **@GUILLAUME : on devrait rajouter un champs texte (ex: champs expliquant comment la contamination a eu lieu ?) afin que le bean soit plus lourd en mémoire et éviter le brute force tout en mémoire interdit ici ?**  
`Rule 3` - We consider that we can contract the virus from a person from another country (virus does not stop at frontiers...). So be careful if you are thinking of processing each country in its own thread (which is not impossible but required extra exchange of read-only informations between threads).   
`Rule 4` - If a chain of contamination reach the score 0, it is considered ended (regardless if other people are later reported to be contaminated by someone in this chain - this allows us to free up, if needed, that chain). Ultimately, if a new case appears to be connected to a case from a chain of contamination of score 0, the new case has to be considered as the root of another new chain of contamination.   
`Rule 5` - In input files, all string date are considered UTC time.  
`Rule 6` - In case f a draw of importance score between two chain of contamination, the oldest one has priority (arbitrary choice, everyone has to follow that one).  



## Your job

1- Write down (write words!) more unit tests. This will give you a better understanding of the problem, as well as a larger tests set for later unit testing.  
2- Design (draw circles, squares, patatoïds ...!) an architecture for your program  
3- Implement your unit test set (our tests + yours from step 1). In practice: Given three input files, check that the generated output file is the same as the one that is expected.  
4- Implement (write code !) a first naïce solution but one that pass all tests (make something simple, but which works and build from that)  
5- Optimize your code : devise solutions to make your code faster while still satisfying the unit tests. Observer, benchmark, think, iterate, reimplement (rinse and repeat)._Hint: multithreading and choices of data structures should be your primary source of optimisation at first._  
 

## What you will deliver

A- Documentation on *your* additional unit test set (4pts)
B- Slides to present your naive program architecture, and than what you did to improve it, report performances of your program (4pts)
C- Demonstration of your program and browsing your source code at defense day (program architecture in practice, comments, conception, clarity, implementation of unit tests set) + including questions/answer from teachers (12pt)

As always, we want FIRST a program that we are sure to be correct through unit testing, and only SECOND optimizations.
A project that is faster but for which we are not sure that all cases works fine (including edges cases) will be regarded lower than a slower solution but with maximum functionality coverage. 

We can provide git repositories if needed, just ask Guillaume or Christophe.


_Finally, it is obvious but better made explicit that teachers will take into account for passing the difficulties raised by not being altogether in the same classroom._


## Group of students


### Groups

Defense takes 15 minutes, sharing your screen or sitting around your laptop depending on how the situation will be.
5 minutes to switch groups.

May, 25th, 2020

| Grp nb | List of students  | Defence | How to get the code |
| ------ | ------------------ | ----------------- | ------- | 
| 1      |  x / y / z   |  | |
| 2      |  x / y / z    |  | |
| 3      |  x / y / z  |  | |
| 4      |  x / y / z  |  | |
| 5      |  x / y / z   |  | |
| 6      |  x / y / z   |  | |
| 7      |  x / y / z   |  | |
| 8      |  x / y / z  |  | |
| 9      |  x / y / z     |  | |

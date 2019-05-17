# Project

### Objective
In this project, we will be creating a solution to one of the [DEBS grand challenges](https://debs.org/grand-challenges/).

Among them, we will be interested in the 2016 edition. In this edition, we have to process a data stream of social network data. Given a stream of users, comments, likes, and friendships link, we want to answer the following queries :
Q1- Identification of the posts that currently trigger the most activity in the social network
Q2- Identification of large communities that are currently involved in a topic

The complete description of the queries expections is located [here](https://debs.org/grand-challenges/2016/).
Full data are locatd [here](http://datasets-satin.telecom-st-etienne.fr/jsubercaze/wikidump/).
Mootse contains two unit tests for query 1.

In this project, we want to leverage what we have learnt in this course to provide an accurate and fastest solution possible to the chosen query.

As a consequence, * you are to solve ONE of the two possible queries * (pick the one you prefer, there equally difficult). 
Note: maybe you would have ilke to apply your new knowledge on HPP to another use case you had in mind (like you have a side project for instance). We may allow you to work on your preferred topic that you bring, provided we discuss/validate it beforehand.

### Rules

There are some contraints :
- Team of 2 persons
- The solution has to be a maven Java project.
- The hardware requirements are 4 CORES, 8Gb RAM (you cannot store all information in memory ...)

### Recommendations
Since we participated in this contest, we have a few guidances to help you :
- Start by creating simple unit tests containing few events (like a dozens or a bit more). This will force you to draw on paper what to do, and understand the subtilities of the queries
- It is less likely that we will use SIMD in that project (or at least it not as obvious as using multi-threading, choosing thread-safe data structures, devising some algorithms tricks to store/update the data)
- Start by implementing a naive solution, that will be very suboptimal. Measure the bandwidth and latency to process the dample data file and the full data, and then iterate from this.

### Evaluation
On the last session of the module, we will ask you to present us :
- your code architecture, what strategies you use to realize the queries, how it works
- the performance (bandwidth/latency) that you achieve
There won't be any presentations using slides, instead we will sit next to your labtop and discuss around your code.


### Groups

Defense takes 15 minutes, sitting around your laptop. 5 minutes to switch groups.

| Grp nb | Query              | List of students  | Defence |
| ------ | ------------------ | ----------------- | ------- |
| 1      |  Q1  |  Poulat / Jarousse / Mercier   | 9:00 | 
| 2      |  Q1  |  Moulaire / Pinier-Rafer  | 9:20 |
| 3      |  Q1  |  Nsossani / Desclaux   | 9:40 |
| 4      |  Q1  |  Massad / Yahiaoui / Bernard  | 10:00 |
| 5      |  Q1  |  Zhizhou / Gukunpeng    | 10:20 |
| 6      |  Q1  |  Kamissokho / Valadares / Chapuis | 10:40 |
| 7      |  Q1? |  Ferrand / Giraud | 11:00 |
| 8      |  Q1  |  Audemard / Muller / Joulin | 11:20 |
| 9      |  Q2  |  Coudot / Dupuis / Mion  | 11:40 |

# BufferReplacement
A simple Java implementation demonstrating common buffer replacement schemes

## About ##
I wrote this in November 2016 to solve textbook buffer replacement problems (i.e. "given this stream of pages, how many I/Os occur with this buffer size using this policy?" etc). I had largely forgotten about it but I decided to publish it on GitHub in case any other students of Database courses find it useful. There are also some other extension ideas I thought of that would be interesting and this was a good start. It's also just a fun idea for coding practice in general. 

The program simulates the basic concepts behind a number of buffer replacement policies and records each step of the process, so if you found yourself confused by this mostly-intuitive-yet-often-poorly-explained topic, hopefully seeing them in action will aid your understanding.

## Background ##

Wikipedia page on the subject: https://en.wikipedia.org/wiki/Cache_replacement_policies

As you probably know, accessing data on secondary memory (e.g. hard-drives) is very slow. Accessing data from main memory (e.g. Random Access Memory, RAM) is magnitudes faster, but main memory is far scarcer. Consider that your computer's hard drive is likely in the terabyte range while having about 4-32 gigabytes of RAM; your secondary memory has a capacity several hundred times greater than that of your primary memory. 

A database deals with datasets that far exceed the maximum capacity of the main memory on any given system yet it can't be constantly dealing with the comparatively enormous memory read times of the secondary memory. Therefore, for maximum performance, the database must dynamically load data in-and-out of main memory with a selection criteria that minimises the amount of time fetching the data from the secondary memory.

Data in memory is grouped into **pages** (typically about 4 kilobytes each). On the main memory there exists a **buffer** (array) of **frames** which may hold the pages. The number of frames is limited, so we must choose a policy which chooses an existing page to evict when we get a request for a page that is not currently in the buffer. 

A cache-hit is when we get a page request for a page which is in the buffer, therefore we do not need to read from secondary memory.
A cache-miss is when we get a page request for a page which is not in the buffer. The database management system must read from secondary memory, which is costly. Therefore the goal of any policy is to minimise the number of secondary management accesses (also called "disk I/O operations"). 

## Policies ##
The following policies are implemented:
- Clock
- FIFI (First-In-First-Out)
- GClock (Generalised Clock)
- LRU (Least-Recently Used)
- MRU (Most-Recently-Used)

Coming up in the near-future:
- LFU (Least-Frequently-Used)

More details on each policy are given in the code documentation.
Feel free to recommend any other policies for implementation and I will see what I can do. 

## Usage ##
Clone the repository and load the files into your IDE of choice. 

The main method in the class Main includes an example page sequence and buffer size; it runs these through all the implemented policies and prints the steps to the output. 

At this stage, there is no reading from standard input as I put this all together very quickly and used it briefly as part of my own study; this initial commit simply included my original Java classes as well as some added documentation. I will add input functionality in the near future. In the meantime, you will have to edit the main method to run your own inputs.

The steps are printed in the following format:
- t(Step  Number) : (<CurrentPage, CurrentReferenceValue>), [(<BufferPage1, ReferenceValue1>), (<BufferPage2, ReferenceValue2>), ...], <TotalI/O>

For example, on step 9 where we are requesting page G and the buffer now (after adding/revising G) contains G, C, B, F, E and having 7 I/O requests in previous iterations, the following is printed:
- t9: (G,0), [(G,0), (C,0), (B,0), (F,0), (E,0)], 7

Each page has a unique single-character name (letters are good choices). When defining a sequence, repeated occurrences of one character are assumed to mean repeated requests for a single page referenced by that character. Therefore in the sequence {'D', 'A', 'B', 'D', 'D', 'C'}, there are 4 unique pages, one of which (D) is requested 3 times. Simply specify a buffer size, a page sequence, and a policy to use, and the Policy::execute() method will take care of the rest of the work.

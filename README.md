# sales-data-analyzer

Simple sales data analyzer, 100% coded in Java.

I have not programmed in Java for the last 20 years, and the last things I did were simple college jobs.

In this project I had to learn Java, and tried to use best programming practices within the context of the language. I believe that many things can still be improved, but as a first exercise, I believe it is good.

## Requirements

* maven
* java version 1.8 or greather

## How to use

```
git clone https://github.com/alexgarzao/sales-data-analyzer.git
cd sales-data-analyzer
make test

make run
```

After that, all files in "data/in/*.dat" (default) will be interpreted. Stats file will be saved on "data/out", and the original file will be moved to "data/proc". Logs are sent to the console and to the log file at "data/log/sda.log".

## Flat file layout

(COPY&PASTE from where a saw this exercise)

There are 3 kinds of data inside those files. For each kind of data there is a different layout.

### Salesman data

Salesman data has the format id 001 and the line will have the following format.
```
001çCPFçNameçSalary
```

### Customer data

Customer data has the format id 002 and the line will have the following format.

```
002çCNPJçNameçBusinessArea
```

### Sales data
Sales data has the format id 003. Inside the sales row, there is the list of items, which is
wrapped by square brackets []. The line will have the following format.

```
003çSaleIDç[ItemID-ItemQuantity-ItemPrice]çSalesmanname
```

### Sample file data
The following is a sample of the data that the application should be able to read. Note that this is
a sample, real data could be 100% different.

```
001ç1234567891234çDiegoç50000
001ç3245678865434çRenatoç40000.99
002ç2345675434544345çJosedaSilvaçRural
002ç2345675433444345çEduardoPereiraçRural
003ç10ç[1-10-100,2-30-2.50,3-40-3.10]çDiego
003ç08ç[1-34-10,2-33-1.50,3-40-0.10]çRenato
```

### Data analysis
Your system must read data from the default directory, located at %HOMEPATH%/data/in.
The system must only read .datfiles.
After processing all files inside the input default directory, the system must create a flat file inside
the default output directory, located at %HOMEPATH%/data/out. The filename must follow this
pattern, {flat_file_name}.done.dat.
The output file contents should summarize the following data:
* Amount of clients in the input file
* Amount of salesman in the input file
* ID of the most expensive sale
* Worst salesman ever

Stats data has the format id 099 and the line will have the following format.

```
099çTotalSalesMançTotalCustomersçMostExpensiveSaleIdçWorstSalesman
```


## Some design decisions

* 1 thread per file: In my opinion, there are a lot of IO, and just a little of CPU time to process these files. It seems to be a classical IO bound application. With this in mind, it's not necessary to split a huge file and process them in parallel. In Linux, AFAIK, if when one file is opened, if is passed a "look ahead" option, the OS do some improvements to speed up this file read.
* Is prepared to huge files? In my opinion, yes. With these files, the memory consumed to process is proportional to the number of sellers, and isn't proportional to the file size.
* All the data, during the process, are keeped in memory. With this in mind, wasn't necessary to use a DBMS.
* The max size of each record line and each token is proportional to the size of a Java string (INT.MAX).

## TO DO

A lot of things in my mind :-)

* How the architecture works, and why
* If the thread pool queue increases a lot, it's interesting to take a look at this (hint 5): https://www.nurkiewicz.com/2014/11/executorservice-10-tips-and-tricks.html
* Check if CPF and CNPJ are valids
* Check if the salesmans, in a sell, are present as a record 002
* Check for duplicated sales id
* /metrics to response to some system (like prometheus, for example)
* Capture CTRL+C
* Lint tools, code coverage, ...
* Docker
* Pass arguments throw command line
* Functional tests with huges files
* A way to escape de field delimiter (is it necessary?)
* A better organization on the data sample tests
* A way to specify the log level
* FileWatcher could use an interface to notify a resource when exist a new file
* Try to use mmap (memory map file): https://www.codeproject.com/Tips/683614/Things-to-Know-about-Memory-Mapped-File-in-Java
* ...

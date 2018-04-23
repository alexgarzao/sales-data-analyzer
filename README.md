# sales-data-analyzer

Simple sales data analyzer, 100% coded in Java.

## Requirements

* maven
* java versao 1.8 or greather

## How to use

```
git clone XXX
cd sales-data-analyzer
make test

make run
```

After that, all files in "data/in/*.dat" (default) will be interpreted. Stats file will be saved on "data/out", and the original file will be moved to "data/proc". Besides that anyone could see the logs on the console, all logs are keeped in a log file at "data/log/sda.log".

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


## Some design decisions

* 1 thread per file: In my opinion, there a lot of IO, and just a little of CPU time to process these files. With this in mind, it's not necessary split huge file and process in parallel. In Linux, AFAIK, if when one file is open, if is passed a "look ahead" option, the OS do some improvements to speed up the file read.
* Is prepared to huge files? In my opinion, yes. With these files, the memory consumed to process is proportional to the number of sellers.
* All the data, during the process, are keeped in memory. With this in mind, wasn't necessary to use a DBMS.
* The size of each record line and each token is proportional to the size of a Java string (INT.MAX)

## TO DO

A lot of things in my mind :-)

* If the thread pool queue increase a lot, it's interesting to take a look at this (hint 5): https://www.nurkiewicz.com/2014/11/executorservice-10-tips-and-tricks.html
* Check if CPF and CNPJ are valids
* Check if the salesman, in a sell, are present as a record 002
* /metrics to responde to some system with stats (prometheus, for example)
* Capture CTRL+C
* Lint tools, code coverage, ...
* Docker
* Arguments throw command line
* Functional tests with huges files






formato de entrada/saida
formato do arquivo de saida (registro 099)

…

//////////////////




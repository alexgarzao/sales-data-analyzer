# sales-data-analyzer

Simple sales data analyzer, 100% coded in Java.

## Requirements

* maven
* java versao 1.8 or greather

## How to use

```git clone XXX
cd sales-data-analyzer
make test

make run
```

After that, all files in "data/in/*.dat" (default) will be interpreted. After that, a stat file will be saved on "data/out", and the original file will be moved to "data/proc". Besides that anyone could see the logs on console, all logs are keepd in a log file in "data/log/sda.log".






formato de entrada/saida
arquitetura
Deixar claro o por que das decisões
1 thread por arquivo…. como tem muito I/O, e o cálculo é “leve”, não vale o preço de separar o arquivo e processar em paralelo. Até porque o S.O, se configurado na abertura do arquivo, vai privilegiar a leitura sequencial fazendo o “look ahead”
Preparado para grandes arquivos?
Acredito que o problema seria a quantidade de vendedores, e não o tamanho do arquivo
Precisa banco? Só se forem muitos vendedores
strings muito grandes no arquivo? e linhas muito grandes?
string tem suporte a INT.MAX
formato do arquivo de saida (registro 099)
melhorias previstas…
Hint 5 from here: https://www.nurkiewicz.com/2014/11/executorservice-10-tips-and-tricks.html
validar cpf/cnpj
validar vendedor desconhecido
api para responder com stats (prometheus?)
capturar CTRL+C
Cobertura de codigo? Lint?
Docker?
parametros via linha de comando
poder definir Nível de log (debug, info, warning, error?)
classe mais generica: watcher
Gerar arquivos gigantes para o teste e valida o resultado
…

//////////////////

Flat file layout
There are 3 kinds of data inside those files. For each kind of data there is a different layout.
Salesman data
Salesman data has the format id 001 and the line will have the following format.
001çCPFçNameçSalary
Customer data
Customer data has the format id 002 and the line will have the following format.
002çCNPJçNameçBusinessArea
Sales data
Sales data has the format id 003. Inside the sales row, there is the list of items, which is
wrapped by square brackets []. The line will have the following format.
003çSaleIDç[ItemID-ItemQuantity-ItemPrice]çSalesmanname

Sample file data
The following is a sample of the data that the application should be able to read. Note that this is
a sample, real data could be 100% different.
001ç1234567891234çDiegoç50000
001ç3245678865434çRenatoç40000.99
002ç2345675434544345çJosedaSilvaçRural
002ç2345675433444345çEduardoPereiraçRural
003ç10ç[1-10-100,2-30-2.50,3-40-3.10]çDiego
003ç08ç[1-34-10,2-33-1.50,3-40-0.10]çRenato
Data analysis
Your system must read data from the default directory, located at %HOMEPATH%/data/in. The
system must only read .datfiles.
After processing all files inside the input default directory, the system must create a flat file inside
the default output directory, located at %HOMEPATH%/data/out. The filename must follow this
pattern, {flat_file_name}.done.dat.
The output file contents should summarize the following data:
● Amount of clients in the input file
● Amount of salesman in the input file
● ID of the most expensive sale
● Worst salesman ever



///////////////////



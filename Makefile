test: clean prepdata
	mvn test

run: clean prepdata
	mvn package
	java -cp target/sales-data-analyzer-1.0-SNAPSHOT.jar sales_data_analyzer.App

clean:
	rm -f data/log/*
	rm -f data/out/*
	rm -f data/proc/*

prepdata:
	cp samples/test/base.dat data/in/base.dat
	cp samples/test/base_2.dat data/in/base_2.dat
	cp samples/test/base.dat data/in/basex3.dat
	cp samples/test/base.dat data/in/base.xxx
	cp samples/test/* samples/data/in

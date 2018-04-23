rebuild: clean
	mvn clean

test: clean prepdata
	mvn test

run:
	mvn package
	java -cp target/sales-data-analyzer-1.0-SNAPSHOT.jar sales_data_analyzer.App

clean:
	rm -f data/in/*
	rm -f data/log/*
	rm -f data/out/*
	rm -f data/proc/*

prepdata:
	mkdir -p samples/data/in
	mkdir -p data/in
	cp samples/test/* samples/data/in
	cp samples/test/* data/in

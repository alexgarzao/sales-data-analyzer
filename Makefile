test:
	mvn test
run:
	mvn package
	java -cp target/sales-data-analyzer-1.0-SNAPSHOT.jar sales_data_analyzer.App

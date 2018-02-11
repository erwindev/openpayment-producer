# Open Payment Producer

This application reads the Open Payment Data from [CMS.gov](https://www.cms.gov/OpenPayments/Explore-the-Data/Dataset-Downloads.html) and sends data to a Kafka Queue.  This application is written in Kotlin.

## Run the application
```
$ git https://github.com/erwindev/openpayment-producer.git
$ cd openpayment-producer
$ gradle build
$ java -jar build/libs/openpayment-producer-1.0.jar
```
note: Make sure the [Health Care ETL application](https://github.com/erwindev/healthcare-etl) is running.
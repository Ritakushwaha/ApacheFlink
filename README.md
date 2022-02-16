# Apache Flink Projects

## Download and install Apache flink

* Download Apache Flink version 1.14.3
```
wget https://dlcdn.apache.org/flink/flink-1.14.3/flink-1.14.3-bin-scala_2.11.tgz -P/tmp
```
* Untar downloaded file to /opt
```
sudo tar xf /tmp/flink-1.14.3-bin-scala_2.11.tgz -C /opt
```
* Install the alternative version for the mvn in your system
```
sudo update-alternatives --install /usr/bin/flink flink /opt/flink-1.14.3/bin/flink 100
```
* Check if your configuration is ok. You may use your current or the 3.8.4 whenever you wish, running the command below.
```
sudo update-alternatives --config flink
```
* Change alternative to new one (Not needed if mvn version is already latest)
```
sudo update-alternatives --config flink

# Press enter to keep the current choice[*], or type selection number: 2 
```
* Check the mvn version
```
flink -version
```
* type command
```
type -a flink
```
* Set the path
```
export PATH=/opt/flink-1.14.3/bin/:$PATH
```

## Error - SLF4J: Failed to load class "org.slf4j.impl.StaticLoggerBinder". Add below dependency to resolve this.
```
<!-- https://mvnrepository.com/artifact/org.slf4j/slf4j-api -->
    <dependency>
      <groupId>org.slf4j</groupId>
      <artifactId>slf4j-api</artifactId>
      <version>2.0.0-alpha6</version>
    </dependency>
```

# Word Count program using DataSet API
#### DataSet API soft Deprecated after 1.12

## Create jar file
```
mvn cleancompile
```
```
mvn package
```

## Start the cluster
```
./bin/start-cluster.sh
```

## Run the jar file
```
./bin/flink run /home/rita/Documents/ApacheFlink/WordCount/target/WordCount-1.0-SNAPSHOT.jar --input /home/rita/Documents/ApacheFlink/WordCount/input.txt --output /home/rita/Documents/ApacheFlink/WordCount/output.csv
```

# Word Count program using DataStream API

## Create jar file
```
mvn cleancompile
```
```
mvn package
```

## Start the cluster
```
./bin/start-cluster.sh
```

## Up the socket
```
nc -l 9999
```

## Run the jar file
```
./bin/flink run /home/rita/Documents/ApacheFlink/WordsCount/target/WordsCount-1.0-SNAPSHOT.jar
```





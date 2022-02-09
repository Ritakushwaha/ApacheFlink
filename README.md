# Apache Flink Projects

## Download and install Apache flink

* Download Apache Flink version 1.14.3
```
wget https://dlcdn.apache.org/flink/flink-1.14.3/flink-1.14.3-bin-scala_2.11.tgz -P/tmp
```
* Untar downloaded file to /opt
```
$sudo tar xf /tmp/apache-maven-*.tar.gz -C /opt
```
* Install the alternative version for the mvn in your system
```
$sudo update-alternatives --install /usr/bin/mvn mvn /opt/apache-maven-3.8.4/bin/mvn 100
```
* Check if your configuration is ok. You may use your current or the 3.8.4 whenever you wish, running the command below.
```
$sudo update-alternatives --config mvn
```
* Change alternative to new one (Not needed if mvn version is already latest)
```
sudo update-alternatives --config mvn

# Press enter to keep the current choice[*], or type selection number: 2 
```
* Check the mvn version
```
$mvn -version
```
* type command
```
$type -a mvn
```
* Set the path
```
$export PATH=/opt/apache-maven-3.8.4/bin/:$PATH
```
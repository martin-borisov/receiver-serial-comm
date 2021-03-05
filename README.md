# receiver-serial-comm
Provides the ability to control various Yamaha receivers over the serial port (i.e. RS-232).

Example use cases are IR remote control replacement, controlling the receiver via a smart phone through e.g. a Raspberry Pi and a mobile SSH app, automating the receiver with smart devices and various others.

## Supported receivers
Tested receivers:
* RX-V2400
* RX-V2400RDS

Receivers assumed to work, but not tested:
* RX-V1400
* RX-V1700
* RX-V2700
* RX-Z9

## How to run
Building the maven project produces a single all-in-one jar file which is all that is needed to run the app. 

## Example commands

Poweron the receiver.
```
java -jar receiver-serial-comm-0.0.1-SNAPSHOT-jar-with-dependencies.jar --port ttyUSB0 --command poweron
```

Increase the volume by one tick.
```
java -jar receiver-serial-comm-0.0.1-SNAPSHOT-jar-with-dependencies.jar --port ttyUSB0 --command volumeup
```

Change the input to tuner.
```
java -jar receiver-serial-comm-0.0.1-SNAPSHOT-jar-with-dependencies.jar --port ttyUSB0 --command input --param tuner
```

Currently the list of supported commands is limited, but will hopefully expand in the future.

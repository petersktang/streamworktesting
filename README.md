# streamworktesting

Steps to Run:

1. Set scripts to execute

```
$ cd streamworktesting
$ chmod 755 bin/*
```
2. Edit properties under config/
3. Execute build
```
bin/build.sh
```
4. Execute kafka listener
```
bin/kafka-listner.sh
```
5. Execute one-usa-gov stream consumer
```
bin/one-usa-gov-stream-consumer.sh
```


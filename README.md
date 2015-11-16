# Getting Started #

## Install Oracle JDK 7 ##

Add notes for this...

### Configure JCE ###
http://www.oracle.com/technetwork/java/javase/downloads/jce-7-download-432124.html

## Linux / OSX ##
```
./gradlew run
```
## Windows ##
```
gradlew.bat run
```
## Creating the SSH Keys ##

### PKCS #12 ###
https://docs.oracle.com/cd/E19509-01/820-3503/ggfhb/index.html

### JKS ###
https://docs.oracle.com/cd/E19509-01/820-3503/ggfen/index.html

### Converting a PKCS #12  Keystore to a JKS File ###

```
keytool -importkeystore -srckeystore yourhostname.p12 -srcstoretype pkcs12 -destkeystore keystore.jks -deststoretype jks
```

## Configuring MarkLogic Server ##

There are 4 XQuery modules in src/main/resources/xquery that can be used as templates for configuring MarkLogic for SSL.  You will need to run them in the order described below:

- insert-template.xqy
- setup-certificates.xqy
- create-xdbc-server.xqy
- configure-certs-with-app-server.xqy




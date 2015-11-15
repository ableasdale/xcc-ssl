# Getting Started #

## Install Oracle JDK 7 ##

Add notes for this...

### Configure JCE ###
http://www.oracle.com/technetwork/java/javase/downloads/jce-7-download-432124.html

## Linux / OSX ##
./gradlew run

## Windows ##
gradlew.bat run

## Creating the SSH Keys ##

(Incomplete - notes at this stage)

### Create the JKS File From your P12 Keystore ###

 keytool -importkeystore -srckeystore yourhostname.p12 -srcstoretype pkcs12 -destkeystore keystore.jks -deststoretype jks

## Configuring MarkLogic Server ##

There are 4 XQuery modules in src/main/resources/xquery that can be used as templates for configuring MarkLogic for SSL

Run them in order
- insert-template.xqy
- setup-certificates.xqy
- create-xdbc-server.xqy
- configure-certs-with-app-server.xqy




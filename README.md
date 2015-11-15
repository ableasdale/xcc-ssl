# Getting Started #

## Install Oracle JDK 7 ##

### Configure JCE ###
http://www.oracle.com/technetwork/java/javase/downloads/jce-7-download-432124.html


## Linux / OSX ##
run gradlew

./gradlew run

## Windows ##
run gradlew.bat


## Create the SSH Keys ##

(Incomplete - notes at this stage)

### Create the JKS File From your P12 Keystore ###

 keytool -importkeystore -srckeystore yourhostname.p12 -srcstoretype pkcs12 -destkeystore keystore.jks -deststoretype jks



# Getting Started #

## Install Oracle JDK 7 ##

```bash
rpm -Uvh /path/to/binary/jdk-7u80-linux-x64.rpm

alternatives --install /usr/bin/java java /usr/java/jdk1.7.0_80/jre/bin/java 200000
alternatives --install /usr/bin/javaws javaws /usr/java/jdk1.7.0_80/jre/bin/javaws 200000
alternatives --install /usr/lib/mozilla/plugins/libjavaplugin.so libjavaplugin.so /usr/java/jdk1.7.0_80/jre/lib/i386/libnpjp2.so 200000
alternatives --install /usr/lib64/mozilla/plugins/libjavaplugin.so libjavaplugin.so.x86_64 /usr/java/jdk1.7.0_80/jre/lib/amd64/libnpjp2.so 200000
alternatives --install /usr/bin/javac javac /usr/java/jdk1.7.0_80/bin/javac 200000
alternatives --install /usr/bin/jar jar /usr/java/jdk1.7.0_80/bin/jar 200000

alternatives --config java
alternatives --config javaws
alternatives --config libjavaplugin.so.x86_64
alternatives --config javac

# THEN ADD TO BASH PROFILE
export JAVA_HOME="/usr/java/jdk1.7.0_80"
```

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




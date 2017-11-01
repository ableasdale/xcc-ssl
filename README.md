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

## Running the application
 
### Linux / OSX 
```
./gradlew run
```
### Windows (CMD)
```
gradlew.bat run
```
### Windows (PowerShell)
```
.\gradlew run
```

## Creating the SSH Keys 

### Oracle Documentation: PKCS #12 
* https://docs.oracle.com/cd/E19509-01/820-3503/ggfhb/index.html

### Oracle Documentation: JKS ###
* https://docs.oracle.com/cd/E19509-01/820-3503/ggfen/index.html

#### Other useful links

* https://support.globalsign.com/customer/en/portal/articles/2121490-java-keytool---create-keystore

## Example step-by-step configuration: Using keytool

Start by creating the keystore; to do this run the following:

```bash
keytool -genkey -alias client -keyalg RSA -keystore keystore.jks
```

Then answer the following questions on-screen:

```bash
keytool -genkey -alias client -keyalg RSA -keystore keystore.jks
Enter keystore password:
Re-enter new password:
What is your first and last name?
  [Unknown]:  www.marklogic.com
What is the name of your organizational unit?
  [Unknown]:  Support
What is the name of your organization?
  [Unknown]:  MarkLogic Corporation
What is the name of your City or Locality?
  [Unknown]:  San Carlos
What is the name of your State or Province?
  [Unknown]:  California
What is the two-letter country code for this unit?
  [Unknown]:  US
Is CN=www.marklogic.com, OU=Support, O=MarkLogic Corporation, L=San Carlos, ST=California, C=US correct?
  [no]:  yes

Enter key password for <client>
        (RETURN if same as keystore password):
Re-enter new password:
```

This should create a file called **keystore.jks**.

This file can be converted into a PKCS #12 file by running:

```bash
keytool -importkeystore -srckeystore keystore.jks -destkeystore keystore.p12 -deststoretype PKCS12
```

You can get the text for certificate and the private key (used for setting up the certificates in MarkLogic Server) by running:

```bash
openssl pkcs12 -in new-store.p12 -nodes
```

The values returned can be pasted into **2-setup-certificates.xqy** and then the module can be run against the Security database

### Converting a PKCS #12  Keystore back into a JKS File ###

```bash
keytool -importkeystore -srckeystore yourhostname.p12 -srcstoretype pkcs12 -destkeystore keystore.jks -deststoretype jks
```

## Configuring MarkLogic Server ##

There are 4 XQuery modules in src/main/resources/xquery that can be used as templates for configuring MarkLogic for SSL.  You will need to run them in the order described below:

- 1-insert-template.xqy
- 2-setup-certificates.xqy
- 3-create-xdbc-server.xqy
- 4-configure-certs-with-app-server.xqy

When you run the application, you should see something like this:

```log
22:59:26.131 [main] INFO com.marklogic.support.SSLTest - Starting SSL test
22:59:26.181 [main] INFO com.marklogic.support.SSLTest - Test 1
22:59:26.186 [main] DEBUG com.marklogic.support.SSLTest - SSL URI: xccs://admin:admin@localhost:9995
22:59:26.186 [main] DEBUG com.marklogic.support.SSLTest - Certificate Location is: src/main/resources/keystore.jks
22:59:26.513 [main] INFO com.marklogic.support.SSLTest - Result: 15095771404323455
```


xquery version "1.0-ml";

import module namespace pki = "http://marklogic.com/xdmp/pki" at "/MarkLogic/pki.xqy";

declare namespace x509 = "http://marklogic.com/xdmp/x509";
declare namespace ssl = "http://marklogic.com/xdmp/ssl";

(: Variables :)
declare variable $TEMPLATE-NAME := "test-template";

declare variable $OPTIONS :=
    <pki:key-options xmlns="ssl:options">
        <key-length>2048</key-length>
    </pki:key-options>;


declare variable $X509 :=
    <x509:req>
        <x509:version>0</x509:version>
        <x509:subject>
            <x509:countryName>US</x509:countryName>
            <x509:stateOrProvinceName>CA</x509:stateOrProvinceName>
            <x509:localityName>San Carlos</x509:localityName>
            <x509:organizationName>MarkLogic</x509:organizationName>
            <x509:organizationalUnitName>Engineering</x509:organizationalUnitName>
            <x509:commonName>my.host.com</x509:commonName>
            <x509:emailAddress>user@marklogic.com</x509:emailAddress>
        </x509:subject>
        <x509:v3ext>
            <x509:basicConstraints critical="false">CA:TRUE</x509:basicConstraints>
            <x509:keyUsage critical="false">Certificate Sign, CRL Sign</x509:keyUsage>
            <x509:nsCertType critical="false">SSL Server</x509:nsCertType>
            <x509:subjectKeyIdentifier critical="false">B2:2C:0C:F8:5E:A7:44:B7</x509:subjectKeyIdentifier>
        </x509:v3ext>
    </x509:req>;

pki:insert-template(
    pki:create-template(
        $TEMPLATE-NAME,
        "Creating a new template",
        "rsa",
        $OPTIONS,
        $X509
    )
)
xquery version "1.0-ml";

(: Configuration Script 1: Run this against the Security Database :)

import module namespace pki = "http://marklogic.com/xdmp/pki" at "/MarkLogic/pki.xqy";

declare namespace x509 = "http://marklogic.com/xdmp/x509";

(: Variables :)

declare variable $TEMPLATE-NAME := "test-template";
declare variable $ORGANIZATION := "YOUR_ORG_NAME_HERE";
declare variable $X509 as element(x509:req) :=
    <x509:req>
        <x509:version>0</x509:version>
        <x509:subject>
            <x509:organizationName>{$ORGANIZATION}</x509:organizationName>
        </x509:subject>
    </x509:req>;

(: Configuration :)

let $tid :=
    pki:insert-template(
        pki:create-template(
            $TEMPLATE-NAME,
            "",
            (),
            (),
            $X509
        )
    )
return pki:generate-template-certificate-authority($tid, 365)

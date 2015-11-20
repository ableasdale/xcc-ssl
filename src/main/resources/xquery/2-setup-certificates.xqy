xquery version "1.0-ml";

(: Configuration Script 2: Run this against the Security Database :)

import module namespace pki = "http://marklogic.com/xdmp/pki" at "/MarkLogic/pki.xqy";

declare variable $TEMPLATE-NAME as xs:string := "test-template";

declare variable $EXTERNAL-CERT := text{'-----BEGIN CERTIFICATE-----
YOUR EXTERNAL CERTIFICATE GOES INSIDE THIS text{} NODE
-----END CERTIFICATE-----'};

declare variable $PRIVATE-KEY := text{'-----BEGIN PRIVATE KEY-----
YOUR PRIVATE KEY GOES INSIDE THIS text{} NODE
-----END PRIVATE KEY-----'};

pki:insert-host-certificate(
        pki:template-get-id(pki:get-template-by-name($TEMPLATE-NAME)),
        $EXTERNAL-CERT,
        $PRIVATE-KEY
)
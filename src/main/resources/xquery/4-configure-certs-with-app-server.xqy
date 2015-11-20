xquery version "1.0-ml";

(: Configuration Script 4: Configure the SSL Client Certificates with the XDBC Server :)

import module namespace pki = "http://marklogic.com/xdmp/pki" at "/MarkLogic/pki.xqy";
import module namespace admin = "http://marklogic.com/xdmp/admin" at "/MarkLogic/admin.xqy";

declare namespace x509 = "http://marklogic.com/xdmp/x509";

declare variable $ORGANIZATION-NAME := "ACME CORPORATION";
declare variable $ORGANIZATION-NAME-2 := "ACME CERTIFICATION CORP";

declare variable $XDBC-SERVER := "xdbc-9995";
declare variable $GROUP := "Default";
declare variable $CONFIG := admin:get-configuration();

let $groupid := admin:group-get-id($CONFIG, $GROUP)
let $appservid := admin:appserver-get-id($CONFIG, $groupid, $XDBC-SERVER)

let $cert-id :=
    for $cert in pki:get-certificates(pki:get-trusted-certificate-ids())
    where $cert/x509:cert/x509:subject/x509:organizationName eq $ORGANIZATION-NAME
            (: Note you may want to delete this below if you don't have a chain of certificates :)
            or $cert/x509:cert/x509:subject/x509:organizationName eq $ORGANIZATION-NAME-2
    return fn:data($cert/pki:certificate-id)

return
    admin:save-configuration(
        admin:appserver-set-ssl-client-certificate-authorities(
            $CONFIG,
            $appservid,
            $cert-id))
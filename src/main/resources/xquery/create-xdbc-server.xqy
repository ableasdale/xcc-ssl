xquery version "1.0-ml";

import module namespace admin = "http://marklogic.com/xdmp/admin" at "/MarkLogic/admin.xqy";
import module namespace pki = "http://marklogic.com/xdmp/pki" at "/MarkLogic/pki.xqy";

declare variable $PORT := 9995;
declare variable $MODULE-PATH := "/space";
declare variable $GROUP := "Default";
declare variable $CONFIG := admin:get-configuration();
declare variable $DATABASE := xdmp:database("Documents");
declare variable $TEMPLATE-NAME := "test-template";

(: Configure XCCS Endpoint :)
let $CONFIG := admin:xdbc-server-create(
    $CONFIG,
    admin:group-get-id($CONFIG, $GROUP),
    concat("xdbc-",$PORT),
    $MODULE-PATH,
    $PORT,
    0,
    $DATABASE
)

let $CONFIG := admin:appserver-set-ssl-certificate-template(
    $CONFIG,
    admin:appserver-get-id($CONFIG, admin:group-get-id($CONFIG, $GROUP), concat("xdbc-",$PORT)),
    pki:template-get-id(pki:get-template-by-name($TEMPLATE-NAME)))

return admin:save-configuration($CONFIG)

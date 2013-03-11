@echo off
call mvn clean install wro4j:run -e -DskipTests=true -Dhttp.proxyHost=proxy -Dhttp.proxyPort=8080
pause "All Done. Press a key to close this window."
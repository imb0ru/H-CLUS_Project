@echo off
setlocal enabledelayedexpansion

set projectBasePath=%~dp0..
set serverProjectPath=%~dp0\Server
set jarFile=%serverProjectPath%\server.jar
set mainClass=Main
:: modifica questo campo con la porta del server
set port=3333

set "mysqlConnector=%projectBasePath%\mysql-connector-java-8.0.17.jar"

echo Esecuzione del file JAR con la porta %port%
java -cp "%mysqlConnector%;%jarFile%" %mainClass% %port%

if errorlevel 1 (
    echo Si e' verificato un errore durante l'esecuzione del file JAR.
) else (
    echo Il file JAR e' stato eseguito correttamente.
)

pause
endlocal
exit /b

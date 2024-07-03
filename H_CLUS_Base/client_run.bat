@echo off
setlocal enabledelayedexpansion

set clientProjectPath=%~dp0Client
set jarFile=%clientProjectPath%\client.jar
:: modifica questo campo con l'indirizzo del server
set address=0.0.0.0 
:: modifica questo campo con la porta del server
set port=3333 

echo Esecuzione del file JAR con l'indirizzo %address% e la porta %port%
java -jar "%jarFile%" %address% %port%

if errorlevel 1 (
    echo Si e' verificato un errore durante l'esecuzione del file JAR.
) else (
    echo Il file JAR e' stato eseguito correttamente.
)

pause
endlocal
exit /b

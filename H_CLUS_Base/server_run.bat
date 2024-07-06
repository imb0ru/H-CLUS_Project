@echo off
setlocal enabledelayedexpansion

set projectBasePath=%~dp0..
set serverProjectPath=%~dp0\Server
set jarFile=%serverProjectPath%\server.jar
set mainClass=Main
:: modifica questo campo con la porta del server
set port=3333

set "dependences=%serverProjectPath%\dependences"

set "classpath=%jarFile%"
for %%j in ("%dependences%\*.jar") do (
    set "classpath=!classpath!;%%j"
)

echo Esecuzione del file JAR con la porta %port%
java -cp "!classpath!" %mainClass% %port%

if errorlevel 1 (
    echo Si e' verificato un errore durante l'esecuzione del file JAR.
) else (
    echo Il file JAR e' stato eseguito correttamente.
)

pause
endlocal
exit /b

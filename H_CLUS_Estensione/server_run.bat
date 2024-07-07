@echo off
setlocal enabledelayedexpansion

set projectBasePath=%~dp0..
set serverProjectPath=%~dp0\Server
set jarFile=%serverProjectPath%\server.jar
set mainClass=Main
set botToken="7094361809:AAF5a-Gn_O4j8rjViVgDq7APmlkZkgRkvFU"
:: modifica questo campo con la porta del server
set port=3333

set "dependences=%serverProjectPath%\dependences"

set "classpath=%jarFile%"
for %%j in ("%dependences%\*.jar") do (
    set "classpath=!classpath!;%%j"
)

for /f "tokens=2 delims=:" %%f in ('ipconfig ^| findstr /i "indirizzo ipv4"') do (
    for /f "tokens=1 delims= " %%g in ("%%f") do (
        set ip=%%g
    )
)

cd /d %serverProjectPath%

echo Esecuzione del file JAR con l' IP %ip% e la porta %port%
java -cp "!classpath!" %mainClass% %botToken% %ip% %port%

if errorlevel 1 (
    echo Si e' verificato un errore durante l'esecuzione del file JAR.
) else (
    echo Il file JAR e' stato eseguito correttamente.
)

pause
endlocal
exit /b

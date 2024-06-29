@echo off
setlocal enabledelayedexpansion

rem Impostazioni del progetto
set sqlfile=%~dp0setup.sql
set username=root
set password=root
set projectBasePath=%~dp0..
set serverProjectPath=%~dp0\Server
set serverSrcPath=%serverProjectPath%\src
set serverOutputPath=%serverProjectPath%\out
set manifestFile=%serverProjectPath%\Manifest.txt
set jarFile=%serverProjectPath%\server.jar
set mainClass=Main
set javadocOutputPath=%serverProjectPath%\server_javadoc
set port=3333

rem Trova il percorso completo del driver MySQL
set "mysqlConnector=%projectBasePath%\mysql-connector-java-8.0.17.jar"

rem Esecuzione del file SQL
echo Esecuzione del file SQL...
call mysql -u %username% -p%password% < %sqlfile% 2>nul

if errorlevel 1 (
    echo Si è verificato un errore durante l'esecuzione del file SQL.
    goto :end
) else (
    echo Il file SQL è stato eseguito correttamente.
)

rem Compilazione del server
echo Compilazione del server...

if not exist %serverOutputPath% (
    mkdir %serverOutputPath%
)

rem Verifica del percorso della cartella sorgente
if not exist "%serverSrcPath%\" (
    echo La cartella sorgente %serverSrcPath% non esiste.
    goto :end
)

rem Trova i file Java nella cartella sorgente
set "javaFiles="
for /R "%serverSrcPath%" %%f in (*.java) do (
    set javaFiles=!javaFiles! "%%f"
)

if "%javaFiles%"=="" (
    echo Nessun file Java trovato nella cartella %serverSrcPath%.
    goto :end
)

rem Aggiungi il driver MySQL al classpath durante la compilazione
set "classpath=%mysqlConnector%;%serverOutputPath%"
call javac -cp "%classpath%" -d %serverOutputPath% -source 22 -target 22 -Xlint:none -nowarn !javaFiles! >nul 2>&1

if errorlevel 1 (
    echo Si è verificato un errore durante la compilazione del server.
    goto :end
) else (
    echo La compilazione del server è stata completata correttamente.
    
    rem Crea il file Manifest
    echo Main-Class: %mainClass% > %manifestFile%

    rem Crea il file JAR includendo il Manifest e il driver MySQL
    echo Creazione del file JAR
    jar cvfm %jarFile% %manifestFile% -C %serverOutputPath% . >nul 2>&1

    if errorlevel 1 (
        echo Si è verificato un errore durante la creazione del file JAR.
        goto :end
    ) else (
        echo Il file JAR è stato creato correttamente.

        rem Rimuovi la directory della documentazione Javadoc se esiste
        if exist %javadocOutputPath% (
            rd /s /q %javadocOutputPath%
        )

        rem Genera la documentazione Javadoc
        echo Generazione della documentazione Javadoc
        javadoc -d %javadocOutputPath% -sourcepath %serverSrcPath% -subpackages clustering data database distance server %serverSrcPath%\Main.java >nul 2>&1

        if errorlevel 1 (
            echo Si è verificato un errore durante la generazione della documentazione Javadoc.
            goto :end
        ) else (
            echo La documentazione Javadoc è stata generata correttamente.

            rem Esegui il file JAR con la porta specificata
            echo Esecuzione del file JAR con la porta %port%
            java -jar "%jarFile%" %port%

            if errorlevel 1 (
                echo Si è verificato un errore durante l'esecuzione del file JAR.
            ) else (
                echo Il file JAR è stato eseguito correttamente.
            )
        )
    )
)

:end
pause
endlocal
exit /b

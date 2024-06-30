@echo off
setlocal enabledelayedexpansion

set sqlfile=%~dp0setup.sql
set username=root :: modifica questo campo con l'username admin del database
set password=root :: modifica questo campo con la password admin del database
set projectBasePath=%~dp0..
set serverProjectPath=%~dp0\Server
set serverSrcPath=%serverProjectPath%\src
set serverOutputPath=%serverProjectPath%\out
set manifestFile=%serverProjectPath%\Manifest.txt
set jarFile=%serverProjectPath%\server.jar
set mainClass=Main
set javadocOutputPath=%serverProjectPath%\server_javadoc
set port=3333 :: modifica questo campo con la porta del server

set "mysqlConnector=%projectBasePath%\mysql-connector-java-8.0.17.jar"

echo Esecuzione del file SQL...
call mysql -u %username% -p%password% < %sqlfile% 2>nul

if errorlevel 1 (
    echo Si è verificato un errore durante l'esecuzione del file SQL.
    goto :end
) else (
    echo Il file SQL è stato eseguito correttamente.
)

echo Compilazione del server...

if not exist %serverOutputPath% (
    mkdir %serverOutputPath%
)

if not exist "%serverSrcPath%\" (
    echo La cartella sorgente %serverSrcPath% non esiste.
    goto :end
)

set "javaFiles="
for /R "%serverSrcPath%" %%f in (*.java) do (
    set javaFiles=!javaFiles! "%%f"
)

if "%javaFiles%"=="" (
    echo Nessun file Java trovato nella cartella %serverSrcPath%.
    goto :end
)

set "classpath=%mysqlConnector%;%serverOutputPath%"
call javac -cp "%classpath%" -d %serverOutputPath% -source 22 -target 22 -Xlint:none -nowarn !javaFiles! >nul 2>&1

if errorlevel 1 (
    echo Si è verificato un errore durante la compilazione del server.
    goto :end
) else (
    echo La compilazione del server è stata completata correttamente.
    
    echo Main-Class: %mainClass% > %manifestFile%

    echo Creazione del file JAR
    jar cvfm %jarFile% %manifestFile% -C %serverOutputPath% . >nul 2>&1

    if errorlevel 1 (
        echo Si è verificato un errore durante la creazione del file JAR.
        goto :end
    ) else (
        echo Il file JAR è stato creato correttamente.

        if exist %javadocOutputPath% (
            rd /s /q %javadocOutputPath%
        )

        echo Generazione della documentazione Javadoc
        javadoc -d %javadocOutputPath% -sourcepath %serverSrcPath% -subpackages clustering data database distance server %serverSrcPath%\Main.java >nul 2>&1

        if errorlevel 1 (
            echo Si è verificato un errore durante la generazione della documentazione Javadoc.
            goto :end
        ) else (
            echo La documentazione Javadoc è stata generata correttamente.

            echo Esecuzione del file JAR con la porta %port%
            java -cp "%mysqlConnector%;%jarFile%" %mainClass% %port%

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

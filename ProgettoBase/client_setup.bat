@echo off
setlocal enabledelayedexpansion

set clientProjectPath=%~dp0Client
set clientSrcPath=%clientProjectPath%\src
set clientOutputPath=%clientProjectPath%\out
set manifestFile=%clientProjectPath%\Manifest.txt
set jarFile=%clientProjectPath%\client.jar
set mainClass=Main
set javadocOutputPath=%clientProjectPath%\client_javadoc
set address=0.0.0.0 :: modifica questo campo con l'indirizzo del server
set port=3333 :: modifica questo campo con la porta del server

echo Compilazione del client...

if not exist %clientOutputPath% (
    mkdir %clientOutputPath%
)

set javaFiles=
for /R "%clientSrcPath%" %%f in (*.java) do (
    set javaFiles=!javaFiles! "%%f"
)

if defined javaFiles (
    call javac -d %clientOutputPath% -source 22 -target 22 -Xlint:none -nowarn !javaFiles! >nul 2>&1

    if errorlevel 1 (
        echo Si e' verificato un errore durante la compilazione del client.
        goto :end
    ) else (
        echo La compilazione del client e' stata completata correttamente.
        
        echo Main-Class: %mainClass% > %manifestFile%

        echo Creazione del file JAR
        jar cvfm %jarFile% %manifestFile% -C %clientOutputPath% . >nul 2>&1

        if errorlevel 1 (
            echo Si e' verificato un errore durante la creazione del file JAR.
            goto :end
        ) else (
            echo Il file JAR e' stato creato correttamente.

            if exist %javadocOutputPath% (
                rd /s /q %javadocOutputPath%
            )

            echo Generazione della documentazione Javadoc
            javadoc -d %javadocOutputPath% -sourcepath %clientSrcPath% -subpackages utils client %clientSrcPath%\Main.java >nul 2>&1

            if errorlevel 1 (
                echo Si e' verificato un errore durante la generazione della documentazione Javadoc.
                goto :end
            ) else (
                echo La documentazione Javadoc e' stata generata correttamente.

                echo Esecuzione del file JAR con la porta %port%
                java -jar "%jarFile%" %address% %port%

                if errorlevel 1 (
                    echo Si e' verificato un errore durante l'esecuzione del file JAR.
                ) else (
                    echo Il file JAR e' stato eseguito correttamente.
                )
            )
        )
    )
) else (
    echo Nessun file Java trovato.
    goto :end
)

:end
pause
endlocal
exit /b

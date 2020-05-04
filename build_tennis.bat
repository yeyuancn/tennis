@echo off
cls
set newline=^& echo.

if ""%1"" == ""deploy"" goto deploy

echo ##################################
echo Start Tennis Master build %newline%
echo ##################################


call mvn clean install
echo ##################################
if NOT [%ERRORLEVEL%]==[0] (
   echo FAILED WITH Tennis!
   EXIT /B
)
echo DONE WITH Tennis %newline%
echo ##################################


:deploy
echo ##################################
echo Deploy the WAR files %newline%
echo ##################################


DEL /F /Q /S C:\tomcat7_tennis\webapps\*  > nul
for /D %%i in ("C:\tomcat7_tennis\webapps\*") do RD /S /Q "%%i"
COPY "target\*.war" "C:\tomcat7_tennis\webapps\"

echo ##################################
if NOT [%ERRORLEVEL%]==[0] (
   echo FAILED WITH MOVE WAR FILE for Tennis!
   EXIT /B
)
echo Moved WAR FILE for Tennis %newline%
echo ##################################

echo ##################################
echo DONE WITH DEPLOY TASK %newline%
echo ##################################



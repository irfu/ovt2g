@echo off
REM Script for OVT startup
SET OVTVERSION=2.3
echo Orbit Visualization Tool %OVTVERSION%
echo Copyright (c) OVT Team, 2000-2009
SET OVT_HOME=..
SET JAVA_HOME=%OVT_HOME%\jre
SET PATH=%JAVA_HOME%\bin;%OVT_HOME%\bin
SET CLASSPATH=%OVT_HOME%;%OVT_HOME%\lib\ovt-%OVTVERSION%.jar;%OVT_HOME%\lib\vtk.jar;%OVT_HOME%\lib\parser.jar;%OVT_HOME%\lib\jaxp.jar

%JAVA_HOME%\bin\java -Dovt.version=%OVTVERSION% -Dovt.home=%OVT_HOME% -Dovt.user=%OVT_HOME% ovt.XYZWindow

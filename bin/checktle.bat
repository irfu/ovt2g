@echo off
REM TLE Validator Programme
REM Copyright (c) OVT Team, 2000-2003
SET OVT_HOME=..
SET OVTVERSION=2.3
SET JAVA_HOME=%OVT_HOME%\jre
SET PATH=%JAVA_HOME%\bin;%OVT_HOME%\bin
SET CLASSPATH=%OVT_HOME%;%OVT_HOME%\lib\ovt-%OVTVERSION%.jar

%JAVA_HOME%\bin\java ovt.util.TLESorter %1 %2

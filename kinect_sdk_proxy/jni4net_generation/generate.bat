@del /s /f /q .\work\* > NUL
@mkdir .\work
@copy ..\bin\Debug\KinectSensorProxy.dll .\work\
@set JAVA_HOME=C:\Program Files\Java\jre7
@set PATH=C:\Windows\Microsoft.NET\Framework64\v4.0.30319;%PATH%
@proxygen .\work\KinectSensorProxy.dll -wd work
@cd work
@call build.cmd
@echo Done!

pause > NUL
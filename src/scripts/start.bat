REM Batch script file by Antoine James Tournepiche
REM for JASC
REM (c) October 10th 2019

REM Launch all jasc*.jar files
FOR /R %%F IN (jasc*.jar) DO java -jar %%F

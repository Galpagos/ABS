cd commons
call  mvn clean install compile 
cd..
cd lib
call mvn install:install-file   -Dfile=commons-0.0.1-SNAPSHOT.jar   -DgroupId=pl.home   -DartifactId=commons   -Dversion=1.0   -Dpackaging=jar   -DgeneratePom=true
cd..
cd ZRODLA
call mvn clean install compile 
pause
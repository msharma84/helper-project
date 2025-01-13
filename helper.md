# Cheat Sheet 

- Generate Maven Project from CMD
 mvn archetype:generate -DgroupId=com.helper-project -DartifactId=java-project -DarchetypeArtifactId=maven-archetype-quickstart -DinteractiveMode=false
 mvn dependency:tree -Dverbose

- Print JDBC Logs
JPA - spring.jpa.properties.hibernate.show_sql=true
JDBC Template - logging.level.org.springframework.jdbc.core=TRACE
================
get java process
jps -l -m -v
================
Get java thread stat 

jstat -gc <java thread pid> 1s <iteration>
jstat -gc 27489 1s 300
jstat -gc 27489 1s 300 > threaddumps.log
================
These are different logging levels and its order from minimum << maximum.
OFF << FATAL << ERROR << WARN << INFO << DEBUG << TRACE << ALL
================
-Dspring.liveBeansView.mbeanDomain
================
CREATE BACKUP TABLE : 
    CREATE TABLE "BLUADMIN"."DB2_TEST_DUMP" LIKE "BLUADMIN"."DB2_TEST";
INSERT DATA INTO IT :
    INSERT INTO "BLUADMIN"."DB2_TEST_DUMP" (SELECT * FROM "BLUADMIN"."DB2_TEST");
Options that are not copied include:
    Check constraints
    Column default values
    Column comments
    Foreign keys
    Logged and compact option on BLOB columns
    Distinct types
================

FROM 123.206.178.99:30100/sp/centos_jdk:1.8.0_131
MAINTAINER alexlc <alexanderlc@126.com>
ADD target/MQ-Center.jar /opt/
WORKDIR /opt/
CMD java -Dfile.encoding=utf-8 -jar -Duser.timezone="Asia/Shanghai" -Djava.awt.headless=true -Djava.net.preferIPv4Stack=true -server -Xmx1g -Xms128m -Xss256k -XX:+DisableExplicitGC -XX:+UseConcMarkSweepGC -XX:+CMSParallelRemarkEnabled -XX:+UseCMSCompactAtFullCollection -XX:LargePageSizeInBytes=4m -XX:+UseFastAccessorMethods -XX:+UseCMSInitiatingOccupancyOnly -XX:CMSInitiatingOccupancyFraction=70 MQ-Center.jar
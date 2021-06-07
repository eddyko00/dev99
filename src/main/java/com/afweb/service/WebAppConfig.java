package com.afweb.service;

import com.afweb.util.CKey;
import com.afweb.util.getEnv;
import javax.sql.DataSource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

public class WebAppConfig {

    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        if ((CKey.SQL_DATABASE == CKey.LOCAL_MYSQL) || (CKey.SQL_DATABASE == CKey.REMOTE_PHP_MYSQL)) {
            dataSource.setDriverClassName("com.mysql.jdbc.Driver");
            dataSource.setUrl("jdbc:mysql://mysql:3306/sampledb");

            dataSource.setUsername("sa");
            dataSource.setPassword("admin");

            if (getEnv.checkLocalPC() == true) {
// https://support.microsoft.com/en-ca/help/196271/when-you-try-to-connect-from-tcp-ports-greater-than-5000-you-receive-t            
//                String Local_mysql = "jdbc:mysql://localhost:3306/sampledb?useSSL=true";
                String Local_mysql = "jdbc:mysql://localhost:3306/sampledb?useSSL=false";
                dataSource.setUrl(Local_mysql);

            }
            ServiceAFweb.URL_LOCALDB = dataSource.getUrl();

        }
        if (CKey.SQL_DATABASE == CKey.DIRECT__MYSQL) {

//            dataSource.setDriverClassName("com.mysql.jdbc.Driver");
//            dataSource.setUrl("jdbc:mysql://www.db4free.net:3306/eddydb_sample?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&useSSL=false");
//            dataSource.setUsername("eddysa");
//            dataSource.setPassword("eddyadmin");
//sh-4.2$ env | grep MYSQL
//MYSQL_PREFIX=/opt/rh/rh-mysql57/root/usr
//MYSQL_VERSION=5.7
//MYSQL_DATABASE=sampledb
//MYSQL_PASSWORD=admin
//MYSQL_PORT_3306_TCP_PORT=3306
//MYSQL_PORT_3306_TCP=tcp://172.30.136.72:3306
//MYSQL_SERVICE_PORT_MYSQL=3306
//MYSQL_PORT_3306_TCP_PROTO=tcp
//MYSQL_PORT_3306_TCP_ADDR=172.30.136.72
//MYSQL_SERVICE_PORT=3306
//MYSQL_USER=sa
//MYSQL_PORT=tcp://172.30.136.72:3306
//MYSQL_SERVICE_HOST=172.30.136.72
//sh-4.2$ 
//Open Shfit error Loading class `com.mysql.jdbc.Driver'. This is deprecated. The new driver class is `com.mysql.cj.jdbc.Driver'. The driver is automatically registered via the SPI and manual loading of the driver class is generally unnecessary.
//            dataSource.setDriverClassName("com.mysql.jdbc.Driver");
////            dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
//            dataSource.setUrl("jdbc:mysql://172.30.136.72:3306/sampledb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&useSSL=false");
//            dataSource.setUsername("sa");
//            dataSource.setPassword("admin");
//////////////////////////////////////////////////////////
//$host = "sql9.freemysqlhosting.net";
//$username = "sql9376612";
//$password = "p4WL3psmvm";
//$DBName = "sql9376612";
//            dataSource.setDriverClassName("com.mysql.jdbc.Driver");
//            dataSource.setUrl("jdbc:mysql://sql9.freemysqlhosting.net:3306/sql9376612?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&useSSL=false");
//            dataSource.setUsername("sql9376612");
//            dataSource.setPassword("p4WL3psmvm");

//////////////////////////////////////////////////////////
//$host = "bmppikx9mn79axgjlhh4-mysql.services.clever-cloud.com";
//$username = "uphyltwqsqsipjri";
//$password = "5V7FaMjFWryhGXYcagw2";
//$DBName = "bmppikx9mn79axgjlhh4";        
//
            dataSource.setDriverClassName("com.mysql.jdbc.Driver");
            dataSource.setUrl("jdbc:mysql://bmppikx9mn79axgjlhh4-mysql.services.clever-cloud.com:3306/bmppikx9mn79axgjlhh4?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&useSSL=false");
            dataSource.setUsername("uphyltwqsqsipjri");
            dataSource.setPassword("5V7FaMjFWryhGXYcagw2");

        }
        if ((CKey.SQL_DATABASE == CKey.MSSQL) || (CKey.SQL_DATABASE == CKey.REMOTE_MS_SQL)) {
            dataSource.setDriverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            dataSource.setUrl("jdbc:sqlserver://sql.freeasphost.net/MSSQL2016;databaseName=eddyko00_SampleDB");
            dataSource.setUsername("eddyko00_SampleDB");
            dataSource.setPassword("DBSamplePW");

            if (ServiceAFweb.URL_LOCALDB.length() == 0) {
                ServiceAFweb.URL_LOCALDB = dataSource.getUrl();
            } else {
                dataSource.setUrl(ServiceAFweb.URL_LOCALDB);
            }
        }

        CKey.dataSourceURL = dataSource.getUrl();
        return dataSource;
    }
}

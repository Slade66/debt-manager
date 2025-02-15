package com.slade.util;

import com.slade.db.DatabaseInitializer;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class JdbcUtil {

    private static final String JDBC_PROPERTIES_PATH = "jdbc.properties";
    private static final Properties properties = getProperties();
    private static final String DB_NAME = properties.getProperty("DB_NAME");
    private static final String SERVER_URL = properties.getProperty("SERVER_URL") + "/" + DB_NAME;
    private static final String USERNAME = properties.getProperty("USERNAME");
    private static final String PASSWORD = properties.getProperty("PASSWORD");

    private static Connection cachedConnection;

    static {
        try {
            cachedConnection = createConnection();
        } catch (SQLException e) {
            throw new RuntimeException("初始化数据库连接失败", e);
        }
    }

    private static Connection createConnection() throws SQLException {
        return DriverManager.getConnection(SERVER_URL, USERNAME, PASSWORD);
    }

    public static Connection getConnection() throws SQLException {
        if (cachedConnection == null || cachedConnection.isClosed()) {
            cachedConnection = createConnection();
        }
        return cachedConnection;
    }

    public static Properties getProperties() {
        // 创建了一个 Properties 对象，用于存储从 jdbc.properties 文件中加载的键值对配置。
        Properties properties = new Properties();
        // 将 InputStream 放入 try 语句的括号中，确保使用完毕后自动关闭资源，防止资源泄漏。
        // 明确告诉 JVM：“请用加载 DatabaseInitializer 这个类的类加载器来查找资源”，这样可以确保读取到债务管理模块自己的 jdbc.properties 文件，从而保证正确的数据库配置信息被加载。
        try (InputStream inputStream = DatabaseInitializer.class.getClassLoader().getResourceAsStream(JDBC_PROPERTIES_PATH)) {
            if (inputStream == null) {
                throw new RuntimeException("找不到配置文件：" + JDBC_PROPERTIES_PATH);
            }
            properties.load(inputStream);
        } catch (Exception e) {
            throw new RuntimeException("加载配置文件出错：" + JDBC_PROPERTIES_PATH, e);
        }
        return properties;
    }

}

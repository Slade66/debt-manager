package com.slade.db;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Properties;

public class DatabaseInitializer {

    public static final String JDBC_PROPERTIES_PATH = "jdbc.properties";

    public static void createDatabase() {
        Properties properties = getProperties();
        String SERVER_URL = properties.getProperty("SERVER_URL");
        String USERNAME = properties.getProperty("USERNAME");
        String PASSWORD = properties.getProperty("PASSWORD");
        String createDatabaseSql = """
                    CREATE DATABASE IF NOT EXISTS debt_manager
                    CHARACTER SET utf8mb4
                    COLLATE utf8mb4_unicode_ci;
                """;
        try (Connection connection = DriverManager.getConnection(SERVER_URL, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(createDatabaseSql)) {
            preparedStatement.executeUpdate();
            System.out.println("数据库创建成功或已存在。");
        } catch (Exception e) {
            throw new RuntimeException("创建数据库出错", e);
        }
    }

    private static Properties getProperties() {
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

    public static void createTable() {
        Properties properties = getProperties();
        String DB_NAME = properties.getProperty("DB_NAME");
        String TABLE_NAME = properties.getProperty("TABLE_NAME");
        String SERVER_URL = properties.getProperty("SERVER_URL") + "/" + DB_NAME;
        String USERNAME = properties.getProperty("USERNAME");
        String PASSWORD = properties.getProperty("PASSWORD");
        String createTableSql = String.format("""
                    CREATE TABLE IF NOT EXISTS %s (
                        id INT AUTO_INCREMENT PRIMARY KEY,         -- 自增主键，用于唯一标识每条记录
                        creditor VARCHAR(100) NOT NULL,              -- 欠款对象，例如银行、朋友等
                        total_amount DECIMAL(10,2) NOT NULL,           -- 总共欠款金额
                        repayment_curr DECIMAL(10,2) DEFAULT NULL,      -- 当月待还金额，可以为 NULL
                        repayment_next DECIMAL(10,2) DEFAULT NULL,      -- 下月待还金额，可以为 NULL
                        repayment_date VARCHAR(100) NOT NULL          -- 还款日期说明，比如“每月21日”或“每月底”
                    );
                """, TABLE_NAME);
        try (Connection connection = DriverManager.getConnection(SERVER_URL, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(createTableSql)) {
             preparedStatement.executeUpdate();
             System.out.println("数据表 " + TABLE_NAME + " 创建成功或已存在。");
        } catch (Exception e) {
            throw new RuntimeException("创建数据表失败", e);
        }
    }

}

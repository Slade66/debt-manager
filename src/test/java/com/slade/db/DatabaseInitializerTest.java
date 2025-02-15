package com.slade.db;

import org.junit.jupiter.api.Test;

public class DatabaseInitializerTest {

    @Test
    void testCreateDatabase() {
        DatabaseInitializer.createDatabase();
    }

    @Test
    void testCreateTable() {
        DatabaseInitializer.createTable();
    }

}

package com.httt.quanlybenhvien;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public final class JdbcUtils {
    private JdbcUtils() {}

    public static void closeQuietly(AutoCloseable ac) {
        if (ac == null) return;
        try {
            ac.close();
        } catch (Exception ignored) {}
    }

    public static void closeQuietly(Statement stmt, ResultSet rs) {
        closeQuietly(rs);
        closeQuietly(stmt);
    }
}

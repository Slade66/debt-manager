package com.slade.db;

import com.slade.util.JdbcUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DebtDAO {

    public static int addDebt(Debt debt) {
        String sql = "INSERT INTO debt (creditor, total_amount, repayment_curr, repayment_next, repayment_date) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = JdbcUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, debt.getCreditor());
            stmt.setBigDecimal(2, debt.getTotalAmount());
            stmt.setBigDecimal(3, debt.getRepaymentCurr());
            stmt.setBigDecimal(4, debt.getRepaymentNext());
            stmt.setString(5, debt.getRepaymentDate());

            return stmt.executeUpdate(); // 返回受影响的行数
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

}

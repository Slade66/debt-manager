package com.slade.db;

import com.slade.util.JdbcUtil;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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


    public static List<Debt> getAllDebts() {
        List<Debt> debts = new ArrayList<>();
        String sql = "SELECT * FROM debt";
        try (Connection conn = JdbcUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                String creditor = rs.getString("creditor");
                BigDecimal totalAmount = rs.getBigDecimal("total_amount");
                BigDecimal repaymentCurr = rs.getBigDecimal("repayment_curr");
                BigDecimal repaymentNext = rs.getBigDecimal("repayment_next");
                String repaymentDate = rs.getString("repayment_date");

                Debt debt = new Debt(creditor, totalAmount, repaymentCurr, repaymentNext, repaymentDate);
                debts.add(debt);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return debts;
    }
}

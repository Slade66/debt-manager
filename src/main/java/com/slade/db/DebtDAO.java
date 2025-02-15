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

    public static int updateDebt(Debt debt) {

        String creditor = debt.getCreditor();
        if (creditor == null || creditor.trim().isEmpty()) {
            System.out.println("债权人不能为空，无法更新。");
            return 0;
        }

        StringBuilder sql = new StringBuilder("UPDATE debt SET ");
        List<Object> params = new ArrayList<>();
        boolean first = true;

        BigDecimal totalAmount = debt.getTotalAmount();
        if (totalAmount != null) {
            sql.append("total_amount = ?");
            params.add(totalAmount);
            first = false;
        }

        BigDecimal repaymentCurr = debt.getRepaymentCurr();
        if (repaymentCurr != null) {
            if (!first) {
                sql.append(", ");
            }
            sql.append("repayment_curr = ?");
            params.add(repaymentCurr);
            first = false;
        }

        BigDecimal repaymentNext = debt.getRepaymentNext();
        if (repaymentNext != null) {
            if (!first) {
                sql.append(", ");
            }
            sql.append("repayment_next = ?");
            params.add(repaymentNext);
            first = false;
        }

        String repaymentDate = debt.getRepaymentDate();
        if (repaymentDate != null && !repaymentDate.trim().isEmpty()) {
            if (!first) {
                sql.append(", ");
            }
            sql.append("repayment_date = ?");
            params.add(repaymentDate);
        }

        // 如果没有任何字段需要更新，则直接返回 0
        if (params.isEmpty()) {
            System.out.println("没有要更新的字段。");
            return 0;
        }

        // 添加 WHERE 条件，根据 creditor 更新
        sql.append(" WHERE creditor = ?");
        params.add(creditor);

        try (Connection conn = JdbcUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql.toString())) {

            for (int i = 1; i <= params.size(); i++) {
                Object param = params.get(i - 1);
                if (param instanceof BigDecimal) {
                    stmt.setBigDecimal(i, (BigDecimal) param);
                } else if (param instanceof String) {
                    stmt.setString(i, (String) param);
                } else {
                    stmt.setObject(i, param);
                }
            }

            return stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }

    }

    public static int deleteDebt(String creditor) {
        String sql = "DELETE FROM debt WHERE creditor = ?";
        try (Connection conn = JdbcUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, creditor);
            return stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

}

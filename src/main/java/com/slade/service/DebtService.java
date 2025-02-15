package com.slade.service;

import com.slade.db.Debt;
import com.slade.db.DebtDAO;

import java.util.List;

public class DebtService {

    public static void addDebt(Debt debt) {
        int affectedRows = DebtDAO.addDebt(debt);
        if (affectedRows > 0) {
            System.out.println("插入数据成功");
        } else {
            System.out.println("插入数据失败");
        }
    }

    public static List<Debt> getAllDebts() {
        return DebtDAO.getAllDebts();
    }

    public static void updateDebt(Debt debt) {
        int affectedRows = DebtDAO.updateDebt(debt);
        if (affectedRows > 0) {
            System.out.println("更新债务记录成功！");
        } else {
            System.out.println("更新债务记录失败！");
        }
    }

}

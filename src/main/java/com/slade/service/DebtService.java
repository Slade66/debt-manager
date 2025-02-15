package com.slade.service;

import com.slade.db.Debt;
import com.slade.db.DebtDAO;

public class DebtService {

    public static void addDebt(Debt debt) {
        int affectedRows = DebtDAO.addDebt(debt);
        if (affectedRows > 0) {
            System.out.println("插入数据成功");
        } else {
            System.out.println("插入数据失败");
        }
    }

}

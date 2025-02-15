package com.slade;

import com.slade.db.DatabaseInitializer;
import com.slade.db.Debt;
import com.slade.service.DebtService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

public class DebtManger {

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        DatabaseInitializer.createDatabase();
        DatabaseInitializer.createTable();
        run();
    }

    public static void run() {
        boolean exit = false;
        while (!exit) {
            System.out.println();
            System.out.println("欢迎使用债务管理系统，请选择操作：");
            System.out.println("1. 新增债务");
            System.out.println("2. 查询债务");
            System.out.println("3. 更新债务");
            System.out.println("4. 删除债务");
            System.out.println("5. 退出");

            System.out.print("请输入选项：");
            String input = scanner.nextLine();

            switch (input) {
                case "1":
                    System.out.println("【新增债务】");
                    handleAddDebt();
                    break;
                case "2":
                    System.out.println("【查询债务】");
                    handleGetAllDebts();
                    break;
                case "3":
                    System.out.println("【更新债务】");
                    handleUpdateDebt();
                    break;
                case "4":
                    System.out.println("【删除债务】");
                    break;
                case "5":
                    System.out.println("【退出程序】");
                    exit = true;
                    break;
                default:
                    System.out.println("【无效输入，重新输入】");
                    break;
            }
        }
        scanner.close();
    }

    private static void handleAddDebt() {
        // 读取欠款对象
        System.out.print("请输入欠款对象：");
        String creditor = scanner.nextLine(); // 用于读取输入流中的一整行文本，直到遇到换行符为止，并返回这个字符串。

        // 读取总欠款金额，并转换为 BigDecimal
        System.out.print("请输入总欠款金额：");
        String totalAmountStr = scanner.nextLine();
        BigDecimal totalAmount;
        try {
            totalAmount = new BigDecimal(totalAmountStr);
        } catch (NumberFormatException e) {
            System.out.println("金额格式错误，请输入有效的数字。");
            return;
        }

        // 读取当月待还金额（可选）
        System.out.print("请输入当月待还金额（可选，直接回车跳过）：");
        String repaymentCurrStr = scanner.nextLine();
        BigDecimal repaymentCurr = null;
        if (!repaymentCurrStr.isEmpty()) {
            try {
                repaymentCurr = new BigDecimal(repaymentCurrStr);
            } catch (NumberFormatException e) {
                System.out.println("金额格式错误，跳过当月待还金额。");
            }
        }

        // 读取下月待还金额（可选）
        System.out.print("请输入下月待还金额（可选，直接回车跳过）：");
        String repaymentNextStr = scanner.nextLine();
        BigDecimal repaymentNext = null;
        if (!repaymentNextStr.isEmpty()) {
            try {
                repaymentNext = new BigDecimal(repaymentNextStr);
            } catch (NumberFormatException e) {
                System.out.println("金额格式错误，跳过下月待还金额。");
            }
        }

        // 读取还款日期说明
        System.out.print("请输入还款日期说明：");
        String repaymentDate = scanner.nextLine();

        Debt debt = new Debt(creditor, totalAmount, repaymentCurr, repaymentNext, repaymentDate);
        DebtService.addDebt(debt);

        System.out.println("录入的债务信息为：");
        System.out.println(debt);
    }

    private static void handleGetAllDebts() {
        List<Debt> debts = DebtService.getAllDebts();
        if (debts.isEmpty()) {
            System.out.println("没有债务记录。");
            return;
        }
        // 格式化输出：先打印表头
        System.out.printf("%-20s %-15s %-15s %-15s %-20s%n", "Creditor", "Total Amount", "Repay Curr", "Repay Next", "Repayment Date");
        System.out.println("-----------------------------------------------------------------------------------------------");

        // 打印每一条记录
        for (Debt debt : debts) {
            System.out.printf("%-20s %-15s %-15s %-15s %-20s%n",
                    debt.getCreditor(),
                    debt.getTotalAmount() != null ? debt.getTotalAmount().toString() : "",
                    debt.getRepaymentCurr() != null ? debt.getRepaymentCurr().toString() : "",
                    debt.getRepaymentNext() != null ? debt.getRepaymentNext().toString() : "",
                    debt.getRepaymentDate());
        }
    }

    private static void handleUpdateDebt() {
        System.out.print("请输入要更新的债务记录对应的债权人：");
        String creditor = scanner.nextLine();

        // 提示用户选择更新哪些字段
        System.out.print("请输入新的总欠款金额（直接回车表示不更新）：");
        String totalAmountStr = scanner.nextLine();
        BigDecimal newTotalAmount = null;
        if (!totalAmountStr.isEmpty()) {
            try {
                newTotalAmount = new BigDecimal(totalAmountStr);
            } catch (NumberFormatException e) {
                System.out.println("金额格式错误，跳过总欠款金额更新。");
            }
        }

        System.out.print("请输入新的当月待还金额（直接回车表示不更新）：");
        String currStr = scanner.nextLine();
        BigDecimal newRepaymentCurr = null;
        if (!currStr.isEmpty()) {
            try {
                newRepaymentCurr = new BigDecimal(currStr);
            } catch (NumberFormatException e) {
                System.out.println("金额格式错误，跳过当月待还金额更新。");
            }
        }

        System.out.print("请输入新的下月待还金额（直接回车表示不更新）：");
        String nextStr = scanner.nextLine();
        BigDecimal newRepaymentNext = null;
        if (!nextStr.isEmpty()) {
            try {
                newRepaymentNext = new BigDecimal(nextStr);
            } catch (NumberFormatException e) {
                System.out.println("金额格式错误，跳过下月待还金额更新。");
            }
        }

        System.out.print("请输入新的还款日期说明（直接回车表示不更新）：");
        String newRepaymentDate = scanner.nextLine();
        if (newRepaymentDate.trim().isEmpty()) {
            newRepaymentDate = null;
        }

        Debt debt = new Debt(creditor, newTotalAmount, newRepaymentCurr, newRepaymentNext, newRepaymentDate);
        DebtService.updateDebt(debt);
    }


}

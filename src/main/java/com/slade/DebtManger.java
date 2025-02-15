package com.slade;

import com.slade.db.DatabaseInitializer;
import com.slade.db.Debt;
import com.slade.service.DebtService;

import java.math.BigDecimal;
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
                    break;
                case "3":
                    System.out.println("【更新债务】");
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


}

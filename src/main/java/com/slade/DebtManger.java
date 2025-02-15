package com.slade;

import com.slade.db.DatabaseInitializer;

import java.util.Scanner;

public class DebtManger {

    public static void main(String[] args) {
        DatabaseInitializer.createDatabase();
        DatabaseInitializer.createTable();
        run();
    }

    public static void run() {
        Scanner scanner = new Scanner(System.in);
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
                    System.out.println("新增债务");
                    break;
                case "2":
                    System.out.println("查询债务");
                    break;
                case "3":
                    System.out.println("更新债务");
                    break;
                case "4":
                    System.out.println("删除债务");
                    break;
                case "5":
                    System.out.println("退出");
                    exit = true;
                    break;
                default:
                    System.out.println("无效输入，重新输入。");
                    break;
            }
        }
        scanner.close();
    }

}

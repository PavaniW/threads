import java.util.*;

class BasicSalaryThread extends Thread {
    private int perDayPayment;
    private int noOfDays;
    private int basicSalary;

    public BasicSalaryThread(int perDayPayment, int noOfDays) {
        this.perDayPayment = perDayPayment;
        this.noOfDays = noOfDays;
    }

    public int getBasicSalary() {
        return basicSalary;
    }

    @Override
    public void run() {
        basicSalary = perDayPayment * noOfDays;
    }
}

class AllowancesThread extends Thread {
    private int basicSalary;
    private int allowances;

    public AllowancesThread(int basicSalary) {
        this.basicSalary = basicSalary;
    }

    public int getAllowances() {
        return allowances;
    }

    @Override
    public void run() {
        allowances = (int) (0.05 * basicSalary);
    }
}

class EPFThread extends Thread {
    private int totalMonthlyEarnings;
    private int epf;

    public EPFThread(int totalMonthlyEarnings) {
        this.totalMonthlyEarnings = totalMonthlyEarnings;
    }

    public int getEPF() {
        return epf;
    }

    @Override
    public void run() {
        epf = (int) (0.08 * totalMonthlyEarnings);
    }
}

public class SalaryCalculator {
    public static void main(String[] args) throws InterruptedException {
        int perDayPayment = 0;
        int noOfDays = 0;

        Scanner obj1 = new Scanner(System.in);
        System.out.print("Enter the payment amount per day : ");
        perDayPayment = obj1.nextInt();
        Scanner obj2 = new Scanner(System.in);
        System.out.print("Enter the no. of days : ");
        noOfDays = obj2.nextInt();

        BasicSalaryThread basicSalaryThread = new BasicSalaryThread(perDayPayment, noOfDays);
        AllowancesThread allowancesThread = new AllowancesThread(0);
        EPFThread epfThread = new EPFThread(0);

        basicSalaryThread.start();
        basicSalaryThread.join();
        int basicSalary = basicSalaryThread.getBasicSalary();

        allowancesThread = new AllowancesThread(basicSalary);
        epfThread = new EPFThread(basicSalary + allowancesThread.getAllowances());

        allowancesThread.start();
        epfThread.start();

        allowancesThread.join();
        epfThread.join();

        int allowances = allowancesThread.getAllowances();
        int epf = epfThread.getEPF();
        int employerContribution = (int) (0.12 * (basicSalary + allowances + epf));
        int finalSalary = basicSalary + allowances + epf - employerContribution;

        System.out.println("Basic Salary: " + basicSalary);
        System.out.println("Allowances: " + allowances);
        System.out.println("EPF: " + finalSalary);
        System.out.println("Employer's Contribution: " + employerContribution);
        System.out.println("Final Salary: " + finalSalary);
    }
}
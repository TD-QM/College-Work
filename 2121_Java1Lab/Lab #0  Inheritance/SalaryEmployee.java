public class SalaryEmployee extends Employee{

    private double salary;

    public SalaryEmployee(String name, double salary){
        super(name); // Invoking the constructor of the super class
        this.salary = salary;
    }

    @Override
    public double getPayment(){
        return (salary/12) / 2;
    }

    @Override
    public String toString(){
        String employeeName = super.toString();
        return String.format("%s, \nSalary:$%.02f", employeeName, this.salary);
    }
}
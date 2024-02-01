public class HourlyEmployee extends Employee{
    private double hourlyRate;
    private double hoursWorked;

    public HourlyEmployee(String name, double hourlyRate, double hoursWorked){
        super(name);
        this.hourlyRate = hourlyRate;
        this.hoursWorked = hoursWorked;
    }

    @Override
    public double getPayment(){
        return hourlyRate * hoursWorked;
    }

    public String toString(){
        String employeeName = super.toString();
        return String.format("%s, \nHourly:$%.02f @ %.02f hours", employeeName, this.hourlyRate, this.hoursWorked);
    }
}

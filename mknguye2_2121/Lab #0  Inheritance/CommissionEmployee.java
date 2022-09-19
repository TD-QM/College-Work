public class CommissionEmployee extends Employee{
    private double commissionRate;
    private double totalSales;

    public CommissionEmployee(String name, double commissionRate, double totalSales){
        super(name);
        this.commissionRate = commissionRate;
        this.totalSales = totalSales;
    }

    @Override
    public double getPayment(){
        return commissionRate * totalSales;
    }

    public String toString(){
        String employeeName = super.toString();
        return String.format("%s, \nCommission:$%.02f @ %.02f sales", employeeName, this.commissionRate, this.totalSales);
    }
}

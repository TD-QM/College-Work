import javax.swing.RowFilter.ComparisonType;

public class TestEmployee {
  public static void main(String[] args) {
    Employee[] employeeList = new Employee[5];
    employeeList[0] = new SalaryEmployee("Meg Manager", 70000);
    employeeList[1] = new HourlyEmployee("Rick Salesman", 15, 50);
    employeeList[2] = new SalaryEmployee("Nita Store Manager", 65000);
    employeeList[3] = new CommissionEmployee("Devi Temp", .25, 4000);
    employeeList[4] = new HourlyEmployee("Pristine", 10.5, 60);

    for(Employee worker : employeeList){
      System.out.println(worker + "\n");
    }
  }
}

public abstract class Employee {
    protected String name;

    public Employee(String name){
        this.name = name;
    }

    public abstract double getPayment();

    @Override
    public String toString(){
        return name;
    }
}

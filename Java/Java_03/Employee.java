public class Employee {

    public static int totalEmployee = 0;

    private String employeeNo;
    private String name;
    private String part;

    public Employee(String employeeNo, String name, String part) {
        this.employeeNo = employeeNo;
        this.name = name;
        this.part = part;
    }

    public static int getTotalEmployee() {
        return totalEmployee;
    }

    public static void setTotalEmployee(int totalEmployee) {
        Employee.totalEmployee = totalEmployee;
    }

    public String getEmployeeNo() {
        return employeeNo;
    }

    public void setEmployeeNo(String employeeNo) {
        this.employeeNo = employeeNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPart() {
        return part;
    }

    public void setPart(String part) {
        this.part = part;
    }

    @Override
    public String toString() {
        return "EmployeeNumber [" + employeeNo + "]" +
                "\nName : " + name +
                "\nPart : " + part + "\n";
    }

    public void ChangeInfo(int number) {
        System.out.println("Not Available");
    }
}

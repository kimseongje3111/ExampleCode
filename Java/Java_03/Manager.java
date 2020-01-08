public class Manager extends Employee{

    private int workingYears;

    public Manager(String employeeNo, String name, String part, int workingYears) {
        super(employeeNo, name, part);
        this.workingYears = workingYears;
        totalEmployee++;
    }

    public int getWorkingYears() {
        return workingYears;
    }

    public void setWorkingYears(int workingYears) {
        this.workingYears = workingYears;
        super.setEmployeeNo("a");
    }

    @Override
    public String toString() {
        return super.toString() + "WorkingYears : " + workingYears;
    }

    @Override
    public void ChangeInfo(int modified) {
        this.workingYears = modified;
    }
}

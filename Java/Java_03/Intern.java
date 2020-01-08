public class Intern extends Employee {

    private int contractPeriod;

    public Intern(String employeeNo, String name, String part, int contractPeriod) {
        super(employeeNo, name, part);
        this.contractPeriod = contractPeriod;
        totalEmployee++;
    }

    public int getContractPeriod() {
        return contractPeriod;
    }

    public void setContractPeriod(int contractPeriod) {
        this.contractPeriod = contractPeriod;
    }

    @Override
    public String toString() {
        return super.toString() +
                "Details : Internship\n" +
                "ContractPeriod : " + contractPeriod;
    }

    @Override
    public void ChangeInfo(int modified) {
        this.contractPeriod = modified;
    }
}

public class Main {

    public static void main(String[] args) {

        Manager manager1 = new Manager("A001", "Kim", "Management", 15);
        Manager manager2 = new Manager("A002", "Lee", "Quality", 10);
        Intern intern1 = new Intern("C001", "Park", "Management", 6);
        Intern intern2 = new Intern("C002", "Choi", "Quality", 6);

        System.out.println("________________________________________");
        System.out.println(manager1);
        System.out.println("________________________________________");
        System.out.println(manager2);

        System.out.println("________________________________________");
        System.out.println(intern1);
        System.out.println("________________________________________");
        System.out.println(intern2);

        System.out.println("________________________________________");
        System.out.println("Total Employees : " + Employee.getTotalEmployee());

        // -------------------------------------------------------------------------------- 다형성

        Employee manager3 = new Manager("A003", "Koo", "Sale", 20);
        Employee intern3 = new Intern("C003", "Lim", "Sale", 3);

        ChangeInformation(manager3, 21);
        ChangeInformation(intern3, 6);

        System.out.println("________________________________________");
        System.out.println(manager3);
        System.out.println("________________________________________");
        System.out.println(intern3);
    }

    private static void ChangeInformation(Employee employee, int value) {
        employee.ChangeInfo(value);
    }
}

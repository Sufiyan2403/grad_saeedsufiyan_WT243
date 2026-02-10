import java.util.*;
import java.util.stream.*;

class Employee{
    private String name;
    private int age;
    private String gender;
    private double salary;
    private String designation;
    private String department;

    public Employee(String name, int age, String gender, double salary, String designation, String department) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.salary = salary;
        this.designation = designation;
        this.department = department;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public String getDesignation() {
        return designation;
    }

    public String getDepartment() {
        return department;
    }


}

public class Assignment {
    
    public static void main(String[] args) {
        List<Employee> employees = Arrays.asList(
            new Employee("Rajesh ", 45, "Male", 95000, "Manager", "IT"),
            new Employee("Priya", 32, "Female", 75000, "Developer", "IT"),
            new Employee("Amit", 28, "Male", 65000, "Developer", "IT"),
            new Employee("Reddy", 35, "Female", 85000, "Senior Developer", "IT"),
            new Employee("Vikram", 50, "Male", 120000, "Manager", "HR"),
            new Employee("Ankit", 29, "Male", 70000, "HR Executive", "HR"),
            new Employee("Rahul", 38, "Male", 80000, "Senior HR Executive", "HR"),
            new Employee("Aditya", 42, "Female", 110000, "Manager", "Finance"),
            new Employee("Suresh", 31, "Male", 72000, "Accountant", "Finance"),
            new Employee("Meera", 27, "Female", 68000, "Junior Accountant", "Finance"),
            new Employee("Arun", 48, "Male", 105000, "Manager", "Sales"),
            new Employee("Divya", 33, "Female", 78000, "Sales Executive", "Sales"),
            new Employee("Karthik", 26, "Male", 62000, "Junior Sales Executive", "Sales"),
            new Employee("Pooja", 36, "Female", 88000, "Senior Sales Executive", "Sales"),
            new Employee("Manoj", 44, "Male", 92000, "Team Lead", "IT"),
            new Employee("Ritu ", 30, "Female", 73000, "Developer", "IT"),
            new Employee("Sanjay", 39, "Male", 89000, "Senior Developer", "IT"),
            new Employee("Deepa", 34, "Female", 76000, "Developer", "IT"),
            new Employee("Arjun", 41, "Male", 98000, "Manager", "Operations"),
            new Employee("Anjali", 43, "Female", 94000, "Senior HR Executive", "HR")
        );


        System.out.println("1. HIGHEST SALARY PAID EMPLOYEE:");
        employees.stream().sorted(Comparator.comparingDouble(Employee::getSalary).reversed()).limit(1)
        .forEach(e->System.out.println(e.getName()+" "+e.getAge()+" "+e.getSalary()+" "+e.getDesignation()+" "+e.getDepartment()) );
        
        System.out.println("-----------------------------------");



        System.out.println(" MALE AND FEMALE EMPLOYEE COUNT:");
        Map<String, Long> genderCount = employees.stream().collect(Collectors.groupingBy(Employee::getGender, Collectors.counting()));
        System.out.println(genderCount);
        System.out.println("-----------------------------------");


        System.out.println("3. DEPARTMENT-WISE TOTAL EXPENSE:");
        Map<String, Double> departmentExpense = employees.stream().collect(Collectors.groupingBy(Employee::getDepartment, Collectors.summingDouble(Employee::getSalary)));
        System.out.println(departmentExpense);
        System.out.println("-----------------------------------");




        System.out.println("4. TOP 5 SENIOR EMPLOYEES (By Age):");
        employees.stream().sorted(Comparator.comparingInt(Employee::getAge).reversed()).limit(5).forEach(emp -> System.out.println("name: "+emp.getName() + "  Age: " + emp.getAge() + " (" + emp.getDesignation() + ")"));
        System.out.println("-----------------------------------");


        System.out.println("5. NAMES OF ALL MANAGERS:");
        List<String> managerNames = employees.stream().filter(emp -> emp.getDesignation().contains("Manager")).map(Employee::getName).collect(Collectors.toList());
        managerNames.forEach(System.out::println);
        System.out.println("-----------------------------------");


        System.out.println("6. SALARY HIKE:");
        System.out.println("Before Hike");
        employees.stream().filter(emp -> !emp.getDesignation().contains("Manager")).forEach(emp -> System.out.println(emp.getName() + " (" + emp.getDesignation() + "): Rs." + emp.getSalary()));
        
        employees.stream().filter(emp -> !emp.getDesignation().contains("Manager")).forEach(emp -> emp.setSalary(emp.getSalary()*1.20));
        
        System.out.println("After Hike");
        employees.stream().filter(emp -> !emp.getDesignation().contains("Manager")).forEach(emp -> System.out.println(emp.getName() + " (" + emp.getDesignation() + "): Rs." + emp.getSalary()));
        System.out.println("-----------------------------------");


        System.out.println("7. TOTAL NUMBER OF EMPLOYEES:");
        long totalEmployees = employees.stream().count();
        System.out.println("Total Employees: " + totalEmployees);

        
        
    }
}

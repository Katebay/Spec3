package ru.kate.spec.third;

/**
 * Created by KateBay on 31.01.17.
 */
public class Employee {

    private final String name;
    private final String jobName;
    private final int salary;

    public Employee(String name, String jobName, int salary) {
        this.name = name;
        this.jobName = jobName;
        this.salary = salary;
    }

    public String getName() {
        return name;
    }

    public String getJobName() {
        return jobName;
    }

    public int getSalary() {
        return salary;
    }

}

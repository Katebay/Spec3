package ru.kate.spec.third;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * Created by KateBay on 31.01.17.
 */
public class EmployeeWorker {

    public static List<Employee> parse(File file) {
        List<Employee> result = new ArrayList<>();
        try(Scanner scan = new Scanner(new FileReader(file))) {
            while(scan.hasNextLine()) {
                String line = scan.nextLine().replace(" ", "");
                String[] args = line.split(",");
                result.add(new Employee(args[0], args[1], Integer.parseInt(args[2])));
            }
        }catch(IOException ex) {
            ex.printStackTrace();
        }
        return result;
    }

    public static int maxSalary(List<Employee> workers) {
        return workers.stream().map(Employee::getSalary).max(Integer::max).orElse(0);
    }

    public static int minSalary(List<Employee> workers) {
        return workers.stream().map(Employee::getSalary).max(Integer::min).orElse(0);
    }

    public static double averageSalary(List<Employee> workers) {
        return workers.stream().mapToInt(Employee::getSalary).average().orElse(0d);
    }

    public static Map<String, Integer> jobCount(List<Employee> workers) {
        return workers.stream().collect(Collectors.groupingBy(Employee::getJobName, Collectors.reducing(0, w -> 1, Integer::sum)));
    }

    public static Map<String, Long> jobCountLong(List<Employee> workers) {
        return workers.stream().collect(Collectors.groupingBy(Employee::getJobName, Collectors.counting()));
    }

    public static Map<Character, Integer> abc(List<Employee> workers) {
        return workers.stream().collect(Collectors.groupingBy(w -> w.getName().charAt(0), Collectors.summingInt(w -> 1)));
    }

}

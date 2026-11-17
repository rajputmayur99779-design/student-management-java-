import java.util.List;
import java.util.Optional;
import java.util.Scanner;

/**
 * Console menu for the Student Management System.
 */
public class Main {
    private static final String HEADER =
            String.format("%-6s %-20s %-12s %6s  %s", "Roll", "Name", "Course", "Marks", "Grade");

    private static final Scanner in = new Scanner(System.in);

    public static void main(String[] args) {
        StudentService service = new StudentService(new FileStorage("students.csv"));
        System.out.println("=== Student Management System ===");

        boolean running = true;
        while (running) {
            printMenu();
            int choice = readInt("Choose an option: ");
            try {
                switch (choice) {
                    case 1 -> addStudent(service);
                    case 2 -> showAll(service);
                    case 3 -> searchStudent(service);
                    case 4 -> updateStudent(service);
                    case 5 -> deleteStudent(service);
                    case 6 -> showRankList(service);
                    case 7 -> showStats(service);
                    case 0 -> running = false;
                    default -> System.out.println("Invalid option, try again.");
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
        System.out.println("Data saved. Goodbye!");
    }

    private static void printMenu() {
        System.out.println();
        System.out.println("1. Add student");
        System.out.println("2. View all students");
        System.out.println("3. Search by roll number or name");
        System.out.println("4. Update student");
        System.out.println("5. Delete student");
        System.out.println("6. Rank list (by marks)");
        System.out.println("7. Statistics");
        System.out.println("0. Exit");
    }

    private static void addStudent(StudentService service) {
        int roll = readInt("Roll number: ");
        String name = readLine("Name: ");
        String course = readLine("Course: ");
        double marks = readDouble("Marks (0-100): ");
        service.add(new Student(roll, name, course, marks));
        System.out.println("Student added.");
    }

    private static void showAll(StudentService service) {
        printTable(service.getAllSortedByRollNo());
    }

    private static void searchStudent(StudentService service) {
        String text = readLine("Enter roll number or part of a name: ");
        if (text.matches("\\d+")) {
            Optional<Student> s = service.findByRollNo(Integer.parseInt(text));
            printTable(s.map(List::of).orElse(List.of()));
        } else {
            printTable(service.searchByName(text));
        }
    }

    private static void updateStudent(StudentService service) {
        int roll = readInt("Roll number to update: ");
        Optional<Student> existing = service.findByRollNo(roll);
        if (existing.isEmpty()) {
            System.out.println("No student with that roll number.");
            return;
        }
        System.out.println("Current: " + existing.get());
        String name = readLine("New name: ");
        String course = readLine("New course: ");
        double marks = readDouble("New marks (0-100): ");
        service.update(roll, name, course, marks);
        System.out.println("Student updated.");
    }

    private static void deleteStudent(StudentService service) {
        int roll = readInt("Roll number to delete: ");
        System.out.println(service.delete(roll) ? "Student deleted." : "No student with that roll number.");
    }

    private static void showRankList(StudentService service) {
        List<Student> ranked = service.getRankList();
        if (ranked.isEmpty()) {
            System.out.println("No students yet.");
            return;
        }
        System.out.println("Rank  " + HEADER);
        int rank = 1;
        for (Student s : ranked) {
            System.out.printf("%-5d %s%n", rank++, s);
        }
    }

    private static void showStats(StudentService service) {
        System.out.println("Total students : " + service.count());
        System.out.printf("Average marks  : %.2f%n", service.averageMarks());
        List<Student> ranked = service.getRankList();
        if (!ranked.isEmpty()) {
            System.out.println("Topper         : " + ranked.get(0).getName() + " (" + ranked.get(0).getMarks() + ")");
        }
    }

    private static void printTable(List<Student> list) {
        if (list.isEmpty()) {
            System.out.println("No records found.");
            return;
        }
        System.out.println(HEADER);
        list.forEach(System.out::println);
    }

    // ---- input helpers: keep asking until the user types a valid value ----

    private static String readLine(String prompt) {
        System.out.print(prompt);
        return in.nextLine().trim();
    }

    private static int readInt(String prompt) {
        while (true) {
            String text = readLine(prompt);
            try {
                return Integer.parseInt(text);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a whole number.");
            }
        }
    }

    private static double readDouble(String prompt) {
        while (true) {
            String text = readLine(prompt);
            try {
                return Double.parseDouble(text);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number.");
            }
        }
    }
}

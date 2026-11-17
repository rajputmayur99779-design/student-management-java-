# Student Management System (Java)

A console application to manage student records. Data is saved in a CSV file,
so nothing is lost when the program is closed.

## Tech stack
Java 17+, Object-Oriented Programming, Collections, Streams, File I/O

## Features
- Add, view, search (roll number or name), update and delete students
- Automatic grade (A / B / C / F) from marks
- Rank list sorted by marks, average marks and topper
- Input validation (duplicate roll number, marks outside 0-100, wrong input type)
- Data saved to `students.csv` and loaded again on the next run

## How to run
```bash
mkdir out
javac -d out src/*.java
java -cp out Main
```

## Project structure
| File | Responsibility |
|------|----------------|
| `Student.java` | Data model with encapsulation and validation |
| `FileStorage.java` | Reads and writes the CSV file |
| `StudentService.java` | Business logic (add, search, update, delete, reports) |
| `Main.java` | Menu and user input |

The program is split into layers (model, storage, logic, UI) so each class has one job.

## What I learned
- Designing classes with encapsulation and validation
- Using ArrayList, Optional and Java Streams
- Reading and writing files with try-with-resources
- Handling exceptions and bad user input

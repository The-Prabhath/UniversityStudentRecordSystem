# University Student Record and Campus Route Management System

CIT300 Data Structures and Algorithms — Graded Practical Assignment 1 (Week 10)

## Group Members

| Name | Student ID | Assigned Responsibility | Individual Contribution |
|---|---|---|---|
| _TODO_ | _TODO_ | Linked List module (student record management) | _TODO_ |
| _TODO_ | _TODO_ | Stack and Queue module (recent actions & service requests) | _TODO_ |
| _TODO_ | _TODO_ | BST/Hashing module (search index) | _TODO_ |
| _TODO_ | _TODO_ | Graph module (campus locations & BFS/DFS) | _TODO_ |

## Project Structure

```
src/
├── common/          Shared Student model (all members)
├── studentrecords/  Linked list module (Member 1)
├── actionsqueue/    Stack and Queue module (Member 2)
├── searchindex/     BST and Hashing module (Member 3)
├── campusgraph/      Graph module (Member 4)
└── app/             Main.java and SystemManager.java (integration)
```

## How to Run

```
javac -d out $(find src -name "*.java")
java -cp out app.Main
```

## Features

- Add, update, delete, search, and display student records
- Linked list storage for student records
- Stack-based recent actions / undo history
- Queue-based service request processing
- BST for ordered student record search by ID
- Hash table for fast student ID lookup
- Graph-based campus location and connection management
- BFS and DFS traversal of the campus network

## Notes

_Add anything the team wants examiners to know here — known limitations,
how to test, etc._

# University Student Record and Campus Route Management System

**Module:** CIT300 Data Structures and Algorithms
**Assignment:** Graded Practical Assignment 1 (Week 10)
**Coverage:** Weeks 1–9 — Linear Data Structures, Trees, Hashing, and Graphs
**Contribution:** 10% of final module grade

---

## 1. Project Overview

This is a Java console application that manages two connected systems:

1. **Student Records** — storing, searching, and managing university students using a linked list, a stack, a queue, a binary search tree, and a hash table.
2. **Campus Route Network** — modeling campus locations and the roads/paths between them as a graph, with BFS/DFS traversal.

The point of the assignment is to demonstrate *practical, working use* of each data structure covered in the module — not to build the most elegant app possible. Every structure listed above must be genuinely used, not simulated or faked with a shortcut.

---

## 2. Group Members

| Name | Student ID | Assigned Responsibility | Individual Contribution |
|---|---|---|---|
| Prabhath | 23DA2-0414 | Linked List module — student record CRUD | _TODO_ |
| Haritha | 23DA2-0421 | Stack and Queue module — actions history & service requests | _TODO_ |
| Hasindu | 23DA2-0150 | BST and Hashing module — search index | _TODO_ |
| Vinod | 23DA2-0464 | Graph module — campus locations & BFS/DFS | _TODO_ |

*(All members: integration, testing, debugging, documentation, GitHub collaboration.)*

**This table must be complete and accurate before submission** — the assignment brief states incorrect or missing member details can result in marks being deducted.

---

## 3. System Architecture

### 3.1 Design principle

Each member owns one Java package. Nobody edits another member's package. A single shared `Student` class, agreed on Day 1 and frozen afterward, is passed by reference into every structure that needs it — this is what keeps the linked list, BST, and hash table all showing the same data without duplicating objects.

`SystemManager.java` is the one class that coordinates between modules. `Main.java` only handles the menu loop — it contains no real logic.

### 3.2 Package structure

```
src/
├── common/
│   └── Student.java                 Shared model — Student ID, Name, Programme, Marks
│
├── studentrecords/                  Member 1
│   ├── StudentNode.java             Linked list node
│   └── StudentLinkedList.java       add / update / delete / search / display
│
├── actionsqueue/                    Member 2
│   ├── ActionRecord.java            One logged action (type, student ID, timestamp)
│   ├── ActionStack.java             Recent actions / undo history (LIFO)
│   ├── ServiceRequest.java          One pending service request
│   └── ServiceQueue.java            Service requests in arrival order (FIFO)
│
├── searchindex/                     Member 3
│   ├── BSTNode.java                 Tree node
│   ├── StudentBST.java              Ordered search by Student ID
│   └── StudentHashTable.java        Fast ID lookup (custom, separate chaining)
│
├── campusgraph/                     Member 4
│   ├── CampusGraph.java             Adjacency list of campus locations/roads
│   └── GraphTraversal.java          BFS and DFS
│
└── app/
    ├── Main.java                    Menu loop only — no business logic
    └── SystemManager.java           Wires all modules together
```

### 3.3 How a request flows through the system

Example — **Add Student Record**:

```
Main.java (reads menu choice)
   → SystemManager.addStudent(id, name, programme, marks)
        → check StudentHashTable.getById(id) for duplicates
        → create ONE Student object
        → StudentLinkedList.addStudent(student)     [Member 1]
        → StudentBST.insert(student)                [Member 3]
        → StudentHashTable.put(student)              [Member 3]
        → ActionStack.push(new ActionRecord(...))    [Member 2]
   → Main.java prints result
```

Update and delete follow the same pattern: every write touches the linked list, BST, and hash table together, then logs to the action stack. The **graph module is independent** — it never touches `Student` or any other module's data.

---

## 4. Requirements Mapping

Use this table to confirm every assignment requirement is covered before submission.

| # | Requirement | Covered by |
|---|---|---|
| 1 | Store Student ID, Name, Programme, Marks | `common.Student` |
| 2 | Linked list to store/manage records | `studentrecords.StudentLinkedList` |
| 3 | Stack for recent actions / undo / history | `actionsqueue.ActionStack` |
| 4 | Queue for service requests in arrival order | `actionsqueue.ServiceQueue` |
| 5 | BST/AVL to organize/search by Student ID | `searchindex.StudentBST` |
| 6 | Hashing for efficient ID searching | `searchindex.StudentHashTable` |
| 7 | Graph representing campus locations/connections | `campusgraph.CampusGraph` |
| 8 | Adjacency list or matrix | `CampusGraph` (adjacency list) |
| 9 | Add/remove locations and connections | `CampusGraph.addLocation/removeLocation/addConnection/removeConnection` |
| 10 | Display connected locations / campus network | `CampusGraph.displayConnections()` |
| 11 | At least one graph traversal (BFS or DFS) | `campusgraph.GraphTraversal` (both implemented) |
| 12 | Add/update/delete/search/display for records | `SystemManager` + `StudentLinkedList` + `StudentBST` + `StudentHashTable` |
| 13 | Menu-driven console interface with input validation | `app.Main` |
| 14 | Handle invalid input, duplicates, missing records, invalid marks, unavailable connections | Validation throughout every module |

---

## 5. Menu

```
1.  Add Student Record
2.  Update Student Record
3.  Delete Student Record
4.  Display All Records using Linked List
5.  Add Service Request to Queue
6.  Process Next Service Request
7.  Display Recent Actions using Stack
8.  Display Students using BST/AVL
9.  Search Student using Hashing
10. Add Campus Location
11. Remove Campus Location
12. Add Campus Connection/Road
13. Remove Campus Connection/Road
14. Display Campus Connections
15. Traverse Campus Locations using BFS or DFS
16. Exit
```

---

## 6. How to Build and Run

From the project root:

```bash
# Compile
javac -d out $(find src -name "*.java")

# Run
java -cp out app.Main
```

If your team is using an IDE (IntelliJ, Eclipse, VS Code with the Java extension) instead, mark `src/` as the source root and run `app.Main`.

---

## 7. Git Workflow Summary

Full detail is in the team's separate GitHub workflow guide — short version:

1. Each member works on their **own branch** (`feature/student-linkedlist`, `feature/stack-queue`, `feature/bst-hashing`, `feature/campus-graph`)
2. Each member **only edits files inside their own package folder**
3. Commit often, with clear messages
4. Open a Pull Request into `main` when a module is ready, referencing its GitHub Issue (`Closes #<number>`)
5. One PR is reviewed and merged at a time; `SystemManager.java` is updated right after each merge
6. Everyone pulls `main` after every merge before starting new work

---

## 8. Testing Checklist

Before considering the project done, manually test:

- [ ] Add a student, then try adding the same ID again — should be rejected
- [ ] Update a student that doesn't exist — should fail gracefully, no crash
- [ ] Delete a student, confirm they're gone from linked list, BST, and hash table
- [ ] Enqueue several service requests, dequeue them — confirm FIFO order
- [ ] Push several actions, display recent actions — confirm most-recent-first (LIFO)
- [ ] Search a student by ID that doesn't exist — clear "not found" message, no crash
- [ ] Add campus locations and connections, display the network
- [ ] Remove a location — confirm its connections are also removed
- [ ] Run BFS and DFS from a valid location — confirm sensible traversal order
- [ ] Run BFS/DFS from a location that doesn't exist — handled gracefully
- [ ] Enter invalid menu input (letters, out-of-range numbers) — no crash

---

## 9. Submission Checklist

(Mirrors the official assignment checklist — confirm all before submitting)

- [ ] Complete project implemented, including all data structures and graph functionality
- [ ] All group members' names and student IDs are correct in this README
- [ ] Responsibilities and individual contributions are documented above
- [ ] GitHub repository and demo video are complete
- [ ] If using Google Drive, the complete project is uploaded
- [ ] Google Drive link is copied correctly into the Notepad (.txt) file
- [ ] `asanka.r@sltc.ac.lk` has Editor access
- [ ] `kaushika.w@sltc.ac.lk` has Editor access
- [ ] Google Drive permissions checked before LMS submission
- [ ] Submitted through the designated LMS link **before the deadline: 29th September**

---

## 10. Known Limitations / Notes

_Add anything the team wants examiners to know — e.g. "BST is not self-balancing (AVL was not required)," edge cases not handled, features cut for time, etc._

# Blockchain Implementation in Java

## Project Overview
This project implements a basic blockchain system using Java. It demonstrates how blockchain technology stores transactions securely using cryptographic hashing and links blocks together.

## Technologies Used
- Java 23
- SHA-256 Hashing
- Object-Oriented Programming
- VS Code

## Features
- Creation of blocks
- Adding transactions
- SHA-256 hash generation
- Linking blocks using previous hashes
- Blockchain validation
- Tampering detection
- Proof of Work mining

## Project Files
- `Block.java` – Defines the block structure and mining functionality.
- `Blockchain.java` – Manages the blockchain and validates blocks.
- `Main.java` – Main program for executing blockchain operations.
- `ApiServer.java` – Backend server implementation.

## How to Run

1. Install Java JDK 23.
2. Open the project folder in VS Code.
3. Open the terminal.
4. Compile the application:

```bash
javac Block.java Blockchain.java Main.java
```

5. Run the application:

```bash
java Main
```

## Learning Outcomes
- Understanding blockchain architecture.
- Implementing cryptographic hashing.
- Learning transaction validation.
- Understanding Proof of Work.
- Detecting unauthorized modifications.

## Conclusion
This project demonstrates the basic working principles of blockchain technology through a Java-based implementation.

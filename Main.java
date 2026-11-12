import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        // Create blockchain
        Blockchain blockchain = new Blockchain();

        System.out.println("================================");
        System.out.println("   JAVA BLOCKCHAIN APPLICATION");
        System.out.println("================================");

        int choice;

        do {
            System.out.println("\n1. Add New Block");
            System.out.println("2. Display Blockchain");
            System.out.println("3. Validate Blockchain");
            System.out.println("4. Exit");

            System.out.print("\nEnter your choice: ");
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1:
                    System.out.print("Enter transaction details: ");
                    String data = sc.nextLine();

                    blockchain.addBlock(data);
                    break;

                case 2:
                    blockchain.displayBlockchain();
                    break;

                case 3:
                    if (blockchain.isChainValid()) {
                        System.out.println("Blockchain is VALID!");
                    } else {
                        System.out.println("Blockchain is INVALID!");
                    }
                    break;

                case 4:
                    System.out.println("Thank you for using Blockchain!");
                    break;

                default:
                    System.out.println("Invalid choice! Try again.");
            }

        } while (choice != 4);

        sc.close();
    }
}
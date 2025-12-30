package library;

import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Library library = new Library();
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== LIBRARY MANAGEMENT SYSTEM ===");
            System.out.println("1. Add New Book");
            System.out.println("2. Remove Book");
            System.out.println("3. View All Books");
            System.out.println("4. Search Books");
            System.out.println("5. Register Member");
            System.out.println("6. Borrow Book");
            System.out.println("7. Return Book");
            System.out.println("8. View Library Statistics");
            System.out.println("9. Calculate Fine");
            System.out.println("10. Exit");

            System.out.print("Enter choice: ");

            int choice;
            try {
                choice = sc.nextInt();
            } catch (Exception ex) {
                System.out.println("Invalid input.");
                sc.next();
                continue;
            }

            sc.nextLine(); 

            switch (choice) {
                case 1:
                    System.out.print("ISBN: ");
                    String isbn = sc.nextLine();
                    System.out.print("Title: ");
                    String title = sc.nextLine();
                    System.out.print("Author: ");
                    String author = sc.nextLine();
                    System.out.print("Year: ");
                    int year = sc.nextInt();
                    sc.nextLine();

                    library.addBook(new Book(isbn, title, author, year));
                    break;

                case 2:
                    System.out.print("Enter ISBN to remove: ");
                    isbn = sc.nextLine();
                    library.removeBook(isbn);
                    break;

                case 3:
                    library.displayAllBooks();
                    break;

                case 4:
                    System.out.print("Enter title or author keyword: ");
                    String keyword = sc.nextLine();
                    List<Book> results = library.searchBooks(keyword);
                    if (results.isEmpty()) {
                        System.out.println("No books found.");
                    } else {
                        System.out.println("\n=== SEARCH RESULTS ===");
                        results.forEach(System.out::println);
}
                    break;    

                case 5:
                    System.out.print("Member ID: ");
                    String id = sc.nextLine();
                    System.out.print("Name: ");
                    String name = sc.nextLine();

                    library.registerMember(new Member(id, name));
                    break;

                case 6:
                    System.out.print("ISBN: ");
                    isbn = sc.nextLine();
                    System.out.print("Member ID: ");
                    id = sc.nextLine();

                    library.borrowBook(isbn, id);
                    break;

                case 7:
                    System.out.print("ISBN: ");
                    isbn = sc.nextLine();
                    System.out.print("Member ID: ");
                    id = sc.nextLine();

                    library.returnBook(isbn, id);
                    break;

                case 8:
                    library.displayStatistics();
                    break;

                case 9:
                    System.out.print("Enter ISBN: ");
                    isbn = sc.nextLine();
                    library.calculateFine(isbn);
                    break;

                case 10:
                    System.out.println("Exiting...");
                    sc.close();
                    return;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}

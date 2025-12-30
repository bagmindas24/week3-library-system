package library;

import java.util.stream.Collectors;
import java.util.List;

public class Library {

    private List<Book> books;
    private List<Member> members;
    private FileHandler fileHandler;


    public Library() {
        fileHandler = new FileHandler();
        books = fileHandler.loadBooks();
        members = fileHandler.loadMembers();
    }


    public void addBook(Book book) {
        books.add(book);
        fileHandler.saveBooks(books);
        System.out.println("Book added successfully.");
    }


    public void removeBook(String isbn) {
    Book book = findBookByIsbn(isbn);

    if (book == null) {
        System.out.println("Book not found.");
        return;
    }

    if (!book.isAvailable()) {
        System.out.println("Cannot remove a borrowed book.");
        return;
    }

    books.remove(book);
    fileHandler.saveBooks(books);
    System.out.println("Book removed successfully.");
}


    public void displayAllBooks() {
        if (books.isEmpty()) {
            System.out.println("No books available.");
            return;
        }

        for (Book book : books) {
            System.out.println(book);
        }
    }


    public Book findBookByIsbn(String isbn) {
        for (Book book : books) {
            if (book.getIsbn().equals(isbn)) {
                return book;
            }
        }
        return null;
    }

    
    public List<Book> searchBooks(String keyword) {
    return books.stream()
            .filter(book -> book.getTitle().toLowerCase().contains(keyword.toLowerCase())
                         || book.getAuthor().toLowerCase().contains(keyword.toLowerCase()))
            .collect(Collectors.toList());
}


    public void registerMember(Member member) {
        members.add(member);
        fileHandler.saveMembers(members);
        System.out.println("Member registered successfully.");
    }


    public Member findMemberById(String id) {
        for (Member member : members) {
            if (member.getId().equals(id)) {
                return member;
            }
        }
        return null;
    }


    public void borrowBook(String isbn, String memberId) {
        Book book = findBookByIsbn(isbn);
        Member member = findMemberById(memberId);

        if (book == null || member == null) {
            System.out.println("Book or Member not found.");
            return;
        }

        if (!book.isAvailable()) {
            System.out.println("Book already borrowed.");
            return;
        }

        book.setAvailable(false);
        book.setBorrowedBy(memberId);
        book.setDueDate(java.time.LocalDate.now().plusWeeks(2));

        member.borrowBook(isbn);

        fileHandler.saveBooks(books);
        fileHandler.saveMembers(members);

        System.out.println("Book borrowed successfully.");
    }


    public void returnBook(String isbn, String memberId) {
        Book book = findBookByIsbn(isbn);
        Member member = findMemberById(memberId);

        if (book == null || member == null) {
            System.out.println("Invalid return request.");
            return;
        }

        book.setAvailable(true);
        book.setBorrowedBy(null);
        book.setDueDate(null);

        member.returnBook(isbn);

        fileHandler.saveBooks(books);
        fileHandler.saveMembers(members);

        System.out.println("Book returned successfully.");
    }


    public void displayStatistics() {
    long availableBooks = books.stream()
            .filter(Book::isAvailable)
            .count();

    long borrowedBooks = books.size() - availableBooks;

    long overdueBooks = books.stream()
            .filter(book -> !book.isAvailable() && book.isOverdue())
            .count();

    System.out.println("\n=== LIBRARY STATISTICS ===");
    System.out.println("Total Books: " + books.size());
    System.out.println("Available Books: " + availableBooks);
    System.out.println("Borrowed Books: " + borrowedBooks);
    System.out.println("Registered Members: " + members.size());
    System.out.println("Overdue Books: " + overdueBooks);
    }


    public void calculateFine(String isbn) {
    Book book = findBookByIsbn(isbn);

    if (book == null || book.isAvailable()) {
        System.out.println("No fine applicable.");
        return;
    }

    if (book.isOverdue()) {
        long daysOverdue = java.time.temporal.ChronoUnit.DAYS.between(
                book.getDueDate(),
                java.time.LocalDate.now()
        );

        double fine = daysOverdue * 5; // ₹5 per day
        System.out.println("Overdue fine: ₹" + fine);
    } else {
        System.out.println("Book returned on time. No fine.");
    }
}

}

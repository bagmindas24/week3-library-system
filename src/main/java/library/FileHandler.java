package library;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FileHandler{
    private final String Books_File = "data/books.txt";
    private final String Members_File = "data/members.txt";

    public FileHandler() {
        File folder = new File("data");
        if (!folder.exists()) {
            folder.mkdirs();
        }
    }

    public List<Book> loadBooks() {
        List<Book> books = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(Books_File))){
            String line;

            while((line = br.readLine()) != null){
                String[] data = line.split("\\|");

                Book book = new Book(
                    data[0],
                    data[1],
                    data[2],
                    Integer.parseInt(data[3])
                );

                book.setAvailable(Boolean.parseBoolean(data[4]));
                book.setBorrowedBy(data[5].equals("null") ? null : data[5]);
                book.setDueDate(data[6].equals("null") ? null : LocalDate.parse(data[6]));

                books.add(book);
            }
        } catch(IOException ex){
            System.out.println("Books file not found. Starting new");
        }

        return books;
    }

     public void saveBooks(List<Book> books) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(Books_File))) {
            for (Book book : books) {
                bw.write(
                        book.getIsbn() + "|" +
                        book.getTitle() + "|" +
                        book.getAuthor() + "|" +
                        book.getYear() + "|" +
                        book.isAvailable() + "|" +
                        book.getBorrowedBy() + "|" +
                        book.getDueDate()
                );
                bw.newLine();
            }
        } catch (IOException ex) {
            System.out.println("Error saving books.");
        }
    }

    public List<Member> loadMembers() {
        List<Member> members = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(Members_File))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] data = line.split("\\|");

                Member member = new Member(data[0], data[1]);

                if (data.length > 2 && !data[2].isEmpty()) {
                    String[] borrowed = data[2].split(",");
                    for (String isbn : borrowed) {
                        member.borrowBook(isbn);
                    }
                }

                members.add(member);
            }

        } catch (IOException ex) {
            System.out.println("Members file not found. Starting new");
        }

        return members;
    }

    public void saveMembers(List<Member> members) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(Members_File))) {
            for (Member member : members) {
                bw.write(
                        member.getId() + "|" +
                        member.getName() + "|" +
                        String.join(",", member.getBorrowedBooks())
                );
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving members.");
        }
    }
}


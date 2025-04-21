import java.util.ArrayList;
import java.util.Scanner;

abstract class LibraryItem {
    protected String id;
    protected String title;
    protected boolean isBorrowed;

    public LibraryItem(String id, String title) {
        this.id = id;
        this.title = title;
        this.isBorrowed = false;
    }

    public void borrow() {
        if (!isBorrowed) {
            isBorrowed = true;
            System.out.println(title + " has been borrowed.");
        } else {
            System.out.println(title + " is already borrowed.");
        }
    }

    public void returnItem() {
        if (isBorrowed) {
            isBorrowed = false;
            System.out.println(title + " has been returned.");
        } else {
            System.out.println(title + " was not borrowed.");
        }
    }

    public boolean isBorrowed() {
        return isBorrowed;
    }

    public abstract void displayInfo(); // Polymorphism
}


class Book extends LibraryItem {
    private String author;

    public Book(String id, String title, String author) {
        super(id, title);
        this.author = author;
    }

    @Override
    public void displayInfo() {
        System.out.println("Book ID: " + id + ", Title: " + title + ", Author: " + author + ", Borrowed: " + isBorrowed);
    }
}


abstract class Member {
    protected String memberId;
    protected String name;

    public Member(String memberId, String name) {
        this.memberId = memberId;
        this.name = name;
    }

    public abstract void borrowBook(Book book); // Polymorphism
}


class Student extends Member {
    public Student(String memberId, String name) {
        super(memberId, name);
    }

    @Override
    public void borrowBook(Book book) {
        if (!book.isBorrowed()) {
            book.borrow();
            System.out.println(name + " borrowed the book: " + book.title);
        } else {
            System.out.println("Book is already borrowed.");
        }
    }
}


class Library {
    private ArrayList<Book> books = new ArrayList<>();
    private ArrayList<Member> members = new ArrayList<>();

    public void addBook(Book book) {
        books.add(book);
    }

    public void registerMember(Member member) {
        members.add(member);
    }

    public void showBooks() {
        for (Book book : books) {
            book.displayInfo();
        }
    }

    public Book getBookById(String id) {
        for (Book book : books) {
            if (book.id.equals(id)) return book;
        }
        return null;
    }

    public Member getMemberById(String id) {
        for (Member member : members) {
            if (member.memberId.equals(id)) return member;
        }
        return null;
    }
}

public class LibraryManagementSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Library lib = new Library();

        // Sample data
        lib.addBook(new Book("B001", "The Alchemist", "Paulo Coelho"));
        lib.addBook(new Book("B002", "1984", "George Orwell"));
        lib.registerMember(new Student("S101", "Alice"));
        lib.registerMember(new Student("S102", "Bob"));

        int choice;
        do {
            System.out.println("\n=== Library Menu ===");
            System.out.println("1. Show Books");
            System.out.println("2. Borrow Book");
            System.out.println("3. Return Book");
            System.out.println("4. Exit");
            System.out.print("Enter choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    lib.showBooks();
                    break;
                case 2:
                    System.out.print("Enter Member ID: ");
                    String memId = scanner.nextLine();
                    System.out.print("Enter Book ID: ");
                    String bookId = scanner.nextLine();
                    Member mem = lib.getMemberById(memId);
                    Book book = lib.getBookById(bookId);
                    if (mem != null && book != null) {
                        mem.borrowBook(book);
                    } else {
                        System.out.println("Invalid member or book ID.");
                    }
                    break;
                case 3:
                    System.out.print("Enter Book ID to return: ");
                    String returnId = scanner.nextLine();
                    Book returnBook = lib.getBookById(returnId);
                    if (returnBook != null) {
                        returnBook.returnItem();
                    } else {
                        System.out.println("Book not found.");
                    }
                    break;
                case 4:
                    System.out.println("Exiting... Thank you!");
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        } while (choice != 4);

        scanner.close();
    }
}

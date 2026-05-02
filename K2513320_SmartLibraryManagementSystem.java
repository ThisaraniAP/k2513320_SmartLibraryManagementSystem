/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.k2513320_smartlibrarymanagementsystem;

/**
 *
 * @author slthi
 */

import java.util.*;

public class K2513320_SmartLibraryManagementSystem {
    private Scanner scanner = new Scanner(System.in);
    private List<K2513320_User> users = new ArrayList<>();
    private ArrayList<K2513320_Book> books = new ArrayList<>();
    private List<K2513320_Borrow> borrows = new ArrayList<>();
    private K2513320_NotificationSubject notificationSubject = new K2513320_NotificationSubject();
    private K2513320_UserActionInvoker invoker = new K2513320_UserActionInvoker();
    private List<K2513320_Reservation> reservations = new ArrayList<>();
    private Map<Integer, K2513320_BookComponent> decoratedBooks = new HashMap<>();
    private static long borrowCounter = 0;
    private static long reservationCounter = 0;

    public static void main(String[] args) {
        System.out.println("\nWelcome to the Smart Library Management System!\n");
        K2513320_SmartLibraryManagementSystem app = new K2513320_SmartLibraryManagementSystem();
        app.tempUsers();
        app.tempBooks();
        app.menu();
    }
    
    public void tempUsers() {
        K2513320_User user1 = new K2513320_User(1, "Harry", "h@gmmail.com", "123567890", "Student");
        K2513320_User user2 = new K2513320_User(2, "Snape", "s@gmail.com", "2345678901", "Faculty");
        K2513320_User user3 = new K2513320_User(3, "Lily", "l@gmail.com", "3456789012", "Guest");
        user1.setFineStrategy(new K2513320_StudentFineStrategy());
        user2.setFineStrategy(new K2513320_FacultyFineStrategy());
        user3.setFineStrategy(new K2513320_GuestFineStrategy());
        notificationSubject.addObserver(user1);
        notificationSubject.addObserver(user2);
        notificationSubject.addObserver(user3);
        users.add(user1);
        users.add(user2);
        users.add(user3);
    
        System.out.println("Sending a test notification to all users...");
        notificationSubject.setNotification(4, "This is a system test message.");
        System.out.println("Done. Check users' notifications.");
    }
    
    public void tempBooks() {
        K2513320_Book.K2513320_BookBuilder builder1 = new K2513320_Book.K2513320_BookBuilder(1, "Harry Potter and the Philosopher's Stone",
                "J. K. Rowling") .category("Fantasy") .isbn("123456");
        K2513320_Book.K2513320_BookBuilder builder2 = new K2513320_Book.K2513320_BookBuilder(2, "Percy Jackson and the Sea of Monsters",
                "Rick Riordan") .category("Fantasy") .isbn("234567");
        K2513320_Book.K2513320_BookBuilder builder3 = new K2513320_Book.K2513320_BookBuilder(3, "The Hobbit"
                , "J.R.R. Tolkien") .category("Fantasy") .isbn("345678");
        
        builder1.edition("2nd Edition");
        builder3.edition("3rd Edition");
        String b2Tags = "Action, Monsters, Mythology";
        String b3Tags = "Action";
        for (String tag : b2Tags.split(",")) { builder2.addTag(tag.trim()); }
        for (String tag : b3Tags.split(",")) { builder3.addTag(tag.trim()); }
        String b1Rev = "Amazing, Magical, My favourite!";
        for (String review : b1Rev.split(",")) { builder1.addReview(review.trim()); }

        K2513320_Book book1 = builder1.build();
        K2513320_Book book2 = builder2.build();
        K2513320_Book book3 = builder3.build();
        books.add(book1);
        books.add(book2);
        books.add(book3);
    }

    public void menu() {
        try (Scanner sc = new Scanner(System.in)) {
            int choice;
            
            do {
                showAllNotifications();
                System.out.println("~~~ SMART LIBRARY MANAGEMENT SYSTEM ~~~");
                System.out.println("1. Manage Books");
                System.out.println("2. Manage Users");
                System.out.println("3. Borrow Book");
                System.out.println("4. Return Book");
                System.out.println("5. Reserve Book");
                System.out.println("6. View Reports");
                System.out.println("7. Undo last action");
                System.out.println("8. Cancel Reservation");
                System.out.println("9. View Log");
                System.out.println("10. Decorate Book");
                System.out.println("0. Exit");
                System.out.print("Enter choice: ");
                
                choice = sc.nextInt();
                
                switch (choice) {
                    case 1 -> manageBooks();
                    
                    case 2 -> manageUsers();
                    
                    case 3 -> borrowBook();
                    
                    case 4 -> returnBook();
                    
                    case 5 -> reserveBook();
                    
                    case 6 -> viewReports();
                    
                    case 7 -> invoker.undoLast();
                    
                    case 8 -> cancelReservation();
                    
                    case 9 -> invoker.viewHistory();
                    
                    case 10 -> decorateBook();
                    
                    case 0 -> {System.out.println("Exiting system..."); return;}
                    
                    default -> System.out.println("Invalid choice. Try again.");
                }
                
            } while (choice != 0);
        }
    }

    private void manageBooks() {
        Scanner sc = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n~~~~~ Book Management ~~~~~");
            System.out.println("1. Add Book");
            System.out.println("2. Update Book");
            System.out.println("3. Remove Book");
            System.out.println("4. View All Books");
            System.out.println("5. Go Back");
            System.out.print("Enter choice: ");

            choice = sc.nextInt();
            sc.nextLine(); // clean buffer

            switch (choice) {

                case 1 -> addBook();

                case 2 -> updateBook();

                case 3 -> removeBook();

                case 4 -> viewBooks();

                case 5 -> System.out.print("");

                default -> System.out.println("Invalid choice, try again.");
            }

        } while (choice != 5);
    }
    
    private void addBook() {
        System.out.println("\n~~~~~ Add New Book ~~~~~");
            
        System.out.print("Enter Book ID: ");
        int id = Integer.parseInt(scanner.nextLine());

        System.out.print("Enter Title: ");
        String title = scanner.nextLine();

        System.out.print("Enter Author: ");
        String author = scanner.nextLine();

        System.out.print("Enter Category: ");
        String category = scanner.nextLine();

        System.out.print("Enter ISBN: ");
        String isbn = scanner.nextLine();

        System.out.print("Enter Edition (press enter if none): ");
        String edition = scanner.nextLine();

        System.out.print("Enter Tags (comma separated, press enter if none): ");
        String tagsInput = scanner.nextLine();

        System.out.print("Enter Reviews (comma separated, press enter if none): ");
        String reviewsInput = scanner.nextLine();

        K2513320_Book.K2513320_BookBuilder builder = new K2513320_Book.K2513320_BookBuilder(id, title, author)
                .category(category)
                .isbn(isbn);

        if (!edition.isBlank()) builder.edition(edition);

        if (!tagsInput.isBlank()) {
            for (String tag : tagsInput.split(",")) {
                builder.addTag(tag.trim());
            }
        }

        if (!reviewsInput.isBlank()) {
            for (String review : reviewsInput.split(",")) {
                builder.addReview(review.trim());
            }
        }

        K2513320_Book book = builder.build();
        books.add(book);

        System.out.println("Book " + book.getTitle() + " added successfully.");
    }

    private void updateBook() {
        System.out.println("\n~~~~~ Update Existing Book ~~~~~");
        
        System.out.print("Enter Book ID to update: ");
        int id = Integer.parseInt(scanner.nextLine());

        K2513320_Book book = findBookById(id);
        if (book == null) {
            System.out.println("Book not found.");
            return;
        }

        System.out.println("Updating book: " + book.getTitle());

        System.out.print("Enter new Title (enter to keep current): ");
        String title = scanner.nextLine();
        if (!title.isBlank()) book = new K2513320_Book.K2513320_BookBuilder(book.getBookID(), title, book.getAuthor())
                .category(book.getCategory())
                .isbn(book.getIsbn())
                .edition(book.getTitle())
                .build();

        System.out.print("Enter new Author (enter to keep current): ");
        String author = scanner.nextLine();
        if (!author.isBlank()) book = new K2513320_Book.K2513320_BookBuilder(book.getBookID(), book.getTitle(), author)
                .category(book.getCategory())
                .isbn(book.getIsbn())
                .edition(book.getTitle())
                .build();

        System.out.print("Enter new Category (enter to keep current): ");
        String category = scanner.nextLine();
        if (!category.isBlank()) book = new K2513320_Book.K2513320_BookBuilder(book.getBookID(), book.getTitle(), book.getAuthor())
                .category(category)
                .isbn(book.getIsbn())
                .edition(book.getTitle())
                .build();

        System.out.print("Enter new ISBN (enter to keep current): ");
        String isbn = scanner.nextLine();
        if (!isbn.isBlank()) book = new K2513320_Book.K2513320_BookBuilder(book.getBookID(), book.getTitle(), book.getAuthor())
                .category(book.getCategory())
                .isbn(isbn)
                .edition(book.getTitle())
                .build();

        System.out.println("Book " + book.getTitle() + " updated successfully.");
    }

    private void removeBook() {
        System.out.println("\n~~~~~ Remove Existing Book ~~~~~");
        System.out.print("Enter Book ID to remove: ");
        int id = Integer.parseInt(scanner.nextLine());

        K2513320_Book book = findBookById(id);
        if (book == null) {
            System.out.println("Book not found.");
            return;
        }

        books.remove(book);
        System.out.println("Book"  + book.getTitle() + " removed successfully.");
    }

    private void viewBooks() {
        if (books.isEmpty()) {
            System.out.println("No books available.");
            return;
        }

        System.out.println("\n~~~~~ Book List ~~~~~");

        for (K2513320_Book b : books) {
            if (decoratedBooks != null && decoratedBooks.containsKey(b.getBookID())) {
                K2513320_BookComponent decorated = decoratedBooks.get(b.getBookID());
                System.out.println("\n" + b.getBookID() + ". " + b.getTitle() + " [" + decorated.getDescription() + "]");
            } else {System.out.println("\n" + b.getBookID() + ". " + b.getTitle());}
            
            System.out.println("   by " + b.getAuthor());
            System.out.println("Category: " + b.getCategory());
            System.out.println("ISBN: " + b.getIsbn());
            System.out.println("State: " + b.getStateName());
            
            if (b.getEdition() != null && !b.getEdition().isBlank()) {
                System.out.println("Edition: " + b.getEdition());
            }

            if (b.getTags() != null && !b.getTags().isEmpty()) {
                System.out.println("Tags: " + String.join(", ", b.getTags()));
            }

            if (b.getReviews() != null && !b.getReviews().isEmpty()) {
                System.out.println("Reviews:");
                for (String r : b.getReviews()) {
                    System.out.println("  - " + r);
                }
            }
        }
    }

    private void manageUsers() {
        Scanner sc = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n~~~~~ User Management ~~~~~");
            System.out.println("1. Add User");
            System.out.println("2. Update User");
            System.out.println("3. Remove User");
            System.out.println("4. View All Users");
            System.out.println("5. Go Back");
            System.out.print("Enter choice: ");

            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1 -> addUser();

                case 2 -> updateUser();

                case 3 -> removeUser();

                case 4 -> viewUsers();

                case 5 -> System.out.println("");

                default -> System.out.println("Invalid choice, try again.");
            }

        } while (choice != 5);
    }
    
    private void addUser() {
        System.out.println("\n~~~~~ Add New User ~~~~~");
        System.out.print("Enter user ID: ");
        int id = Integer.parseInt(scanner.nextLine());

        System.out.print("Enter name: ");
        String name = scanner.nextLine();

        System.out.print("Enter email: ");
        String email = scanner.nextLine();

        System.out.print("Enter contact number: ");
        String contact = scanner.nextLine();

        System.out.print("Enter membership type (Student / Faculty / Guest): ");
        String membership = scanner.nextLine();

        K2513320_User user = new K2513320_User(id, name, email, contact, membership);

        switch (membership.toLowerCase()) {
            case "student" -> user.setFineStrategy(new K2513320_StudentFineStrategy());
            case "faculty" -> user.setFineStrategy(new K2513320_FacultyFineStrategy());
            case "guest" -> user.setFineStrategy(new K2513320_GuestFineStrategy());
            default -> System.out.println("Invalid membership type, please update.");
        }

        notificationSubject.addObserver(user);

        users.add(user);

        System.out.println("User added successfully.");
    }

    private void updateUser() {
        System.out.println("\n~~~~~ Update Existing User ~~~~~");
        System.out.print("Enter user ID to update: ");
        int id = Integer.parseInt(scanner.nextLine());

        K2513320_User user = findUserById(id);

        if (user == null) {
            System.out.println("User not found.");
            return;
        }

        System.out.println("Updating user: " + user.getName());

        System.out.print("Enter new name (enter to keep current): ");
        String name = scanner.nextLine();
        if (!name.isBlank()) {
            user.setName(name);
        }

        System.out.print("Enter new email (enter to keep current): ");
        String email = scanner.nextLine();
        if (!email.isBlank()) {
            user.setEmail(email);
        }

        System.out.print("Enter new contact number (enter to keep current): ");
        String contact = scanner.nextLine();
        if (!contact.isBlank()) {
            user.setContactNumber(contact);
        }

        System.out.print("Enter new membership type (Student / Faculty / Guest, enter to keep current): ");
        String membership = scanner.nextLine();
        if (!membership.isBlank()) {
            user.setMembershipType(membership);

            // Reassign fine strategy
            switch (membership.toLowerCase()) {
                case "student" -> user.setFineStrategy(new K2513320_StudentFineStrategy());
                case "faculty" -> user.setFineStrategy(new K2513320_FacultyFineStrategy());
                case "guest" -> user.setFineStrategy(new K2513320_GuestFineStrategy());
                default -> System.out.println("Invalid membership type; please update.");
            }
        }

        System.out.println("User updated successfully.");
    }

    private void removeUser() {
        System.out.println("\n~~~~~ Remove Existing User ~~~~~");
        System.out.print("Enter user ID to remove: ");
        int id = Integer.parseInt(scanner.nextLine());

        K2513320_User user = findUserById(id);

        if (user == null) {
            System.out.println("User not found.");
            return;
        }

        notificationSubject.removeObserver(user);
        users.remove(user);

        System.out.println("User removed successfully.");
    }
    
    private K2513320_User findUserById(int id) {
        for (K2513320_User u : users) {
            if (u.getUserID() == id) {
                return u;
            }
        }
        return null;
    }

    private void viewUsers() {
        if (users.isEmpty()) {
            System.out.println("No users in the system.");
            return;
        }

        System.out.println("\n~~~~~ User List ~~~~~");
        for (K2513320_User u : users) {
            System.out.println("\n" + u.getUserID() + ". " + u.getMembershipType() + " " + u.getName());
            System.out.println(u.getContactNumber());
            System.out.println(u.getEmail());
            
            System.out.println("Borrowed Books:");
            for (K2513320_Borrow b: u.getBorrowedBooks()){
                System.out.println("   ~ " + findBookById(b.getBookID()).getTitle());
            }
        }
    }
    
    private void borrowBook() {
        System.out.println("\n~~~~~ Borrow Book ~~~~~");
        System.out.print("Enter User ID: ");
        int uid = Integer.parseInt(scanner.nextLine());

        System.out.print("Enter Book ID: ");
        int bid = Integer.parseInt(scanner.nextLine());

        K2513320_User user = findUserById(uid);
        K2513320_Book book = findBookById(bid);

        K2513320_Command cmd = new K2513320_BorrowCommand(user, book, this);
        invoker.executeCommand(cmd);
    }

    private void returnBook() {
        System.out.println("\n~~~~~ Return Book ~~~~~");
        System.out.print("Enter User ID: ");
        int uid = Integer.parseInt(scanner.nextLine());

        System.out.print("Enter Book ID: ");
        int bid = Integer.parseInt(scanner.nextLine());

        K2513320_User user = findUserById(uid);
        K2513320_Book book = findBookById(bid);
        
        K2513320_Command cmd = new K2513320_ReturnCommand(user, book, this);
        invoker.executeCommand(cmd);
    }

    private void reserveBook() {
        System.out.println("\n~~~~~ Reserve Book ~~~~~");
        System.out.print("Enter User ID: ");
        int uid = Integer.parseInt(scanner.nextLine());

        System.out.print("Enter Book ID: ");
        int bid = Integer.parseInt(scanner.nextLine());

        K2513320_User user = findUserById(uid);
        K2513320_Book book = findBookById(bid);
        
        K2513320_Command cmd = new K2513320_ReserveCommand(user, book, this);
        invoker.executeCommand(cmd);
    }
    
    private K2513320_Book findBookById(int id) {
        for (K2513320_Book b : books) {
            if (b.getBookID() == id)
                return b;
        }
        return null;
    }

    private void viewReports() {
        Scanner sc = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n===== REPORTS =====");
            System.out.println("1. Most Borrowed Books");
            System.out.println("2. Active Borrowers");
            System.out.println("3. Overdue Books");
            System.out.println("4. Go Back");
            System.out.print("Enter choice: ");

            choice = sc.nextInt();

            switch (choice) {

                case 1 -> reportMostBorrowedBooks();

                case 2 -> reportActiveBorrowers();

                case 3 -> reportOverdueBooks();

                case 4 -> System.out.println("Returning to main menu...");

                default -> System.out.println("Invalid choice, try again.");
            }

        } while (choice != 4);
    }
    
    private void reportMostBorrowedBooks() {
        if (books.isEmpty()) {
            System.out.println("No books in the system.");
            return;
        }

        List<K2513320_Book> sorted = new ArrayList<>(books);
        sorted.sort((b1, b2) -> 
            Integer.compare(b2.getBorrowHistory().size(), b1.getBorrowHistory().size())
        );

        System.out.println("\n--- Top 3 Most Borrowed Books ---");

        for (int i = 0; i < Math.min(3, sorted.size()); i++) {
            K2513320_Book b = sorted.get(i);
            System.out.println((i + 1) + ". " + b.getTitle() + " - Borrowed " + b.getBorrowHistory().size() + " times");
        }
    }

    private void reportActiveBorrowers() {
        System.out.println("\n~~~~~ Active Borrowers ~~~~~");

        for (K2513320_User u : users) {
            boolean hasActive = u.getBorrowedBooks()
                    .stream()
                    .anyMatch(b -> b.getReturnedDate() == null);

            if (hasActive) {
                System.out.println("   ~ " + u.getMembershipType() + u.getName());
            }
        }
    }

    private void reportOverdueBooks() {
        System.out.println("\n~~~~~ Overdue Books ~~~~~");

        Date today = new Date();
        boolean foundAny = false;

        for (K2513320_Borrow b : borrows) {
            if (b.getReturnedDate() == null && b.getDueDate().before(today)) {

                K2513320_Book bookObj = findBookById(b.getBookID());

                System.out.println("   ~ " + bookObj.getTitle() + " - due " + b.getDueDate());

                foundAny = true;
            }
        }

        if (!foundAny) {
            System.out.println("No overdue books.");
        }
    }
    
    private void cancelReservation() {
        System.out.println("\n~~~~~ Cancel Reservation ~~~~~");
        System.out.print("Enter User ID: ");
        int uid = Integer.parseInt(scanner.nextLine());

        System.out.print("Enter Book ID: ");
        int bid = Integer.parseInt(scanner.nextLine());

        K2513320_User user = findUserById(uid);
        K2513320_Book book = findBookById(bid);
        
        K2513320_Reservation activeReservation = findActiveReservation(book, user);
        if (activeReservation != null && !activeReservation.isFulfilled() && !activeReservation.isCancelled()) {
            activeReservation.markCancelled();
            if (notificationSubject != null) {
                String msg = "Reservation for book " + book.getTitle() + " by " + user.getName() + " was cancelled.";
                notificationSubject.setNotification(2, msg);
            }
            System.out.println("Reservation cancelled.");
        } else {
            System.out.println("Cannot cancel reservation.");
        }
    }
    
    private void showAllNotifications() {
        System.out.println("\n~~~~~ Notifications ~~~~~");

        boolean hasNotifications = false;

        for (K2513320_User u : users) {
            List<String> userNotes = u.getNotifications();

            if (!userNotes.isEmpty()) {
                hasNotifications = true;

                for (String msg : userNotes) {
                    System.out.println(" - " + msg);
                }

                userNotes.clear();
            }
            if (hasNotifications){break;}
        }

        if (!hasNotifications) {
            System.out.println("No new notifications.");
        }

        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~\n");
    }

    public K2513320_NotificationSubject getNotificationSubject() {
        return notificationSubject;
    }

    public List<K2513320_Borrow> getBorrows() {
        return borrows;
    }

    public List<K2513320_Reservation> getReservations() {
        return reservations;
    }
    
    public String generateBorrowID() {
        return "BOR-" + (borrowCounter++);
    }

    public String generateReservationID() {
        return "RES-" + (reservationCounter++);
    }
    
    public K2513320_Reservation findActiveReservation(K2513320_Book book, K2513320_User user) {
        for (K2513320_Reservation r : reservations) {
            if (r.getBookID() == book.getBookID() && r.getUserID() == user.getUserID() && !r.isFulfilled() && !r.isCancelled()) {
                return r;
            }
        }
        return null;
    }
   
    private void decorateBook() {
        if (books.isEmpty()) {
            System.out.println("No books available to decorate.");
            return;
        }

        System.out.print("Enter the ID of the book to decorate: ");
        int choice = Integer.parseInt(scanner.nextLine());

        K2513320_Book selectedBook = findBookById(choice);
        K2513320_BookComponent decoratedBook = selectedBook;

        while (true) {
            System.out.println("\nSelect a decoration to apply:");
            System.out.println("1. Featured");
            System.out.println("2. Recommended");
            System.out.println("3. Special Edition");
            System.out.println("4. Done");
            System.out.print("Choice: ");

            String option = scanner.nextLine();
            switch (option) {
                case "1" -> {
                    decoratedBook = new K2513320_FeaturedDecorator(decoratedBook);
                    System.out.println("Applied 'Featured'.");
                }
                case "2" -> {
                    decoratedBook = new K2513320_RecommendedDecorator(decoratedBook);
                    System.out.println("Applied 'Recommended'.");
                }
                case "3" -> {
                    decoratedBook = new K2513320_SpecialEditionDecorator(decoratedBook);
                    System.out.println("Applied 'Special Edition'.");
                }
                case "4" -> {
                    decoratedBooks.put(selectedBook.getBookID(), decoratedBook);
                    System.out.println("Decoration finished.");
                    System.out.println("Final Book Description: " + decoratedBook.getDescription());
                    return;
                }
                default -> System.out.println("Invalid option. Try again.");
            }
        }
    }
}

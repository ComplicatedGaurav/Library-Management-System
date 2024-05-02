import java.sql.*;
import java.util.Collection;
import java.util.Scanner;

class LibraryMS{
    static  String url="jdbc:mysql://localhost:3306/library_ms";
    static String username="root";
    static String password="Gaurav@1211";
    public static void main(String[] args) throws ClassNotFoundException,SQLException, InterruptedException{
        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("connnected");

        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());

        }
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            System.out.println("Connection Establish Succesfully");
            while (true) {
                System.out.println();
                System.out.println("WELCOME TO LIBRARY MANAGEMENT SYSYTEM");
                System.out.println();
                Scanner scanner = new Scanner(System.in);
                System.out.println("OPERATIONS!");
                System.out.println("1.ADD BOOK");
                System.out.println("2.REMOVE BOOK");
                System.out.println("3.VIEW BOOK");
                System.out.println("4.GET BOOK");
                System.out.println("5.UPDATE DETAILS");
                System.out.println("6.EXIT");

                int choice = scanner.nextInt();
                System.out.println("SELECTED OPERATION : "+choice );

                switch (choice) {
                    case 1:
                        addBook(connection, scanner);
                    case 2:
                        removeBook(connection, scanner);
                    case 3:
                        viewBook(connection);
                    case 4:
                        getBook(connection, scanner);
                    case 5:
                        updateBook(connection, scanner);
                    case 6:
                        exit();
                        scanner.close();
                        return;
                    default:
                        System.out.println("ENTER VALID CHOICE");
                }

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());


        }
    }
    private static void addBook(Connection connection,Scanner scanner) throws SQLException{
        try {
            System.out.println("Enter Book Name:");
            String bookName = scanner.next();
            System.out.println("Enter Book ID:");
            int bookId = scanner.nextInt();
            System.out.println("Enter Author Name:");
            String bookAuthor = scanner.next();
            String sql="INSERT INTO management ( book_name, book_id,book_author)" + " VALUES( '" +bookName +"' , " +bookId+" ,'"+bookAuthor +"')";
            try(Statement statement=connection.createStatement()){
                int affectedRows=statement.executeUpdate(sql);
                if(affectedRows >0){
                    System.out.println("Book Added Successfully Successfull !");

                }else{
                    System.out.println("Book Added Successfully Failed !");
                }

            }
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }


    }
    static void removeBook(Connection connection,Scanner scanner){
        try{
            System.out.println("ENTER BOOK ID:");
            int bookId=scanner.nextInt();


            String sql="DELETE FROM management WHERE book_id =  "+ bookId;
            try(Statement statement=connection.createStatement()){
              int affectedRow=statement.executeUpdate(sql);
              if(affectedRow > 0){
                  System.out.println("Book Delete Succesfully !");
              }else {

                      System.out.println("BOOK NOT DELETE");


              }


                }




        }catch (SQLException e){
            System.out.println(e.getMessage());

        }

    }
    private  static void viewBook(Connection connection){
        try {
            String sql="SELECT book_name,book_id,book_author FROM management";
            try(Statement statement=connection.createStatement()){
                ResultSet resultSet=statement.executeQuery(sql);
                while (resultSet.next()){
                    String bookName=resultSet.getString("book_name");
                    int bookId=resultSet.getInt("book_id");
                    String bookAuthor=resultSet.getString("book_author");
                    System.out.println(bookName+"," + bookId +"," +bookAuthor+ " ");
                }
            }



        }catch (SQLException e){
            System.out.println(e.getMessage());

        }
    }
    private static void getBook(Connection connection,Scanner scanner){
        try {
            System.out.println("ENTER BOOK ID :" );
            int bookId=scanner.nextInt();
            String sql="SELECT book_name , book_author FROM management WHERE book_id ="+ bookId;
            try(Statement statement=connection.createStatement()) {
                ResultSet resultSet=statement.executeQuery(sql);
                if(resultSet.next()){
                    String bookName=resultSet.getString("book_name");
                    String bookAuthor=resultSet.getString("book_author");
                    System.out.println("BOOK NAME :" +bookName);
                    System.out.println("BOOK AUTHOR: "+bookAuthor);
                }

            }

        }catch (SQLException e){
            System.out.println();
        }
    }
    private static void updateBook(Connection connection,Scanner scanner){

        try{
            System.out.println("ENTER BOOK ID :");
            int bookId=scanner.nextInt();
            System.out.println("ENTER BOOK NAME :");
            String bookName=scanner.next();

            System.out.println("ENTER BOOK AUTHOR NAME :");
            String bookAuthor=scanner.next();

            String sql="UPDATE management SET book_name= '"+ bookName + "'," +"book_author ='"+ bookAuthor+"' " +"WHERE book_id =" +bookId;
            try(Statement statement=connection.createStatement()) {
                int affectedRows =statement.executeUpdate(sql);
                if(affectedRows > 0){
                    System.out.println("UPDATE BOOK DETAILS SUCCESFULLY!");
                }else {
                    System.out.println("Failed");
                }

            }



        }catch (SQLException e){
            System.out.println(e.getMessage());

        }
    }
    public static void exit() throws InterruptedException {
        System.out.print("Exiting System");
        int i = 5;
        while(i!=0){
            System.out.print(".");
            Thread.sleep(1000);
            i--;
        }
        System.out.println();
        System.out.println("ThankYou For Using Library Management System!!!");
    }
    private static boolean bookExists(Connection connection, int bookId) {
        try {
            String sql = "SELECT book_id FROM management WHERE book_id = " + bookId;

            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(sql)) {

                return resultSet.next(); // If there's a result, the reservation exists
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Handle database errors as needed
        }
    }
    }


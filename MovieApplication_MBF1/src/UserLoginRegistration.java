import models.Database;
import models.Movie;
import models.User;

import java.io.*;
import java.util.Scanner;

public class UserLoginRegistration {

    private Database database;
    private DatabaseRepository databaseRepository;

    public UserLoginRegistration(Database database) {
        this.database = database;
        this.databaseRepository = new DatabaseRepository();
    }

    public User registerNewUser(Scanner inputScanner) throws IOException {
        System.out.print("Please input your name: --> ");
        String name = inputScanner.nextLine();
        name = inputScanner.nextLine();
        System.out.println();
        System.out.print("Please input your email: --> ");
        String email = inputScanner.next();
        System.out.print("Please input your password: --> ");
        String password = inputScanner.next();
        System.out.println();
        User user = new User(name, email, password);
        if (!checkIfUniqueUser(name)) {
            System.out.println(" --- SORRY, THIS EMAIL IS ALREADY TAKEN! --- ");
        } else {
            // this is a new user, we should save it before losing it.
            this.database.getUsers().add(user);
            this.databaseRepository.saveDatabase(database);
        }
        return user;
    }

    public boolean checkIfUniqueUser(String name) {
        if (database.getUsers().size() == 0) {
            return true;
        }
        for (User user : database.getUsers()) {
            if (user.getName().equals(name)) {
                return false;
            }
        }
        return true;
    }


    public User findUserByName(Database database, String userName) {
        for (User user : database.getUsers()) {
            if (user.getName().equalsIgnoreCase(userName)) {
                return user;
            }
        }

        return null;
    }


    public User login(Scanner inputScanner, User activeUser) {
        System.out.print("Please input your email: --> ");
        String name = inputScanner.next();
        System.out.print("Please input your password: --> ");
         String password = inputScanner.next();
        System.out.println();
        boolean foundUser = false;
        for (User user : database.getUsers()) {
            if (user.getName().equalsIgnoreCase(name)) {
                activeUser = user;
                foundUser = true;
            }
        }
        if (foundUser) {
            System.out.println(" --- GLAD TO SEE YOU BACK, " + activeUser.getName() + " ---\n");
        } else {
            System.out.println(" --- NAME DOES NOT MATCH, TRY AGAIN ---");
            login(inputScanner, activeUser);
        }
        return activeUser;
    }
}

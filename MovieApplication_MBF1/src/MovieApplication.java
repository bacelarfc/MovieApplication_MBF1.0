import models.Database;
import models.Movie;
import models.MovieRecord;
import models.User;

import java.io.*;
import java.util.List;
import java.util.Scanner;

//connects all classes together

public class MovieApplication implements Serializable {


    // instance variables
    private Scanner inputScanner;
    private Database databaseDb;
    private User activeUser;
    private UserLoginRegistration userLoginRegistration;
    private MovieSearch movieSearch;
    private DatabaseRepository databaseRepository;


    //instance of the variables
    //created inside the constructor so we dont need to create a new object
    //make it possible to use the objects in different classes
    public MovieApplication() throws IOException, ClassNotFoundException {
        this.inputScanner = new Scanner(System.in);
        this.activeUser = new User();
        this.databaseRepository = new DatabaseRepository();
        this.databaseDb = databaseRepository.getMovieDatabase();
        //the user object needs to access our database, but we dont want to create a database multiple times
        this.userLoginRegistration = new UserLoginRegistration(databaseDb);
    }

    // This method is intended to initialize the movieSearch objects AFTER the user
    // enters his/hers credentials and logs into the system.

    private void initializeUserRelatedObjects() {
        this.movieSearch = new MovieSearch(databaseDb, inputScanner, activeUser);
    }


    public void displayMenu() {
        System.out.println("--- MOVIE APP MENU ---");
        System.out.println("Press 0 to list all movies in the application");
        System.out.println("Press 1 to search for a movie");
        System.out.println("Press 2 to see the account statistics");
        System.out.println("Press 3 to save a movie as favorite");
        System.out.println("Press 4 to list all favorite movies");
        System.out.println("Press 5 to remove a movie from your favourite list");
        System.out.println("Press 6 to quit");
        System.out.println("---------------------");
    }

    public void askForCredentials() throws IOException {
        System.out.println("Are you a registered user or not (Y/N) ?");
        String userChoice = inputScanner.next();
        while (!userChoice.equalsIgnoreCase("Y") && !userChoice.equalsIgnoreCase("N")) {
            System.out.println("--- ERROR: Invalid input, please try again!");
            System.out.println("Are you a registered user or not (Y/N) ?");
            userChoice = inputScanner.next();
        }
        if (userChoice.equalsIgnoreCase("y")) {
            this.activeUser = this.userLoginRegistration.login(inputScanner, activeUser);
        } else if (userChoice.equalsIgnoreCase("n")) {
            this.activeUser = this.userLoginRegistration.registerNewUser(inputScanner);
        }
        initializeUserRelatedObjects();
    }


    public void run() {
        System.out.println("----- Welcome to the MOVIE APPLICATION -----");
        try {
            askForCredentials();
            mainMenu();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

        public void mainMenu() throws IOException {
            int userMenuChoice = -1;

            while (userMenuChoice != 6) {
                displayMenu();
                userMenuChoice = inputScanner.nextInt();

                if (userMenuChoice == 0) {
                    displayAllMovies(databaseDb.getMovies());
                }
                if (userMenuChoice == 1) {
                    this.movieSearch.searchForMovie();
                }
                if (userMenuChoice == 2) {
                    this.displayAccountStatistics(activeUser);
                }
                if (userMenuChoice == 3) {
                    this.movieSearch.searchForMovie();
                }
                if (userMenuChoice == 4) {
                   listAllFavoriteMovies();
                }
                if (userMenuChoice == 5) {
                    removeMovieFromFavourites();
                }
                if (userMenuChoice == 6) {
                    System.out.println("--- SAD TO SEE YOU GO ---");
                    break;
                }
                databaseRepository.saveDatabase(databaseDb);
            }
        }

    public static void displayAllMovies(List<Movie> movies) {
        for (Movie movie : movies) {
            System.out.println(movie.toString());
        }
    }

    public void listAllFavoriteMovies() {
        System.out.println("----- LISTING FAVOURITE MOVIES FOR USER '" + activeUser.getName() + "' -----");
        User user = this.userLoginRegistration.findUserByName(databaseDb, this.activeUser.getName());
        displayAllMovies(user.getFavMovieList());

        System.out.println("--------------------");
    }
    public void removeMovieFromFavourites() {
        System.out.println("Input a title of the movie that you would like removed from your fav list: ");
        String movieName = inputScanner.nextLine();
        movieName = inputScanner.nextLine();
        System.out.println(" --- SEARCHING FOR '" + movieName + "' IN YOUR FAVOURITE LIST..... --- ");
        for (Movie movie : activeUser.getFavMovieList()) {
            if (movie.toString().contains(movieName)) {
                activeUser.getFavMovieList().remove(movie);
                System.out.println(" --- FOUND A MATCH... REMOVING A MOVIE FROM THE LIST..... --- ");
                return;
            }
        }
        System.out.println(" --- THE SEARCH DIDN'T RETURN ANY RESULT, TRY AGAIN LATER --- ");

    }
    public void displayAccountStatistics(User activeUser) {
        System.out.println("----- ACCOUNT STATISTICS FOR USER '" + activeUser.getName() + "' -----");
        for (MovieRecord movieRecord : activeUser.getMovieRecords()) {
            System.out.println("Movie '" + movieRecord.getMovie().getTitle() + "' was played on " +
                    movieRecord.getDatePlayed());
        }
        System.out.println("--------------------");
    }

}




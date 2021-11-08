
import models.*;

import java.sql.Date;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Scanner;

public class MovieSearch {

    private Database databaseDb;
    private Scanner inputScanner;
    private User activeUser;
    private UserLoginRegistration userLoginRegistration;


    public MovieSearch(Database database, Scanner inputScanner, User activeUser) {
        this.databaseDb = database;
        this.inputScanner = inputScanner;
        this.activeUser = activeUser;
        this.userLoginRegistration = new UserLoginRegistration(database);
    }

    public void searchForMovie() {
        System.out.println("Pick one criteria to search by");
        System.out.println("Press 1 for searching by title");
        System.out.println("Press 2 for searching by production year");
        System.out.println("Press 3 for searching by an actor");
        int userChoice = inputScanner.nextInt();
        switch (userChoice) {
            case 1:
                searchByTitle(databaseDb);
                break;
            case 2:
                searchByProductionYear();
                break;
            case 3:
                searchByActor();
                break;
            default:
                System.out.println(" *** INVALID INPUT, RETURNING TO MAIN MENU ***");
                break;
        }
    }

    private Movie searchForMovieByName(String name) {
        for (Movie movie : databaseDb.getMovies()) {
            if (movie.getTitle().contains(name)) {
                return movie;
            }
        }
        return null;
    }

    // Passing an argument of list of movies that was returned as a result of some search (either by keyword, or by title, or by prod year)
    private void processMovieResult(ArrayList<Movie> movieArrayList) {
        // The search result returned line is going to count how many movies were found, and it returns that numbers below
        System.out.println(" --- SEARCH RETURNED " + movieArrayList.size() + " RESULT: --- ");
        for (Movie movie : movieArrayList) {
            System.out.println(movie.toString());
            System.out.println("---------------------");
        }



        if (movieArrayList.size() > 0) {
            System.out.println("Do you want to add the movie to your favourite list? >> (y/n)");

            boolean addingToFav = false;

            String userReply = inputScanner.next().substring(0, 1);
            if (userReply.equalsIgnoreCase("y")) {
                System.out.println("..... JUST TO CONFIRM, PLEASE SPECIFY THE TITLE OF THE MOVIE .....");
                Movie movieChoice;
                String title = getUserFullLineInput();
                movieChoice = searchForMovieByName(title);
                addMovieToFavourite(databaseDb, this.activeUser, movieChoice);
                System.out.println("... MOVIE '" + movieChoice.getTitle() + "' ADDED TO YOUR FAVOURITES ...");
                addingToFav = true;
            }

                int userChoice = inputScanner.nextInt();

                if (userChoice == 2) {
                    return;
                }

                System.out.println("Press 1 to play a movie");
                System.out.println("Press 2 to return to main menu");

                Movie movieChoice;

                System.out.println("Input the title of the movie >> ");
                String title = getUserFullLineInput();
                movieChoice = searchForMovieByName(title);

                if (movieChoice == null) {
                    System.out.println(" *** SEARCH DIDN'T RETURN ANY RESULT, RETURNING TO MAIN MENU *** ");
                    return;
                }

                if (userChoice == 1) {
                    System.out.println("..... PLAYING A MOVIE '" + movieChoice.getTitle() + "' .....");
                    //adding date to play movie
                    addMovieRecord(movieChoice);
                    ArrayList<Actor> actorArrayList = movieChoice.getActors();
                    System.out.println("................... ACTORS .......................");
                    for (Actor actor : actorArrayList) {
                        System.out.println(actor.toString());
                    }
                    System.out.println("..................................................");
                    return;
                }
            }
        }


    private void addMovieRecord(Movie movie){
        MovieRecord movieRecord = new MovieRecord(movie, Date.from(Instant.now()));
        this.activeUser.getMovieRecords().add(movieRecord);
    }

    public Database addMovieToFavourite(Database databaseDb, User activeUser, Movie movie) {
        User user = userLoginRegistration.findUserByName(databaseDb, activeUser.getName());

        if (user != null) {
            user.getFavMovieList().add(movie);
        }

        return databaseDb;
    }

    private String getUserFullLineInput() {
        String userInput = inputScanner.nextLine();
        userInput = inputScanner.nextLine();
        return userInput;
    }

    private void searchByTitle(Database databaseDb) {
        System.out.println("Input a title of the movie: >> ");
        String title = getUserFullLineInput();
        for (Movie movie : databaseDb.getMovies()) {
            if (movie.getTitle().contains(title)) {
                ArrayList<Movie> resultList = new ArrayList<>();
                resultList.add(movie);
                processMovieResult(resultList);
                return;
            }
        }
        System.out.println(" *** SEARCH DIDN'T RETURN ANY RESULT, RETURNING TO MAIN MENU *** ");
    }


    private void searchByProductionYear() {
        System.out.println("Input a production year of the movie: >> ");
        int prodYear = inputScanner.nextInt();
        ArrayList<Movie> resultList = new ArrayList<>();

        for (Movie movie : databaseDb.getMovies()) {
            if (movie.getProdYear() == (prodYear)) {
                resultList.add(movie);
            }
        }
        processMovieResult(resultList);

    }

    private void searchByActor() {
        System.out.println("Input an actor name to search a movie by: >> ");
        String actorName = getUserFullLineInput();
        ArrayList<Movie> resultList = new ArrayList<>();
        for (Movie movie : databaseDb.getMovies()) {
            ArrayList<Actor> actorArrayList = movie.getActors();
            for (Actor actor : actorArrayList) {
                if (actor.getFullName().equals(actorName)) {
                    resultList.add(movie);
                }
            }
        }
        processMovieResult(resultList);

    }
}


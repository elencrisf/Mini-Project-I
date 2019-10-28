package HangMan;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class HangMan {

    private String[] cts;
    private String ct = "";
    private int chance = 10, wrong = 0;

    ArrayList<String> wrongLetters = new ArrayList<String>();
    ArrayList<String> correctLetters = new ArrayList<String>();

    public static void main(String[] args) {
        HangMan m = new HangMan();
        m.game();

    }

    public HangMan() {
        ListCities();
        RandomCity();
    }

    public void ListCities() {

        // 1. Code read the city list and display the whole list
        try {
            File f = new File("src/HangMan/cities.txt");
            Scanner in = new Scanner(f);
            String c = " ";
            while (in.hasNextLine()) {
                c += in.nextLine() + ".";
            }
            c = c.substring(0, c.length() - 1);
            cts = c.split("\\.");
            System.out.println("The file contains this cities names:\n" + c);

        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
        }
    }

    public void RandomCity() {

        //2. code to randomly pick one city and display that title only
        Random r = new Random();
        int i = (int) r.nextInt(this.cts.length-1);
        this.ct = cts[i];
        //System.out.println(ct);
    }

    private String checkLetters() {

        // 3. convert its letters to underscores (_) and display
        String string = "";

        for (int i = 0; i < ct.length(); i++) {
            String currentChar = Character.toString(ct.toLowerCase().charAt(i));
            if (correctLetters.contains(currentChar)) {
                string += currentChar + " ";
            }else {
                string += "_ ";
            }
        }

        return string;
    }

    private boolean WordFind() {

        // 4. compare the user's input and search for it in the city and check if its correct
        boolean find = true;
        for (int i = 0; i < ct.length(); i++) {
            String currentChar = Character.toString(ct.toLowerCase().charAt(i));
            if (!correctLetters.contains(currentChar)) {
                find = false;
            }
        }

        return find;
    }

    private String checkWrongLetters() {

        //  //checks wrong letters if user already guessed the letter previously
        String string = "";
        for (int i = 0; i < wrongLetters.size(); i++) {
            string += wrongLetters.get(i) + ", ";
        }
        if (string.length() > 2)
            string = string.substring(0, string.length() - 2);

        return string;
    }

    //city[i].substring(0,1).toUpperCase() + city[i].substring(1,city[i].length()).toLowerCase());
    public void validate(String l) {

        // add correct letters or count how many wrong guesses
        if (!wrongLetters.contains(l) && !correctLetters.contains(l)) {
            if (ct.toLowerCase().contains(l.toLowerCase())) {
                correctLetters.add(l);
            } else {
                wrongLetters.add(l);
                wrong += 1;
            }
        }
    }


    public boolean GameOver() {
        // if 10 wrong guesses and end the game if they lose
        // if they have guessed all the letters and let them know they've won
        boolean Over = false;
        if (wrong >= chance) {
            Over = true;
            System.out.println("You lose!\nThe correct word was '"+ ct +"'!");
        }else if (WordFind()) {
            Over = true;
            System.out.println("You win!\nYou have guessed '"+ ct +"' correctly.");
        }

        return Over;
    }

    public void game() {

        // reading the user's input and search for it in the city
        System.out.println("Here's the question.");

        System.out.println(this.checkLetters());
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("Guess a letter: ");
            String val = scanner.nextLine();

            if (val.length() > 1) {
                System.out.println("You should type only a letter per time. Try again!");
                continue;
            }

            validate(val);

            if (GameOver()) {
                break;
            }

            System.out.println("You are guessing: " + checkLetters());
            System.out.println("You have guessed (" + wrong + "), wrong letters: " + checkWrongLetters());
        }
    }
}

// Reference: Done with Douglas's help friend.

//Write some code that will simply read the city list and display the whole list.
//Then add to your code to randomly pick one city and display that title only.
//Then convert its letters to underscores (_) and display that instead, and so on.
//Once you've got that part done start reading the user's input and search for it in the city.
//Work on revealing the correct letters and displaying them.
//Add the logic to keep track of wrong letters so they don't lose points for guessing the same letter twice.
//After that, you can keep track of how many wrong guesses and end the game if they lose.
//Finally, detect when they have guessed all the letters and let them know they've won!
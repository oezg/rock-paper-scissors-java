package rockpaperscissors;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;
import java.util.stream.IntStream;

enum Outcome {
    LOSS,
    DRAW,
    WIN
}

public class Main {
    final static Scanner scanner = new Scanner(System.in);

    static String[] choices = {"rock", "paper", "scissors"};

    public static void main(String[] args) {
        String name = getName();
        int rating = getRating(name);
        updateChoices();
        while (true) {
            String input = scanner.nextLine();
            if (input.equals("!exit")) {
                break;
            }
            if (input.equals("!rating")) {
                System.out.printf("Your rating: %d\n", rating);
                continue;
            }
            if (!isValid(input)) {
                System.out.println("Invalid input");
                continue;
            }
            int randomChoice = new Random().nextInt(choices.length);
            Outcome outcome = determineOutcome(input, randomChoice);
            rating += outcome.ordinal();
            showResult(outcome, randomChoice);
        }
        writeRating(name, rating);
        System.out.println("Bye!");
    }

    static String getName() {
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();
        System.out.printf("Hello, %s\n", name);
        return name;
    }

    static int getRating(String name) {
        File file = new File("rating.txt");
        try (Scanner reader = new Scanner(file)) {
            while (reader.hasNext()) {
                String[] data = reader.nextLine().trim().split("=");
                if (name.equals(data[0])) {
                    return Integer.parseInt(data[1]);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }

    static void writeRating(String name, int rating) {
        File file = new File("rating.txt");
        List<String> data = new ArrayList<>();

        // Read the file line by line into a list of lines
        try (Scanner reader = new Scanner(file)) {
            while (reader.hasNext()) {
                data.add(reader.nextLine());
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }

        // Find the index of the current player in the list
        int index = -1;
        for (int i = 0; i < data.size(); i++) {
            String[] fields = data.get(i).split("=");
            if (name.equals(fields[0])) {
                index = i;
            }
        }

        // Modify the current player in the list or append the new player
        if (index < 0) {
            data.add("%s=%d".formatted(name, rating));
        } else {
            data.set(index, "%s=%d".formatted(name, rating));
        }

        // Write the modified list of lines into the same file
        try (PrintWriter printWriter = new PrintWriter(file)) {
            for (String line : data) {
                printWriter.println(line);
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    static void updateChoices() {
        System.out.println("Enter options separated by commas like rock,paper,scissors,lizard,spock");
        String line = scanner.nextLine();
        if (line.isBlank()) {
            System.out.println("You chose to play with the default options rock, paper and scissors");
            return;
        }
        choices = line.split(",");
        for (int i = 0; i < choices.length; i++) {
            choices[i] = choices[i].trim();
        }
        System.out.println("Okay, let's start");
    }

    static boolean isValid(String input) {
        return Arrays.asList(choices).contains(input);
    }

    // The implementation of the algorithm
    static Outcome determineOutcome(String user, int computer) {
        if (user.equals(choices[computer])) {
            return Outcome.DRAW;
        }
        int idxUser = IntStream.range(0, choices.length)
                .filter(i -> choices[i].equals(user))
                .findFirst()
                .orElse(-1);
        if (IntStream.range(0, choices.length / 2)
                .map(i -> (idxUser + 1+ i) % choices.length)
                .anyMatch(i -> i == computer)) {
            return Outcome.LOSS;
        }
        return Outcome.WIN;
    }

    static void showResult(Outcome outcome, int computer) {
        String[] results = {
                "Sorry, but the computer chose %s",
                "There is a draw (%s)",
                "Well done. The computer chose %s and failed"
        };
        System.out.printf((results[outcome.ordinal()]) + "%n", choices[computer]);
    }
}

package vgu.trac.ttt.basic;

public class App {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Please, input a valid option [1-2]");
            return;
        }
        try {
            int firstPlayer = Integer.parseInt(args[0]);

            if (firstPlayer != 1 && firstPlayer != 2) {
                System.out.println("Please, input a valid option [1-2]");
                return;
            }
            else {
                Game newGame = new Game(firstPlayer);
                newGame.start();
            }
        } catch (NumberFormatException e) {
            System.out.println("Input a number, not a string");
        }
    }
}

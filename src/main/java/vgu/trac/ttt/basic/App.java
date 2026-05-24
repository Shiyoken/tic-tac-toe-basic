package vgu.trac.ttt.basic;

public class App {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Please, input a valid option [1-2]");
            return;
        }
        try {
            // int firstPlayer = Integer.parseInt(args[0]);
            String firstPlayer = args[0];
            if (!firstPlayer.equals("1") && !firstPlayer.equals("2")) {
                System.out.println("Please, input a valid option [1-2]");
                return;
            }
            else {
                int first = Integer.parseInt(firstPlayer); // Who plays first? 1 or 2
                Game newGame = new Game(first);
                newGame.start();
            }
        } catch (NumberFormatException e) {
            System.out.println("Please, input a valid option [1-2]");
        }
    }
}

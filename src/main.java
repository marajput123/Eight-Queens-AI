
public class main {

    public static void main(String[] args) throws InterruptedException {
        //object to call methods
        Board instance = new Board();

        //variables to record changes and restarts
        int stateChange = 0;
        int restartChange = 0;

        //a loop that cause the board to keep restart if no solution is found
        while (!instance.getSolution()) {

            //statement that executes if restart field is true
            if (instance.getRestart()) {
                instance.clearBoard();
                instance.randomize();
                instance.setRestart(false);
            }
            instance.rowCheck();
            instance.diagonolCheck();
            System.out.println("Current h: " + instance.getHeuristic());
            System.out.println("Current State:");
            instance.board();
            instance.runAllQueenSearch();
            instance.neighborStates();
            System.out.println("Neighbors found with lower h: " + instance.getNeighbors());
            System.out.println("Setting new current state");

            //statement that executes if solution field is true
            if (instance.getSolution()) {
                System.out.println("Solution Found");
                System.out.println("State Changes: " + stateChange);
                System.out.println("Restarts: " + restartChange);
            }

            //if solution field is false then the board restarts
            else if (instance.getRestart()) {
                System.out.println("RESTART");
                instance.setRestart(true);
                //Every restart is recorded to display later
                restartChange++;
            }
            instance.moveQueen();
            System.out.println("");
            //Every state change is recorded to display later
            stateChange++;
            Thread.sleep(1000);
        }
    }


}

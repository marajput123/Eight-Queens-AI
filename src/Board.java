import java.util.Random;

public class Board {

    private int heuristic = 0;
    private int neighbors = 0;

    private boolean solution = false;
    private boolean restart = true;

    public int[][] board = new int[8][8];
    public int[] position = new int[8];
    public int[][] heuristicAndPosition = new int[2][8];
    public int[] queenToMove = new int[3];

    public int getHeuristic() {
        return heuristic;
    }

    /**
     * method that gets the value of neighbors with lower heuristic value
     *
     * @return neighbors field value
     */
    public int getNeighbors() {
        return neighbors;
    }

    /**
     * method that gets the boolean value of the solution
     *
     * @return solution field value
     */
    public boolean getSolution() {
        return solution;
    }

    /**
     * method that gets the boolean value of the restart field
     *
     * @return restart field value
     */
    public boolean getRestart() {
        return restart;
    }

    /**
     * method that sets the restart value to be true or false
     *
     * @param x
     */
    public void setRestart(boolean x) {
        this.restart = x;
    }

    /**
     * method that prints the dimensions of the board with 0s
     */
    public void board() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                System.out.print(board[i][j]);
            }
            System.out.print("\n");
        }
    }

    /**
     * method that randomizes the queens on the board
     */
    public void randomize() {
        Random random = new Random();
        for (int i = 0; i < board.length; i++) {
            int rand = random.nextInt(8);
            board[rand][i] = 1;
            this.position[i] = rand;
        }
    }

    /**
     * method that counts the conflicts in each row
     */
    public void rowCheck() {
        for (int i = 0; i < 8; i++) {
            for (int j = i + 1; j < 8; j++) {
                if (position[i] == position[j]) {
                    this.heuristic += 1;
                }
            }
        }
    }

    /**
     * method that counts the diagonal conflicts
     */
    public void diagonolCheck() {
        for (int i = 0; i < 8; i++) {
            for (int j = i + 1; j < 8; j++) {
                if (Math.abs(calculateSlope(position[i], i, position[j], j)) == 1.0) {
                    this.heuristic += 1;
                }
                else {
                    this.heuristic += 0;
                }
            }
        }
    }

    /**
     * method that takes in values to calculate the slope and helps determine what's diagonal
     * @param xOne
     * @param yOne
     * @param xTwo
     * @param yTwo
     * @return
     */
    public double calculateSlope(int xOne, int yOne, int xTwo, int yTwo) {
        if (xTwo == xOne) {
            return 0.0;
        }
        else {
            double upper = (yTwo - yOne);
            double lower = (xTwo - xOne);
            return (upper / lower);
        }
    }

    /**
     * method that calculates the current queen's heuristic
     * @param row
     * @param column
     * @return
     */
    public int queenHeuristic(int row, int column) {
        int currentH = 0;
        for (int i = 0; i < 8; i++) {
            if (column != i) {
                if (row == position[i]) {
                    currentH += 1;
                }
                else if (Math.abs(calculateSlope(column, row, i, position[i])) == 1.0) {
                    currentH += 1;
                }
            }
        }
        return currentH;
    }

    /**
     * method that moves the queen to other rows and finds and records that specific heuristic
     * @param column
     */
    public void findAllQueenH(int column) {
        int lowestQueenHeuristic = queenHeuristic(position[column], column);
        int lowestQueenPosition = position[column];
        for (int i = 0; i < 8; i++) {
            if (!(position[column] == i)) {
                if (queenHeuristic(i, column) < lowestQueenHeuristic) {
                    lowestQueenHeuristic = queenHeuristic(i, column);
                    lowestQueenPosition = i;
                }
            }
        }
        this.heuristicAndPosition[0][column] = lowestQueenHeuristic - queenHeuristic(position[column], column);
        this.heuristicAndPosition[1][column] = lowestQueenPosition;
    }

    /**
     * finds the states with lowest heuristic and stores them in the field and prints
     */
    public void neighborStates() {
        int lowestH = 0;
        for (int i = 0; i < 8; i++) {
            if (this.heuristicAndPosition[0][i] < lowestH) {
                lowestH = this.heuristicAndPosition[0][i];
                this.queenToMove[0] = this.heuristicAndPosition[1][i];
                this.queenToMove[1] = this.position[i];
                this.queenToMove[2] = i;
            }
        }
        for(int i =0; i<8; i++) {
            if(this.heuristicAndPosition[0][i] == lowestH && lowestH != 0) {
                this.neighbors ++;
            }
        }
        if(this.heuristic == 0 && this.neighbors == 0) {
            this.solution = true;
        }else if(this.heuristic != 0 && this.neighbors == 0) {
            this.restart = true;
        }
    }

    /**
     * calls the findAllQueenH(method) for all queens
     */
    public void runAllQueenSearch() {
        for(int i = 0; i < 8; i++) {
            findAllQueenH(i);
        }
    }

    /**
     * method that stores the queen to a new position
     */
    public void moveQueen() {
        int row = this.queenToMove[0];
        int oldRow = this.queenToMove[1];
        int column = this.queenToMove[2];
        this.position[column] = row;
        this.board[oldRow][column] = 0;
        this.board[row][column] = 1;
        this.neighbors = 0;
        this.heuristic = 0;
    }

    /**
     * method that clears the board in order to print each state with accurate values
     */
    public void clearBoard() {
        for (int i = 0; i < 8; i++) {
            for (int j= 0; j < 8; j++) {
                this.board[i][j] = 0;
            }
        }
    }

}

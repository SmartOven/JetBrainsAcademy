package tictactoe;

public class Game {
    private final char[][] field;
    private GameState gameState;
    private char player;

    /**
     * Default game constructor with empty field
     */
    public Game() {
        field = new char[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                field[i][j] = '_';
            }
        }
        gameState = GameState.GAME_NOT_FINISHED;
        player = 'X';
    }

    /**
     * Game constructor from the state as String
     * @param state String representing current game state
     */
    public Game(String state) {
        field = new char[3][3];
        int index = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                field[i][j] = state.charAt(index);
                index++;
            }
        }
        gameState = getGameState();
        player = 'X';
    }

    /**
     *
     * @return true if game is finished; false if not
     */
    public boolean isFinished() {
        return !gameState.equals(GameState.GAME_NOT_FINISHED);
    }

    /**
     * Checks user input and makes move in the game
     * If user input is not correct or out of bounds, or the cell is already occupied -> return false;
     * Else makes move -> return true;
     * @param input String with two integers separated by the space
     * @return true if move was successful; false if not
     */
    public boolean makeMove(String input) {
        // Parsing user input
        String[] splitInput = input.split(" ");
        if (splitInput.length != 2) {
            System.out.println("You should enter numbers!");
            return false;
        }
        int x;
        int y;
        try {
            x = Integer.parseInt(splitInput[0]) - 1;
            y = Integer.parseInt(splitInput[1]) - 1;
        } catch (Exception e) {
            System.out.println("You should enter numbers!");
            return false;
        }

        // Checking if it is out of bounds or not
        if (!(x >= 0 && x < 3 && y >= 0 && y < 3)) {
            System.out.println("Coordinates should be from 1 to 3!");
            return false;
        }

        if (field[x][y] != '_') {
            System.out.println("This cell is occupied! Choose another one!");
            return false;
        }

        field[x][y] = player;
        if (player == 'X') {
            player = 'O';
        } else {
            player = 'X';
        }

        gameState = getGameState();

        return true;
    }

    /**
     *
     * @return String value for each existing game state
     */
    public String getGameStateInfo() {
        return switch (gameState) {
            case GAME_NOT_FINISHED -> "Game not finished";
            case X_WINS -> "X wins";
            case O_WINS -> "O wins";
            case DRAW -> "Draw";
            case IMPOSSIBLE -> "Impossible";
        };
    }

    /**
     * Checks if game state changed to finished or not
     * @return GameState
     */
    private GameState getGameState() {
        // Get count of X and O
        int xCounter = 0;
        int oCounter = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (field[i][j] == 'X') {
                    xCounter++;
                } else if (field[i][j] == 'O') {
                    oCounter++;
                }
            }
        }

        // Too many X or too many O
        if (Math.abs(xCounter - oCounter) >= 2) {
            return GameState.IMPOSSIBLE;
        }

        // Check if X or O has three in a row
        boolean xHasLine = checkRows('X') || checkColumns('X') || checkDiagonals('X');
        boolean oHasLine = checkRows('O') || checkColumns('O') || checkDiagonals('O');

        // Check impossible situation (both won)
        if (xHasLine && oHasLine) {
            return GameState.IMPOSSIBLE;
        }

        // No one has line
        if (!xHasLine && !oHasLine) {
            // Can't place anymore chars
            if (xCounter + oCounter == 9) {
                return GameState.DRAW;
            }
            return GameState.GAME_NOT_FINISHED;
        }

        // If X has line - X wins, else O wins
        if (xHasLine) {
            return GameState.X_WINS;
        } else {
            return GameState.O_WINS;
        }
    }

    private boolean checkRows(char c) {
        for (int i = 0; i < 3; i++) {
            boolean rowHasSameChars = true;
            for (int j = 0; j < 3; j++) {
                if (field[i][j] != c) {
                    rowHasSameChars = false;
                    break;
                }
            }
            if (rowHasSameChars) {
                return true;
            }
        }
        return false;
    }

    private boolean checkColumns(char c) {
        for (int j = 0; j < 3; j++) {
            boolean columnHasSameChars = true;
            for (int i = 0; i < 3; i++) {
                if (field[i][j] != c) {
                    columnHasSameChars = false;
                    break;
                }
            }
            if (columnHasSameChars) {
                return true;
            }
        }
        return false;
    }

    private boolean checkDiagonals(char c) {
        boolean mainDiagonalHasSameChars = true;
        for (int i = 0; i < 3; i++) {
            if (field[i][i] != c) {
                mainDiagonalHasSameChars = false;
                break;
            }
        }

        boolean subDiagonalHasSameChars = true;
        for (int i = 0; i < 3; i++) {
            if (field[i][2 - i] != c) {
                subDiagonalHasSameChars = false;
                break;
            }
        }

        return mainDiagonalHasSameChars || subDiagonalHasSameChars;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("---------\n");
        for (int i = 0; i < 3; i++) {
            stringBuilder.append("| ");
            for (int j = 0; j < 3; j++) {
                stringBuilder.append(field[i][j]).append(" ");
            }
            stringBuilder.append("|\n");
        }
        stringBuilder.append("---------");
        return stringBuilder.toString();
    }
}

package vgu.trac.ttt.basic;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
// import java.util.logging.Level;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BlackBoxTest {
    private final PrintStream originalOut = System.out;
    private final InputStream originalIn = System.in;
    private PipedOutputStream outputStream;
    private BufferedReader scanner;

    // @BeforeEach
    // void setUp() {

    // outputStream = new PipedOutputStream();
    // try {
    // PipedInputStream inputStream = new PipedInputStream(outputStream); // Connect
    // in constructor
    // scanner = new BufferedReader(new InputStreamReader(inputStream,
    // StandardCharsets.UTF_8));
    // } catch (IOException ex) {
    // // Logger.getLogger(AbstractPlayer.class.getName()).log(Level.SEVERE, null,
    // ex);
    // ex.printStackTrace();
    // }

    // System.setOut(new PrintStream(outputStream));
    // }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
        System.setIn(originalIn);
    }

    @Test
    public void startGameWithHumanFirst() throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output, true));

        String data = "q" + System.lineSeparator();
        byte[] byteArray = data.getBytes();
        InputStream inputStream = new ByteArrayInputStream(byteArray);
        System.setIn(inputStream);

        App.main(new String[] { "1" });
        byte[] printOut = output.toByteArray();

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new ByteArrayInputStream(printOut), StandardCharsets.UTF_8))) {
            assertEquals("Hello!", reader.readLine());
            assertEquals(" | 0 | 0 | 0 | ", reader.readLine());
            assertEquals(" | 0 | 0 | 0 | ", reader.readLine());
            assertEquals(" | 0 | 0 | 0 | ", reader.readLine());
            assertEquals("Player#1's turn", reader.readLine());
        }
    }

    @Test
    public void startGameWithComputerFirst() throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output, true));

        String data = "q" + System.lineSeparator();
        byte[] byteArray = data.getBytes();
        InputStream inputStream = new ByteArrayInputStream(byteArray);
        System.setIn(inputStream);

        App.main(new String[] { "2" });
        byte[] printOut = output.toByteArray();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new ByteArrayInputStream(printOut), StandardCharsets.UTF_8))) {
            assertEquals("Hello!", reader.readLine());
            assertEquals(" | 0 | 0 | 0 | ", reader.readLine());
            assertEquals(" | 0 | 0 | 0 | ", reader.readLine());
            assertEquals(" | 0 | 0 | 0 | ", reader.readLine());
            assertEquals("Player#2's turn", reader.readLine());
        }
    }

    @Test
    public void rejectMissingStartupArgument() throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output, true));

        String data = "q" + System.lineSeparator();
        byte[] byteArray = data.getBytes();
        InputStream inputStream = new ByteArrayInputStream(byteArray);
        System.setIn(inputStream);

        App.main(new String[] {});
        byte[] printOut = output.toByteArray();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new ByteArrayInputStream(printOut), StandardCharsets.UTF_8))) {
            assertEquals("Please, input a valid option [1-2]", reader.readLine());
            assertNull(reader.readLine());
        }
    }

    @Test
    public void startWithInvalidNumber() throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output, true));

        String data = "q" + System.lineSeparator();
        byte[] byteArray = data.getBytes();
        InputStream inputStream = new ByteArrayInputStream(byteArray);
        System.setIn(inputStream);

        App.main(new String[] { "3" });
        byte[] printOut = output.toByteArray();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new ByteArrayInputStream(printOut), StandardCharsets.UTF_8))) {
            assertEquals("Please, input a valid option [1-2]", reader.readLine());
            assertNull(reader.readLine());
        }
    }

    @Test
    public void startWithString() throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output, true));

        String data = "q" + System.lineSeparator();
        byte[] byteArray = data.getBytes();
        InputStream inputStream = new ByteArrayInputStream(byteArray);
        System.setIn(inputStream);

        App.main(new String[] { "abc" });
        byte[] printOut = output.toByteArray();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new ByteArrayInputStream(printOut), StandardCharsets.UTF_8))) {
            assertEquals("Please, input a valid option [1-2]", reader.readLine());
            assertNull(reader.readLine());
        }
    }

    @Test
    public void startWithNegativeNumber() throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output, true));

        String data = "q" + System.lineSeparator();
        byte[] byteArray = data.getBytes();
        InputStream inputStream = new ByteArrayInputStream(byteArray);
        System.setIn(inputStream);

        App.main(new String[] { "-1" });
        byte[] printOut = output.toByteArray();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new ByteArrayInputStream(printOut), StandardCharsets.UTF_8))) {
            assertEquals("Please, input a valid option [1-2]", reader.readLine());
            assertNull(reader.readLine());
        }
    }

    @Test
    public void startWith01() throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output, true));

        String data = "q" + System.lineSeparator();
        byte[] byteArray = data.getBytes();
        InputStream inputStream = new ByteArrayInputStream(byteArray);
        System.setIn(inputStream);

        App.main(new String[] { "01" });
        byte[] printOut = output.toByteArray();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new ByteArrayInputStream(printOut), StandardCharsets.UTF_8))) {
            assertEquals("Please, input a valid option [1-2]", reader.readLine());
            assertNull(reader.readLine());
        }
    }

    @Test
    public void startWithExtraArgument() throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output, true));

        String data = "q" + System.lineSeparator();
        byte[] byteArray = data.getBytes();
        InputStream inputStream = new ByteArrayInputStream(byteArray);
        System.setIn(inputStream);

        App.main(new String[] { "1 2" });
        byte[] printOut = output.toByteArray();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new ByteArrayInputStream(printOut), StandardCharsets.UTF_8))) {
            assertEquals("Please, input a valid option [1-2]", reader.readLine());
            assertNull(reader.readLine());
        }
    }

    @Test
    public void startWithWhiteSpace() throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output, true));

        String data = "q" + System.lineSeparator();
        byte[] byteArray = data.getBytes();
        InputStream inputStream = new ByteArrayInputStream(byteArray);
        System.setIn(inputStream);

        App.main(new String[] { " 1" });
        byte[] printOut = output.toByteArray();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new ByteArrayInputStream(printOut), StandardCharsets.UTF_8))) {
            assertEquals("Please, input a valid option [1-2]", reader.readLine());
            assertNull(reader.readLine());
        }
    }

    @Test
    public void boardRender() throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output, true));

        String data = "5" + System.lineSeparator() +
                "q" + System.lineSeparator();
        byte[] byteArray = data.getBytes();
        InputStream inputStream = new ByteArrayInputStream(byteArray);
        System.setIn(inputStream);

        App.main(new String[] { "1" });
        byte[] printOut = output.toByteArray();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new ByteArrayInputStream(printOut), StandardCharsets.UTF_8))) {
            assertEquals("Hello!", reader.readLine());
            assertEquals(" | 0 | 0 | 0 | ", reader.readLine());
            assertEquals(" | 0 | 0 | 0 | ", reader.readLine());
            assertEquals(" | 0 | 0 | 0 | ", reader.readLine());
            assertEquals("Player#1's turn", reader.readLine());

            assertEquals(" | 0 | 0 | 0 | ", reader.readLine());
            assertEquals(" | 0 | 1 | 0 | ", reader.readLine());
            assertEquals(" | 0 | 0 | 0 | ", reader.readLine());
            assertEquals("Player#2's turn", reader.readLine());
        }
    }

    @Test
    public void humanInvalidNonIntegerInput() throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output, true));

        String data = "abc" + System.lineSeparator() +
                "@" + System.lineSeparator() +
                " " + System.lineSeparator() +
                "q" + System.lineSeparator();
        byte[] byteArray = data.getBytes();
        InputStream inputStream = new ByteArrayInputStream(byteArray);
        System.setIn(inputStream);

        App.main(new String[] { "1" });
        byte[] printOut = output.toByteArray();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new ByteArrayInputStream(printOut), StandardCharsets.UTF_8))) {
            assertEquals("Hello!", reader.readLine());
            assertEquals(" | 0 | 0 | 0 | ", reader.readLine());
            assertEquals(" | 0 | 0 | 0 | ", reader.readLine());
            assertEquals(" | 0 | 0 | 0 | ", reader.readLine());
            assertEquals("Player#1's turn", reader.readLine());

            assertEquals("Please, input a valid number [1-9]", reader.readLine());
            assertEquals("Player#1's turn", reader.readLine());
            assertEquals("Please, input a valid number [1-9]", reader.readLine());
            assertEquals("Player#1's turn", reader.readLine());
            assertEquals("Please, input a valid number [1-9]", reader.readLine());
            assertEquals("Player#1's turn", reader.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test // TS- 009
    public void quitGame() throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output, true));

        String data = "q" + System.lineSeparator();
        byte[] byteArray = data.getBytes();
        InputStream inputStream = new ByteArrayInputStream(byteArray);
        System.setIn(inputStream);
        App.main(new String[] { "1" });

        byte[] printOut = output.toByteArray();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new ByteArrayInputStream(printOut), StandardCharsets.UTF_8))) {
            assertEquals("Hello!", reader.readLine());
            assertEquals(" | 0 | 0 | 0 | ", reader.readLine());
            assertEquals(" | 0 | 0 | 0 | ", reader.readLine());
            assertEquals(" | 0 | 0 | 0 | ", reader.readLine());
            assertEquals("Player#1's turn", reader.readLine());
            assertEquals("End of the game", reader.readLine());
        }
    }

    @Test // TS-010
    public void quitCaseSensitive() throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output, true));

        String data = "Q" + System.lineSeparator() +
                " q" + System.lineSeparator() +
                "q " + System.lineSeparator() +
                "q" + System.lineSeparator();
        byte[] byteArray = data.getBytes();
        InputStream inputStream = new ByteArrayInputStream(byteArray);
        System.setIn(inputStream);
        App.main(new String[] { "1" });

        byte[] printOut = output.toByteArray();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new ByteArrayInputStream(printOut), StandardCharsets.UTF_8))) {
            assertEquals("Hello!", reader.readLine());
            assertEquals(" | 0 | 0 | 0 | ", reader.readLine());
            assertEquals(" | 0 | 0 | 0 | ", reader.readLine());
            assertEquals(" | 0 | 0 | 0 | ", reader.readLine());
            assertEquals("Player#1's turn", reader.readLine());

            // "Q"
            assertEquals("Please, input a valid number [1-9]", reader.readLine());
            assertEquals("Player#1's turn", reader.readLine());
            // "q "
            assertEquals("Please, input a valid number [1-9]", reader.readLine());
            assertEquals("Player#1's turn", reader.readLine());
            // " q"
            assertEquals("Please, input a valid number [1-9]", reader.readLine());
            assertEquals("Player#1's turn", reader.readLine());
        }
    }

    @Test // TS-011
    public void invalidRangeInteger() throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output, true));

        String data = 
        "0" + System.lineSeparator() +
        "10" + System.lineSeparator() +
        "-3" + System.lineSeparator() +
        "q" + System.lineSeparator();
        byte[] byteArray = data.getBytes();
        InputStream inputStream = new ByteArrayInputStream(byteArray);
        System.setIn(inputStream);
        App.main(new String[] { "1" });

        byte[] printOut = output.toByteArray();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new ByteArrayInputStream(printOut), StandardCharsets.UTF_8))) {
            assertEquals("Hello!", reader.readLine());
            assertEquals(" | 0 | 0 | 0 | ", reader.readLine());
            assertEquals(" | 0 | 0 | 0 | ", reader.readLine());
            assertEquals(" | 0 | 0 | 0 | ", reader.readLine());
            assertEquals("Player#1's turn", reader.readLine());

            // "0"
            assertEquals("Please, input a valid number [1-9]", reader.readLine());
            assertEquals("Player#1's turn", reader.readLine());
            // "10 "
            assertEquals("Please, input a valid number [1-9]", reader.readLine());
            assertEquals("Player#1's turn", reader.readLine());
            // " -3"
            assertEquals("Please, input a valid number [1-9]", reader.readLine());
            assertEquals("Player#1's turn", reader.readLine());
        }
    }

    @Test //TS-012: Reject move to occupied cell
    public void rejectOccupiedCell() throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output, true));

        String data = 
        "1" + System.lineSeparator() + 
        "1" + System.lineSeparator() + 
        "q" + System.lineSeparator();
        byte[] byteArray = data.getBytes();
        InputStream inputStream = new ByteArrayInputStream(byteArray);
        System.setIn(inputStream);
        App.main(new String[] { "1" });

        byte[] printOut = output.toByteArray();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new ByteArrayInputStream(printOut), StandardCharsets.UTF_8))) {
            assertEquals("Hello!", reader.readLine());
            assertEquals(" | 0 | 0 | 0 | ", reader.readLine());
            assertEquals(" | 0 | 0 | 0 | ", reader.readLine());
            assertEquals(" | 0 | 0 | 0 | ", reader.readLine());
            assertEquals("Player#1's turn", reader.readLine());

            skipLine(reader);
            skipLine(reader);
            // "1"
            assertEquals("The cell is occupied!", reader.readLine());
            assertEquals("Player#1's turn", reader.readLine());
        }
    }

    @Test //TS-013 Row
    public void humanWinRow() throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output, true));

        String data = 
        "2" + System.lineSeparator() + // thu tu can choi 2 7 8 9
        "7" + System.lineSeparator() +
        "8" + System.lineSeparator() +
        "9" + System.lineSeparator();
        byte[] byteArray = data.getBytes();
        InputStream inputStream = new ByteArrayInputStream(byteArray);
        System.setIn(inputStream);
        App.main(new String[] { "2" });

        byte[] printOut = output.toByteArray();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new ByteArrayInputStream(printOut), StandardCharsets.UTF_8))) {
            assertEquals("Hello!", reader.readLine());
            assertEquals(" | 0 | 0 | 0 | ", reader.readLine());
            assertEquals(" | 0 | 0 | 0 | ", reader.readLine());
            assertEquals(" | 0 | 0 | 0 | ", reader.readLine());
            assertEquals("Player#2's turn", reader.readLine());

            // skipLine(reader); // Player#2 -> 1 # Không cần dòng này tại vì máy tính đã tự chạy vào auto in ra terminal trước
            // khi người dùng nhập 1 cái gì đó

            skipLine(reader); // Player#1 -> 2
            skipLine(reader); // Player#2 -> 3
            skipLine(reader); // Player#1 -> 7
            skipLine(reader); // Player#2 -> 4
            skipLine(reader); // Player#1 -> 8
            skipLine(reader); // Player#2 -> 5
            skipLine(reader); // Player#1 -> 9
            
            assertEquals(" | 2 | 1 | 2 | ", reader.readLine());
            assertEquals(" | 2 | 2 | 0 | ", reader.readLine());
            assertEquals(" | 1 | 1 | 1 | ", reader.readLine());
            assertEquals("Player#1 won!", reader.readLine());
        }
    }
    @Test //TS-013 Column
    public void humanWinColumn() throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output, true));

        String data = 
        "2" + System.lineSeparator() + // thu tu can choi 2 7 8 9
        "5" + System.lineSeparator() +
        "8" + System.lineSeparator();
        byte[] byteArray = data.getBytes();
        InputStream inputStream = new ByteArrayInputStream(byteArray);
        System.setIn(inputStream);
        App.main(new String[] { "2" });

        byte[] printOut = output.toByteArray();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new ByteArrayInputStream(printOut), StandardCharsets.UTF_8))) {
            assertEquals("Hello!", reader.readLine());
            assertEquals(" | 0 | 0 | 0 | ", reader.readLine());
            assertEquals(" | 0 | 0 | 0 | ", reader.readLine());
            assertEquals(" | 0 | 0 | 0 | ", reader.readLine());
            assertEquals("Player#2's turn", reader.readLine());

            skipLine(reader); // Player#1 -> 2
            skipLine(reader); // Player#2 -> 3
            skipLine(reader); // Player#1 -> 5
            skipLine(reader); // Player#2 -> 4
            skipLine(reader); // Player#1 -> 8
            
            assertEquals(" | 2 | 1 | 2 | ", reader.readLine());
            assertEquals(" | 2 | 1 | 0 | ", reader.readLine());
            assertEquals(" | 0 | 1 | 0 | ", reader.readLine());
            assertEquals("Player#1 won!", reader.readLine());
        }
    }
    @Test //TS-013 Diagonal
    public void humanWinDiagonal() throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output, true));

        String data = 
        "3" + System.lineSeparator() + // thu tu can choi 2 7 8 9
        "5" + System.lineSeparator() +
        "7" + System.lineSeparator();
        byte[] byteArray = data.getBytes();
        InputStream inputStream = new ByteArrayInputStream(byteArray);
        System.setIn(inputStream);
        App.main(new String[] { "2" });

        byte[] printOut = output.toByteArray();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new ByteArrayInputStream(printOut), StandardCharsets.UTF_8))) {
            assertEquals("Hello!", reader.readLine());
            assertEquals(" | 0 | 0 | 0 | ", reader.readLine());
            assertEquals(" | 0 | 0 | 0 | ", reader.readLine());
            assertEquals(" | 0 | 0 | 0 | ", reader.readLine());
            assertEquals("Player#2's turn", reader.readLine());

            skipLine(reader); // Player#1 -> 3
            skipLine(reader); // Player#2 -> 2
            skipLine(reader); // Player#1 -> 5
            skipLine(reader); // Player#2 -> 4
            skipLine(reader); // Player#1 -> 7
            
            assertEquals(" | 2 | 2 | 1 | ", reader.readLine());
            assertEquals(" | 2 | 1 | 0 | ", reader.readLine());
            assertEquals(" | 1 | 0 | 0 | ", reader.readLine());
            assertEquals("Player#1 won!", reader.readLine());
        }
    }
    @Test //TS-014 Computer Win
    public void computerWin() throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output, true));

        String data = 
        "4" + System.lineSeparator() + 
        "5" + System.lineSeparator();
        byte[] byteArray = data.getBytes();
        InputStream inputStream = new ByteArrayInputStream(byteArray);
        System.setIn(inputStream);
        App.main(new String[] { "2" });

        byte[] printOut = output.toByteArray();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new ByteArrayInputStream(printOut), StandardCharsets.UTF_8))) {
            assertEquals("Hello!", reader.readLine());
            assertEquals(" | 0 | 0 | 0 | ", reader.readLine());
            assertEquals(" | 0 | 0 | 0 | ", reader.readLine());
            assertEquals(" | 0 | 0 | 0 | ", reader.readLine());
            assertEquals("Player#2's turn", reader.readLine());

            skipLine(reader); // Player#1 -> 4
            skipLine(reader); // Player#2 -> 2
            skipLine(reader); // Player#1 -> 5
            skipLine(reader); // Player#2 -> 3
            
            assertEquals(" | 2 | 2 | 2 | ", reader.readLine());
            assertEquals(" | 1 | 1 | 0 | ", reader.readLine());
            assertEquals(" | 0 | 0 | 0 | ", reader.readLine());
            assertEquals("Player#2 won!", reader.readLine());
            assertNull(reader.readLine()); // Program ends
        }
    }
    @Test // TS-015 Draw detection when board full after human move
    public void drawDetectionHumanMove() throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output, true));

        String data = 
        "5" + System.lineSeparator() + // thu tu can choi 2 7 8 9
        "2" + System.lineSeparator() +
        "6" + System.lineSeparator() + 
        "7" + System.lineSeparator() +
        "9" + System.lineSeparator();
        byte[] byteArray = data.getBytes();
        InputStream inputStream = new ByteArrayInputStream(byteArray);
        System.setIn(inputStream);
        App.main(new String[] { "1" });

        byte[] printOut = output.toByteArray();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new ByteArrayInputStream(printOut), StandardCharsets.UTF_8))) {
            assertEquals("Hello!", reader.readLine());
            assertEquals(" | 0 | 0 | 0 | ", reader.readLine());
            assertEquals(" | 0 | 0 | 0 | ", reader.readLine());
            assertEquals(" | 0 | 0 | 0 | ", reader.readLine());
            assertEquals("Player#1's turn", reader.readLine());

            skipLine(reader); // Player#1 -> 5
            skipLine(reader); // Player#2 -> 1
            skipLine(reader); // Player#1 -> 2
            skipLine(reader); // Player#2 -> 3
            skipLine(reader); // Player#1 -> 6 
            skipLine(reader); // Player#2 -> 4
            skipLine(reader); // Player#1 -> 7
            skipLine(reader); // Player#2 -> 8
            
            // Player#1 -> 9 => Announce draw
            assertEquals(" | 2 | 1 | 2 | ", reader.readLine());
            assertEquals(" | 2 | 1 | 1 | ", reader.readLine());
            assertEquals(" | 1 | 2 | 1 | ", reader.readLine());
            assertEquals("It is a draw!", reader.readLine());
            assertNull(reader.readLine()); // Program ends
        }
    }

    @Test //TS-016 Draw detection when board full after computer move
    public void drawDetectionComputerMove() throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output, true));

        String data = 
        "2" + System.lineSeparator() + // thu tu can choi 2 7 8 9
        "5" + System.lineSeparator() +
        "7" + System.lineSeparator() +
        "9" + System.lineSeparator();
        byte[] byteArray = data.getBytes();
        InputStream inputStream = new ByteArrayInputStream(byteArray);
        System.setIn(inputStream);
        App.main(new String[] { "2" });

        byte[] printOut = output.toByteArray();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new ByteArrayInputStream(printOut), StandardCharsets.UTF_8))) {
            assertEquals("Hello!", reader.readLine());
            assertEquals(" | 0 | 0 | 0 | ", reader.readLine());
            assertEquals(" | 0 | 0 | 0 | ", reader.readLine());
            assertEquals(" | 0 | 0 | 0 | ", reader.readLine());
            assertEquals("Player#2's turn", reader.readLine());
            
            // Máy tính tự động chạy, không cần skip // Player#2 -> 1
            skipLine(reader); // Player#1 -> 2
            skipLine(reader); // Player#2 -> 3
            skipLine(reader); // Player#1 -> 5
            skipLine(reader); // Player#2 -> 4
            skipLine(reader); // Player#1 -> 7
            skipLine(reader); // Player#2 -> 6
            skipLine(reader); // Player#1 -> 9
            
            skipLine(reader); // Player#2 -> 8 => Announce draw
            
            assertEquals(" | 2 | 1 | 2 | ", reader.readLine());
            assertEquals(" | 2 | 1 | 2 | ", reader.readLine());
            assertEquals(" | 1 | 2 | 1 | ", reader.readLine());
            assertEquals("It is a draw!", reader.readLine());
            assertNull(reader.readLine()); // Program ends
        }
    }
    
    @Test //TS-017 Computer chooses first available cell
    public void firstAvailableCell() throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output, true));

        String data = 
        "2" + System.lineSeparator() + 
        "q" + System.lineSeparator();
        byte[] byteArray = data.getBytes();
        InputStream inputStream = new ByteArrayInputStream(byteArray);
        System.setIn(inputStream);
        App.main(new String[] { "2" });

        byte[] printOut = output.toByteArray();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new ByteArrayInputStream(printOut), StandardCharsets.UTF_8))) {
            assertEquals("Hello!", reader.readLine());
            assertEquals(" | 0 | 0 | 0 | ", reader.readLine());
            assertEquals(" | 0 | 0 | 0 | ", reader.readLine());
            assertEquals(" | 0 | 0 | 0 | ", reader.readLine());
            assertEquals("Player#2's turn", reader.readLine());
            
            assertEquals(" | 2 | 0 | 0 | ", reader.readLine());
            assertEquals(" | 0 | 0 | 0 | ", reader.readLine());
            assertEquals(" | 0 | 0 | 0 | ", reader.readLine());
            assertEquals("Player#1's turn", reader.readLine());

            skipLine(reader);
            assertEquals(" | 2 | 1 | 2 | ", reader.readLine());
            assertEquals(" | 0 | 0 | 0 | ", reader.readLine());
            assertEquals(" | 0 | 0 | 0 | ", reader.readLine());
            assertEquals("Player#1's turn", reader.readLine());
        }
    }
    @Test //TS-018 Board integrity after every move
    public void boardIntegrity() throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output, true));

        String data = 
        "2" + System.lineSeparator() + 
        "5" + System.lineSeparator() +
        "7" + System.lineSeparator() +
        "q" + System.lineSeparator();
        byte[] byteArray = data.getBytes();
        InputStream inputStream = new ByteArrayInputStream(byteArray);
        System.setIn(inputStream);
        App.main(new String[] { "2" });

        byte[] printOut = output.toByteArray();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new ByteArrayInputStream(printOut), StandardCharsets.UTF_8))) {
            assertEquals("Hello!", reader.readLine());
            assertEquals(" | 0 | 0 | 0 | ", reader.readLine());
            assertEquals(" | 0 | 0 | 0 | ", reader.readLine());
            assertEquals(" | 0 | 0 | 0 | ", reader.readLine());
            assertEquals("Player#2's turn", reader.readLine());
            
            // Computer plays first
            assertEquals(" | 2 | 0 | 0 | ", reader.readLine());
            assertEquals(" | 0 | 0 | 0 | ", reader.readLine());
            assertEquals(" | 0 | 0 | 0 | ", reader.readLine());
            assertEquals("Player#1's turn", reader.readLine());
            
            // Player 1 moves 2
            assertEquals(" | 2 | 1 | 0 | ", reader.readLine());
            assertEquals(" | 0 | 0 | 0 | ", reader.readLine());
            assertEquals(" | 0 | 0 | 0 | ", reader.readLine());
            assertEquals("Player#2's turn", reader.readLine());

            // Player 2 moves 3
            assertEquals(" | 2 | 1 | 2 | ", reader.readLine());
            assertEquals(" | 0 | 0 | 0 | ", reader.readLine());
            assertEquals(" | 0 | 0 | 0 | ", reader.readLine());
            assertEquals("Player#1's turn", reader.readLine());

            // Player 1 moves 5
            assertEquals(" | 2 | 1 | 2 | ", reader.readLine());
            assertEquals(" | 0 | 1 | 0 | ", reader.readLine());
            assertEquals(" | 0 | 0 | 0 | ", reader.readLine());
            assertEquals("Player#2's turn", reader.readLine());

            // Player 1 moves 4
            assertEquals(" | 2 | 1 | 2 | ", reader.readLine());
            assertEquals(" | 2 | 1 | 0 | ", reader.readLine());
            assertEquals(" | 0 | 0 | 0 | ", reader.readLine());
            assertEquals("Player#1's turn", reader.readLine());

            // Player 2 moves 
            assertEquals(" | 2 | 1 | 2 | ", reader.readLine());
            assertEquals(" | 2 | 1 | 0 | ", reader.readLine());
            assertEquals(" | 1 | 0 | 0 | ", reader.readLine());
            assertEquals("Player#2's turn", reader.readLine());
        }
    }

    @Test //TS-019 Turn messaging and flow continuity
    public void turnPromptSequenceCorrectness() throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output, true));

        String data = null;
        for (int i = 0; i < 5; i++) {
            data += "x" + System.lineSeparator();
            data += "!" + System.lineSeparator();
            data += "999" + System.lineSeparator();
            data += " " + System.lineSeparator();
        }
        data += "5" + System.lineSeparator() + "q" + System.lineSeparator();
        byte[] byteArray = data.getBytes();
        InputStream inputStream = new ByteArrayInputStream(byteArray);
        System.setIn(inputStream);
        App.main(new String[] { "1" });

        byte[] printOut = output.toByteArray();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new ByteArrayInputStream(printOut), StandardCharsets.UTF_8))) {
            assertEquals("Hello!", reader.readLine());
            assertEquals(" | 0 | 0 | 0 | ", reader.readLine());
            assertEquals(" | 0 | 0 | 0 | ", reader.readLine());
            assertEquals(" | 0 | 0 | 0 | ", reader.readLine());
            assertEquals("Player#1's turn", reader.readLine());
            
            for (int i = 0; i < 5; i++){
                for (int j = 0; j < 4; j++) {
                    assertEquals("Please, input a valid number [1-9]", reader.readLine());
                    assertEquals("Player#1's turn", reader.readLine());
                }
            }
            assertEquals(" | 0 | 0 | 0 | ", reader.readLine());
            assertEquals(" | 0 | 1 | 0 | ", reader.readLine());
            assertEquals(" | 0 | 0 | 0 | ", reader.readLine());
            assertEquals("Player#2's turn", reader.readLine());
        }
    }
    // @Test // TS-015 Immediate Ctrl+C Interruption
    // public void immediateCtrlC() throws IOException {
    //     ByteArrayOutputStream output = new ByteArrayOutputStream();
    //     System.setOut(new PrintStream(output, true));

    //     // Inject the custom broken stream directly
    //     System.setIn(new CtrlCStream());

    //     // Start the game. It will crash/exit on the very first read() attempt.
    //     App.main(new String[] { "2" });

    //     byte[] printOut = output.toByteArray();
    //     try (BufferedReader reader = new BufferedReader(
    //             new InputStreamReader(new ByteArrayInputStream(printOut), StandardCharsets.UTF_8))) {
            
    //         // Assert the initial game setup
    //         assertEquals("Hello!", reader.readLine());
    //         assertEquals(" | 0 | 0 | 0 | ", reader.readLine());
    //         assertEquals(" | 0 | 0 | 0 | ", reader.readLine());
    //         assertEquals(" | 0 | 0 | 0 | ", reader.readLine());
    //         assertEquals("Player#2's turn", reader.readLine());
    //         assertEquals("Unexpected exception when reading player", reader.readLine());
        
    //         assertNull(reader.readLine()); 
    //     }
    // }

    // Skip printing 3 lines of the board and the turn's notification
    private void skipLine(BufferedReader reader) throws IOException {
        try {
            reader.readLine();
            reader.readLine();
            reader.readLine();
            reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}



// Handle EOF
/*
 * class BrokenInputStream extends Input STream {
 * 
 * @override
 * public void read() throws IOExcetpion {
 * throw new IOException("Read failed");
 * }
 * // Đổi read thành m lúc nào cũng quăng Exception cho t
 * 
 * //Create a mock up class (to handle Exception in a Test)
 * }
 * 
 * how can we pass an Exception into a test (làm sao để tạo 1 cái exception r
 * quăng nó vô setIn được)
 * 
 * tạo 1 class
 * class
 * 
 * 
 * makeMove của player sẽ throw IOException
 * 
 * và sửa trong Game.java
 * catch (IOException ex) {
 * getBoardGame().printMessage("Unexpected exception when reading player");
 * return 0;
 * }
 * 
 */

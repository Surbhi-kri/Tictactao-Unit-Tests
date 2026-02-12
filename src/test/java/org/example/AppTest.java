package org.example;

import org.junit.jupiter.api.*;
import java.io.*;
import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    private final PrintStream originalOut = System.out;
    private final InputStream originalIn = System.in;
    private ByteArrayOutputStream outputStream;

    @BeforeEach
    void setUp() {
        // Reset board before each test
        Main.assignValue(Main.tictac);

        // Capture System.out
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
        System.setIn(originalIn);
    }

    @Test
    void testAssignValue() {
        // Fill board with X
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                Main.tictac[i][j] = 'X';

        Main.assignValue(Main.tictac);

        // Check all are empty
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                assertEquals(' ', Main.tictac[i][j]);
    }

    @Test
    void testWinRows() {
        Main.assignValue(Main.tictac);
        Main.tictac[0][0] = Main.tictac[0][1] = Main.tictac[0][2] = 'X';
        assertTrue(Main.win(Main.tictac, 'X'));
        assertFalse(Main.win(Main.tictac, 'O'));
    }

    @Test
    void testWinColumns() {
        Main.assignValue(Main.tictac);
        Main.tictac[0][0] = Main.tictac[1][0] = Main.tictac[2][0] = 'O';
        assertTrue(Main.win(Main.tictac, 'O'));
    }

    @Test
    void testWinDiagonals() {
        Main.assignValue(Main.tictac);
        Main.tictac[0][0] = Main.tictac[1][1] = Main.tictac[2][2] = 'X';
        assertTrue(Main.win(Main.tictac, 'X'));

        Main.assignValue(Main.tictac);
        Main.tictac[0][2] = Main.tictac[1][1] = Main.tictac[2][0] = 'O';
        assertTrue(Main.win(Main.tictac, 'O'));
    }

    @Test
    void testIsDraw() {
        Main.assignValue(Main.tictac);

        char[][] drawBoard = {
                {'X','O','X'},
                {'X','O','O'},
                {'O','X','X'}
        };

        Main.tictac = drawBoard;
        assertTrue(Main.isDraw());
    }

    @Test
    void testEasyMove() {
        Main.assignValue(Main.tictac);
        Main.easyMove('X');

        int count = 0;
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (Main.tictac[i][j] == 'X') count++;

        assertEquals(1, count);
    }

    @Test
    void testHardMoveMakesValidMove() {
        Main.assignValue(Main.tictac);
        Main.hardMove('X');

        int count = 0;
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (Main.tictac[i][j] == 'X') count++;

        assertEquals(1, count);
    }

    @Test
    void testMediumMoveBlocksOrWins() {
        Main.assignValue(Main.tictac);
        // Setup board so X can win
        Main.tictac[0][0] = 'X';
        Main.tictac[0][1] = 'X';
        Main.mediumMove('X');
        assertEquals('X', Main.tictac[0][2]);
    }

    @Test
    void testCount() {
        Main.assignValue(Main.tictac);
        Main.tictac[0][0] = Main.tictac[0][1] = 'X';
        assertEquals(2, Main.count('X', 0,0, 0,1, 0,2));
        assertEquals(0, Main.count('O', 0,0,0,1,0,2));
    }
}

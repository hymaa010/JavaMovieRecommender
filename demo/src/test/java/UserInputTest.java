import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

import com.example.User;
import com.example.userInput;

class UserInputTest {
    private userInput ui = new userInput();
    @BeforeEach
    void setUp() throws Exception {
        ui = new userInput();
    }
    
    private String createTestFile(String content) throws IOException {
        File temp = File.createTempFile("testFile", ".txt");
        temp.deleteOnExit();
        try (FileWriter writer = new FileWriter(temp)) {
            writer.write(content);
        }
        return temp.getAbsolutePath();
    }
    
    @Test
    void testCheckName_valid() {
        assertTrue(ui.checkName("Ziad Tamer"));
        assertTrue(ui.checkName("Ziad"));
    }
    
    @Test
    void testCheckName_startsWithSpace() {
        assertFalse(ui.checkName(" Mazen"));
    }
    
    @Test
    void testCheckName_containsNumbers() {
        assertFalse(ui.checkName("Rana2"));
    }
    
    @Test
    void testCheckName_containsSpecialChars() {
        assertFalse(ui.checkName("Shahd@"));
    }
    
    @Test
    void testCheckId_valid() {
        assertTrue(ui.checkId("12345678B"));
    }
    
    @Test
    void testCheckId_invalidLength() {
        assertFalse(ui.checkId("1234"));
    }
    @Test
    void testCheckId_invalidDigit() {
        assertFalse(ui.checkId("1234A678A"));
    }
    
    @Test
    void testCheckId_allnumbers() {
        assertTrue(ui.checkId("123456789"));
    }
    @Test
    void testCheckId_duplicate() {
        assertTrue(ui.checkId("12345678Z"));
        assertFalse(ui.checkId("12345678Z"));
    }
    @Test
    void testGetUsers_validFile() throws Exception {
        String content =
                "Menna Mohamed,12345678A\n" +
                        "M001,M002\n" +
                        "Mazen Mohamed,98765432B\n" +
                        "M010,M020,M030\n";

        String path = createTestFile(content);
        ui.setFilePath(path);

        Vector<User> users = ui.getUsers();
        assertEquals(2, users.size());

        assertEquals("Menna Mohamed", users.getFirst().getUserName());
        assertEquals("12345678A", users.getFirst().getUserId());
        assertArrayEquals(new String[]{"M001", "M002"}, users.getFirst().getMoviesIds());

        assertEquals("Mazen Mohamed", users.get(1).getUserName());
        assertEquals("98765432B", users.get(1).getUserId());
        assertArrayEquals(new String[]{"M010", "M020", "M030"}, users.get(1).getMoviesIds());
    }
    @Test
    void testGetUsers_invalidname() throws Exception {
        String content =
                "$Mazen Mohamed,12345678B\n" +
                        "M001\n";

        String path = createTestFile(content);
        ui.setFilePath(path);

        Vector<User> users = ui.getUsers();

        assertEquals(0, users.size());
    }
    @Test
    void testGetUsers_invalidId() throws Exception {
        String content =
                "Mohamed Allam,1234AB\n" +
                        "M001\n";

        String path = createTestFile(content);
        ui.setFilePath(path);

        Vector<User> users = ui.getUsers();

        assertEquals(0, users.size());
    }
    @Test
    void testGetUsers_duplicateIds() throws Exception {
        String content =
                "Shahd Bassem,12345678F\n" +
                        "M001,M002\n" +
                        "Rana Essam,12345678F\n" +
                        "M005\n";

        String path = createTestFile(content);
        ui.setFilePath(path);

        Vector<User> users = ui.getUsers();

        assertEquals(1, users.size());

        assertEquals("Shahd Bassem", users.get(0).getUserName());
        assertEquals("12345678F", users.get(0).getUserId());

    }
    @Test
    void testGetUsers_filePathNotSet() {
        ui.setFilePath(null);
        Vector<User> users = ui.getUsers();
        assertTrue(users.isEmpty());
    }
    @Test
    void testGetUsers_emptyFile() throws Exception {
        String path = createTestFile("");
        ui.setFilePath(path);

        Vector<User> users = ui.getUsers();
        assertTrue(users.isEmpty());
    }
}

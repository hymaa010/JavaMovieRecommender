import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

import java.io.File;
import java.io.FileWriter;
import java.util.Vector;

import com.example.Entities.User;
import com.example.Readers.userInput;

class UserInputTest {

    private userInput ui;

    @BeforeEach
    void setUp() {
        ui = new userInput();
    }

    private String createTestFile(String content) throws Exception {
        File temp = File.createTempFile("testFile", ".txt");
        temp.deleteOnExit();
        try (FileWriter writer = new FileWriter(temp)) {
            writer.write(content);
        }
        return temp.getAbsolutePath();
    }

    @Test
    void testGetUsers_validFile() throws Exception {
        String content =
                "Menna Mohamed,12345678A\n" +
                "M001,M002\n" +
                "Mazen Mohamed,98765432B\n" +
                "M010,M020,M030\n";

        ui.setFilePath(createTestFile(content));
        Vector<User> users = ui.getUsers();

        assertEquals(2, users.size());
    }

    @Test
    void testGetUsers_invalidName() throws Exception {
        String content =
                "$Mazen Mohamed,12345678B\n" +
                "M001\n";

        ui.setFilePath(createTestFile(content));
        assertTrue(ui.getUsers().isEmpty());
    }

    @Test
    void testGetUsers_invalidId() throws Exception {
        String content =
                "Mohamed Allam,1234AB\n" +
                "M001\n";

        ui.setFilePath(createTestFile(content));
        assertTrue(ui.getUsers().isEmpty());
    }

    @Test
    void testGetUsers_duplicateIds() throws Exception {
        String content =
                "Shahd Bassem,12345678F\n" +
                "M001,M002\n" +
                "Rana Essam,12345678F\n" +
                "M005\n";

        ui.setFilePath(createTestFile(content));
        Vector<User> users = ui.getUsers();

        assertEquals(1, users.size());
        assertEquals("Shahd Bassem", users.get(0).getUserName());
    }

    @Test
    void testGetUsers_filePathNotSet() {
        ui.setFilePath(null);
        assertTrue(ui.getUsers().isEmpty());
    }

    @Test
    void testGetUsers_emptyFile() throws Exception {
        ui.setFilePath(createTestFile(""));
        assertTrue(ui.getUsers().isEmpty());
    }
}

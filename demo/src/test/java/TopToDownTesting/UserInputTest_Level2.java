package TopToDownTesting;

import com.example.Entities.User;
import com.example.Validators.user_validator;
import com.example.Readers.userInput;

import org.junit.jupiter.api.*;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.io.*;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;

class UserInputTest_Level2 {

    private userInput ui;

    @BeforeEach
    void setUp() {
        ui = new userInput();
    }

    @Test
    void testGetUsers_FilePathNotSet() {
        ui.setFilePath(null);
        Vector<User> users = ui.getUsers();
        assertTrue(users.isEmpty());
    }

    @Test
    void testGetUsers_ValidFile() throws IOException {
        File tempFile = File.createTempFile("testUsers", ".txt");
        tempFile.deleteOnExit();

        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
        writer.write("Mazen,123456789\n");
        writer.write("M123,Z123,W123\n");
        writer.write("Ziad,987654321\n");
        writer.write("X123,U123\n");
        writer.close();

        ui.setFilePath(tempFile.getAbsolutePath());

        MockedStatic<user_validator> uv = Mockito.mockStatic(user_validator.class);
        uv.when(() -> user_validator.checkName(anyString())).thenReturn(true);
        uv.when(() -> user_validator.checkId(anyString())).thenReturn(true);

        Vector<User> users = ui.getUsers();

        assertEquals(2, users.size());

        assertEquals("Mazen", users.get(0).getUserName());
        assertEquals("123456789", users.get(0).getUserId());
        assertArrayEquals(new String[]{"M123","Z123","W123"}, users.get(0).getMoviesIds());

        assertEquals("Ziad", users.get(1).getUserName());
        assertEquals("987654321", users.get(1).getUserId());
        assertArrayEquals(new String[]{"X123","U123"}, users.get(1).getMoviesIds());
        uv.close();
    }
    @Test
void testGetUsers_InvalidName() throws IOException {
    File tempFile = File.createTempFile("testUsersPartialInvalid", ".txt");
    tempFile.deleteOnExit();

    BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
    writer.write("Mazen,123456789\n");
    writer.write("I123,I456\n");
    writer.write("$Mazen,123123123\n");
    writer.write("M123,W123\n");
    writer.close();

    ui.setFilePath(tempFile.getAbsolutePath());

    MockedStatic<user_validator> uv = Mockito.mockStatic(user_validator.class);
    uv.when(() -> user_validator.checkName("Mazen")).thenReturn(true);
    uv.when(() -> user_validator.checkName("$Mazen")).thenReturn(false);
    uv.when(() -> user_validator.checkId(anyString())).thenReturn(true);

    Vector<User> users = ui.getUsers();

    assertEquals(1, users.size());
    assertEquals("Mazen", users.get(0).getUserName());

    uv.close();
}

    @Test
void testGetUsers_UserWithNoLikedMovies() throws IOException {
    File tempFile = File.createTempFile("testUsersNoMovies", ".txt");
    tempFile.deleteOnExit();

    BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
    writer.write("Mazen Mohamed,123456789\n"); 
    writer.close();

    ui.setFilePath(tempFile.getAbsolutePath());

    MockedStatic<user_validator> uv = Mockito.mockStatic(user_validator.class);
    uv.when(() -> user_validator.checkName(anyString())).thenReturn(true);
    uv.when(() -> user_validator.checkId(anyString())).thenReturn(true);

    Vector<User> users = ui.getUsers();

    assertEquals(0, users.size()); 
    uv.close();
}

    @Test
void testGetUsers_duplicateIds() throws Exception {
    String content =
            "Mazen Mohamed,12345678F\n" +
            "M123,I123\n" +
            "Ziad Tamer,12345678F\n" +
            "M123\n";

    File tempFile = File.createTempFile("testUsersDuplicate", ".txt");
    tempFile.deleteOnExit();
    BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
    writer.write(content);
    writer.close();

    ui.setFilePath(tempFile.getAbsolutePath());

    MockedStatic<user_validator> uv = Mockito.mockStatic(user_validator.class);

    uv.when(() -> user_validator.checkName(anyString())).thenReturn(true);

    Set<String> seenIds = new HashSet<>();
    uv.when(() -> user_validator.checkId(anyString())).thenAnswer(invocation -> {
        String id = invocation.getArgument(0);
        if (seenIds.contains(id)) return false;
        seenIds.add(id);
        return true;
    });

    Vector<User> users = ui.getUsers();

    assertEquals(1, users.size());
    assertEquals("Mazen Mohamed", users.get(0).getUserName());
    uv.close();
}

}   
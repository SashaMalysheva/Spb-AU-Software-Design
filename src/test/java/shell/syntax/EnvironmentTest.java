package shell.syntax;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import shell.Environment;
import shell.commands.Cd;
import shell.commands.Ls;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertArrayEquals;

public class EnvironmentTest {

    Environment env;
    Path testDirectory;

    @Before
    public void before() throws Exception {
        env = Environment.getInstance();
        testDirectory = Files.createTempDirectory("testDirectory");
    }

    @After
    public void after() throws Exception {
        Files.walkFileTree(testDirectory, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Files.deleteIfExists(file);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                Files.deleteIfExists(dir);
                return FileVisitResult.CONTINUE;
            }
        });
        env = null;
    }

    @Test
    public void testEnvironmentCommands() throws Exception {
        // cd
        Cd cd = new Cd(new String[]{"cd", testDirectory.toAbsolutePath().toString()});
        cd.execute();

        assertEquals(testDirectory.toAbsolutePath(), env.getCurrentWorkingDirectory());

        // ls
        Files.createFile(testDirectory.resolve("file1"));
        Files.createFile(testDirectory.resolve("file2"));

        Ls ls = new Ls(new String[]{"ls"});
        ls.execute();
        BufferedReader resultReader = new BufferedReader(new InputStreamReader(ls.getOutputStreamOnRead()));
        String[] result = resultReader.readLine().split(" ");
        assertArrayEquals(new String[]{"file1", "file2"}, result);

    }
}

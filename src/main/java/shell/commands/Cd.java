package shell.commands;

import shell.Environment;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * 'cd' Command.
 *
 * Change the current working directory.
 */
public final class Cd extends Command {

    public Cd(String[] args) throws IOException {
        super(args);
    }

    @Override
    void execute() throws Exception {
        Path directory = Environment.getInstance()
                .getCurrentWorkingDirectory()
                .resolve(Paths.get(args[1]));
        if (Files.exists(directory) && Files.isDirectory(directory)) {
            Environment.getInstance().setCurrentWorkingDirectory(directory);
        }
    }
}

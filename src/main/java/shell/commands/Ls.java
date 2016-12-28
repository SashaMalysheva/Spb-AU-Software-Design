package shell.commands;

import shell.Environment;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.File;

/**
 * 'ls' Command.
 *
 * List files on the current working directory.
 *
 * @see Environment#getCurrentWorkingDirectory()
 */
public final class Ls extends  Command {

    public Ls(String[] args) throws IOException {
        super(args);
    }

    /**
     * {@inheritDoc}
     *
     * Lists files on th current working directory.
     *
     * @throws IOException if i/o error occurred.
     */
    @Override
    public void execute() throws IOException {
        Path directory = Environment.getInstance().getCurrentWorkingDirectory();
        final StringBuilder sb = new StringBuilder();
        Files.list(directory).sorted().forEach(child -> {
            sb.append(child.getFileName());
            sb.append(' ');
        });
        sb.append('\n');
        outputStream.write(sb.toString().getBytes());
    }
}

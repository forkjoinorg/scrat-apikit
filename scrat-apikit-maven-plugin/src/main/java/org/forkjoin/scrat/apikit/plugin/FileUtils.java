package org.forkjoin.scrat.apikit.plugin;

import org.apache.maven.plugin.logging.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public class FileUtils {

    public static boolean isSymlink(File file) throws IOException {
        if (file == null) {
            throw new NullPointerException("File must not be null");
        } else {
            return Files.isSymbolicLink(file.toPath());
        }
    }

    public static void deleteDirectory(File directory, List<String> cleanExcludes, Log log) throws IOException {
        if (directory.exists()) {
            if (!isSymlink(directory)) {
                cleanDirectory(directory, cleanExcludes, log);
            }
            if (!checkExclude(directory, cleanExcludes)) {
                log.info("Delete dir:" + directory);
                if (!directory.delete()) {
                    String message = "Unable to delete directory " + directory + ".";
                    throw new IOException(message);
                }
            }
        }
    }

    public static void cleanDirectory(final File directory, List<String> cleanExcludes, Log log) throws IOException {
        final File[] files = verifiedListFiles(directory);

        IOException exception = null;
        for (final File file : files) {
            try {
                forceDelete(file, cleanExcludes, log);
            } catch (final IOException ioe) {
                exception = ioe;
            }
        }

        if (null != exception) {
            throw exception;
        }
    }

    private static File[] verifiedListFiles(final File directory) throws IOException {
        if (!directory.exists()) {
            final String message = directory + " does not exist";
            throw new IllegalArgumentException(message);
        }

        if (!directory.isDirectory()) {
            final String message = directory + " is not a directory";
            throw new IllegalArgumentException(message);
        }

        final File[] files = directory.listFiles();
        if (files == null) {  // null if security restricted
            throw new IOException("Failed to list contents of " + directory);
        }
        return files;
    }

    public static boolean checkExclude(final File file, List<String> cleanExcludes) throws IOException {
        for (String item : cleanExcludes) {
            if (file.toString().matches(item)) {
                return true;
            }
        }
        return false;
    }

    public static void forceDelete(final File file, List<String> cleanExcludes, Log log) throws IOException {
        if (checkExclude(file, cleanExcludes)) {
            return;
        }
        if (file.isDirectory()) {
            deleteDirectory(file, cleanExcludes, log);
        } else {
            final boolean filePresent = file.exists();
            log.info("Delete file:" + file);
            if (!file.delete()) {
                if (!filePresent) {
                    throw new FileNotFoundException("File does not exist: " + file);
                }
                final String message =
                        "Unable to delete file: " + file;
                throw new IOException(message);
            }
        }
    }
}

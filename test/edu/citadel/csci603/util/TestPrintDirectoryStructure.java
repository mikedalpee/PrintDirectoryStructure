package edu.citadel.csci603.util;


import org.junit.jupiter.api.Test;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;

import java.security.Permission;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestPrintDirectoryStructure {
    final Logger LOG = LogManager.getLogger(TestPrintDirectoryStructure.class.getName());

    protected static class ExitException extends SecurityException
    {
        public final int status;
        public ExitException(int status)
        {
            super("There is no escape!");
            this.status = status;
        }
    }

    private static class NoExitSecurityManager extends SecurityManager
    {
        @Override
        public void checkPermission(Permission perm)
        {
            // allow anything.
        }
        @Override
        public void checkPermission(Permission perm, Object context)
        {
            // allow anything.
        }
        @Override
        public void checkExit(int status)
        {
            super.checkExit(status);
            throw new ExitException(status);
        }
    }

    @Test
    void testBadFileName() {
        LOG.info(
                "Testing bad file name");

        String[] args = {"arg1"};

        new PrintDirectoryStructure().main(args);
    }

    @Test
    void testExistentFileName() {
        LOG.info(
                "Testing existent file name");

        String[] args = {"C:\\pagefile.sys"};

        PrintDirectoryStructure.main(args);
    }

    @Test
    void testExistentDirectoryName() {
        LOG.info(
                "Testing existent directory name");

        String[] args = {"C:\\Users\\Mike\\IdeaProjects\\PrintDirectoryStructure"};

        PrintDirectoryStructure.main(args);
    }

    @Test
    void testBadArguments() {
        LOG.info(
                "Testing bad arguments");

        String[] args = {"arg1", "arg2"};

        System.setSecurityManager(new NoExitSecurityManager());

        try {
            PrintDirectoryStructure.main(args);
        } catch (ExitException e) {
            assertTrue(e.status == -1,"Exit Status Incorrect");
        }
        finally
        {
            System.setSecurityManager(null);
        }
     }
}

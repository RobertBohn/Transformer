package Transformer;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import java.io.*;

public class AppTest extends TestCase {
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest(String testName) {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(AppTest.class);
    }

    /**
     * Byte Test
     */
    public void testBytes() {
        byte[] buffer = new byte[256];
        byte[] output, revert;

        for (int i = 0; i < buffer.length; i++) {
            buffer[i] = (byte) i;
        }

        try {
            App app = new App();
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            app.convert2text(new ByteArrayInputStream(buffer), out);
            output = out.toByteArray();

            app = new App();
            out = new ByteArrayOutputStream();
            app.convert2dat(new ByteArrayInputStream(output), out);
            revert = out.toByteArray();

            for (int i = 0; i < revert.length; i++) {
                assertTrue(buffer[i] == revert[i]);
            }
        } catch (IOException ex) {
            assertTrue(false);
        }
    }

    /**
     * String Test
     */
    public void testString() {
        final String input = "Hello There My Friend, What Do you know this is a very long text just to see what happens!";
        String output, revert;

        try {
            App app = new App();
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            app.convert2text(new ByteArrayInputStream(input.getBytes()), out);
            output = out.toString();

            app = new App();
            out = new ByteArrayOutputStream();
            app.convert2dat(new ByteArrayInputStream(output.getBytes()), out);
            revert = out.toString();

            assertTrue(input.equals(revert));

        } catch (IOException ex) {
            assertTrue(false);
        }
    }
}
package Transformer;

import javax.xml.bind.DatatypeConverter;
import java.io.*;

public class App {
    private long adjustment = 97;
    private static final long delta = 3;

    private static enum Direction { FORWARD(1), BACKWARD(-1);
        private final long direction;
        Direction(long direction) { this.direction = direction; }
        public long getValue() { return direction; }
    }

    /**
     * Convert input binary data into hexadecimal text.
     * @param in binary data InputStream
     * @param out text output OutputStream
     * @throws IOException
     */
     protected void convert2text(final InputStream in, final OutputStream out) throws IOException {
        byte[] buffer = new byte[1];
        while ((buffer[0] = (byte) in.read()) != -1) {
            adjust(buffer, Direction.FORWARD);
            out.write(DatatypeConverter.printHexBinary(buffer).getBytes());
        }
    }

    protected void convert2dat(final InputStream in, final OutputStream out) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String line;
        while ((line = reader.readLine()) != null) {
            byte[] buffer = DatatypeConverter.parseHexBinary(line);
            adjust(buffer, Direction.BACKWARD);
            out.write(buffer);
        }
    }

    private void adjust(final byte[] buffer, final Direction direction) {
        for (int i = 0; i < buffer.length; i++) {
            adjustment += delta;
            long value = (long)buffer[i] + (direction.getValue() * adjustment);
            while (value >= 256) value -= 256;
            while (value < 0) value += 256;
            buffer[i] = (byte)value;
        }
    }

    /**
     * Command line application to invoke transformations.
     * @param args command line parameters.
     * @throws IOException
     */
    public static void main(final String[] args) throws IOException {
        App app = new App();

        if (args.length == 1) {
            app.convert2text(new FileInputStream(args[0]), System.out);
        }

        if (args.length == 2) {
            OutputStream out = new FileOutputStream(args[1]);
            app.convert2dat(new FileInputStream(args[0]), out);
            out.close();
        }
    }
}
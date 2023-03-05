package utils;

import java.io.IOException;
import java.io.PrintStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import wse.utils.stream.EmptyOutputStream;

public class OutStream extends PrintStream {

    public void println() { }
    public void println(String s) { super.print(s); }
    public void println(boolean x) { super.print(x); }
    public void println(char x) { super.print(x); }
    public void println(int x) { super.print(x); }
    public void println(long x) { super.print(x); }
    public void println(float x) { super.print(x); }
    public void println(double x) { super.print(x); }
    public void println(char[] x) { super.print(x); }
    public void println(Object x) { super.print(x); }

    private final Logger log;
    private final Level level;

    public OutStream(Logger log, Level level) {
        super(new EmptyOutputStream());
        this.log = log;
        this.level = level;
    }

    @Override
    public void write(byte[] b) throws IOException {
        log.log(level, new String(b));
    }

    @Override
    public void write(byte[] buf, int off, int len) {
        log.log(level, new String(buf, off, len));
    }

    @Override
    public void write(int b) {
        log.log(level, String.valueOf((char) b));
    }
}

package throne.orchestration.common.util;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.Charset;

import static org.junit.Assert.*;

public class ThroneUtilTest {


    @Test
    public void read() {
        String test = "TEST  test 11 -2";
        try {
            String read = ThroneUtil.read(new ByteArrayInputStream(test.getBytes()));
            assertEquals(read, test);
        } catch (IOException e) {
            throw new AssertionError();
        }
    }
}
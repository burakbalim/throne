package throne.orchestration.common.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

public class ThroneUtil {

    public static String read(InputStream inputStream) throws IOException {
        StringBuilder lines = new StringBuilder();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, Charset.defaultCharset()));
        String line;

        while ((line = bufferedReader.readLine()) != null) {
            lines.append(line);
        }

        bufferedReader.close();
        return lines.toString();
    }

}

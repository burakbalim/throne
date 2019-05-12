package throne.orchestration.common.util;

import com.google.gson.Gson;
import throne.orchestration.common.IData;
import throne.orchestration.common.exception.OrchestractionException;

import java.io.File;
import java.io.FileInputStream;

public class ConfigurationUtil {

    public static String readFile(String path) throws OrchestractionException {
        try {
            File file = new File(path);
            FileInputStream fileInputStream = new FileInputStream(file);
            return ThroneUtil.read(fileInputStream);
        }
        catch (Exception e) {
            throw new OrchestractionException("File Read Exception ", e);
        }
    }

    public static <T> T readJson(String value, Class<T> clazz) {
        return new Gson().fromJson(value, clazz);
    }

    public static <T extends IData> String writeData(IData value) {
        return new Gson().toJson(value.getMessage());
    }
}

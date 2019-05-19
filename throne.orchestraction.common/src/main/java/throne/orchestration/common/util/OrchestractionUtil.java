package throne.orchestration.common.util;

import com.google.gson.Gson;
import throne.orchestration.common.IData;
import throne.orchestration.common.exception.OrchestractionException;

import java.io.File;
import java.io.FileInputStream;

@SuppressWarnings("unchecked")
public class OrchestractionUtil {

    private OrchestractionUtil() {

    }

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

    public static <T> T newInstanceOfClass(String className) throws OrchestractionException {
        T instance;
        try {
            instance = (T) Class.forName(className).newInstance();
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            throw new OrchestractionException("Occurred Exception while new instance of class", e);
        }

        return instance;
    }
}

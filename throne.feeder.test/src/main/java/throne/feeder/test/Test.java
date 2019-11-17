package throne.feeder.test;

import com.google.gson.Gson;
import throne.feeder.manager.FeederManager;
import throne.feeder.manager.FeederService;
import throne.orchestration.common.IData;
import throne.orchestration.common.exception.OrchestrationException;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Test {

    public static ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(10, 10, 30, TimeUnit.SECONDS, new LinkedBlockingQueue<>());

    public static void main(String[] args) throws OrchestrationException {
        FeederService service = FeederService.getInstance();
        service.init("/Users/burakbalim/codes/throne/throne.feeder.test/src/main/resources/feederManager.json");
        FeederManager feederManager = service.getFeederManager();
        feederManager.start();

        while (true) {
            threadPoolExecutor.submit(() -> feederManager.feed("demo", (IData) () -> new Gson().toJson(new  TestData("TEST"))));
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

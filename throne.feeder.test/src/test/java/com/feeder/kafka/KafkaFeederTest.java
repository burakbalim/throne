package com.feeder.kafka;

import org.junit.Test;
import throne.feeder.manager.FeederManager;
import throne.feeder.manager.ThroneFeederService;

import java.io.IOException;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

public class KafkaFeederTest {

    public ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(10, 10, 30, TimeUnit.SECONDS, new LinkedBlockingQueue<>());

    @Test
    public void feederCountTest() throws IOException {
        ThroneFeederService service = ThroneFeederService.getInstance();
        service.init("/home/burakbalim/configuration/feederManager.json");
        FeederManager feederManager = service.getFeederManager();
        assertEquals(feederManager.getFeederList().size(), 3);
        feederManager.start();

        while (true) {
            threadPoolExecutor.submit(new Runnable() {
                @Override
                public void run() {
                }
            });
        }
    }
}

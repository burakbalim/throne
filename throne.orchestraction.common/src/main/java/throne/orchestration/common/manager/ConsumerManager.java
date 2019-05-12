package throne.orchestration.common.manager;


import throne.orchestration.common.IConsumer;
import throne.orchestration.common.IData;
import throne.orchestration.common.IPlugin;
import throne.orchestration.common.exception.ConsumerException;

import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class ConsumerManager implements OrchestractionManager {

    private ThreadPoolExecutor threadPoolExecutor;
    private List<IConsumer> consumers;
    private boolean isStopSignal;
    private Thread consumerManagerThread;
    private IPlugin iPlugin;

    @Override
    public void start() {
        isStopSignal = false;
        threadPoolExecutor = new ThreadPoolExecutor(10, 10, 30, TimeUnit.SECONDS, new LinkedBlockingQueue<>());
        consumerManagerThread = new Thread(this::process, "Consumer Main Thread");
        consumerManagerThread.start();
    }

    @Override
    public void stop() {
        isStopSignal = true;
        threadPoolExecutor.shutdown();
    }

    private void process() {
        while (!isStopSignal) {
            List<IConsumer> activeConsumers = this.consumers.stream().filter(IConsumer::state).collect(Collectors.toList());
            for (IConsumer item : activeConsumers) {
                threadPoolExecutor.submit(() -> {
                    try {
                        IData consume = item.consume();
                        writeMessage(consume);
                    } catch (ConsumerException e) {
                        e.printStackTrace();
                    }
                });
            }
        }
    }

    public void setConsumers(List<IConsumer> list) {
        this.consumers = list;
    }

    private void writeMessage(IData message) {
        if (iPlugin != null) {
            iPlugin.send(message);
        }
    }

    public void setPlugin(IPlugin iPlugin) {
        this.iPlugin = iPlugin;
    }
}

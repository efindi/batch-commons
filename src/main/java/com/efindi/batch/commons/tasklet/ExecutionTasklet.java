package com.efindi.batch.commons.tasklet;

import com.efindi.batch.commons.function.VoidMethod;
import com.efindi.batch.commons.function.VoidMethodExecutionException;
import com.efindi.batch.commons.log.LogConfig;
import com.efindi.batch.commons.tasklet.model.RunnableTask;
import com.efindi.batch.commons.tasklet.model.Task;
import com.efindi.batch.commons.tasklet.model.VoidMethodTask;
import org.immutables.value.Value;
import org.slf4j.Logger;
import org.springframework.batch.core.step.tasklet.Tasklet;

import java.util.List;
import java.util.Optional;

public abstract class ExecutionTasklet<T> implements Tasklet {

    public abstract List<Task<T>> taskList();
    public abstract Optional<Logger> logger();
    public abstract Optional<LogConfig> logConfig();

    @Value.Derived
    public void run(Task<T> task) throws VoidMethodExecutionException {
        if (task instanceof RunnableTask) {
            ((Runnable) task.task()).run();
        } else if (task instanceof VoidMethodTask) {
            ((VoidMethod) task.task()).execute();
        }
    }

    @Value.Derived
    public void log(Exception e) {
        if (logConfig().isPresent()) {
            LogConfig logConfig = logConfig().get();
            if (logConfig.messageOnly()) {
                logConfig.logger().error(logConfig.logFormat(), e.getMessage());
            } else {
                logConfig.logger().error(logConfig.logFormat(), e);
            }
        } else if (logger().isPresent()){
            logger().get().error("", e);
        }
    }
}

package com.efindi.batch.commons.tasklet;

import com.efindi.batch.commons.function.Task;
import com.efindi.batch.commons.function.VoidMethod;
import com.efindi.batch.commons.log.ImmutableLogConfig;
import com.efindi.batch.commons.log.LogConfig;
import org.immutables.value.Value;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.step.tasklet.Tasklet;

import java.util.List;

public abstract class ExecutionTasklet<T> implements Tasklet {

    public abstract List<Task<T>> taskList();

    @Value.Default
    public LogConfig logConfig() {
        return ImmutableLogConfig.of("{}", true);
    }

    @Value.Derived
    void run(Task<T> task) throws RuntimeException {
        if (task.execute() instanceof Runnable) {
            ((Runnable) task.execute()).run();
        } else if (task.execute() instanceof VoidMethod) {
            ((VoidMethod) task.execute()).execute();
        }
    }

    @Value.Derived
    void log(Exception e) {
        if (logConfig().messageOnly()) {
            logger().error(logConfig().logFormat(), e.getMessage());
        } else {
            logger().error(logConfig().logFormat(), e);
        }
    }

    @Value.Derived
    private Logger logger() {
        return LoggerFactory.getLogger(this.getClass());
    }
}

package com.efindi.batch.commons.tasklet;

import com.efindi.batch.commons.tasklet.function.VoidMethod;
import com.efindi.batch.commons.tasklet.option.ILogOption;
import com.efindi.batch.commons.tasklet.task.ITask;
import org.immutables.value.Value;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public abstract class AbstractExecutionTasklet<T> {

    public abstract Optional<ILogOption> logConfig();

    @Value.Derived
    protected void run(ITask<T> ITask) throws RuntimeException {
        if (ITask.task() instanceof Runnable) {
            ((Runnable) ITask.task()).run();
        } else if (ITask.task() instanceof VoidMethod) {
            ((VoidMethod) ITask.task()).execute();
        }
    }

    @Value.Derived
    protected void log(Exception e) {
        if (logConfig().isPresent()) {
            Object obj = logConfig().get().logMessageOnly() ? e.getMessage() : e;
            logger().error(logConfig().get().format(), obj);
        } else {
            logger().error("{}", e.getMessage());
        }
    }

    @Value.Derived
    protected Logger logger() {
        return LoggerFactory.getLogger(this.getClass());
    }

}

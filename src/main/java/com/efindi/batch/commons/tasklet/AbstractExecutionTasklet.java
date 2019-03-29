package com.efindi.batch.commons.tasklet;

import com.efindi.batch.commons.tasklet.function.VoidMethod;
import com.efindi.batch.commons.tasklet.task.ITask;
import org.immutables.value.Value;

public abstract class AbstractExecutionTasklet<T> {

    @Value.Default
    protected boolean propagateThrowable() {
        return true;
    }

    @Value.Derived
    protected void run(ITask<T> ITask) throws RuntimeException {
        if (ITask.task() instanceof Runnable) {
            ((Runnable) ITask.task()).run();
        } else if (ITask.task() instanceof VoidMethod) {
            ((VoidMethod) ITask.task()).execute();
        }
    }

}

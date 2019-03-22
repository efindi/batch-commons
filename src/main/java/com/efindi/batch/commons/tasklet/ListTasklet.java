package com.efindi.batch.commons.tasklet;

import com.efindi.batch.commons.tasklet.task.ITask;
import org.springframework.batch.core.step.tasklet.Tasklet;

import java.util.List;

public interface ListTasklet<T> extends Tasklet {
    List<ITask<T>> tasks();
}

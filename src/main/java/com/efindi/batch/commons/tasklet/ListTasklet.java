package com.efindi.batch.commons.tasklet;

import com.efindi.batch.commons.tasklet.task.ITask;
import java.util.List;
import org.springframework.batch.core.step.tasklet.Tasklet;

public interface ListTasklet<T> extends Tasklet {
  List<ITask<T>> tasks();
}

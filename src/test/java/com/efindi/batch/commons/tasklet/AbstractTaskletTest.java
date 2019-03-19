package com.efindi.batch.commons.tasklet;

import com.efindi.batch.commons.function.VoidMethodExecutionException;
import com.efindi.batch.commons.tasklet.model.ImmutableRunnableTask;
import com.efindi.batch.commons.tasklet.model.ImmutableVoidMethodTask;
import com.efindi.batch.commons.tasklet.model.RunnableTask;
import com.efindi.batch.commons.tasklet.model.VoidMethodTask;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.scope.context.StepContext;

@ExtendWith(MockitoExtension.class)
public abstract class AbstractTaskletTest {

    final Logger logger = LoggerFactory.getLogger(AbstractTaskletTest.class);

    @Mock
    protected StepContribution contribution;

    @Mock
    protected ChunkContext chunkContext;

    static RunnableTask runnableTask;
    static VoidMethodTask voidMethodTask;
    static VoidMethodTask voidMethodTaskWithException;

    @BeforeAll
    static void initAll() {
        StepExecution stepExecution = Mockito.mock(StepExecution.class);
        StepContext stepContext = Mockito.mock(StepContext.class);
        ChunkContext chunkContext = Mockito.mock(ChunkContext.class);

        Mockito.when(chunkContext.getStepContext()).thenReturn(stepContext);
        Mockito.when(stepContext.getStepExecution()).thenReturn(stepExecution);

        runnableTask = ImmutableRunnableTask
                .builder()
                .task(() -> {})
                .build();

        voidMethodTask = ImmutableVoidMethodTask
                .builder()
                .task(() -> null)
                .build();

        voidMethodTaskWithException = ImmutableVoidMethodTask
                .builder()
                .task(() -> {throw new VoidMethodExecutionException("Throwing VoidMethodExecutionException");})
                .build();
    }

    abstract void taskletRunnableHasOneTask();
    abstract void taskletVoidMethodHasOneTask();
    abstract void taskletRunnableHasFiveTask();
    abstract void taskletVoidMethodHasFiveTask();
    abstract void taskletVoidMethodWillThrowVoidMethodExecutionException();
    abstract void taskletShouldRunSuccessfully();

}

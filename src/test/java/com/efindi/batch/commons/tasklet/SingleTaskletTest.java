package com.efindi.batch.commons.tasklet;

import com.efindi.batch.commons.tasklet.function.VoidMethod;
import com.efindi.batch.commons.tasklet.function.VoidMethodExecutionException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SingleTaskletTest extends AbstractTaskletTest {

    @Test
    @Override
    void runnableTaskletShouldRunSuccessfully() {
        SingleTasklet<VoidMethod> tasklet = SingleTasklet.of(voidMethodTask);
        assertAll(() -> tasklet.execute(contribution, chunkContext));
    }

    @Test
    @Override
    void voidMethodTaskletShouldRunSuccessfully() {
        SingleTasklet<Runnable> tasklet = SingleTasklet.of(runnableTask);
        assertAll(() -> tasklet.execute(contribution, chunkContext));
    }

    @Test
    @Override
    void voidMethodTaskletWillThrowVoidMethodExecutionException() {
        SingleTasklet<VoidMethod> tasklet = SingleTasklet
                .<VoidMethod>builder()
                .task(voidMethodTaskWithException)
                .build();
        assertThrows(VoidMethodExecutionException.class, () -> tasklet.execute(contribution, chunkContext));
    }

}

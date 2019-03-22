package com.efindi.batch.commons.tasklet;

public abstract class AbstractListTaskletTest extends AbstractTaskletTest {
    abstract void runnableTaskletHasOneTask();
    abstract void runnableTaskletHasFiveTask();
    abstract void voidMethodTaskletHasOneTask();
    abstract void voidMethodTaskletHasFiveTask();
}

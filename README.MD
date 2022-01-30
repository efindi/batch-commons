# Batch Commons
[![Build Status](https://app.travis-ci.com/efindi/batch-commons.svg?branch=master)](https://app.travis-ci.com/efindi/batch-commons)
![GitHub](https://img.shields.io/github/license/efindi/batch-commons)

#### Maven
```xml
<dependency>
  <groupId>com.efindi</groupId>
  <artifactId>batch-commons</artifactId>
  <version>0.0.4</version>
</dependency>
```
#### Gradle
```groovy
implementation 'com.efindi:batch-commons:0.0.4'
```
#### Features:
* Tasklet enhancements to improve task chaining
* ListReader and ListWriter

##### Task
```java
  Task<Runnable> runnableTask = Task.of(() -> {}); // Any Runnable method
  Task<VoidMethod> voidMethodTask = Task.of((stepContribution, chunkContext) -> {}) // Any Void method

  public Task<VoidMethod> voidMethodTask() {
    return Task.of((stepContribution, chunkContext) -> {
      try {
        doSomething();
      } catch (Exception e) {
        log.error("Log your error", e);
        throw new VoidMethodExecutionException(e); // re-throw it using the wrapper exception if needed
      }
    });
  }

```

##### Tasklet
SingleTasklet - A tasklet that executes one task.
```java
// Builder
SingleTasklet
    .<VoidMethod>builder()
    .propagateThrowable(true) // optional, default to be true
    .task(voidMethodTask) // Add a task.
    .build();

// Shorthand, using default configuration.
SingleTasklet.of(voidMethodTask);
```

SequentialTasklet - A tasklet that executes each task from a list with a pause (default to 1000ms).
```java
// Builder
SequentialTasklet
    .<VoidMethod>builder()
    .propagateThrowable(true) // optional, default to be true
    .pause(1000) // optional, default to 1000ms
    .addTasks(voidMethodTask) // Add arbitrary tasks...
    .addAllTaskList(List.of(voidMethodTask)) // Add a list of tasks
    .build();

// Shorthand, using default configuration.
SequentialTasklet.of(List.of(voidMethodTask));
```

ParallelStreamTasklet - A tasklet that executes all tasks from a list in parallel.
```java
// Builder
ParallelStreamTasklet
    .<VoidMethod>builder()
    .propagateThrowable(true) // optional, default to be true
    .addTasks(voidMethodTask) // Add arbitrary tasks...
    .addAllTaskList(List.of(voidMethodTask)) // Add a list of tasks
    .build();

// Shorthand, using default configuration.
ParallelStreamTasklet.of(List.of(voidMethodTask));
```
##### ListReader and ListWriter
Allow reading and writing List with ItemStream support.
* ItemReader takes an ItemReader delegate and a chunk size.
    * The step's chunk size becomes the multiplier of the ListReader's chunk size. 
* ListWriter takes an ItemWriter delegate. 
```java
  @Bean
  public Step step1() {
    return stepBuilderFactory
        .get("step1")
        .<List<Integer>, List<Integer>>chunk(1)
        .reader(new ListReader<>(new IntegerItemReader(), 10))
        .writer(new ListWriter<>(new IntegerItemWriter()))
        .build();
  }

```

### Open-source dependencies:
* [spring-batch](https://spring.io/projects/spring-batch)
* [immutables](http://immutables.github.io/)
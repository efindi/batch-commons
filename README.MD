# Efindi Batch Commons
[![Codeship Status for efindi/batch-commons](https://app.codeship.com/projects/ea3b8a50-2991-0137-a5f8-1edb361fe982/status?branch=master)](https://app.codeship.com/projects/331016)

Batch Commons for Spring Batch 4.1.x

#### Maven
```xml
<dependency>
  <groupId>com.efindi</groupId>
  <artifactId>batch-commons</artifactId>
  <version>0.0.2-RELEASE</version>
</dependency>
```
#### Gradle
```groovy
implementation 'com.efindi:batch-commons:0.0.2-RELEASE'
```
#### Features:
Tasklet enhancement to improve task chaining

##### Task
```java
Task<Runnable> runnableTask = Task.of(() -> {}); // Any Runnable method
Task<VoidMethod> voidMethodTask = Task.of(() -> null) // Any Void method

public VoidMethod doSomethingVoidMethod() {
    return () -> {
        try {
            doSomething()
        } catch (Exception e) {
            log.error("Log your error", e);
            throw new VoidMethodExecutionException(e); // re-throw it using the wrapper exception if needed
        }
        return null;
    };
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

## Open-source dependencies:
* [spring-batch] - Spring Batch is a framework for writing offline and batch applications using Spring and Java http://projects.spring.io/spring-batch/
* [immutables] - Annotation processor to create immutable objects and builders. Feels like Guava's immutable collections but for regular value objects. JSON, Jackson, Gson, JAX-RS integrations included http://immutables.org
    
License
----
The Apache License, Version 2.0

https://www.apache.org/licenses/LICENSE-2.0

   [spring-batch]: <https://github.com/spring-projects/spring-batch/tree/4.1.x>
   [immutables]: <https://github.com/immutables/immutables/tree/immutables-2.7.4>

# Efindi Batch Commons
[![Codeship Status for efindi/batch-commons](https://app.codeship.com/projects/ea3b8a50-2991-0137-a5f8-1edb361fe982/status?branch=master)](https://app.codeship.com/projects/331016)

Batch Commons for Spring Batch 4.1.x

#### Maven
```xml
<dependency>
  <groupId>com.efindi</groupId>
  <artifactId>batch-commons</artifactId>
  <version>0.0.1-RELEASE</version>
</dependency>
```
#### Gradle
```groovy
implementation 'com.efindi:batch-commons:0.0.1-RELEASE'
```
#### Features:
Tasklet enhancement to improve task chaining

##### Task
```java
Task<Runnable> runnableTask = ImmutableTask.of(() -> {}); // Any Runnable method
Task<VoidMethod> voidMethodTask = ImmutableTask.of(() -> null) // Any Void method
```

##### Tasklet
SequentialExecutionTasklet - A tasklet that executes each task from a list with a pause (default to 1000ms).
```java
ExecutionTasklet<VoidMethod> voidMethodExecutionTasklet = ImmutableSequentialExecutionTasklet
                .<VoidMethod>builder()
                .logConfig(ImmutableLogConfig.of(logger, "Customized logFormat: {}", true)) // optional
                .pause(1000) // optional, default to 1000ms
                .addTaskList(voidMethodTask, voidMethodTask) // Add arbitrary tasks...
                .addAllTaskList(List.of(voidMethodTask, voidMethodTask)) // Add a list of tasks
                .build();
```

ParallelStreamExecutionTasklet - A tasklet that executes all tasks from a list in parallel.
```java
ExecutionTasklet<VoidMethod> voidMethodExecutionTasklet = ImmutableParallelStreamExecutionTasklet
                .<VoidMethod>builder()
                .logConfig(ImmutableLogConfig.of(logger, "Customized logFormat: {}", true)) // optional
                .addTaskList(voidMethodTask, voidMethodTask) // Add arbitrary tasks...
                .addAllTaskList(List.of(voidMethodTask, voidMethodTask)) // Add a list of tasks
                .build();
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

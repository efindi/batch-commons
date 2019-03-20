package com.efindi.batch.commons.log;

import org.immutables.value.Value;

@Value.Immutable(builder = false)
@Value.Style(allParameters = true)
public interface LogConfig {
    String logFormat();
    boolean messageOnly();
}

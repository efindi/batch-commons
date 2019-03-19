package com.efindi.batch.commons.log;

import org.immutables.value.Value;
import org.slf4j.Logger;

@Value.Immutable(builder = false)
@Value.Style(allParameters = true)
public interface LogConfig {
    Logger logger();
    String logFormat();
    boolean messageOnly();
}

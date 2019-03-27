package com.efindi.batch.commons.tasklet.option;

import com.efindi.batch.commons.style.OfStyle;
import org.immutables.value.Value;

@Value.Immutable
@OfStyle
public interface ILogOption {
    String format();
    boolean logMessageOnly();
}

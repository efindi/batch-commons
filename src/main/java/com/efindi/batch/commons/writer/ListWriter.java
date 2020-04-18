package com.efindi.batch.commons.writer;

import static java.util.stream.Collectors.toList;

import java.util.Collection;
import java.util.List;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.ItemStreamWriter;
import org.springframework.batch.item.ItemWriter;

public class ListWriter<T> implements ItemStreamWriter<List<T>> {

  private final ItemWriter<T> delegate;

  public ListWriter(ItemWriter<T> delegate) {
    this.delegate = delegate;
  }

  @Override
  public void write(List<? extends List<T>> items) throws Exception {
    delegate.write(items.stream().flatMap(Collection::stream).collect(toList()));
  }

  @Override
  public void open(ExecutionContext executionContext) throws ItemStreamException {
    if (delegate instanceof ItemStreamWriter) {
      ((ItemStreamWriter<T>) delegate).open(executionContext);
    }
  }

  @Override
  public void update(ExecutionContext executionContext) throws ItemStreamException {
    if (delegate instanceof ItemStreamWriter) {
      ((ItemStreamWriter<T>) delegate).update(executionContext);
    }
  }

  @Override
  public void close() throws ItemStreamException {
    if (delegate instanceof ItemStreamWriter) {
      ((ItemStreamWriter<T>) delegate).close();
    }
  }
}

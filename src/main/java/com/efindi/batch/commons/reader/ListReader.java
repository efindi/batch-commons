package com.efindi.batch.commons.reader;

import static java.util.Objects.isNull;

import java.util.ArrayList;
import java.util.List;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.batch.item.ItemStreamWriter;

public class ListReader<T> implements ItemStreamReader<List<T>> {

  protected final ItemReader<T> delegate;
  protected final int chunkSize;

  public ListReader(ItemReader<T> delegate, int chunkSize) {
    this.delegate = delegate;
    this.chunkSize = chunkSize;
  }

  @Override
  public List<T> read() throws Exception {
    final List<T> objectList = new ArrayList<>();
    T object;
    do {
      object = delegate.read();
      if (isNull(object)) break;
      objectList.add(object);
    } while (objectList.size() < chunkSize);
    return objectList.isEmpty() ? null : objectList;
  }

  @Override
  public void open(ExecutionContext executionContext) throws ItemStreamException {
    if (delegate instanceof ItemStreamWriter) {
      ((ItemStreamReader<T>) delegate).open(executionContext);
    }
  }

  @Override
  public void update(ExecutionContext executionContext) throws ItemStreamException {
    if (delegate instanceof ItemStreamWriter) {
      ((ItemStreamReader<T>) delegate).update(executionContext);
    }
  }

  @Override
  public void close() throws ItemStreamException {
    if (delegate instanceof ItemStreamWriter) {
      ((ItemStreamReader<T>) delegate).close();
    }
  }
}

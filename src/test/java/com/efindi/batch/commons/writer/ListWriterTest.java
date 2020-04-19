package com.efindi.batch.commons.writer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.batch.item.ItemWriter;

public class ListWriterTest {

  private TestItemWriter<Object> itemWriter;

  @BeforeEach
  void each() {
    itemWriter = new TestItemWriter<>();
  }

  @Test
  void listWriterTest() throws Exception {
    List<List<Object>> items =
        new ArrayList<List<Object>>() {
          {
            add(Collections.singletonList(new Object()));
            add(Collections.singletonList(new Object()));
          }
        };
    ListWriter<Object> listWriter = new ListWriter<>(itemWriter);
    Assertions.assertAll(() -> listWriter.write(items));
    Assertions.assertEquals(2, itemWriter.getItems().size());
    for (Object item : itemWriter.getItems()) {
      Assertions.assertFalse(item instanceof List);
    }
  }

  static class TestItemWriter<T> implements ItemWriter<T> {
    private List<? extends T> items;

    @Override
    public void write(List<? extends T> items) throws Exception {
      this.items = items;
    }

    public List<? extends T> getItems() {
      return items;
    }

    public void setItems(List<? extends T> items) {
      this.items = items;
    }
  }
}

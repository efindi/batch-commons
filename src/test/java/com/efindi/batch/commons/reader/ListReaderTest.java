package com.efindi.batch.commons.reader;

import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.batch.item.ItemReader;

public class ListReaderTest {

  private static final int MAX_OBJECT_TO_READ = 10;

  private ItemReader<Object> itemReader;

  @BeforeEach
  void each() {
    itemReader =
        new ItemReader<Object>() {
          private int count = 0;

          @Override
          public Object read() {
            return (count++ < MAX_OBJECT_TO_READ) ? new Object() : null;
          }
        };
  }

  @Test
  void listReaderTest() throws Exception {
    ListReader<Object> listReader = new ListReader<>(itemReader, MAX_OBJECT_TO_READ);
    List<Object> objectList = listReader.read();
    Assertions.assertNotNull(objectList);
    Assertions.assertFalse(objectList.isEmpty());
    Assertions.assertEquals(objectList.size(), MAX_OBJECT_TO_READ);
  }
}

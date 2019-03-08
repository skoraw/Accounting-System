package pl.coderstrust;

import java.util.ArrayList;
import java.util.List;

public class ChristmasTree {

  private static final String SPACE_CHAR = " ";
  private static final String ASTERISK_CHAR = "*";

  public static void main(String[] args) {
    for (int i = 0; i <= 10; i++) {
      System.out.println("i = " + i + " : " + christmasTree(i));
    }
  }

  public static List<String> christmasTree(int size) {
    if (size <= 2) {
      List<String> strings = new ArrayList<>();
      return strings;
    }
    List<String> christmasTreeList = new ArrayList<String>();
    StringBuilder stringBuilder;
    String line;
    for (int i = 0; i < size; i++) {
      stringBuilder = new StringBuilder();
      for (int j = 0; j < size - (i + 1); j++) {
        stringBuilder.append(SPACE_CHAR);
      }
      for (int k = 0; k < 2 * i + 1; k++) {
        stringBuilder.append(ASTERISK_CHAR);
      }
      line = stringBuilder.toString();
      christmasTreeList.add(line);
    }
    stringBuilder = new StringBuilder();
    for (int i = 0; i < (2 * size - 1) / 2 - 1; i++) {
      stringBuilder.append(SPACE_CHAR);
    }
    stringBuilder.append(ASTERISK_CHAR + ASTERISK_CHAR);
    line = stringBuilder.toString();
    christmasTreeList.add(line);
    return christmasTreeList;
  }
}

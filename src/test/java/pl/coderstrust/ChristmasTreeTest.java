package pl.coderstrust;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class ChristmasTreeTest {

  @Parameter
  public int actual;
  @Parameter(1)
  public List<String> expected;

  @Parameters
  public static Collection<Object[]> data() {
    Object[][] data = new Object[][]{{-1, List.of()},
        {2, List.of()},
        {3, List.of(
            "  *",
            " ***",
            "*****",
            " **")},
        {4, List.of(
            "   *",
            "  ***",
            " *****",
            "*******",
            "  **")},
        {10, List.of(
            "         *",
            "        ***",
            "       *****",
            "      *******",
            "     *********",
            "    ***********",
            "   *************",
            "  ***************",
            " *****************",
            "*******************",
            "        **")}
    };
    return Arrays.asList(data);
  }

  @Test
  public void test() {
    assertEquals(expected, ChristmasTree.christmasTree(actual));
  }
}
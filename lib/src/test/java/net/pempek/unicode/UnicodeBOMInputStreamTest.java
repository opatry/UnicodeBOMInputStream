// (‑●‑●)> released under the WTFPL v2 license, by Gregory Pakosz (@gpakosz)

package net.pempek.unicode;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import net.pempek.unicode.UnicodeBOMInputStream.BOM;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.fail;

/**
 * The <code>UnicodeBOMInputStreamTest</code> class tests the
 * <code>UnicodeBOMInputStream</code> class.
 *
 * @see net.pempek.unicode.UnicodeBOMInputStream
 *
 * @author Gregory Pakosz
 * @version 1.0
 */
@RunWith(JUnit4.class)
public class UnicodeBOMInputStreamTest
{
  @Test
  public final void test_UnicodeBOMInputStream_InputStream() throws IOException
  {
    try
    {
      new UnicodeBOMInputStream(null);
      fail("should have raised a NullPointerException exception");
    }
    catch(final NullPointerException e)
    {
      // success
    }
  }

  @Test
  public void test_getBOM() throws IOException
  {
    int i;
    int value;

    try (UnicodeBOMInputStream content = new UnicodeBOMInputStream(new ByteArrayInputStream(CONTENT))) {

      assertEquals(BOM.NONE, content.getBOM());

      i = 0;
      while ((value = content.read()) != -1)
        assertEquals(value, CONTENT[i++]);
    }

    try (ByteArrayInputStream bais = new ByteArrayInputStream(UTF8_BOM_CONTENT);
         UnicodeBOMInputStream utf8BOM = new UnicodeBOMInputStream(bais)) {

      assertEquals(BOM.UTF_8, utf8BOM.getBOM());

      utf8BOM.skipBOM();

      i = 0;
      while ((value = utf8BOM.read()) != -1)
        assertEquals(value, CONTENT[i++]);
    }

    try (ByteArrayInputStream bais = new ByteArrayInputStream(UTF16_BE_BOM_CONTENT);
         UnicodeBOMInputStream utf16BEBOM = new UnicodeBOMInputStream(bais)) {

      assertEquals(BOM.UTF_16_BE, utf16BEBOM.getBOM());

      utf16BEBOM.skipBOM();

      i = 0;
      while ((value = utf16BEBOM.read()) != -1)
        assertEquals(value, CONTENT[i++]);
    }

    try (ByteArrayInputStream bais = new ByteArrayInputStream(UTF16_LE_BOM_CONTENT);
         UnicodeBOMInputStream utf16LEBOM = new UnicodeBOMInputStream(bais)) {

      assertEquals(BOM.UTF_16_LE, utf16LEBOM.getBOM());

      utf16LEBOM.skipBOM();

      i = 0;
      while ((value = utf16LEBOM.read()) != -1)
        assertEquals(value, CONTENT[i++]);
    }

    try (ByteArrayInputStream bais = new ByteArrayInputStream(UTF32_BE_BOM_CONTENT);
         UnicodeBOMInputStream utf32BEBOM = new UnicodeBOMInputStream(bais)) {

      assertEquals(BOM.UTF_32_BE, utf32BEBOM.getBOM());

      utf32BEBOM.skipBOM();

      i = 0;
      while ((value = utf32BEBOM.read()) != -1)
        assertEquals(value, CONTENT[i++]);
    }

    try (ByteArrayInputStream bais = new ByteArrayInputStream(UTF32_LE_BOM_CONTENT);
         UnicodeBOMInputStream utf32LEBOM = new UnicodeBOMInputStream(bais)) {

      assertEquals(BOM.UTF_32_LE, utf32LEBOM.getBOM());

      utf32LEBOM.skipBOM();

      i = 0;
      while ((value = utf32LEBOM.read()) != -1)
        assertEquals(value, CONTENT[i++]);
    }
  }

  @Test
  public void test_JavaBehaviour() throws IOException
  {
    try (InputStream utf8BOM = new ByteArrayInputStream(UTF8_BOM_CONTENT);
         InputStreamReader reader = new InputStreamReader(utf8BOM, StandardCharsets.UTF_8)) {

      assertEquals(0xFEFF, reader.read()); // http://www.fileformat.info/info/unicode/char/feff/

      for (int i = 0; i < UTF8_BOM_CONTENT.length - 3; ++i)
        assertEquals(UTF8_BOM_CONTENT[i + 3], reader.read());

      assertEquals(-1, reader.read());
    }
  }

  private static final byte[] CONTENT = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
  private static final byte[] UTF8_BOM_CONTENT;
  private static final byte[] UTF16_BE_BOM_CONTENT;
  private static final byte[] UTF16_LE_BOM_CONTENT;
  private static final byte[] UTF32_BE_BOM_CONTENT;
  private static final byte[] UTF32_LE_BOM_CONTENT;

  static
  {
    UTF8_BOM_CONTENT = new byte[BOM.UTF_8.getBytes().length + CONTENT.length];

    System.arraycopy(BOM.UTF_8.getBytes(), 0, UTF8_BOM_CONTENT, 0, BOM.UTF_8.getBytes().length);
    System.arraycopy(CONTENT, 0, UTF8_BOM_CONTENT, BOM.UTF_8.getBytes().length, CONTENT.length);

    UTF16_BE_BOM_CONTENT = new byte[BOM.UTF_16_BE.getBytes().length + CONTENT.length];

    System.arraycopy(BOM.UTF_16_BE.getBytes(), 0, UTF16_BE_BOM_CONTENT, 0, BOM.UTF_16_BE.getBytes().length);
    System.arraycopy(CONTENT, 0, UTF16_BE_BOM_CONTENT, BOM.UTF_16_BE.getBytes().length, CONTENT.length);

    UTF16_LE_BOM_CONTENT = new byte[BOM.UTF_16_LE.getBytes().length + CONTENT.length];

    System.arraycopy(BOM.UTF_16_LE.getBytes(), 0, UTF16_LE_BOM_CONTENT, 0, BOM.UTF_16_LE.getBytes().length);
    System.arraycopy(CONTENT, 0, UTF16_LE_BOM_CONTENT, BOM.UTF_16_LE.getBytes().length, CONTENT.length);

    UTF32_BE_BOM_CONTENT = new byte[BOM.UTF_32_BE.getBytes().length + CONTENT.length];

    System.arraycopy(BOM.UTF_32_BE.getBytes(), 0, UTF32_BE_BOM_CONTENT, 0, BOM.UTF_32_BE.getBytes().length);
    System.arraycopy(CONTENT, 0, UTF32_BE_BOM_CONTENT, BOM.UTF_32_BE.getBytes().length, CONTENT.length);

    UTF32_LE_BOM_CONTENT = new byte[BOM.UTF_32_LE.getBytes().length + CONTENT.length];

    System.arraycopy(BOM.UTF_32_LE.getBytes(), 0, UTF32_LE_BOM_CONTENT, 0, BOM.UTF_32_LE.getBytes().length);
    System.arraycopy(CONTENT, 0, UTF32_LE_BOM_CONTENT, BOM.UTF_32_LE.getBytes().length, CONTENT.length);
  }
} // UnicodeBOMInputStreamTest

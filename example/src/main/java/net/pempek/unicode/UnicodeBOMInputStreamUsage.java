// (‑●‑●)> released under the WTFPL v2 license, by Gregory Pakosz (@gpakosz)

package net.pempek.unicode;

import java.io.*;

public final class UnicodeBOMInputStreamUsage {
  public static void main(final String[] args) throws Exception {
    // here we're setting the output encoding to US-ASCII on purpose
    // would you choose UTF-8, Java would read it with U+FEFF, see
    // http://www.fileformat.info/info/unicode/char/feff/
    //
    // with US-ASCII, the BOM is replaced by ?
    PrintStream out = new PrintStream(System.out, true, "US-ASCII");

    try (InputStream is = UnicodeBOMInputStreamUsage.class.getResourceAsStream("/offending_bom.txt");
         UnicodeBOMInputStream ubis = new UnicodeBOMInputStream(is);
         InputStreamReader isr = new InputStreamReader(ubis);
         BufferedReader br = new BufferedReader(isr)) {

      out.println("Detected BOM: " + ubis.getBOM());

      out.print("Reading the content of the file without skipping the BOM: ");

      out.println(br.readLine());
    }

    try (InputStream is = UnicodeBOMInputStreamUsage.class.getResourceAsStream("/offending_bom.txt");
         UnicodeBOMInputStream ubis = new UnicodeBOMInputStream(is);
         InputStreamReader isr = new InputStreamReader(ubis);
         BufferedReader br = new BufferedReader(isr)) {
      ubis.skipBOM();

      out.print("Reading the content of the file after skipping the BOM: ");

      out.println(br.readLine());
    }
  }

} // UnicodeBOMInputStreamUsage

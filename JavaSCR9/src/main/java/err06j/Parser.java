//The MIT License (MIT)
//
//Copyright (c) 2020 Robert C. Seacord
//
//Permission is hereby granted, free of charge, to any person obtaining a copy
//of this software and associated documentation files (the "Software"), to deal
//in the Software without restriction, including without limitation the rights
//to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
//copies of the Software, and to permit persons to whom the Software is
//furnished to do so, subject to the following conditions:
//
//The above copyright notice and this permission notice shall be included in all
//copies or substantial portions of the Software.
//
//THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
//IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
//FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
//AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
//LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
//OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
//SOFTWARE.

package err06j;

import java.awt.AWTException;
import java.io.IOException;

class Parser<T extends Exception> {
  @SuppressWarnings("unchecked")
  public void parse(String s) throws T {
      throw (T) new AWTException(s);
  }
  
  public static void main(String[] args) {
    String s = "my new string";
    Parser<IOException> p = new Parser<>();
    try {
      p.parse(s);
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  } // end main()
} // end class Parser
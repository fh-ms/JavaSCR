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

package ser10j;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

import one.microstream.persistence.binary.types.Binary;
import one.microstream.persistence.binary.types.ChunksWrapper;
import serial.MicroStreamSerializer;

public enum EnumSingleton {
  INSTANCE;
  private int value;
  public int getValue() {
    return this.value;
  }
  public void setValue(final int value) {
    this.value = value;
  }
  
  private static MicroStreamSerializer serializer;
  static
  {
	  serializer = MicroStreamSerializer.New();
	  serializer.serialize(EnumSingleton.INSTANCE);
  }
  
  private static void serialize(final Object o) throws IOException {
    final Binary data = serializer.serialize(o);
    try(FileOutputStream fout = new FileOutputStream("tempdata.ser"))
    {
    	fout.getChannel().write(data.buffers());
    }
  }
  
  public static EnumSingleton deserialize() throws IOException, ClassNotFoundException
  {
	  final File file = new File("tempdata.ser");
	  final ByteBuffer buffer = ByteBuffer.allocateDirect((int)file.length());
	  try(FileInputStream fin = new FileInputStream(file))
	  {
		  fin.getChannel().read(buffer);
		  buffer.rewind();
	  }
	  return (EnumSingleton)serializer.deserialize(ChunksWrapper.New(buffer));
  }

  public static void main(final String[] args) throws IOException {
    EnumSingleton.INSTANCE.setValue(42);
    System.out.println("EnumSingleton.INSTANCE = " + EnumSingleton.INSTANCE.getValue());
    serialize(EnumSingleton.INSTANCE);
  }
}

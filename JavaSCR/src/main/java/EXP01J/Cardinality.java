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

package EXP01J;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

class Cardinality {

	// returns the number of occurrences of Object obj in Collection col.
	private static int cardinality(Object obj, final Collection<?> col) {
		int count = 0;
		for (Object elt : col) {
			// Because membership in the collection is checked using
			// the expression obj.equals(elt), a null pointer dereference
			// is guaranteed whenever obj is null and elt is not null.
			if ((null == obj && null == elt) || obj.equals(elt)) {
				count++;
			}
		}
		return count;
	}

	public static void main(String[] args) {
		// a non-null List of nullable Strings
		List<String> myList = new ArrayList<>();
		myList.add("Java"); 
		myList.add("C"); 
		myList.add(null);
		myList.add("C++"); 
		System.out.println("Cardinality of myList: " + cardinality("C", myList));  
		System.out.println("Cardinality of myList: " + cardinality(null, myList)); 
	}

}

















// returns the number of occurrences of Object obj in Collection col.
/*
import java.util.Objects;
public static int cardinality(@Nullable Object obj, final Collection<?> col) {
	int count = 0;
	if (col == null) {
		return count;
	}
	Iterator<?> it = col.iterator();
	while (it.hasNext()) {
		Object elt = it.next();
		if (Objects.equals(obj, elt))  {
			count++;
		}
	}
	return count;
}
*/
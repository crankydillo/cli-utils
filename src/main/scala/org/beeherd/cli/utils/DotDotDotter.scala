/**
* Copyright 2010 Samuel Cox
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing,
* software distributed under the License is distributed on an
* "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
* KIND, either express or implied. See the License for the
* specific language governing permissions and limitations
* under the License.
*/

package org.beeherd.cli.utils

import java.util.concurrent._

/**
* This class takes a funtion and executes it.  While the function executes, it
* will print a message with repeating periods to standard out.
*/
class DotDotDotter[T](
    fn: () => T
    , msg: String
    , numDots: Int
    , delay: Int
  ) {

  def this(fn: () => T) = this(fn, "Executing", 3, 500);

  private val blockingQueue = new ArrayBlockingQueue[T](1);
 
  /**
  * Execute the member function and return its value.  While the function is
  * executing in a separate thread, print a message with repeating dots.
  */
  def execute(): T = {

    def backspace(spaces: Int) = (1 to spaces).foreach {i => print("\b \b")}

    val runner = new Runner(fn);
    new Thread(runner).start();
    
    var result = blockingQueue.poll();
    print(msg);
    var dotCtr = 1;
    while (result == null) {
      if (dotCtr > numDots) {
        backspace(2 * numDots);
        dotCtr = 1;
      }
      print(" .");
      dotCtr = dotCtr + 1;
      Thread.sleep(delay);
      result = blockingQueue.poll();
    }
    result
  }

  class Runner(fn: () => T) extends Runnable {
    def run(): Unit = blockingQueue.put(fn())
  }
}

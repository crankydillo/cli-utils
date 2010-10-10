/**
* Licensed to the Apache Software Foundation (ASF) under one
* or more contributor license agreements. See the NOTICE file
* distributed with this work for additional information
* regarding copyright ownership. The ASF licenses this file
* to you under the Apache License, Version 2.0 (the
* "License"); you may not use this file except in compliance
* with the License. You may obtain a copy of the License at
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

import org.specs._
import org.specs.runner.JUnit4

/**
* @author scox
*/
class TablizerSpecTest extends JUnit4(TablizerSpec)
object TablizerSpec extends Specification {
  private val pad = "  "
  private val tablizer = new Tablizer(pad);

  private val headers = List("col1", "column2");
  private val data = 
        List(
            List("11", "12")
          , List("21", "22")
        )

  "A Tablizer" should {
    "create column headers if specified" in {
      val rows = tablizer.tablize(List(), headers);
      rows must haveSize(2);
      rows(0) must containInOrder(List("col1", pad + "column2"));
      rows(1) must containInOrder(List("----", pad + "-------"));
    }

    "create right-aligned data with padding" in {
      val rows = tablizer.tablize(data);
      rows must haveSize(2);
      rows(0) must containInOrder(List("11", pad + "12"));
      rows(1) must containInOrder(List("21", pad + "22"));
    }

    "create right-aligned headers and data" in {
      val rows = tablizer.tablize(data, headers);
      rows must haveSize(4);
      rows(0) must containInOrder(List("col1", pad + "column2"));
      rows(1) must containInOrder(List("----", pad + "-------"));
      rows(2) must containInOrder(List("  11", pad + "     12"));
      rows(3) must containInOrder(List("  21", pad + "     22"));
    }

    "match the underline to the longest column element regardless of whether or not that element in the header" in {
      val headers = List("a", "b");
      val data = List(List("aa", "bbb"));
      val rows = tablizer.tablize(data, headers);
      rows must haveSize(3);
      rows(1) must containInOrder(List("--", pad + "---"))
    }

    /*
    "print a table" in {
      val expected = """
col1  column2
----  -------
  11       12
  21       22""".trim
      tablizer.tablizeToStr(data, headers) must beEqual(expected);

    }
    */
  }
}

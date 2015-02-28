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

import scala.collection.mutable.ListBuffer

/** 
* This class is responsible for taking data and creating rows of Strings that
* can be used to pretty print tables on the console.
*
* @author scox
*/
class Tablizer(val pad: String) {
  type Row = List[String]

  def tablize(dataRows: List[Row], headers: Row = List()): List[Row] = {
    val rows = 
      if (headers.isEmpty)
        dataRows
      else
        headers +: dataRows

    val maxLens = findMaxLengths(rows);

    val rowsWithLengths = rows.map {_ zip maxLens}

    val lst = rowsWithLengths.map {r => 
      r.zipWithIndex.map {f => 
        val str = String.format("%" + f._1._2 +"s", f._1._1);
        if (f._2 > 0)
          pad + str
        else
          str
      }
    }

    if (headers.size == 0)
      return lst;

    val formattedHeaders = lst.head
    val underlines = maxLens.zipWithIndex.map {l => 
      if (l._2 > 0)
        pad + "-" * l._1
      else
        "-" * l._1
    }
    formattedHeaders +: underlines +: lst.drop(1)
  }

  def tablizeToStr(dataRows: List[Row], headers: Row = List()) =
    tablize(dataRows, headers).map {_.mkString}.mkString("\n")

  private def findMaxLengths(rows: List[Row]): List[Int] = {
    val start = List.tabulate(rows.head.size) {n => 0} // start with a bunch of 0s

    // TODO: Avoid index access to List..
    rows.foldLeft (start) {(x, y) => 
      y.zipWithIndex.map {p => Math.max(p._1.size, x(p._2))}
    }
  }
}

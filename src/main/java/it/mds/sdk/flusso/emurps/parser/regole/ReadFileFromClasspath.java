/* SPDX-License-Identifier: BSD-3-Clause */

package it.mds.sdk.flusso.emurps.parser.regole;

import java.io.File;
import java.net.URL;

public class ReadFileFromClasspath 
{
   
  public File getFile(String fileName)
  {
    ClassLoader classLoader = getClass().getClassLoader();
        URL resource = classLoader.getResource(fileName);
         
        if (resource == null) {
            throw new IllegalArgumentException("file is not found!");
        } else {
            return new File(resource.getFile());
        }
  }
}

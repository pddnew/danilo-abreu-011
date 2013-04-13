/**
 * 
 */
package com.classsocialnet.util;

import java.io.File;
import java.io.IOException;

/**
 * Generic 
 */
public interface GenericFileIO<T> {

	
	public T[] readLines(File file) throws IOException;
}
package com.mobiquityinc.packer.io.exception;

import com.mobiquityinc.exception.APIException;

/**
 * Special Exception for Input file errors
 * @author ricardo.cortes
 *
 */
public class FilePackageReaderException extends APIException {

	private static final long serialVersionUID = 4790323336250866668L;

	public FilePackageReaderException(String message, Exception e) {
		super(message, e);
	}

	public FilePackageReaderException(String message) {
		super(message);
	}

}

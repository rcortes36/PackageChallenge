package com.mobiquityinc.packer.reader;

import com.mobiquityinc.exception.APIException;

public class FilePackageReaderException extends APIException {

	private static final long serialVersionUID = 4790323336250866668L;

	public FilePackageReaderException(String message, Exception e) {
		super(message, e);
	}

	public FilePackageReaderException(String message) {
		super(message);
	}

}

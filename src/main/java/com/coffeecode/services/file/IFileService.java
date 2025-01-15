package com.coffeecode.services.file;

import java.io.File;

import com.coffeecode.exception.DictionaryException;

public interface IFileService {

    File getDefaultDictionaryFile() throws DictionaryException;
    File getFile(String filePath) throws DictionaryException;

}

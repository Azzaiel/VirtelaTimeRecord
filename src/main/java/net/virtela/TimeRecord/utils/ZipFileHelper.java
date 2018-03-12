package net.virtela.TimeRecord.utils;


import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

public class ZipFileHelper {
	
	private ZipFileHelper() {
		
	}

	/**
	 * Compress a file using zip4j.
	 * 
	 * @param sourcePath Path to the file/directory to compress
	 * @return path to the compressed file
	 */
	public static Path zipFile(Path sourcePath) {
		final File fileToZip = sourcePath.toFile();
		final String zipFilePath = fileToZip.getAbsolutePath();

		final StringBuilder zipPath = new StringBuilder();
		zipPath.append(zipFilePath)
		       .append(Constants.DOT)
		       .append(Constants.FILE_TYPE_ZIP);
		
		try {
			final ZipFile zipFile = new ZipFile(zipPath.toString());
			final ZipParameters parameters = new ZipParameters();
			parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
			parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
			zipFile.addFolder(zipFilePath, parameters);
		} catch (ZipException e) {
			e.printStackTrace();
		}
		return Paths.get(zipPath.toString());
	}

}

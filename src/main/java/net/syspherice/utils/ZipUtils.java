package net.syspherice.utils;

//Import all needed packages
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipUtils {

	private List<String> fileList;
	private String outputZipFile;
	private String sourceFolder; // SourceFolder path

	public List<String> getFileList() {
		return fileList;
	}

	public void setFileList(List<String> fileList) {
		this.fileList = fileList;
	}

	public String getOutputZipFile() {
		return outputZipFile;
	}

	public void setOutputZipFile(String outputZipFile) {
		this.outputZipFile = outputZipFile;
	}

	public String getSourceFolder() {
		return sourceFolder;
	}

	public void setSourceFolder(String sourceFolder) {
		this.sourceFolder = sourceFolder;
	}

	public ZipUtils() {
		fileList = new ArrayList<String>();
	}

	/*
	 * public static void main(String[] args) { ZipUtils appZip = new
	 * ZipUtils(); appZip.generateFileList(new File(sourceFolder));
	 * appZip.zipIt(outputZipFile); }
	 */
	public Boolean zipIt(String zipFile) {
		byte[] buffer = new byte[1024];
		String source = "";
		FileOutputStream fos = null;
		ZipOutputStream zos = null;
		try {
			try {
				source = sourceFolder.substring(
						sourceFolder.lastIndexOf("\\") + 1,
						sourceFolder.length());
			} catch (Exception e) {
				source = sourceFolder;
			}
			fos = new FileOutputStream(zipFile);
			zos = new ZipOutputStream(fos);

			//System.out.println("Output to Zip : " + zipFile);
			FileInputStream in = null;

			for (String file : this.fileList) {
				//System.out.println("File Added : " + file);
				ZipEntry ze = new ZipEntry(source + File.separator + file);
				zos.putNextEntry(ze);
				try {
					in = new FileInputStream(sourceFolder + File.separator
							+ file);
					int len;
					while ((len = in.read(buffer)) > 0) {
						zos.write(buffer, 0, len);
					}
				} finally {
					in.close();
				}
			}

			zos.closeEntry();
			//System.out.println("Folder successfully compressed");

		} catch (IOException ex) {
			
			ex.printStackTrace();
			return false;
		} finally {
			try {
				zos.close();
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}

	public void generateFileList(File node) {

		// add file only
		if (node.isFile()) {
			fileList.add(generateZipEntry(node.toString()));

		}

		if (node.isDirectory()) {
			String[] subNote = node.list();
			for (String filename : subNote) {
				generateFileList(new File(node, filename));
			}
		}
	}

	private String generateZipEntry(String file) {
		return file.substring(sourceFolder.length() + 1, file.length());
	}
}

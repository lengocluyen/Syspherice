package net.syspherice.utils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpSession;

import net.syspherice.form.BinImageData;
import net.syspherice.form.SearchType;
import net.syspherice.form.UnidentifiedObject;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.multipart.MultipartFile;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.html.simpleparser.HTMLWorker;
import com.itextpdf.text.pdf.PdfWriter;

public class Common {
	public static String CutWithInterval(String field, String name) {

		// cas <=
		name = name.replaceAll("<=", "__hoai__");
		// cas >=
		name = name.replaceAll(">=", "__khong__");
		// cas =
		name = name.replaceAll("=", "this." + field + "==");
		// cas <
		name = name.replaceAll("<", "this." + field + "<");
		// cas >
		name = name.replaceAll(">", "this." + field + ">");
		name = name.replaceAll("__hoai__", "this." + field + "<=");
		// cas <
		name = name.replaceAll("__khong__", "this." + field + ">=");

		// cas and
		name = name.replaceAll(" & ", "&&");
		name = name.replaceAll(" and ", "&&");

		//name = name.replaceAll("|", "||");
		name = name.replaceAll(" or ", "||");

		return name;
	}

	public static List<String> CutWithAnd(String Name) {
		List<String> rs = new ArrayList();
		if (Name.indexOf(" and ") > 0 || Name.indexOf(" or ") > 0
				|| Name.indexOf(" & ") > 0 || Name.indexOf(" | ") > 0) {
			String[] rAnd = Name.split(" and ");

			for (int i = 0; i < rAnd.length; i++) {
				String[] rOr = rAnd[i].split(" or ");
				for (int j = 0; j < rOr.length; j++) {
					String[] rAnd1 = rOr[j].split(" & ");
					for (int k = 0; k < rAnd1.length; k++) {
						String[] rOr1 = rAnd1[k].split(" | ");
						for (int h = 0; h < rOr1.length; h++) {
							rs.add(rOr1[h]);
						}
					}
				}
			}
		}
		return rs;
	}

	// write data to xls
	public static List<String> getListImageBySearchResult(
			Map<String, List<UnidentifiedObject>> data) {
		List<String> result = new ArrayList();
		try {
			for (int i = 0; i < data.size(); i++) {
				List<UnidentifiedObject> uObjects = data.get(data.keySet()
						.toArray()[i]);
				for (UnidentifiedObject uobj : uObjects) {
					for (int j = 0; j < uobj.getData().size(); j++) {
						if (uobj.getData().keySet().toArray()[j].toString()
								.toUpperCase().compareTo("ID") == 0)
							if (!findStringInList(
									result,
									uobj.getData()
											.get(uobj.getData().keySet()
													.toArray()[j]).toString()))
								result.add(uobj
										.getData()
										.get(uobj.getData().keySet().toArray()[j])
										.toString());
					}
				}
			}
		} catch (Exception e) {
			return null;
		}
		return result;
	}

	public static Boolean findStringInList(List<String> list, String str) {
		for (String s : list) {
			if (s.compareTo(str) == 0) {
				return true;
			}
		}
		return false;
	}

	public static File findFileInListForArray(File[] list, String str) {
		if (list == null || list.length < 1)
			return null;
		for (int i = 0; i < list.length; i++) {
			if (list[i].getName().compareTo(str) == 0) {
				return list[i];
			}
		}
		return null;
	}

	public static Boolean copyImageToFolderTemp(List<BinImageData> images,
			String folderTemp, HttpSession session) {

		File folderOrigine = new File(folderTemp);
		if (!folderOrigine.exists())
			folderOrigine.mkdirs();
		try {
			for (BinImageData data : images) {
				File img = new File(Config.ROOT_PATH
						+ data.getUrl().substring(7, data.getUrl().length()));
				File[] files = folderOrigine.listFiles();
				File toFile = findFileInListForArray(files,
						"G" + data.getPlantID());
				if (toFile != null) {
					if (toFile.isDirectory())
						FileUtils.copyFileToDirectory(img, toFile);
				} else {
					File newFolder = new File(folderTemp + "/G"
							+ data.getPlantID());
					newFolder.mkdirs();
					if (newFolder.isDirectory())
						FileUtils.copyFileToDirectory(img, newFolder);
				}
			}
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public static Boolean exportToZip(String zipfile, String folder) {
		try {
			byte[] buffer = new byte[1024];
			FileOutputStream fout = new FileOutputStream(zipfile);
			ZipOutputStream zout = new ZipOutputStream(fout);
			File dir = new File(folder);
			if (dir.isDirectory()) {
				File[] files = dir.listFiles();
				if (files != null && files.length > 0)
					for (int i = 0; i < files.length; i++) {
						FileInputStream fin = new FileInputStream(files[i]);
						zout.putNextEntry(new ZipEntry(files[i].getName()));
						int length;
						while ((length = fin.read(buffer)) > 0) {
							zout.write(buffer, 0, length);
						}
						zout.closeEntry();
						fin.close();
						zout.close();
						return true;
					}
			} else {
				zout.closeEntry();
				zout.close();
				return false;
			}
		} catch (Exception e) {
			return false;
		}
		return false;
	}

	public static String exportSearchResultToPdf(String htmlContent,
			String author, String title) {
		Document document = new Document(com.itextpdf.text.PageSize.A4);
		String fileNameWithPath = Config.ROOT_PATH + "PDF-HtmlWorkerParsed.pdf";

		FileOutputStream fos;
		try {

			fos = new FileOutputStream(fileNameWithPath);

			PdfWriter pdfWriter = PdfWriter.getInstance(document, fos);
			document.open();
			// **********************************************************
			document.addAuthor(author);
			document.addSubject(title);
			document.addCreationDate();
			document.addTitle(title);
			// **********************************************************/

			HTMLWorker htmlWorker = new HTMLWorker(document);
			htmlWorker.parse(new StringReader(htmlContent.toString()));

			document.close();
			fos.close();
		} catch (FileNotFoundException e) {

			return "";
		} catch (DocumentException e) {
			return "";
		} catch (IOException e) {
			return "";
		}
		return fileNameWithPath;
	}

	public static String regexForSearchUnicode(String asciiStr) {
		String oStr = "(o|ò|ó|ỏ|õ|ọ|ô|ố|ồ|ộ|ổ|ỗ|ơ|ở|ờ|ớ|ợ|ỡ)";
		String aStr = "(a|à|á|ả|ã|ạ|â|ẩ|ẫ|ấ|ầ|ậ|ă|ẳ|ẵ|ắ|ằ|ặ)";
		String iStr = "(i|ì|í|ĩ|ỉ|ị)";
		String uStr = "(u|ù|ú|ủ|ũ|ụ|ư|ứ|ừ|ử|ữ|ự)";
		String eStr = "(e|è|é|ẽ|ẻ|ẹ|ê|ể|ễ|ế|ề|ệ)";
		String yStr = "(y|ý|ỳ|ỷ|ỹ|ỵ)";
		String dStr = "(đ|Đ)";
		asciiStr = asciiStr.replaceAll("o", oStr);
		asciiStr = asciiStr.replaceAll("a", aStr);
		asciiStr = asciiStr.replaceAll("i", iStr);
		asciiStr = asciiStr.replaceAll("u", uStr);
		asciiStr = asciiStr.replaceAll("e", eStr);
		asciiStr = asciiStr.replaceAll("y", yStr);
		asciiStr = asciiStr.replaceAll("d", dStr);
		return asciiStr;
	}

	public static List<File> listFilesForFolder(File folder) {
		List<File> result = new ArrayList<File>();
		for (File fileEntry : folder.listFiles()) {
			if (fileEntry.isDirectory()) {
				result.addAll(Common.listFilesForFolder(fileEntry));
			} else {
				result.add(fileEntry);
			}
		}
		return result;
	}

	public static List<File> listFilesForFolerFromExtension(File folder,
			String[] extensions) {
		List<File> result = new ArrayList<File>();
		for (File fileEntry : folder.listFiles()) {
			if (fileEntry.isDirectory()) {
				result.addAll(Common.listFilesForFolder(fileEntry));
			} else {
				if (Common.checkExisteExtensionFile(fileEntry, extensions))
					result.add(fileEntry);
			}
		}
		return result;
	}

	public static Boolean checkExisteExtensionFile(File file,
			String[] extensions) {
		String ex = FilenameUtils.getExtension(file.getName());
		for (int i = 0; i < extensions.length; i++) {
			if (extensions[i].compareTo(ex.toLowerCase()) == 0)
				return true;
		}
		return false;
	}

	public static List<String> getSubCollectionByCompareName(
			List<String> collections, String name) {
		List<String> result = new ArrayList();
		for (String item : collections) {
			if (item != "" && item.indexOf(name) > 0)
				result.add(item);
		}
		return result;
	}

	public static List<SheetDataRow> getRowBody(SheetDataRow rowHead,
			ExcelsHandles exlhandle, SheetInfo sheetinfo, List<SheetInfo> sheets) {
		List<SheetDataRow> sheetdatarows = exlhandle.ReadSheet(sheets
				.get(sheetinfo.getNumber()));
		List<SheetDataRow> rowsBody = new ArrayList<SheetDataRow>();
		for (int i = exlhandle.getTitleRow() + 1; i < sheetdatarows.size(); i++) {
			SheetDataRow rowData = new SheetDataRow();
			for (int j = 0; j < rowHead.getColomns().size(); j++) {
				SheetDataCell sdc = new SheetDataCell();
				try {
					sdc = sheetdatarows.get(i).getColomns().get(j);
				} catch (Exception e) {
				}
				if (sdc == null)
					sdc.setCell("");
				rowData.addRow(sdc);
			}
			rowsBody.add(rowData);
		}
		return rowsBody;
	}

	public static List<LabelValue> getColumnsAttributes(SheetDataRow head) {

		List<LabelValue> results = new ArrayList<LabelValue>();
		for (SheetDataCell sii : head.getColomns()) {
			results.add(new LabelValue(sii.getCell(), sii.getCell()));
		}
		return results;
	}

	public static List<LabelValue> getColumnsfromSheets(List<SheetInfo> sheets) {
		List<LabelValue> selectList = new ArrayList<LabelValue>();

		for (SheetInfo si : sheets) {
			selectList.add(new LabelValue(si.getName(), si.getNumber()));
		}
		return selectList;
	}

	public static Boolean checkStringInArray(String list, String test) {
		String[] lists = list.split(";");
		for (String i : lists) {
			if (test.compareTo(i) == 0)
				return true;
		}
		return false;
	}

	public static String getSimpleDateFormat(Date date) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
				"MM-dd-yyyy HH:mm");
		return simpleDateFormat.format(date);
	}

	public static File multipartToFile(MultipartFile multipart)
			throws IllegalStateException, IOException {
		File tmpFile = new File(System.getProperty("java.io.tmpdir")
				+ System.getProperty("file.separator")
				+ multipart.getOriginalFilename());
		multipart.transferTo(tmpFile);
		return tmpFile;
	}

	public static String getLabelByValue(List<LabelValue> selects, int value) {
		for (LabelValue lv : selects) {
			if (lv.getValue() == value)
				return lv.getLable();
		}
		return null;
	}

	public static String ConvertToString(Object obj, String valueDefault) {
		try {
			if (obj != null) {
				return obj.toString();
			} else
				return valueDefault;
		} catch (Exception e) {
			return valueDefault;
		}
	}

	public static int ConvertToInt(Object obj, int valueDefault) {
		try {
			return Integer.parseInt(obj.toString());
		} catch (Exception e) {
			return valueDefault;
		}
	}

	public static Boolean ConvertToBoolean(Object obj, Boolean valueDefault) {
		try {
			return Boolean.parseBoolean(obj.toString());
		} catch (Exception e) {
			return valueDefault;
		}
	}

	public static String getFieldName(Method method) {
		try {
			Class<?> clazz = method.getDeclaringClass();
			BeanInfo info = Introspector.getBeanInfo(clazz);
			PropertyDescriptor[] props = info.getPropertyDescriptors();
			for (PropertyDescriptor pd : props) {
				if (method.equals(pd.getWriteMethod())
						|| method.equals(pd.getReadMethod())) {
					System.out.println(pd.getDisplayName());
					return pd.getName();
				}
			}
		} catch (IntrospectionException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}

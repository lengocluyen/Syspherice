package net.syspherice.utils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.syspherice.form.SearchType;

import org.apache.commons.io.FilenameUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.multipart.MultipartFile;

public class Common {

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

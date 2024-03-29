package com.philip.edu.excel;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.xml.namespace.QName;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import org.apache.poi.ooxml.util.SAXHelper;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.openxml4j.opc.PackageAccess;
import org.apache.poi.openxml4j.opc.PackagePart;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.model.SharedStringsTable;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

public class RapidExcelSheetsHelper {
	private static int lines = 0;
	private static int columns = 0;
	private static ArrayList all = new ArrayList();
	private static ArrayList sheetNames = new ArrayList();
	private static ArrayList sheetArray = new ArrayList();
	private static ArrayList line = new ArrayList();
	private static ArrayList file = new ArrayList();
	private static String sheetName = "";

	public void refresh() {
		lines = 0;
		columns = 0;
		file = new ArrayList();
		all = new ArrayList();
		line = new ArrayList();
		sheetName = "";
	}

	public static int getLines() {
		return lines;
	}

	public static void setLines(int lines) {
		RapidExcelSheetsHelper.lines = lines;
	}

	public static int getColumns() {
		return columns;
	}

	public static void setColumns(int columns) {
		RapidExcelSheetsHelper.columns = columns;
	}

	public static ArrayList getAll() {
		return all;
	}

	public static void setAll(ArrayList all) {
		RapidExcelSheetsHelper.all = all;
	}
	
	public static ArrayList getFile() {
		return file;
	}

	public void processFirstSheet(String filename) throws Exception {
		try (OPCPackage pkg = OPCPackage.open(filename, PackageAccess.READ);) {
			XSSFReader r = new XSSFReader(pkg);
			SharedStringsTable sst = r.getSharedStringsTable();

			XMLReader parser = fetchSheetParser(sst);
			// process the first sheet
			try (InputStream sheet = r.getSheetsData().next()) {
				InputSource sheetSource = new InputSource(sheet);
				parser.parse(sheetSource);
			}
		}

	}

	public void processFirstSheetStream(InputStream in) throws Exception {
		try (OPCPackage pkg = OPCPackage.open(in);) {
			XSSFReader r = new XSSFReader(pkg);
			SharedStringsTable sst = r.getSharedStringsTable();

			XMLReader parser = fetchSheetParser(sst);
			// process the first sheet
			try (InputStream sheet = r.getSheetsData().next()) {
				InputSource sheetSource = new InputSource(sheet);
				parser.parse(sheetSource);
			}
		}

	}

	public void processAllSheets(InputStream in) throws Exception {
		try (OPCPackage pkg = OPCPackage.open(in);) {

			//1. get all the names:
			PackagePart workbookpart = pkg.getPartsByName(Pattern.compile("/xl/workbook.xml")).get(0);

			XMLEventReader reader = XMLInputFactory.newInstance().createXMLEventReader(workbookpart.getInputStream());

			while (reader.hasNext()) {
				XMLEvent event = (XMLEvent) reader.next();

				if (event.isStartElement()) {
					StartElement startElement = (StartElement) event;
					QName startElementName = startElement.getName();
					if (startElementName.getLocalPart().equalsIgnoreCase("sheet")) {
						Attribute attribute = startElement.getAttributeByName(new QName("name"));
						String sheetName = attribute.getValue();
						sheetNames.add(sheetName);
						//System.out.println(sheetName);
					}
				} else if (event.isEndElement()) {
					EndElement endElement = (EndElement) event;
					QName endElementName = endElement.getName();
				}
			}
			
			//2. get all the data:
			XSSFReader r = new XSSFReader(pkg);
			SharedStringsTable sst = r.getSharedStringsTable();

			// System.out.println("count:" + sst.getCount());
			XMLReader parser = fetchSheetParser(sst);

			Iterator<InputStream> sheets = r.getSheetsData();
			int index = 0;
			while (sheets.hasNext()) {
				//System.out.println("Processing new sheet:\n");
				try (InputStream sheet = sheets.next()) {
					InputSource sheetSource = new InputSource(sheet);
					parser.parse(sheetSource);
					sheetArray = new ArrayList();
					sheetArray.add(sheetNames.get(index));
					sheetArray.add(lines);
					sheetArray.add(columns);
					lines = 0;
					columns = 0;
					
					sheetArray.add(all);
					file.add(sheetArray);
					all = new ArrayList();
					index++;
				}
				//System.out.println("");
			}
		}
	}

	public static String readStrByCode(InputStream is, String code) {
		StringBuilder builder = new StringBuilder();
		BufferedReader reader = null;

		try {
			reader = new BufferedReader(new InputStreamReader(is, code));
			String line;
			while ((line = reader.readLine()) != null) {
				builder.append(line + "\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return builder.toString();
	}

	public int getSheetNum(InputStream in) throws Exception {
		int num = 0;
		try (OPCPackage pkg = OPCPackage.open(in);) {
			XSSFReader r = new XSSFReader(pkg);
			SharedStringsTable sst = r.getSharedStringsTable();

			// XMLReader parser = fetchSheetParser(sst);

			Iterator<InputStream> sheets = r.getSheetsData();
			while (sheets.hasNext()) {
				num++;
			}
		}
		return num;
	}

	public void processSheetAt(InputStream in, int index) throws Exception {
		int i = 0;
		try (OPCPackage pkg = OPCPackage.open(in);) {
			XSSFReader r = new XSSFReader(pkg);
			SharedStringsTable sst = r.getSharedStringsTable();

			XMLReader parser = fetchSheetParser(sst);

			Iterator<InputStream> sheets = r.getSheetsData();
			while (sheets.hasNext()) {
				if (i++ == index) {
					// System.out.println("Processing new sheet:\n");
					try (InputStream sheet = sheets.next()) {
						InputSource sheetSource = new InputSource(sheet);
						parser.parse(sheetSource);
					}
				}
			}
		}
	}

	public XMLReader fetchSheetParser(SharedStringsTable sst) throws SAXException, ParserConfigurationException {
		XMLReader parser = SAXHelper.newXMLReader();
		ContentHandler handler = new SheetHandler(sst);
		parser.setContentHandler(handler);
		return parser;
	}

	/**
	 * See org.xml.sax.helpers.DefaultHandler javadocs 重写 startElement
	 * characters endElements方法 
	 */
	private static class SheetHandler extends DefaultHandler {
		private SharedStringsTable sst;
		private String lastContents;
		private boolean nextIsString; // 是否为string格式标识
		private final LruCache<Integer, String> lruCache = new LruCache<>(60);

		private int sheetIndex = -1;
		private int curRow = 0;
		private int curCol = 1;
		private List<String> rowlist = new ArrayList<String>();

		/**
		 * 缓存
		 * 
		 * @author Administrator
		 *
		 * @param <A>
		 * @param <B>
		 */
		private static class LruCache<A, B> extends LinkedHashMap<A, B> {
			private final int maxEntries;

			public LruCache(final int maxEntries) {
				super(maxEntries + 1, 1.0f, true);
				this.maxEntries = maxEntries;
			}

			@Override
			protected boolean removeEldestEntry(final Map.Entry<A, B> eldest) {
				return super.size() > maxEntries;
			}
		}

		private SheetHandler(SharedStringsTable sst) {
			this.sst = sst;
		}

		/**
		 * 该方法自动被调用，每读一行调用一次，在方法中写自己的业务逻辑即可
		 * 
		 * @param sheetIndex
		 *            工作簿序号
		 * @param curRow
		 *            处理到第几行
		 * @param rowList
		 *            当前数据行的数据集合
		 */
		public void optRow(int sheetIndex, int curRow, List<String> rowList) {
			String temp = "";
			for (String str : rowList) {
				temp += str + "_";
			}
			this.rowlist.clear();
			this.curRow++;
			this.curCol = 1;
			//System.out.println(temp);
		}

		@Override
		public void startElement(String uri, String localName, String name, Attributes attributes) throws SAXException {
			// c => cell 代表单元格
			if (name.equals("c")) {
				// Print the cell reference
				// 获取单元格的位置，如A1,B1
				// System.out.print(attributes.getValue("r") + " - ");
				// line.add(attributes.getValue("r"));
				if (lines == 0)
					columns++;

				String col = attributes.getValue("r");
				int realCol = convertRowIdtoInt(col);
				int gap = realCol - curCol;
				// System.out.println("realCol:" + realCol + ", curCol:"
				// +curCol);
				for (int i = 0; i < gap; i++) {
					line.add("");
				}
				curCol = realCol;

				// Figure out if the value is an index in the SST 如果下一个元素是 SST
				// 的索引，则将nextIsString标记为true
				// 单元格类型
				String cellType = attributes.getValue("t");
				// cellType值 s:字符串 b:布尔 e:错误处理
				if (cellType != null && cellType.equals("s")) {
					// 标识为true 交给后续endElement处理
					nextIsString = true;
				} else {
					nextIsString = false;
				}
			}
			// Clear contents cache
			lastContents = "";
		}

		/**
		 * 得到单元格对应的索引值或是内容值 如果单元格类型是字符串、INLINESTR、数字、日期，lastIndex则是索引值
		 * 如果单元格类型是布尔值、错误、公式，lastIndex则是内容值
		 */
		@Override
		public void characters(char[] ch, int start, int length) throws SAXException {
			lastContents += new String(ch, start, length);
			// line.add(new String(ch, start, length))
		}

		@Override
		public void endElement(String uri, String localName, String name) throws SAXException {
			// Process the last contents as required.
			// Do now, as characters() may be called more than once
			if (nextIsString) {
				int idx = Integer.parseInt(lastContents);
				lastContents = lruCache.get(idx);
				// 如果内容为空 或者Cache中存在相同key 不保存到Cache中
				if (lastContents == null && !lruCache.containsKey(idx)) {
					lastContents = new XSSFRichTextString(sst.getEntryAt(idx)).toString();
					lruCache.put(idx, lastContents);
				}
				// if(lastContents==null)line.add("");
				nextIsString = false;
			}

			// v => contents of a cell
			// Output after we've seen the string contents
			if (name.equals("v")) {
				// System.out.println(lastContents);
				line.add(lastContents);
				curCol++;
				// rowlist.add(curCol++,lastContents);
			} else {
				// 如果标签名称为 row , 已到行尾
				if (name.equals("row")) {
					optRow(sheetIndex, curRow, rowlist);
					// System.out.println(lruCache);
					all.add(line);
					lines++;
					line = new ArrayList();
					lruCache.clear();
				}
			}
		}

		public static int convertRowIdtoInt(String rowId) {
			int firstDigit = -1;
			for (int c = 0; c < rowId.length(); ++c) {
				if (Character.isDigit(rowId.charAt(c))) {
					firstDigit = c;
					break;
				}
			}
			// AB7-->AB
			// AB是列号, 7是行号
			String newRowId = rowId.substring(0, firstDigit);
			int num = 0;
			int result = 0;
			int length = newRowId.length();
			for (int i = 0; i < length; i++) {
				// 先取最低位，B
				char ch = newRowId.charAt(length - i - 1);
				// B表示的十进制2，ascii码相减，以A的ascii码为基准，A表示1，B表示2
				num = (int) (ch - 'A' + 1);
				// 列号转换相当于26进制数转10进制
				num *= Math.pow(26, i);
				result += num;
			}
			return result;

		}

	}

	public static void main(String[] args) throws Exception {
		RapidExcelSheetsHelper example = new RapidExcelSheetsHelper();
		FileInputStream in = new FileInputStream("D:/Develop/education/backup/采集系统数据-深圳大学-2019-06-18.xlsx");
		example.processAllSheets(in);
		/*
		 * int lines = 0; int columns = 0; lines = example.getLines(); columns =
		 * example.getColumns(); System.out.println("lines: " +
		 * example.getLines()); System.out.println("columns: " +
		 * example.getColumns()); String[][] data = new String[lines][columns];
		 * ArrayList all = example.getAll(); for (int i = 0; i < lines; i++) {
		 * ArrayList line = (ArrayList) all.get(i); for (int j = 0; j < columns;
		 * j++) { String cell = (String) line.get(j); data[i][j] = cell;
		 * System.out.print(cell + "-"); } System.out.println("\n"); }
		 */
	}
}

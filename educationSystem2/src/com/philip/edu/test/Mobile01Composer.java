package com.philip.edu.test;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zk.ui.util.Template;
//import org.zkoss.zkmax.zul.Chosenbox;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Fileupload;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

import com.philip.edu.basic.FormField;
import com.philip.edu.basic.FormManager;

public class Mobile01Composer extends GenericForwardComposer{

	private Listbox lbClient;
	private Button butSave;
	private static FormManager formManager = new FormManager(); 
		
	@Wire
	private Div bdlBody;
	 
	private ListModelList<FormField> lmlField;
	//加载页面时方法
	@SuppressWarnings("null")
	public void doAfterCompose(Component comp) throws Exception {
		 
		super.doAfterCompose(comp);// 关联前端页面控件的必要方法
		ArrayList fields = formManager.getFormFields(21);
		lmlField = new ListModelList(fields);
		lbClient.setTooltiptext("");// 提示功能属性

		lbClient.setModel(lmlField);
	}
	
	//保存数据按钮
	public void onClick$butSave() {
 
		
	}
	
	//多选框赋值
	public void setField(Combobox comp)  {
		
		//如果没有选择，则刷新值列表
		if(comp.getSelectedItem() == null ) {
			 ListModelList<Object> list = new ListModelList();
			 list.add("1");
			 list.add("2");
			 list.add("3");
			 list.add("4");
			 comp.setModel(list); 
		 }
	}
	
	//文件上传
	public void FileUpload(UploadEvent event) {
		
		
 
		Media media = event.getMedia();
		// 判断上传文件的格式是否是Excel
		if (!checkFileFormat(media.getName())) {
			Messagebox.show("非EXCEL文件");
			return;
		}

		// 保存上传的Excel文件
		File fExcel = saveUploadedExcel(media, "C:/TMP/");
		if (fExcel == null) {
			Messagebox.show("无内容");
			return;
		}
		System.out.println("Excel saved to: " + fExcel.getAbsolutePath());
	}
	
	 // Excel文件格式校验
	 public static boolean checkFileFormat(String sFileName) {
			// 获取扩展名
			String sFileExt = sFileName.substring(sFileName.lastIndexOf(".") + 1);

			String[] checkFormat = { "XLSX" };
			for (int i = 0; i < checkFormat.length; i++) {
				if (sFileExt.equalsIgnoreCase(checkFormat[i])) {
					return true;
				}
			}

			return false;
		}
	
	public File saveUploadedExcel(Media media, String sSavePath) {

		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd_HHmmss_SSS_");// 设置日期格式作为文件名前缀
		File fExcel = new File(sSavePath + df.format(new Date()) + "_" + media.getName());

		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(fExcel);
			fos.write(media.getByteData());
			fos.close();
		} catch (Exception e) {
			System.out.println("路径或文件有误");
			return null;
		} finally {
			if (fos != null) {
				try {
					fos.close(); // 关闭流
				} catch (Exception e) {
					System.out.println("上传失败");
					return null;
				}
			}
		}

		return fExcel;
	}
}

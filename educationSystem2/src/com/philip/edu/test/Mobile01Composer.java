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
	//����ҳ��ʱ����
	@SuppressWarnings("null")
	public void doAfterCompose(Component comp) throws Exception {
		 
		super.doAfterCompose(comp);// ����ǰ��ҳ��ؼ��ı�Ҫ����
		ArrayList fields = formManager.getFormFields(21);
		lmlField = new ListModelList(fields);
		lbClient.setTooltiptext("");// ��ʾ��������

		lbClient.setModel(lmlField);
	}
	
	//�������ݰ�ť
	public void onClick$butSave() {
 
		
	}
	
	//��ѡ��ֵ
	public void setField(Combobox comp)  {
		
		//���û��ѡ����ˢ��ֵ�б�
		if(comp.getSelectedItem() == null ) {
			 ListModelList<Object> list = new ListModelList();
			 list.add("1");
			 list.add("2");
			 list.add("3");
			 list.add("4");
			 comp.setModel(list); 
		 }
	}
	
	//�ļ��ϴ�
	public void FileUpload(UploadEvent event) {
		
		
 
		Media media = event.getMedia();
		// �ж��ϴ��ļ��ĸ�ʽ�Ƿ���Excel
		if (!checkFileFormat(media.getName())) {
			Messagebox.show("��EXCEL�ļ�");
			return;
		}

		// �����ϴ���Excel�ļ�
		File fExcel = saveUploadedExcel(media, "C:/TMP/");
		if (fExcel == null) {
			Messagebox.show("������");
			return;
		}
		System.out.println("Excel saved to: " + fExcel.getAbsolutePath());
	}
	
	 // Excel�ļ���ʽУ��
	 public static boolean checkFileFormat(String sFileName) {
			// ��ȡ��չ��
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

		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd_HHmmss_SSS_");// �������ڸ�ʽ��Ϊ�ļ���ǰ׺
		File fExcel = new File(sSavePath + df.format(new Date()) + "_" + media.getName());

		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(fExcel);
			fos.write(media.getByteData());
			fos.close();
		} catch (Exception e) {
			System.out.println("·�����ļ�����");
			return null;
		} finally {
			if (fos != null) {
				try {
					fos.close(); // �ر���
				} catch (Exception e) {
					System.out.println("�ϴ�ʧ��");
					return null;
				}
			}
		}

		return fExcel;
	}
}

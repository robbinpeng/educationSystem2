package com.philip.edu.action;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zk.ui.util.Template;
//import org.zkoss.zkmax.zul.Chosenbox;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Fileupload;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.philip.edu.basic.Constants;
import com.philip.edu.basic.DataInfo;
import com.philip.edu.basic.DataManager;
import com.philip.edu.basic.Form;
import com.philip.edu.basic.FormField;
import com.philip.edu.basic.FormFieldData;
import com.philip.edu.basic.FormManager;

public class RecordUpdateController extends SelectorComposer<Component>{

	private static Logger logger = Logger.getLogger(RecordUpdateController.class);
	private static DataManager dataManager = new DataManager();
	
	@Wire
	private Listbox lbClient;
	@Wire
	private Button butSave;
	private static FormManager formManager = new FormManager(); 
	private ArrayList fields;
	private Form form;
	private int id;
	private FormFieldData fieldD;
		
	@Wire
	private Window bdlBody;
	@Wire
	private Button closeBtn;
	 
	private ListModelList<FormField> lmlField;
	//����ҳ��ʱ����
	@SuppressWarnings("null")
	public void doAfterCompose(Component comp) throws Exception {
		 
		super.doAfterCompose(comp);// ����ǰ��ҳ��ؼ��ı�Ҫ���� 
		ArrayList fieldData = new ArrayList();
		
		Integer sForm = (Integer)Executions.getCurrent().getArg().get("form_id");
		int form_id = sForm.intValue();
		
		Integer sId = (Integer)Executions.getCurrent().getArg().get("id");
		id = sId.intValue();
		
		form = formManager.getFormById(form_id);
		fields = formManager.getFormFields(form_id);
		ArrayList record = dataManager.getTableDataById(fields, form.getPhsic_name(), id);
		for(int i=0; i<fields.size(); i++){
			FormField field = (FormField)fields.get(i);
			DataInfo data = (DataInfo)record.get(i+1);
			
			fieldD = new FormFieldData();
			fieldD.setId(field.getId());
			fieldD.setBus_name(field.getBus_name());
			fieldD.setDis_method(field.getDis_method());
			fieldD.setDictid(field.getDictid());
			fieldD.setPhysic_name(field.getPhysic_name());
			fieldD.setSequence(field.getSequence());
			fieldD.setText_format(field.getText_format());
			fieldD.setValue(data.getValue());
			
			if(data.getUrl()!=null)fieldD.setUrl(data.getUrl());
			
			fieldData.add(fieldD);
		}
		
		lmlField = new ListModelList(fieldData);
		lbClient.setTooltiptext("");// ��ʾ��������

		lbClient.setModel(lmlField);
	}
	
	@Listen("onClick = #closeBtn")
    public void closeModal(Event e) {
		logger.info("to close.");
		bdlBody.detach();
    }
	
	//�������ݰ�ť
	@Listen("onClick = #butSave")
	public void saveInfo(Event e) {
		int task_id;
		String sTask = (String) Sessions.getCurrent().getAttribute("task_id");
		if(sTask==null){
			task_id = Constants.SYSTEM_TASK_ID;
		}
		else{
			task_id = Integer.parseInt(sTask);
		}
		
		List<Listitem> list = lbClient.getItems();
		ArrayList record = new ArrayList();
		
		for(int i=0; i<list.size(); i++){
			Listitem item = (Listitem)list.get(i);
			Listcell cell = (Listcell)item.getLastChild();
			FormField field = (FormField)fields.get(i);
			
			DataInfo data = new DataInfo();
			switch(field.getDis_method()){
			case Constants.V_DISPLAY_SINGLE_TEXTBOX:
				Textbox text = (Textbox)cell.getLastChild();
				if(text.getValue()==null || "".equals(text.getValue())){Messagebox.show("" + field.getBus_name() + "�ֶβ���Ϊ��","����",Messagebox.OK,Messagebox.ERROR);return;}
				data.setKey(field.getPhysic_name());
				data.setValue(text.getValue());
				break;
			case Constants.V_DISPLAY_MUTIPLE_TEXTBOX:
				Textbox textM = (Textbox)cell.getLastChild();
				if(textM.getValue()==null || "".equals(textM.getValue())){Messagebox.show("" + field.getBus_name() + "�ֶβ���Ϊ��","����",Messagebox.OK,Messagebox.ERROR);return;}
				data.setKey(field.getPhysic_name());
				data.setValue(textM.getValue());
				break;
			case Constants.V_DISPLAY_DATE_CONTROL:
				Datebox date = (Datebox)cell.getLastChild();
				SimpleDateFormat sdf = null;
				switch(field.getText_format()){
				case Constants.V_TEXT_FORMAT_DATE_YEAR:
					sdf = new SimpleDateFormat("yyyy");
					break;
				case Constants.V_TEXT_FORMAT_DATE_MONTH:
					sdf = new SimpleDateFormat("yyyy-MM");
					break;
				case Constants.V_TEXT_FORMAT_DATE_MONTH_NOSLASH:
					sdf = new SimpleDateFormat("yyyyMM");
					break;
				case Constants.V_TEXT_FORMAT_DATE_DAY:
					sdf = new SimpleDateFormat("yyyy-MM-dd");
					break;
				}
				if(date.getValue()==null){Messagebox.show("" + field.getBus_name() + "�ֶβ���Ϊ��","����",Messagebox.OK,Messagebox.ERROR);return;}
				data.setKey(field.getPhysic_name());
				data.setValue(sdf.format(date.getValue()));
				break;
			case Constants.V_DISPLAY_SINGLE_COMBOBOX:
				Combobox box = (Combobox)cell.getLastChild();
				if(box.getSelectedItem()==null || "".equals(box.getSelectedItem().getValue())){Messagebox.show("" + field.getBus_name() + "�ֶβ���Ϊ��","����",Messagebox.OK,Messagebox.ERROR);return;}
				data.setKey(field.getPhysic_name());
				data.setValue(box.getSelectedItem().getLabel());
				break;
			case Constants.V_DISPLAY_MULTIPLE_COMBOBOX:
				Combobox box1 = (Combobox)cell.getLastChild();
				if(box1.getSelectedItem()==null || "".equals(box1.getSelectedItem().getValue())){Messagebox.show("" + field.getBus_name() + "�ֶβ���Ϊ��","����",Messagebox.OK,Messagebox.ERROR);return;}
				data.setKey(field.getPhysic_name());
				data.setValue(box1.getSelectedItem().getLabel());
				break;
			case Constants.V_DISPLAY_UPLOAD_CONTROL:
				Fileupload upload = (Fileupload) cell.getLastChild();
				data.setKey(field.getPhysic_name());
				String path = (String)upload.getAttribute("file_path");
				data.setValue(path);
				break;
			}
			record.add(data);			
		}
		
		dataManager.updateRecord(form, record, task_id, id);
		Window pList = (Window)Path.getComponent("/window1");
		bdlBody.detach();
		Executions.getCurrent().sendRedirect("");
	}
	
	// �ļ��ϴ�
		@Listen("onUpload = Fileupload")
		public void FileUpload(UploadEvent event) {

			Media media = event.getMedia();

			// �����ϴ���Excel�ļ�
			File fExcel = saveUploadedExcel(media, "fileUpload\\");
			if (fExcel == null) {
				Messagebox.show("û�����ݣ�", "����", Messagebox.OK, Messagebox.ERROR);
				return;
			}
			System.out.println("Excel saved to: " + fExcel.getAbsolutePath());
			
			Fileupload upload = (Fileupload)event.getTarget();
			//String path = fExcel.getAbsolutePath().replaceAll(Pattern.quote(File.separator), "\\\\\\\\\\\\\\\\");
			fieldD.setUrl(fExcel.getAbsolutePath());
			upload.setAttribute("file_path", fExcel.getAbsolutePath().replaceAll(Pattern.quote(File.separator), "\\\\\\\\\\\\\\\\"));
			upload.setLabel(media.getName());
		}

		public File saveUploadedExcel(Media media, String sSavePath) {

			SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd_HHmmss_SSS_");// �������ڸ�ʽ��Ϊ�ļ���ǰ׺
			File fExcel = new File(Sessions.getCurrent().getWebApp().getRealPath("") + sSavePath + df.format(new Date()) + "_" + media.getName());
			logger.info(Sessions.getCurrent().getWebApp().getRealPath("") + sSavePath + df.format(new Date()) + "_" + media.getName());
			
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

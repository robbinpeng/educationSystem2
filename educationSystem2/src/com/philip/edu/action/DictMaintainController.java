package com.philip.edu.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Window;

import com.philip.edu.basic.Constants;
import com.philip.edu.basic.Dict;
import com.philip.edu.basic.DictGroup;
import com.philip.edu.basic.DictI;
import com.philip.edu.basic.DictItem;
import com.philip.edu.basic.DictManager;
import com.philip.edu.basic.Form;
import com.philip.edu.basic.FormManager;
import com.philip.edu.basic.Group;

public class DictMaintainController extends SelectorComposer<Component> {
	private static Logger logger = Logger.getLogger(DictMaintainController.class);
	private static FormManager formManager = new FormManager();
	private static DictManager dictManager = new DictManager();

	@Wire
	private Window window;
	@Wire
	private Listbox dictionlist;
	@Wire
	private Listbox dictitemlist;
	@Wire
	private Button addDict;
	@Wire
	private Button addItem;
	@Wire
	private Button delete;

	@Override
	public void doAfterCompose(Component window) throws Exception {
		super.doAfterCompose(window);

		List<Dict> diction = dictManager.getDictionary();
		List<DictI> dictionI = new ArrayList();
		for (int i = 0; i < diction.size(); i++) {
			Dict dict = diction.get(i);
			DictI dictI = new DictI();
			dictI.setDictcode(dict.getDictcode());
			dictI.setDictgroupid(dict.getDictgroupid());
			dictI.setDictname(dict.getDictname());
			dictI.setId(dict.getId());
			dictI.setRemark(dict.getRemark());
			dictI.setStatus(dict.getStatus());
			// logger.info(dictI.getDictname());

			DictGroup dictGroup = dictManager.getDictGroupById(dict.getDictgroupid());
			dictI.setDictgroupname(dictGroup.getGroupname());
			dictionI.add(dictI);
		}

		dictionlist.setModel(new ListModelList<DictI>(dictionI));

	}

	@Listen("onClick = #dictionlist")
	public void showDictItems(Event e) {
		DictI item = (DictI) e.getData();
		// Messagebox.show(item.getDictname());
		if (item != null) {
			List<DictItem> dictitems = dictManager.getDictItemByDict(item.getId());
			dictitemlist.setModel(new ListModelList<DictItem>(dictitems));
		}
	}

	@Listen("onClick = #addDict")
	public void createDict() {
		Window window1 = (Window) Executions.createComponents("/new_dict.zul", null, null);

		window1.doModal();
	}

	@Listen("onUpdate = #dictionlist")
	public void updateDict(Event e) {
		DictI dict = (DictI) e.getData();
		HashMap map = new HashMap();
		map.put("dictId", dict.getId());

		Window window2 = (Window) Executions.createComponents("/update_dict.zul", null, map);

		window2.doModal();
	}

	@Listen("onDelete = #dictionlist")
	public void deleteDict(Event e) {
		DictI dict = (DictI) e.getData();

		Messagebox.show("你确定要删除这个数据字典项吗？", "确定删除", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION,
				new org.zkoss.zk.ui.event.EventListener() {
					public void onEvent(Event e) throws InterruptedException {
						if (e.getName().equals("onOK")) {
							// Messagebox.show("删除了");
							dictManager.deleteDict(dict.getId());

							List<Dict> diction = dictManager.getDictionary();
							List<DictI> dictionI = new ArrayList();
							for (int i = 0; i < diction.size(); i++) {
								Dict dict = diction.get(i);
								DictI dictI = new DictI();
								dictI.setDictcode(dict.getDictcode());
								dictI.setDictgroupid(dict.getDictgroupid());
								dictI.setDictname(dict.getDictname());
								dictI.setId(dict.getId());
								dictI.setRemark(dict.getRemark());
								dictI.setStatus(dict.getStatus());
								// logger.info(dictI.getDictname());

								DictGroup dictGroup = dictManager.getDictGroupById(dict.getDictgroupid());
								dictI.setDictgroupname(dictGroup.getGroupname());
								dictionI.add(dictI);
							}

							dictionlist.setModel(new ListModelList<DictI>(dictionI));
						} else {

						}
					}
				});
	}
}

package com.philip.edu.test;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Paging;
import org.zkoss.zul.event.PagingEvent;

public class TestListController extends SelectorComposer<Component>{
private static final long serialVersionUID = 1L;
    
	@Wire
    private Listbox listbox;
    private ListModelList<String> model;
    private List<String> testList = new ArrayList<String>();
    @Wire
    private Paging pagetest;
    private int count = 1;
    
    @Override
    public void doAfterCompose(Component comp) throws Exception {
        // TODO Auto-generated method stub
        super.doAfterCompose(comp);
        
        //填充第一页数据
        for (int i = 1; i < pagetest.getPageSize()+1; i++) {
            testList.add(i+"");
        }
        
        model = new ListModelList<String>(testList);
        listbox.setModel(model);
        listbox.setItemRenderer(new testItemRenderer());
        
        //监听分页改变事件
        pagetest.addEventListener("onPaging", new EventListener<Event>() {

            public void onEvent(Event event) throws Exception {
                // TODO Auto-generated method stub
                PagingEvent pe = (PagingEvent) event;
                
                redraw(pe.getActivePage()+1,pagetest.getPageSize());
            }
        });
    }
    
    public class testItemRenderer implements ListitemRenderer<String>{

        @Override
        public void render(Listitem item, String data, final int index)
                throws Exception {
            // TODO Auto-generated method stub
            
            item.setValue(data);
            Listcell listcell2 = new Listcell();
            Label label = new Label(data);
            listcell2.appendChild(label);
            listcell2.setParent(item);
        }
    }
    
    private void redraw(int activePage,int pageSize) {  
        
        //清空所有数据
        listbox.getItems().clear();  
        testList.clear();
        
        int total = activePage * pageSize;
        
        for (int i = total-pageSize+1; i <= total; i++) {
            
            //当超过总页数时
            if (i > pagetest.getTotalSize()) {
                break;
            }
            
            testList.add(i+"");
        }
        
        model = new ListModelList<String>(testList);
        listbox.setModel(model);
    } 
}

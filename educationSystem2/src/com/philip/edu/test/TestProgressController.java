package com.philip.edu.test;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Desktop;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.util.GenericAutowireComposer;
import org.zkoss.zul.Label;
import org.zkoss.zul.Progressmeter;
import org.zkoss.zul.Window;

public class TestProgressController extends GenericAutowireComposer<Component>{

    private static final long serialVersionUID = 1L;

    private Window progresswindow;
    private Progressmeter userprogress;
    private Label progresslabel;
    
    @Override
    public void doAfterCompose(Component comp) throws Exception {
        // TODO Auto-generated method stub
        super.doAfterCompose(comp);
        
        // 启动进度条
        desktop.enableServerPush(true);
        Thread t1 = new Thread(new UserHandleThread(userprogress, progresswindow, progresslabel));
        t1.start();
    }
    
    class UserHandleThread implements Runnable {
        
        private Desktop dt;
        private Progressmeter pg;
        private Window win;
        private Label prglb;

        public UserHandleThread(Progressmeter watchpg, Window window, Label lb) {
            pg = watchpg;
            dt = watchpg.getDesktop();
            win = window;
            prglb = lb;
        }

        @Override
        public void run() {
            
                int rows = 10000;
                //作为进度条区域值标准
                int[] rowslarge = new int[10];
                rowslarge[0] = (int) (rows * 0.1) >= 0 ? (int) (rows * 0.1) : 0;
                rowslarge[1] = (int) (rows * 0.2) >= 0 ? (int) (rows * 0.2) : 0;
                rowslarge[2] = (int) (rows * 0.3) >= 0 ? (int) (rows * 0.3) : 0;
                rowslarge[3] = (int) (rows * 0.4) >= 0 ? (int) (rows * 0.4) : 0;
                rowslarge[4] = (int) (rows * 0.5) >= 0 ? (int) (rows * 0.5) : 0;
                rowslarge[5] = (int) (rows * 0.6) >= 0 ? (int) (rows * 0.6) : 0;
                rowslarge[6] = (int) (rows * 0.7) >= 0 ? (int) (rows * 0.7) : 0;
                rowslarge[7] = (int) (rows * 0.8) >= 0 ? (int) (rows * 0.8) : 0;
                rowslarge[8] = (int) (rows * 0.9) >= 0 ? (int) (rows * 0.9) : 0;
                rowslarge[9] = (int) (rows * 1)   >= 0 ? (int) (rows * 1)   : 0;
                
                try {
                    //从1数到10000
                    for (int i = 0; i <= rows; i++) {

                        if (i == rowslarge[0]) {
                            Executions.activate(dt);
                            pg.setValue(10);
                            prglb.setValue("已完成10%……");
                            Executions.deactivate(dt);
                            Thread.sleep(2000);
                        } else if (i == rowslarge[1]) {
                            Executions.activate(dt);
                            pg.setValue(20);
                            prglb.setValue("已完成20%……");
                            Executions.deactivate(dt);
                            Thread.sleep(2000);
                        } else if (i == rowslarge[2]) {
                            Executions.activate(dt);
                            pg.setValue(30);
                            prglb.setValue("已完成30%……");
                            Executions.deactivate(dt);
                            Thread.sleep(2000);
                        } else if (i == rowslarge[3]) {
                            Executions.activate(dt);
                            pg.setValue(40);
                            prglb.setValue("已完成40%……");
                            Executions.deactivate(dt);
                            Thread.sleep(2000);
                        } else if (i == rowslarge[4]) {
                            Executions.activate(dt);
                            pg.setValue(50);
                            prglb.setValue("已完成50%……");
                            Executions.deactivate(dt);
                            Thread.sleep(2000);
                        } else if (i == rowslarge[5]) {
                            Executions.activate(dt);
                            pg.setValue(60);
                            prglb.setValue("已完成60%……");
                            Executions.deactivate(dt);
                            Thread.sleep(2000);
                        } else if (i == rowslarge[6]) {
                            Executions.activate(dt);
                            pg.setValue(70);
                            prglb.setValue("已完成70%……");
                            Executions.deactivate(dt);
                            Thread.sleep(2000);
                        } else if (i == rowslarge[7]) {
                            Executions.activate(dt);
                            pg.setValue(80);
                            prglb.setValue("已完成80%……");
                            Executions.deactivate(dt);
                            Thread.sleep(2000);
                        }else if (i == rowslarge[8]) {
                            Executions.activate(dt);
                            pg.setValue(90);
                            prglb.setValue("已完成90%……");
                            Executions.deactivate(dt);
                            Thread.sleep(2000);
                        }else if (i == rowslarge[9]) {
                            Executions.activate(dt);
                            pg.setValue(100);
                            prglb.setValue("100%,导入成功,正自动关闭窗口,请稍等..");
                            Executions.deactivate(dt);
                            Thread.sleep(2000);
                        }
                    }
                    Executions.activate(dt);
                    win.detach();
                    Executions.deactivate(dt);
                } catch (Exception e) {
                    // TODO: handle exception
                }
        }
    }
    

}

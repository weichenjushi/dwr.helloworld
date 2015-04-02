package com.dwr;

import org.directwebremoting.Browser;
import org.directwebremoting.ScriptSession;
import org.directwebremoting.ServerContextFactory;
import org.directwebremoting.WebContextFactory;
import org.directwebremoting.extend.UninitializingBean;
import org.directwebremoting.ui.dwr.Util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by WangLuHui on 2015/3/31.
 */

public class SendData implements Runnable , UninitializingBean {
    private final static String SCRIPT_SESSION_ATTR = "SCRIPT_SESSION_ATTR";
    private  ScheduledThreadPoolExecutor executor=null;

    public SendData() {
        //定义一个执行任务的线程池,池中线程为1
        executor=new ScheduledThreadPoolExecutor(1);
        //60s执行一次
        executor.scheduleAtFixedRate(this,1,60, TimeUnit.SECONDS);
    }

    @Override
    public void run() {
        //执行主任务：及时向客户端发送更新后的数据
        sendDataTimely();
    }

    public void sendDataTimely() {
        //准备数据
        String filePath = "D:/text.txt";
        BufferedReader reader = null;
        final StringBuffer sb = new StringBuffer();
        try {
            reader = new BufferedReader(new InputStreamReader(
                    new FileInputStream(filePath), "UTF-8"));

            String tempString = null;

            while ((tempString = reader.readLine()) != null) {
                sb.append(tempString + "\n\r");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //显示数据  将数据发送到根目录下的showdata.html文件中
        String page = ServerContextFactory.get().getContextPath()
                + "/showdata.html";
        Browser.withPage(page, new Runnable() {
            public void run() {
                //将内容显示到showdata页面中的“id=content”元素中
                Util.setValue("content", sb.toString());
            }
        });

    }

    public void addAttributeToScriptSession() {
        ScriptSession scriptSession = WebContextFactory.get()
                .getScriptSession();
        scriptSession.setAttribute(SCRIPT_SESSION_ATTR, true);
    }

    /**
     * 实现接口 UninitializingBean的两个方法，进行资源的释放
     */
    @Override
    public void contextDestroyed() {

    }

    @Override
    public void servletDestroyed() {
        executor.shutdown();
        try {
            executor.awaitTermination(1, TimeUnit.SECONDS);
        } catch (InterruptedException ex) {
        }
    }
}

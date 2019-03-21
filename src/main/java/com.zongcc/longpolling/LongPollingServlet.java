package com.zongcc.longpolling;

/**
 * @author chunchengzong
 * @date 2018-05-18 20:59
 **/
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by andy on 17/7/6.
 */
public class LongPollingServlet extends HttpServlet {

    private Random random = new Random();

    private AtomicLong sequenceId = new AtomicLong();

    private AtomicLong count = new AtomicLong();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        System.out.println("第" + (count.incrementAndGet()) + "次 longpolling");

        int sleepSecends = random.nextInt(100);
//随机获取等待时间，来通过sleep模拟服务端是否准备好数据

        System.out.println("wait " + sleepSecends + " second");

        try {
            TimeUnit.SECONDS.sleep(sleepSecends);//sleep
        } catch (InterruptedException e) {

        }

        PrintWriter out = response.getWriter();
        long value = sequenceId.getAndIncrement();
        out.write(Long.toString(value));
    }

}
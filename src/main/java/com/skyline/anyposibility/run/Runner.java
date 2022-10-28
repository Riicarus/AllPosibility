package com.skyline.anyposibility.run;

import com.skyline.anyposibility.run.register.RunnerProperties;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * [FEATURE INFO]<br/>
 * 运行器
 *
 * @author Skyline
 * @create 2022-10-28 20:06
 * @since 1.0.0
 */
public class Runner {

    private static final Runtime RUNTIME = Runtime.getRuntime();

    private final RunnerProperties properties = new RunnerProperties();

    public Runner() {
        properties.load();
    }

    public void run(String name) throws IOException, InterruptedException {
        String command = properties.getCommand(name);

        // todo: 改为新建一个 thread 来执行程序, 避免主程序因为执行的程序而阻塞
        // 可能需要一个线程池来进行管理
        Process process = RUNTIME.exec(command);

        BufferedInputStream bis = new BufferedInputStream(process.getErrorStream());
        BufferedReader br = new BufferedReader(new InputStreamReader(bis));

        String lineStr;
        while ((lineStr = br.readLine()) != null) {
            System.out.println(lineStr);
        }

        if (process.waitFor() != 0) {
            if (process.exitValue() == 1) {
                System.out.println("命令执行失败.");
            }
        }

        br.close();
        bis.close();
    }

    public void add(String name, String command) {
        properties.register(name, command);
    }

}

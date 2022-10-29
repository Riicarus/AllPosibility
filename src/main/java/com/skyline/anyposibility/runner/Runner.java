package com.skyline.anyposibility.runner;

import com.skyline.anyposibility.runner.exception.RunApplicationFailed;
import com.skyline.anyposibility.runner.register.RunnerProperties;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

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

    private final ThreadPoolExecutor pool = new ThreadPoolExecutor(
            16, 32, 2,
            TimeUnit.MINUTES, new LinkedBlockingDeque<>());

    public Runner() {
        properties.load();
    }

    public void run(String name) {
        String command = properties.getCommand(name);

        pool.execute(new ThreadRunner(RUNTIME, "Thread-" + name, command));
    }

    public void add(String name, String command) {
        properties.register(name, command);
    }

    public Set<String> listName() {
        return properties.listRegisteredName();
    }

    public HashMap<String, String> listDetail() {
        return properties.getRunnerCommandRegisterMap();
    }

    private static class ThreadRunner implements Runnable {

        private final Runtime RUNTIME;

        private final String name;

        private final String command;

        public ThreadRunner(Runtime RUNTIME, String name, String command) {
            this.RUNTIME = RUNTIME;
            this.name = name;
            this.command = command;
        }

        @Override
        public void run() {
            try {
                Process process = RUNTIME.exec(command);

                BufferedInputStream bis = new BufferedInputStream(process.getErrorStream());
                BufferedReader br = new BufferedReader(new InputStreamReader(bis, StandardCharsets.UTF_8));

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
            } catch (IOException | InterruptedException e) {
                throw new RunApplicationFailed("程序执行失败.", e.getCause());
            }
        }

    }
}

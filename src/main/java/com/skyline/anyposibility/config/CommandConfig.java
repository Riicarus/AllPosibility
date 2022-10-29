package com.skyline.anyposibility.config;

import com.skyline.anyposibility.runner.Runner;
import com.skyline.anyposibility.runner.command.RunnerCommand;
import com.skyline.command.SkyCommand;
import com.skyline.command.manage.ConsoleIOHandler;
import com.skyline.command.manage.IOHandler;

/**
 * [FEATURE INFO]<br/>
 * 命令行配置
 *
 * @author Skyline
 * @create 2022-10-28 19:41
 * @since 1.0.0
 */
public class CommandConfig {

    private static final IOHandler IO_HANDLER = new ConsoleIOHandler();

    private static final SkyCommand SKY_COMMAND = SkyCommand.getSkyCommand(IO_HANDLER);

    public static void enable() {
        SKY_COMMAND.startSkyCommand();
        new RunnerCommand(SKY_COMMAND, new Runner()).defineCommand();
    }

}

package com.skyline.anyposibility.run.command;

import com.skyline.anyposibility.run.Runner;
import com.skyline.anyposibility.run.exception.RunApplicationFailed;
import com.skyline.command.SkyCommand;
import com.skyline.command.argument.StringCommandArgumentType;
import com.skyline.command.command.BaseCommand;

import java.io.IOException;

/**
 * [FEATURE INFO]<br/>
 * 运行器指令
 *
 * @author Skyline
 * @create 2022-10-28 19:43
 * @since 1.0.0
 */
public class RunnerCommand extends BaseCommand {

    private final Runner RUNNER;

    public RunnerCommand(SkyCommand SKY_COMMAND, Runner RUNNER) {
        super(SKY_COMMAND);
        this.RUNNER = RUNNER;
    }

    @Override
    public void defineCommand() {
        SKY_COMMAND.register().execution("runner").action("run")
                .option("name", "n").argument("name", new StringCommandArgumentType())
                .executor(
                        args -> {
                            String name = (String) args[0];
                            try {
                                RUNNER.run(name);
                            } catch (IOException | InterruptedException e) {
                                throw new RunApplicationFailed("程序执行失败.", e.getCause());
                            }
                        }
                );
        SKY_COMMAND.register().execution("runner").action("add")
                .option("name", "n").argument("name", new StringCommandArgumentType())
                .option("command", "c").argument("command", new StringCommandArgumentType())
                .executor(
                        args -> {
                            String name = (String) args[0];
                            String command = (String) args[1];

                            RUNNER.add(name, command);
                        }
                );
    }
}

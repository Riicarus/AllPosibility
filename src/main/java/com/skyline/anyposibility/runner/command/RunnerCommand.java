package com.skyline.anyposibility.runner.command;

import com.skyline.anyposibility.runner.Runner;
import com.skyline.anyposibility.runner.exception.RunApplicationFailed;
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
        SKY_COMMAND.register().execution("R").action("run")
                .option("name", "n").argument("name", new StringCommandArgumentType())
                .executor(
                        args -> {
                            String name = (String) args[0];

                            RUNNER.run(name);
                        }
                );
        SKY_COMMAND.register().execution("R").action("add")
                .option("name", "n").argument("name", new StringCommandArgumentType())
                .option("command", "c").argument("command", new StringCommandArgumentType())
                .executor(
                        args -> {
                            String name = (String) args[0];
                            String command = (String) args[1];

                            RUNNER.add(name, command);
                        }
                );
        SKY_COMMAND.register().execution("R").action("list")
                .option("all", "a").option("name", "n")
                .executor(
                        args -> RUNNER.listName()
                );
        SKY_COMMAND.register().execution("R").action("list")
                .option("all", "a")
                .executor(
                        args -> RUNNER.listDetail()
                );
    }
}

package nikoisntcat.client.managers;

import nikoisntcat.client.commands.Command;
import nikoisntcat.client.events.impl.ChatMessageEvent;
import nikoisntcat.client.utils.PlayerUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class CommandManager {
    private List<Command> field2287 = new ArrayList();

    public void method1876(Command command) {
        this.field2287.add(command);
    }

    public CommandManager() {
        //this.method1876(new Class255());
        //this.method1876(new Class258());
        //this.method1876(new Class251());
        //this.method1876(new Class256());
        //this.method1876(new Class257());
        //this.method1876(new Class253());
    }

    public List method1878() {
        return this.field2287;
    }

    public Command method1879(String commandName) {
        for (Command command : this.field2287) {
            if (!command.getName().equalsIgnoreCase(commandName)) continue;
            return command;
        }
        return null;
    }

    public void onChat(ChatMessageEvent event) {
        if (event.method1421().startsWith("(String)CommandManager.method1881('\u0000')")) {
            event.cancel();
            AtomicBoolean atomicBoolean = new AtomicBoolean(false);
            String[] stringArray = event.method1421().split("(String)CommandManager.method1881('\u0001')");
            if (stringArray.length != 0 && !stringArray[0].isEmpty() && !stringArray[0].equalsIgnoreCase("(String)CommandManager.method1881('\u0000')")) {
                this.field2287.forEach(e -> {
                    if (e.name.equalsIgnoreCase(stringArray[0].substring(1))) {
                        e.method1685(event.method1421());
                        atomicBoolean.set(true);
                    }
                });
            }
            if (!atomicBoolean.get()) {
                PlayerUtil.sendChatMessage("(String)CommandManager.method1881('\u0002')");
            }
        }
    }
}

package nikoisntcat.client.commands;

public abstract class Command {
    public String name;

    public abstract void method1685(String var1);

    public Command(String commandName) {
        this.name = commandName;
    }

    public String getName() {
        return this.name;
    }
}

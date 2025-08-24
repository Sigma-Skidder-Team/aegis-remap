package nikoisntcat.client.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.IOException;

public abstract class Config {
    public static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private final File file;

    public abstract void method1975() throws IOException;

    public File getFile() {
        return this.file;
    }

    public Config(File file) {
        this.file = file;
    }

    public abstract void method1974() throws IOException;

    public void method1978() throws IOException {
        this.file.createNewFile();
    }

    public boolean method1979() {
        return this.file.exists();
    }
}
package phi.ap.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import phi.ap.model.App;
import phi.ap.model.Game;
import phi.ap.model.User;
import phi.ap.model.data.AppData;
import phi.ap.model.data.LoggedInUser;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileManager {
    private final String path = System.getProperty("user.home") + "/.stardewValley/";
    private final String appDataPath = path + "appData.json";

    public void writeAppData(){
        createIfNotExists();
        AppData data = AppData.build();
        try {
            new java.io.File(appDataPath).getParentFile().mkdirs();
            FileWriter writer = new FileWriter(appDataPath);
            new Gson().toJson(data, writer);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public AppData readAppData(){
        createIfNotExists();
        try (FileReader reader = new FileReader(appDataPath)) {
            Gson gson = new Gson();
            return gson.fromJson(reader, AppData.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    private void createIfNotExists() {
        File baseDirectory = new File(path);
        if(!baseDirectory.exists()){
            baseDirectory.mkdirs();
        }
        File appData = new File(appDataPath);
        if(!appData.exists()){
            try (FileWriter writer = new FileWriter(appData)) {
                writer.write("{}");
            }catch (IOException ignored){}
        }
    }
}

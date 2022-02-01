package me.nanorasmus;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SaveManager {
    Main main;

    public Gson gson = new GsonBuilder().setPrettyPrinting().create();


    String schemPath = "plugins/NanoSchem/";

    public SaveManager(Main main) throws IOException {
        this.main = main;

        // Initialize Save Folder
        if (Files.notExists(Paths.get("plugins/NanoSchem")))
            Files.createDirectory(Paths.get("plugins/NanoSchem"));

    }

    public Structure Load(String name) {
        StructureRaw structureRaw;
        try {
            structureRaw = gson.fromJson(Files.readString(Path.of(schemPath + name + ".nano")), (Type) StructureRaw.class);
        } catch (IOException e) {
            return null;
        }
        return new Structure(structureRaw);
    }

    public void Save(Structure structure, String name) throws IOException {
        String buildJSON = gson.toJson(structure.Export());
        Path dest = Path.of(schemPath + name + ".nano");
        Files.deleteIfExists(dest);
        Files.write(dest, buildJSON.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.WRITE);
    }
}

package de.skeletoneye.creative.api.data;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.World;
import org.bukkit.entity.Player;

import com.google.gson.Gson;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class DataFile<T extends DataObject>
{
    public static final Path DATA_FOLDER = Paths.get("creative");

    public static <T extends DataObject> DataFile<T> of(Class<T> type, Player player) throws IOException
    {
        return DataFile.of(type, player.getWorld());
    }

    public static <T extends DataObject> DataFile<T> of(Class<T> type, World world) throws IOException
    {
        Path directory = world.getWorldFolder().toPath().resolve(DataFile.DATA_FOLDER).resolve(type.getName());
        Files.createDirectories(directory);
        return new DataFile<T>(directory, world, type);
    }

    private @Getter Path directory;
    private @Getter World world;

    private Class<T> type;

    private Path getPath(String name)
    {
        return this.getDirectory().resolve(name + ".json");
    }

    public List<String> list() throws IOException
    {
        List<String> list = new ArrayList<>();

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(this.getDirectory())) {
            for (Path entry : stream) {
                if (!Files.isRegularFile(entry) || !entry.getFileName().endsWith(".json")) {
                    continue;
                }

                list.add(entry.getFileName().toString());
            }
        }

        return list;
    }

    public T read(String name) throws IOException
    {
        return (new Gson()).fromJson(new String(Files.readAllBytes(this.getPath(name))), this.type);
    }

    public Map<String, T> readAll() throws IOException
    {
        Map<String, T> dataObjects = new HashMap<>();
        Gson gson = new Gson();

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(this.getDirectory())) {
            for (Path entry : stream) {
                if (!Files.isRegularFile(entry) || !entry.getFileName().endsWith(".json")) {
                    continue;
                }

                T object = gson.fromJson(new String(Files.readAllBytes(entry)), this.type);
                object.setContext(this.getWorld());
                dataObjects.put(object.getName(), object);
            }
        }

        return dataObjects;
    }

    public void write(T object) throws IOException
    {
        Path path = this.getPath(object.getName());
        Files.deleteIfExists(path);
        Files.write(path, (new Gson()).toJson(object).getBytes());
    }
}

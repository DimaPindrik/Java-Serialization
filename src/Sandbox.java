import com.devDim.GameDev.serialization.NCDatabase;
import com.devDim.GameDev.serialization.NCField;
import com.devDim.GameDev.serialization.NCObject;
import com.devDim.GameDev.serialization.NCString;

import java.util.ArrayList;
import java.util.List;

/**
 * Serialization Demonstration Class
 * The output is a binary file holding all the data of the elements of this class
 */
public class Sandbox {

    static class Level {
        private String name;
        private String path;
        private int width, height;
        private List<Entity> entities = new ArrayList<Entity>();

        private Level() { }

        public Level(String path) {
            this.name = "Level 1";
            this.path = path;
            width = 64;
            height = 128;
        }

        public void add(Entity e) {
            e.init(this);
            entities.add(e);
        }

        public void update() {
        }

        public void render() {
        }

        public void save(String path) {
            System.out.println("Serializing level \"" + path + "\"...");
            NCDatabase database = new NCDatabase(name);

            NCObject object = new NCObject("LevelData");
            object.addString(NCString.String("name", name));
            object.addString(NCString.String("path", this.path));
            object.addField(NCField.Integer("width", width));
            object.addField(NCField.Integer("height", height));
            object.addField(NCField.Integer("entityCount", entities.size()));
            database.addObject(object);

            for (int i = 0; i < entities.size(); i++) {
                Entity e = entities.get(i);
                NCObject entity = new NCObject("E" + i);
                byte type = 0;
                if (e instanceof Player)
                    type = 1;
                entity.addField(NCField.Byte("type", type));
                entity.addField(NCField.Integer("arrayIndex", i));
                e.serialize(entity);
                database.addObject(entity);
            }

            database.serializeToFile(path);
        }

        public static Level load(String path) {
            System.out.println("Deserializing level \"" + path + "\"...");

            NCDatabase database = NCDatabase.DeserializeFromFile(path);
            NCObject levelData = database.findObject("LevelData");

            Level result = new Level();
            NCString s = levelData.findString("name");
            result.name = levelData.findString("name").getString();
            result.path = levelData.findString("path").getString();
            result.width = levelData.findField("width").value();
            result.height = levelData.findField("height").value();
            int entityCount = levelData.findField("entityCount").value();

            byte type = 0;
            for (int i = 0; i < entityCount; i++) {
                NCObject entity = database.findObject("E" + i);
                Entity e;
                type = entity.findField("type").value();
                if (type == 1)
                    e = Player.deserialize(entity);
                else
                    e = Entity.deserialize(entity);
                result.add(e);
            }
            return result;
        }
    }

    static class Entity {
       protected int x, y;
       protected boolean removed = false;
       protected Level level;

       public Entity() {
           this.x = 0;
           this.y = 0;
       }

        public void init(Level level) {
            this.level = level;
        }

        public void serialize(NCObject object) {
            object.addField(NCField.Integer("x", x));
            object.addField(NCField.Integer("y", y));
            object.addField(NCField.Boolean("removed", removed));
        }

        public static Entity deserialize(NCObject object) {
           Entity result = new Entity();
           result.x = object.findField("x").value();
           result.y = object.findField("y").value();
           result.removed = object.findField("removed").value();
           return result;
        }
    }

    static class Player extends Entity {
        private String name;
        private String avatarPath;

        private Player() { }

        public Player(String name, int x, int y) {
            this.x = x;
            this.y = y;

            this.name = name;
            this.avatarPath = "res/avatar.png";
        }

        public void serialize(NCObject object) {
            super.serialize(object);
            object.addString(NCString.String("name", name));
            object.addString(NCString.String("avatarPath", avatarPath));
        }

        public static Player deserialize(NCObject object) {
            Entity e = Entity.deserialize(object);
            Player result = new Player();
            result.x = e.x;
            result.y = e.y;
            result.removed = e.removed;

            result.name = object.findString("name").getString();
            result.avatarPath = object.findString("avatarPath").getString();
            return result;
        }
    }

    public void play() {
        {
            //****** Serialize ******//
            Entity mob = new Entity();
            Player player = new Player("Dima", 40, 58);
            Level level = new Level("res/level.lvl");
            level.add(mob);
            level.add(player);

            level.save("level.ncd");
        }
        {
            //****** Deserialize ******//
            Level level = Level.load("level.ncd");
            System.out.println();
        }
    }

}

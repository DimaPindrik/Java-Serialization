import com.devDim.GameDev.serialization.*;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Main {

    public static void printBytes(byte[] data) {
        for (int i = 0; i < data.length; i++) {
            System.out.printf("0x%x-", data[i]);
        }
    }



    public static void serializationTest() {
        NCDatabase database = new NCDatabase("Database");

        NCArray array = NCArray.Integer("TestNumbers", new int[] {1, 2, 3, 4, 5});
        NCString string = NCString.String("String", "Hello");
        NCField field = NCField.Integer("Integer", 8);
        NCField xfield = NCField.Short("xShort", (short)100);
        NCField yfield = NCField.Short("yShort", (short)2);
        NCField bool = NCField.Boolean("Boolean", false);

        NCObject object1 = new NCObject("Entity");
        NCObject object2 = new NCObject("Player");
        NCObject object3 = new NCObject("Mob");
        object1.addArray(array);
        object1.addString(string);
        object1.addField(field);
        object1.addField(xfield);
        object1.addField(yfield);
        object2.addField(xfield);
        object2.addField(bool);
        object3.addField(yfield);

        database.addObject(object1);
        database.addObject(object2);
        database.addObject(object3);
        System.out.println("FIELD:" + field.getSize());
        System.out.println("OBJECT:" + object1.getSize());
        System.out.println("DATABASE:" + database.getSize());

        database.serializeToFile("test.ncd");
    }

    public static void deserializationTest() {
        NCDatabase database = NCDatabase.DeserializeFromFile("test.ncd");

        System.out.println("HEADER         : " + new String(database.HEADER));
        System.out.println("VERSION        : " + database.VERSION);
        System.out.println("Container-type : " + database.CONTAINER_TYPE);
        System.out.println("Name           : " + database.getName());
        System.out.println("Size           : " + database.getSize());
        System.out.println("Object-amount  : " + database.objectCount);
        for (NCObject object : database.objects) {
            System.out.println("Object : " + object.getName());
            for (NCField field : object.fields)
                System.out.println("\t" + field.getName());
            for (NCString string: object.strings)
                System.out.println("\t" + string.getName());
            for (NCArray array: object.arrays)
                System.out.println("\t" + array.getName());
        }
        System.out.println("--------------------------------------------------");
    }

    public static void main(String[] args) {
        Sandbox sandbox = new Sandbox();
        sandbox.play();
    }
}

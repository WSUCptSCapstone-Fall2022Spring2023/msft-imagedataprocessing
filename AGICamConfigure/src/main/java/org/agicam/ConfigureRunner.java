package org.agicam;

import com.mongodb.*;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;


import static com.mongodb.client.model.Filters.eq;

public class ConfigureRunner {
    public static void main(String[] args) throws IOException {
        // First argument should be the location of file to write configuration too.
        // Second argument should be connection string
        // Third argument
        if(args.length != 2)
        {
            System.out.println("Invalid arguments. Please supply (config location) (mongo connection string)");
            return;
        }

        // Collect arguments
        String configLocation = args[0];
        String connection = args[1];
        Integer cameraNumber;

        // Connect to MongoDatabase
        ConnectionString connectionString = new ConnectionString(connection);
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .serverApi(ServerApi.builder()
                        .version(ServerApiVersion.V1)
                        .build())
                .build();

        // Get client
        MongoClient mongoClient = MongoClients.create(settings);
        MongoDatabase database = mongoClient.getDatabase("test");

        // Get / Create collection
        MongoCollection<Document> configCollection = database.getCollection("configs");

        cameraNumber = getCamNumber(configCollection);

        // Find the config for this camera
        Document config = configCollection.find(eq("camID", cameraNumber)).first();
        //configCollection.deleteOne(eq("_id", config.getObjectId("_id")));
        if(config == null)
        {
            config = getDefaultConfig(cameraNumber);
            configCollection.insertOne(config); // Write to remote the configuration for this device.
        }

        //Test for time specific configurations


        if(config.getBoolean("changed").equals(true))
       {
            // Update config locally
            updateConfig(configLocation, config);
            config.replace("changed",false);
            configCollection.replaceOne(eq("_id", config.getObjectId("_id")), config);
       }
    }

    private static int getCamNumber(MongoCollection<Document> configCollection) throws IOException {
        File file = new File("./cameraNumber.txt");
        long numConfigs;
        int camNumber;

        //Create file if it doesn't exist
        if(!file.exists())
        {
            file.createNewFile();

            //Count number of config documents
            numConfigs = configCollection.countDocuments();

            //write to file
            while(configCollection.find(eq("camID", numConfigs)).first() != null)
            {
                numConfigs++;
            }
            camNumber = (int)numConfigs;

            FileOutputStream fos = new FileOutputStream(file.getName());
            BufferedWriter bs = new BufferedWriter(new OutputStreamWriter((fos)));
            bs.write("" + numConfigs);
            bs.close();

        }
        else
        {
            BufferedReader reader = new BufferedReader(new FileReader(file.getName()));
            String line = reader.readLine();
            reader.close();
            camNumber = Integer.parseInt(line);
        }
        return camNumber;
    }

    private static Document getDefaultConfig(int cameraNumber)
    {
        Document configDoc = new Document();
        configDoc.put("camID", cameraNumber); // Id of the camera
        //configDoc.put("type", "select"); // "select" allows for selection of picture times (uneven intervals).
                                            // "interval" allows for pictures taken at even intervals.
        //configDoc.put("start", 600); // What time do we start? In minutes (600 = 10 a.m). Every config needs this.
        //configDoc.put("distance", 120); // How long between each capture in minutes. For interval.
        configDoc.put("amount", 2); // Amount of captures in day
        configDoc.put("duration", 1); //How long to leave sensor on. This should not change.
        //configDoc.put("intervalBetweenPics", 360); //How long between pictures. MUST BE AT LEAST 30MINS
        configDoc.put("changed", true); //Was the configuration updated? If so the wittyPi schedule needs to be updated

        configDoc.put("times", Arrays.asList(600, 630, 720, 780)); // STORES AS TYPE ArrayList! Takes pictures at 10, 10:30, 12, and 1

        return configDoc;
    }


    private static void updateConfig(String configLocation, Document doc) throws IOException {
        File localConfig = new File(configLocation); // find the local file
        FileWriter writer = new FileWriter(localConfig);

        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Date sDate = new Date();
        Date eDate = new Date();
        //List<Integer> times = null;

        ArrayList<Integer> times = new ArrayList<Integer>();

        times = doc.get("times", ArrayList.class);

//        if(doc.getString("type").equals("interval"))
//        {
//            int offInterval = doc.getInteger("intervalBetweenPics") - doc.getInteger("duration");
//
//            sDate.setHours(doc.getInteger("start")/60);
//            sDate.setMinutes(doc.getInteger("start")%60);
//            sDate.setSeconds(0);
//            String sDateStr = formatter.format(sDate);
//
//            eDate.setYear(sDate.getYear() + 5);
//            eDate.setHours(doc.getInteger("start")/60);
//            eDate.setMinutes(doc.getInteger("start")%60);
//            eDate.setSeconds(0);
//            String eDateStr = formatter.format(eDate);
//
//            writer.write("BEGIN " + sDateStr + System.lineSeparator());
//            writer.write("END " + eDateStr + System.lineSeparator());
//
//            int totalTime = doc.getInteger("start");
//
//            for (int i = 0; i < doc.getInteger("amount") && totalTime < 60 * 24; i++)
//            {
//                int onHours = doc.getInteger("duration") / 60;
//                int onMins = doc.getInteger("duration") % 60;
//                int offHours = (doc.getInteger("intervalBetweenPics") - doc.getInteger("duration")) / 60;
//                int offMins = (doc.getInteger("intervalBetweenPics") - doc.getInteger("duration")) % 60;
//
//                totalTime = totalTime + (onHours * 60) + onMins;
//                totalTime = totalTime + (offHours * 60) + offMins;
//
//                if(totalTime < 60*24)
//                {
//                    //Write ON times
//                    writer.write("ON ");
//                    //if (onHours > 0)
//                    //{
//                    writer.write("H" + onHours + " ");
//                    //}
//                    //if (onMins > 0)
//                    //{
//                    writer.write("M" + onMins);
//                    //}
//                    writer.write(System.lineSeparator());
//
//
//                    //Write OFF times
//                    writer.write("OFF ");
//                    writer.write("H " + offHours + " ");
//                    writer.write("M " + offMins + System.lineSeparator());
//                }
//
//
//            }
//
//        }

        writer.close();
    }
}
package org.agicam;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;


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

    // This function either gets the cam number from the ./cameraNumber.txt file, or creates one based on
    // how many config documents there are in the config collection.
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
        Document doc = new Document();
        doc.put("camID", cameraNumber); // Id of the camera
        doc.put("type", "interval"); // Are we capturing on intervals?
        doc.put("start", 600); // What time do we start? In minutes (600 = 10 a.m)
        doc.put("distance", 120); // How long between each capture in minutes
        doc.put("amount", 2); // Amount of captures in day
        doc.put("duration", 5); //How long to leave sensor on
        doc.put("intervalBetweenPics", 360); //How long between pictures. MUST BE AT LEAST 30MINS
        doc.put("changed", true); //Was the configuration updated? If so the wittyPi schedule needs to be updated
        return doc;
    }

    //private static Document get
    

    private static void updateConfig(String configLocation, Document doc) throws IOException {
        File localConfig = new File(configLocation); // find the local file
        FileWriter writer = new FileWriter(localConfig);

        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Date sDate = new Date();
        Date eDate = new Date();

        if(doc.getString("type").equals("interval"))
        {
            String B = "BEGIN";
            String E = "END";
            int offInterval = doc.getInteger("intervalBetweenPics") - doc.getInteger("duration");

            sDate.setHours(doc.getInteger("start")/60);
            sDate.setMinutes(doc.getInteger("start")%60);
            sDate.setSeconds(0);
            String sDateStr = formatter.format(sDate);

            eDate.setYear(sDate.getYear() + 5);
            eDate.setHours(doc.getInteger("start")/60);
            eDate.setMinutes(doc.getInteger("start")%60);
            eDate.setSeconds(0);
            String eDateStr = formatter.format(eDate);

            writer.write(B + " " + sDateStr + System.lineSeparator());
            writer.write(E + " " + eDateStr + System.lineSeparator());

            int totalTime = doc.getInteger("start");

            for (int i = 0; i < doc.getInteger("amount") && totalTime < 60 * 24; i++)
            {
                int onHours = doc.getInteger("duration") / 60;
                int onMins = doc.getInteger("duration") % 60;
                int offHours = (doc.getInteger("intervalBetweenPics") - doc.getInteger("duration")) / 60;
                int offMins = (doc.getInteger("intervalBetweenPics") - doc.getInteger("duration")) % 60;

                totalTime = totalTime + (onHours * 60) + onMins;
                totalTime = totalTime + (offHours * 60) + offMins;

                if(totalTime < 60*24)
                {
                    //Write ON times
                    writer.write("ON ");
                    //if (onHours > 0)
                    //{
                    writer.write("H" + onHours + " ");
                    //}
                    //if (onMins > 0)
                    //{
                    writer.write("M" + onMins);
                    //}
                    writer.write(System.lineSeparator());


                    //Write OFF times
                    writer.write("OFF ");
                    writer.write("H " + offHours + " ");
                    writer.write("M " + offMins + System.lineSeparator());
                }


            }

        }
        writer.close();
    }
}
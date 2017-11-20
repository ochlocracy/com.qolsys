package sensors;

import au.com.bytecode.opencsv.CSVReader;
import org.testng.annotations.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class CSV {

    //public static void main(String[] args) throws IOException {
    public void read_sensors_from_csv() throws IOException {

        List<SensorObject> sensor_object_list = new ArrayList();

        SensorObject sensor;

        BufferedReader reader = new BufferedReader(new FileReader( "home/qolsys/Desktop/test.csv"));
        String line = null;
        Scanner scanner = null;
        int index = 0;
        while ((line = reader.readLine()) != null) {


            //CSV emp = new CSV();

            scanner = new Scanner(line);

            int zone = 0;
            String dlid = "";
            String sensor_type = "";
            int sensor_group = 0;
            int protocol = 0;

            scanner.useDelimiter(",");
            while (scanner.hasNext()) {
                String data = scanner.next();
                if (index == 0)
                    zone = Integer.parseInt(data);
                else if (index == 1)
                    dlid = data;
                else if (index == 2)
                    sensor_type = data;
                else if (index == 3)
                    sensor_group = Integer.parseInt(data);
                else if (index == 4)
                    protocol = Integer.parseInt(data);
                else
                    System.out.println("invalid data::" + data);
                index++;
            }
            index = 0;

            //sensor = new SensorObject(zone, dlid, sensor_type, sensor_group, protocol);

            // sensor_object_list.add(sensor);
        }
        reader.close();

        System.out.println(sensor_object_list);
    }

    public void getWholeColumn (int index) throws Exception {
        String csvFile = "/home/anya/Desktop/test.csv";
        CSVReader reader = new CSVReader( new FileReader(csvFile));
        String[] col = null;
        while ((col = reader.readNext()) != null){
            System.out.println(col[index]);
        }
        reader.close();
    }

    @Test
    public void Test ()throws Exception {
        getWholeColumn( 1 );
    }

    public void getWholeCSV () throws Exception {
        CSVReader reader = new CSVReader( new FileReader( "/home/anya/Desktop/test.csv" ) );
        // this will load content into list
        List<String[]> li = reader.readAll();
        System.out.println( "Total rows which we have is " + li.size() );
        // create Iterator reference
        Iterator<String[]> i1 = li.iterator();
        // Iterate all values
        while (i1.hasNext()) {
            String[] str = i1.next();
            for (int i = 0; i < str.length; i++) {
                System.out.print( " " + str[i] );
            }
            System.out.println( "   " );
        }
    }
}
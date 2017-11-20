//package ADC;
//
//import org.testng.annotations.Test;
//
///**
// * Created by qolsys on 11/14/17.
// */
//public class SensorObject {
//
//    public String group;
//    public int zone;
//
//    private SensorObject(SensorBuilder builder){
//        this.group = builder.group;
//        this.zone = builder.zone;
//    }
//
//    public static class SensorBuilder{
//
//        public String group;
//        public int zone;
//
//    }
//
//    public SensorBuilder setGroup(String group){
//        this.group = group;
//        return this;
//    }
//
//    public SensorBuilder setZone(int zone){
//        this.zone = zone;
//        return this;
//    }
//
//    public SensorObject build(){
//        return new SensorObject(this);
//    }
//
//}

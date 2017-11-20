package iqRemote;

/**
 * Created by qolsys on 9/18/17.
 */
public class EventContains {

    public static final String Exception = "Caught Exception";

    public static final String client_crt = "receiveFileOverSocket() ***** File /data/security/client.crt downloaded";
    public static final String ca_crt = "receiveFileOverSocket() ***** File /data/security/ca.crt downloaded";
    public static final String Certificate_exchange_complete = "ServerSocket:: listenToClient() completed";

    public static final String Initialization = "RemoteMqttClient:: connectToBroker() Mqtt client is initialized with clientID:";
    public static final String Attempt_to_connect = "RemoteMqttClient:: connectToBroker() client created but not connected, doing connect";

    public static final String DB_sync = "IQ2RemoteUiEventReceiver:: onReceive() eventType:dbsyncCompleted";

    public static final String ARM_STAY_Ui = "T3HomeFragment:: setPanelStatusIcons() curPanelStatus : ARM-STAY";
    public static final String DISARM_event = "CCObserver:: contentValues: {\"name\":\"SYSTEM_STATUS\",\"value\":\"DISARM\"}";
    public static final String DISARM_Ui = "T3HomeFragment:: setPanelStatusIcons() curPanelStatus : DISARM";
}

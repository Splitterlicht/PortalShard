package grafnus.portalshard.database.data;

public class LinkData {

    private PortalData[] portals;
    private ConnectionData connection;
    //private PermissionData permission;

    public LinkData (PortalData[] portals, ConnectionData connection) {
        this.portals = portals;
        this.connection = connection;
    }

}

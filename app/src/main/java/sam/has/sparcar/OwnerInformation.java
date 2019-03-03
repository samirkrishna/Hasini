package sam.has.sparcar;

public class OwnerInformation {
    public String name;
    public String address;
    public String govtid;
    public double lati;
    public double longi;
    public String costperhour;
    public String slottype;
    String id;
    String status;

    public OwnerInformation()
    {

    }

    public OwnerInformation(String id,String name, String address, String govtid, double latitude, double longitude, String costperhour, String slottype,String status)
    {
            this.name=name;
            this.address=address;
            this.govtid=govtid;
            this.lati=latitude;
            this.longi=longitude;
            this.costperhour=costperhour;
            this.slottype=slottype;
            this.id=id;
            this.status=status;
    }

}

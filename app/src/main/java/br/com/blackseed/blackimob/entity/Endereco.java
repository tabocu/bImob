package br.com.blackseed.blackimob.entity;

/**
 * Created by nilol_000 on 18/05/2016.
 */
public class Endereco  extends Item {

    private String placeId;
    private String local;
    private String complemento;
    private double longitude;
    private double latitude;

    public String getPlaceId() { return placeId; }

    public void setPlace_id(String place_id) { this.placeId = place_id; }

    public String getLocal() { return local; }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
}

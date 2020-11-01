package design.kfu.entity.dto;

public class SongForm {
    private int id;
    private String name;
    private String urlOrig;

    public SongForm(int id, String name, String urlOrig) {
        this.id = id;
        this.name = name;
        this.urlOrig = urlOrig;
    }

    public SongForm() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrlOrig() {
        return urlOrig;
    }

    public void setUrlOrig(String urlOrig) {
        this.urlOrig = urlOrig;
    }
}

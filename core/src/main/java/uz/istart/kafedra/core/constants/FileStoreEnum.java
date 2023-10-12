package uz.istart.kafedra.core.constants;


public enum FileStoreEnum {

    User("/user");

    FileStoreEnum(String folder){
        this.folder = folder;
    }

    private String folder;

    public String getFolder() {
        return folder;
    }
}

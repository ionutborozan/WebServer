package server;

import java.util.ArrayList;
import java.util.List;

public class PageAndDirectoryModel {

    public List<String> defaultDir = new ArrayList<>();
    public List<String> defaultPage = new ArrayList<>();

    public PageAndDirectoryModel(List<String> defaultDir, List<String> defaultPage) {
        this.defaultDir = defaultDir;
        this.defaultPage = defaultPage;
    }

    public PageAndDirectoryModel() {

    }

    public void setDefaultDir(List<String> defaultDir) {
        this.defaultDir = defaultDir;
    }

    public void setDefaultPage(List<String> defaultPage) {
        this.defaultPage = defaultPage;
    }

    public List<String> getDefaultDir() {
        return defaultDir;
    }

    public List<String> getDefaultPage() {
        return defaultPage;
    }
}

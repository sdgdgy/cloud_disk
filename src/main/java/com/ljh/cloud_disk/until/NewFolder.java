package com.ljh.cloud_disk.until;

import java.io.File;

public class NewFolder {
    public void NewFolder(String path) {
        File realPath = new File(path);
        if (!realPath.exists()){
            realPath.mkdir();
        }
    }

    public NewFolder() {
    }
}

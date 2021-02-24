
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;


public class FileOperator {
    private Console cons;
    private char[] illegalchars = {92, 47, 58, 63, 42, 34, 60, 62, 124};
    private String illegalcharsstring = "\\ / : ? * \" < > |";

    public FileOperator(Console con) {
        this.cons = con;
    }
    public void list(String path){
        File path1 = new File(path);
        if(path1.exists() && path1.isDirectory()){
            String[] lista = path1.list();
            if(lista.length == 0){
                cons.sendMessage("Folder is empty.");
            } else {
                for (String s : lista) {
                    cons.sendMessage(s);
                }
            }
        } else {
            cons.sendMessage("Wrong path entered!");
        }
    }

    public void info(String path){
        File path1 = new File(path);
        if(path1.exists()){
            cons.sendMessage("Name: " + path1.getName());
            cons.sendMessage("Absolute path: " + path1.getAbsolutePath());
            cons.sendMessage("Relative path: " + path1.getPath());
            cons.sendMessage("Size: " + path1.length());
            Path p = Paths.get(path);
            try {
                BasicFileAttributes bfa = Files.readAttributes(p, BasicFileAttributes.class);
                cons.sendMessage("Created: " + time(bfa.creationTime().toMillis()));
            } catch (IOException ex) {
                cons.sendMessage(ex.toString());
            }
            cons.sendMessage("Last Modified: " + time(path1.lastModified()));
        } else {
            cons.sendMessage("Wrong path entered!");
        }
    }

    public String time(long l){
        Instant instant = Instant.ofEpochMilli(l);
        LocalDateTime dateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd. MMMM yyyy. HH:mm:ss");
        return dateTime.format(dateTimeFormatter);
    }

    public void createDir(String path){
        File folder = new File(path);
        String s = folder.getName();
        boolean illegalChar = checkForIllegalChars(s);
        try {
            if(!folder.exists()){
                if(illegalChar){
                    cons.sendMessage("A file name can't contain any of the following characters: ");
                    cons.sendMessage(illegalcharsstring);
                } else {
                    makeDir(folder.getParentFile().exists(), folder);
                    cons.sendMessage("Created a folder called " + folder.getName());
                }
            } else {
                cons.sendMessage("Folder called " + folder.getName() + " already exists.");
            }
        } catch (Exception e) {
            cons.sendMessage("Couldn't create a folder called " + folder.getName());
        }
    }

    private boolean checkForIllegalChars(String s) {
        for (char kh : illegalchars) {
            if (s.indexOf(kh) >= 0) {
                return true;
            }
        }
        return false;
    }

    public void rename(String of, String nf) {
        File oldFile = new File(of);
        String[] strings = of.split("\\\\+");
        int n = strings.length;
        strings[n-1] = nf;
        StringBuilder sb = new StringBuilder();
        sb.append(strings[0]);
        sb.append("\\");
        for(int i = 1; i <strings.length; i++){
            sb.append("\\");
            sb.append(strings[i]);
        }
        File newFile = new File(sb.toString());
        if(newFile.exists()){
            cons.sendMessage("File with desired name already exists!");
            return;
        }
        if(oldFile.renameTo(newFile)){
            cons.sendMessage("Rename succesful.");
        } else {
            cons.sendMessage("Rename failed!");
        }
    }

    public void copyCut(File f1, File f2, String c){
        try (FileInputStream inStream = new FileInputStream(f1);
             FileOutputStream outStream = new FileOutputStream(f1);) {
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inStream.read(buffer)) > 0) {
                outStream.write(buffer, 0, length);
            }
            inStream.close();
            outStream.close();
            if("move".equals(c)){
                f1.delete();
                cons.sendMessage("Moving is successfuly finished.");
            } else {
                cons.sendMessage("Copying is successfuly finished.");
            }
        } catch (IOException e) {
            if("move".equals(c)){
                cons.sendMessage("Moving failed!");
            } else {
                cons.sendMessage("Copying failed!");
            }
        }
    }

    public void delete(String path){
        File file = new File(path);
        if(file.exists()){
            if(file.isFile()){
                file.delete();
                cons.sendMessage("File successfully deleted!");
            } else {
                deleteDir(file);
                cons.sendMessage("Folder successfully deleted!");
            }
        } else {
            cons.sendMessage("Cannot delete " + file.getName() + " because " + file.getName() + " does not exist on this path.");
        }
    }

    public void deleteDir(File f){
        File[] files = f.listFiles();
        if(files != null){
            for(File f1 : files){
                deleteDir(f1);
            }
        }
        f.delete();
    }

    public void copyCutDir(File f1, File f2, String c) {
        if(!f2.exists()){
            f2.mkdir();
        }
        String fs[] = f1.list();
        for (String f : fs) {
            copyCutDir(new File(f1, f), new File(f2, f), c);
        }
        if("move".equals(c)){
            deleteDir(f1);
        }
    }

    public void makeDir(boolean b, File f){
        if(b){
            f.mkdir();
        } else {
            f.mkdirs();
        }
    }
}
import java.io.*;

public class FileManager {

    private Console console;
    private FileOperator fileops;
    private boolean running = true;

    public FileManager(Console c) {
        this.console = c;
        this.fileops = new FileOperator(c);
    }

    public static void main(String[] args) {
        FileManager app = new FileManager(new Console());
        int status = app.handleUserInput();
        java.lang.System.exit(status);
    }

    public Integer handleUserInput() {
        this.sendIntro();
        String message = "Enter a desired command: ";

        while (this.running) {
            String str = console.promptForString(message);
            if (null == str || str.equals("")) {
                console.sendMessage("Did nothing, as you commanded.");
            } else if("help".equals(str)){
                this.sendIntro();
            } else {
                this.processCommand(str);
            }
        }
        console.sendMessage("You're exiting File Manager.");
        return 0; // normal exit
    }

    private void processCommand(String command) {
        switch (command) {
            case "list":
                fileops.list(console.promptForString("Enter folder's path: "));
                break;
            case "info":
                fileops.info(console.promptForString("Enter desired file/folder's path: "));
                break;
            case "mkdir":
                fileops.createDir(console.promptForString("Enter new folder's path: "));
                break;
            case "rename":
                this.renameFile(command);
                break;
            case "copy":
            case "move":
                this.performCopyMove(command);
                break;
            case "delete":
                fileops.delete(console.promptForString("Enter path of file/folder you want to delete: "));
                break;
            case "quit":
                running = false;
                break;
            default:
                console.sendMessage("Command you entered, doesn't exist! (enter HELP for available commands)");
                break;
        }
    }

    private void renameFile(String str) {
        String s1 = console.promptForString("Enter path of file/folder you want to rename: ");
        File f = new File(s1);
        if(!f.exists()){
            console.sendMessage("File doesn't exists!");
            return;
        }
        String s2 = console.promptForString("Enter new name of file/folder: ");
        fileops.rename(s1, s2);
    }

    private void performCopyMove(String command) {
        String s1 = console.promptForString("Enter path of file/folder you would like to copy/move: ");
        String s2 = console.promptForString("Enter destination path: ");
        File f1 = new File(s1);
        File f2 = new File(s2);
        if(f1.isDirectory()){
            try{
                fileops.copyCutDir(f1, f2, command);
                if("move".equals(command)){
                    console.sendMessage("Moving is successfuly finished.");
                } else {
                    console.sendMessage("Copying is successfuly finished.");
                }
            } catch(Exception e) {
                if("move".equals(command)){
                    console.sendMessage("Moving failed!");
                } else {
                    console.sendMessage("Copying failed!");
                }
            }
        } else {
            fileops.copyCut(f1, f2, command);
        }
    }

    private void sendIntro() {
        String commands = "Available commands: \nlist, info\nmkdir, rename\ncopy, move, delete\nquit\n";
        console.sendMessage(commands);
    }
}
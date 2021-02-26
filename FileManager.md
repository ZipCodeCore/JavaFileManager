# FileManager.java

What in the world does it do?

1. Handles the input from a user. Seems to be like a text box input
2. While it's running, there are 3 options for results to be yielded. If null or empty, then it says the message that it did nothing. If the user enters "help" it will give the user the list of available commands. Everything else, it will do the command.
3. If one of the available commands is entered, it goes into the switch statement to do whatever action is assigned to that command. Unless they enter a command not listed, in which case the system tells them to use the "help" command
4. If the action is renameFile, it goes into the renameFile method which asks for the file/folder path that should be renamed, but will let you know if that destination doesn't exist. Then asks for new name.
5. If the action is performCopyMove it enters the performCopyMove method which asks for the file/folder the user would like to move and then the destination to move it to. It will then try the copy or move. The program will then tell the user if the move or copy has failed or succeeded.
7. It then exits File Manager and tells the user that.


# File Operator

What does it do?

This is ultimately file and directory interaction. Copying, pasting, moving them, deleting them, creation, the stats like date modified and created, what is allowed in names of files, checking for illegal characters,
 
1. Obtaining info of a file. If the path entered exists, it prints to the system the name, absolute path, relative path, and length. It tries to then find the info for when it was created and last modified to print that as well. Otherwise it tells you the wrong message was entered.
2. public String time(long l) is simply making it possible to find the time based on timezones and how to format the date and time.
3. public void createDir checks to make sure that a folder can be created and does not use illegal chars, does not have a file already named that, or if it is safe to be created


# Console

What is it's purpose?

It is the basic form of printing messages to the system. So anytime a message pops up about a file being moved, a directory being made, an illegal character being used etc, this is used as the basis for printing the message.

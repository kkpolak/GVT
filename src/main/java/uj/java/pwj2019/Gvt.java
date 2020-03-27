package uj.java.pwj2019;

import java.io.*;
import java.util.ArrayList;

class GitFile
{
    private int version;
    private String nrFile;
    private String messageDefault;
    private String message;

    GitFile(int v, String msd, String ms) {
        this.version = v;
        this.messageDefault = msd;
        this.message = changeString(ms);
        nrFile = "";
    }
    GitFile(int v, String msd) {
        this.version = v;
        this.messageDefault = msd;
        this.message = "-";
        nrFile = "";
    }

    void setNrFile(int v, String name) {
        nrFile = v + name;
    }

    String getNrFile() {
        return  nrFile;
    }

    void updateGit()  {
        try{
            PrintWriter writer = new PrintWriter(new FileWriter(".."+File.separator+".gvt"+File.separator+"neverUsed.txt",true));
            writer.println(version);
            writer.println(messageDefault);
            writer.println(nrFile);
            writer.println(message);
            writer.close();
        }catch (Exception e){
            System.out.println("Underlying system problem. See ERR for details.");
            System.exit(-3);
        }

    }

    private String changeString(String s) {
        String[] arr= s.split("\n");
        return arr[0];
    }
}

public class Gvt {
    private File dir;
    private ArrayList<GitFile> fileList;
    private boolean dirIsExist;

    private Gvt() {
        //dir  = new File(".."+File.separator+"my_repo"+File.separator+".gvt");
        dir  = new File(".."+File.separator+".gvt");
        fileList = new ArrayList<>();
        dirIsExist = false;
    }

    private static boolean isNumeric(final String str) {
        if (str == null || str.length() == 0) {
            return true;
        }
        return !str.chars().allMatch(Character::isDigit);
    }

    private boolean isDirExist() {
        return !dir.exists();
    }

    private String pickupName(String messageMSD){
        String []arr = messageMSD.trim().split(" ");
        return arr[2];
    }

    private void init() {
        try{
            if(isDirExist()) {
                dirIsExist = dir.mkdir();
            }
            GitFile tmp = new GitFile(0,"GVT initialized.");
            fileList.add(tmp);
            tmp.setNrFile(0, "-");
            tmp.updateGit();
        } catch (Exception e){
            System.out.println("Underlying system problem. See ERR for details.");
            System.exit(-3);
        }
    }

    private int counter()  {
        int count = 0;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(".." + File.separator + ".gvt" + File.separator + "neverUsed.txt"));
            while (true) {
                if (reader.readLine() == null) break;
                if (reader.readLine() == null) break;
                if (reader.readLine() == null) break;
                if (reader.readLine() == null) break;
                count++;
            }
            reader.close();

        }catch (Exception e){
            System.out.println("Underlying system problem. See ERR for details.");
            System.exit(-3);
        }
        return count;
    }

    private void history(int x)  {
        try
        {
            int count = counter();
            while (x--> 0) {
                BufferedReader reader = new BufferedReader(new FileReader(".."+File.separator+".gvt"+File.separator+"neverUsed.txt"));
                String version;
                String messageMSD;
                int i = 1;
                while (true){ //(i <= count) {
                    if ((version = reader.readLine()) == null) break;
                    if ((messageMSD = reader.readLine()) == null) break;
                    if ((reader.readLine()) == null) break;
                    if (reader.readLine() == null) break;
                    version = version.replace("\n", "").replace("\r", "");
                    messageMSD = messageMSD.replace("\n", "").replace("\r", "");

                    if (i == count) {
                        System.out.println(version + ": " + messageMSD);
                        break;
                    }
                    i++;
                }
                count--;
                reader.close();
            }
        }catch (Exception e){
            System.out.println("Underlying system problem. See ERR for details.");
            System.exit(-3);
        }

    }

    private void history(){
        try {
            BufferedReader reader = new BufferedReader(new FileReader(".." + File.separator + ".gvt" + File.separator + "neverUsed.txt"));
            String version;
            String messageMSD;

            while (true) {
                if ((version = reader.readLine()) == null) break;
                if ((messageMSD = reader.readLine()) == null) break;
                if ((reader.readLine()) == null) break;
                if ((reader.readLine()) == null) break;
                version = version.replace("\n", "").replace("\r", "");
                messageMSD = messageMSD.replace("\n", "").replace("\r", "");
                System.out.println(version + ": " + messageMSD);
            }
            reader.close();
        }catch (Exception e){
            System.out.println("Underlying system problem. See ERR for details.");
            System.exit(-3);
        }
    }

    private void add(String name, String m) {
        try {
            String newFile = ".."+File.separator+".gvt"+File.separator+name;
            String content;// = "";
            BufferedReader reader1 = new BufferedReader(new FileReader(name));
            PrintWriter writer = new PrintWriter(new FileWriter(newFile));
            while((content=reader1.readLine())!=null) {
                writer.print(content);
            }
            reader1.close();
            writer.close();

            int cout = counter();

            String msgD = "Added file: " + name;
            GitFile tmp;

            if(m.isBlank()) {
                tmp = new GitFile(cout,msgD);
            } else {
                tmp = new GitFile(cout,msgD,m);
            }

            tmp.setNrFile(cout, name);
            tmp.updateGit();

            String newVersionName= ".."+File.separator+".gvt"+File.separator+name;
            String newName = ".."+File.separator+".gvt"+File.separator+tmp.getNrFile();
            BufferedReader reader2 = new BufferedReader(new FileReader(newVersionName));
            PrintWriter writer2 = new PrintWriter(new FileWriter(newName));
            while((content=reader2.readLine())!=null) {
                writer2.print(content);
            }
            reader2.close();
            writer2.close();

        } catch (Exception exception) {
            System.out.println("File " + name + " cannot be added, see ERR for details.");
            System.exit(22);
        }
    }

    private void commit(String name, String m) {
        try{
            String x = ".."+File.separator+".gvt"+File.separator+name;
            String content;
            BufferedReader reader2 = new BufferedReader(new FileReader(name));
            PrintWriter writer2 = new PrintWriter(new FileWriter(x));
            while((content=reader2.readLine())!=null) {
                writer2.print(content);
            }
            reader2.close();
            writer2.close();

            int count = counter();
            String v = Integer.toString(count);
            String nameFirst = ".."+File.separator+".gvt"+File.separator+name;
            String nameSec = ".."+File.separator+".gvt"+File.separator+ v +name;
            BufferedReader reader1 = new BufferedReader(new FileReader(nameFirst));
            PrintWriter writer = new PrintWriter(new FileWriter(nameSec));
            while((content=reader1.readLine())!=null) {
                writer.print(content);
            }
            reader1.close();
            writer.close();

            String msgD = "Committed file: " + name;

            GitFile tmp;
            if(m.isBlank())tmp = new GitFile(count,msgD);
            else tmp = new GitFile(count,msgD,m);

            tmp.setNrFile(count, name);
            tmp.updateGit();
        }catch (Exception e){
            System.out.println("File " + name + " cannot be commited, see ERR for details.");
            System.exit(-52);
        }

    }

    private void version(int v)  {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(".."+File.separator+".gvt"+File.separator+"neverUsed.txt"));
            String version;
            String messageMSD="";
            String message="";
            int count = 1;
            while(true)
            {
                if((version = reader.readLine())== null) break;
                if((messageMSD = reader.readLine())== null) break;
                if((reader.readLine())== null) break;
                if((message = reader.readLine())== null) break;
                if(count==v) break;
                count++;
            }
            reader.close();

            if(version!=null && message!=null && messageMSD!=null) {
                version = version.replace("\n", "").replace("\r", "");
                messageMSD = messageMSD.replace("\n", "").replace("\r", "");
                message = message.replace("\n", "").replace("\r", "");

                System.out.println("Version: "+version);
                System.out.println(messageMSD);
                if(!message.equals("-")) System.out.print(message);
            }
        } catch (Exception e){
            System.out.println("Underlying system problem. See ERR for details.");
            System.exit(-3);
        }

    }

    private void checkout(int v)  {
        try{
            BufferedReader reader = new BufferedReader(new FileReader(".."+File.separator+".gvt"+File.separator+"neverUsed.txt"));
            int count = 0;
            String messageMSD="";
            while(true) {
                if(reader.readLine() == null) break;
                if((messageMSD = reader.readLine())== null) break;
                if(reader.readLine() == null) break;
                if(reader.readLine() == null) break;
                if(count==v) break;
                count++;
            }
            reader.close();

            String name = pickupName(messageMSD);
            String namePart = Integer.toString(v);
            String nameVersion = ".."+File.separator+".gvt"+File.separator + namePart + name;//"+File.separator+"
            String nameGit = ".."+File.separator+".gvt"+File.separator+name;
            String content;
            BufferedReader reader1 = new BufferedReader(new FileReader(nameVersion));
            PrintWriter writer1 = new PrintWriter(new FileWriter(nameGit));
            PrintWriter writer2 = new PrintWriter(new FileWriter(name));
            while((content=reader1.readLine())!=null) {
                writer1.println(content);
                writer2.println(content);
            }
            reader1.close();
            writer1.close();
            writer2.close();
        } catch (Exception e){
            System.out.println("Underlying system problem. See ERR for details.");
            System.exit(-3);
        }

    }

    private void detach(String name, String m){
        try{
            int count = counter();
            String msgD = "Detached file: " + name;
            GitFile tmp;
            if(m.isBlank()) {
                tmp = new GitFile(count,msgD);
            } else {
                tmp = new GitFile(count,msgD,m);
            }
            tmp.setNrFile(count, name);
            tmp.updateGit();

            File deleteFromGvt = new File(".."+File.separator+".gvt"+File.separator+name);
            if(deleteFromGvt.exists()) {
                deleteFromGvt.delete();
            }
        } catch (Exception e){
            System.out.println("Underlying system problem. See ERR for details.");
            System.exit(-3);
        }

    }

    public static void main(String [] args)  {
        if(args.length == 0) {
            System.out.println("Please specify command.");
            System.exit(1);
        }
        else
        {
            Gvt myGit = new Gvt();

            switch (args[0]) {
                case "init":
                    if (myGit.isDirExist()) {
                        myGit.init();
                        System.out.println("Current directory initialized successfully.");
                    } else {
                        System.out.println("Current directory is already initialized.");
                        System.exit(10);
                    }

                    break;
                case "add":
                    if (myGit.isDirExist()) {
                        System.out.println("Current directory is not initialized. Please use \"init\" command to initialize.");
                        System.exit(-2);
                    } else {
                        try {
                            if (args[1].isEmpty()) {
                                System.out.println("Please specify file to add.");
                                System.exit(20);
                            } else {
                                File newFile = new File((args[1]));
                                String name = ".." + File.separator + ".gvt" + File.separator + args[1];
                                File newFileGvt = new File(name);
                                if (!newFile.exists())
                                {
                                    System.out.println("File " + args[1] + " not found.");
                                    System.exit(21);
                                } else if (newFileGvt.exists()) // jest dodany
                                {
                                    System.out.println("File " + args[1] + " already added.");
                                } else {
                                    if (args.length > 2) {
                                        if (!args[2].isBlank() && args[2].equals("-m")) {
                                            StringBuilder message = new StringBuilder();
                                            for (int a = 3; a < args.length; a++) message.append(args[a]);
                                            String m = message.toString();
                                            myGit.add(args[1], m);
                                        }
                                    } else {
                                        myGit.add(args[1], "");
                                    }

                                    System.out.println("File " + args[1] + " added successfully.");
                                }
                            }
                        } catch (Exception exception) {
                            System.out.println("File " + args[1] + " cannot be added, see ERR for details.");
                            System.exit(22);
                        }

                    }
                    break;
                case "detach":
                    if (myGit.isDirExist()) {
                        System.out.println("Current directory is not initialized. Please use \"init\" command to initialize.");
                        System.exit(-2);
                    } else {
                        try {
                            if (args[0].equals("detach") && args.length == 1) {
                                System.out.println("Please specify file to detach.");
                                System.exit(30);
                            } else {
                                String name = ".." + File.separator + ".gvt" + File.separator + args[1];
                                File newFileGvt = new File(name);

                                if (!newFileGvt.exists()) {
                                    System.out.println("File " + args[1] + " is not added to gvt.");
                                } else {
                                    if (args.length > 2) {
                                        if (!args[2].isBlank() && args[2].equals("-m")) {
                                            StringBuilder message = new StringBuilder();
                                            for (int a = 3; a < args.length; a++) message.append(args[a]);
                                            String m = message.toString();
                                            myGit.detach(args[1], m);
                                        }
                                    } else {
                                        myGit.detach(args[1], "");
                                    }
                                    System.out.println("File " + args[1] + " detached successfully.");
                                }
                            }
                        } catch (Exception exception) {
                            System.out.println("File " + args[1] + " cannot be detached, see ERR for details.");
                            System.exit(31);
                        }
                    }

                    break;
                case "checkout":
                    if (myGit.isDirExist()) {
                        System.out.println("Current directory is not initialized. Please use \"init\" command to initialize.");
                        System.exit(-2);
                    } else {
                        if (isNumeric(args[1]) || myGit.counter() < Integer.parseInt(args[1].trim())) {
                            System.out.println("Invalid version number: " + args[1]);
                            System.exit(40);
                        } else {
                            int x = Integer.parseInt(args[1].trim());
                            myGit.checkout(x);
                            System.out.println("Version " + args[1] + " checked out successfully.");
                        }
                    }
                    break;
                case "commit":
                    if (myGit.isDirExist()) {
                        System.out.println("Current directory is not initialized. Please use \"init\" command to initialize.");
                        System.exit(-2);
                    } else {
                        try {
                            if (args[0].equals("commit") && args.length == 1) {
                                System.out.println("Please specify file to commit.");
                                System.exit(50);
                            } else {
                                File newFile = new File((args[1]));
                                String name = ".." + File.separator + ".gvt" + File.separator + args[1];
                                File newFileGvt = new File(name);

                                if (!newFileGvt.exists()) {
                                    System.out.println("File " + args[1] + " is not added to gvt.");
                                } else if (!newFile.exists()) //kolejnosc dobra ??????
                                {
                                    System.out.println("File " + args[1] + " does not exist.");
                                    System.exit(51);
                                } else {
                                    if (args.length > 2) {
                                        if (!args[2].isBlank() && args[2].equals("-m")) {
                                            StringBuilder message = new StringBuilder();
                                            for (int a = 3; a < args.length; a++) message.append(args[a]);
                                            String m = message.toString();
                                            myGit.commit(args[1], m);
                                        }
                                    } else {
                                        myGit.commit(args[1], "");
                                    }

                                    System.out.println("File " + args[1] + " committed successfully.");
                                }
                            }
                        } catch (Exception exception) {
                            System.out.println("File " + args[1] + " cannot be commited, see ERR for details.");
                            System.exit(-52);
                        }
                    }
                    break;
                case "history":
                    if (myGit.isDirExist()) {
                        System.out.println("Current directory is not initialized. Please use \"init\" command to initialize.");
                        System.exit(-2);
                    } else {
                        if (args.length == 1) {
                            myGit.history();
                        } else {
                            if (args[1].equals("-last")) {
                                if (!args[2].isBlank()) {
                                    int x = Integer.parseInt(args[2].trim());
                                    myGit.history(x);
                                }
                            }
                        }
                    }
                    break;
                case "version":
                    if (myGit.isDirExist()) {
                        System.out.println("Current directory is not initialized. Please use \"init\" command to initialize.");
                        System.exit(-2);
                    } else {
                        if (args[0].equals("version") && args.length == 1) {
                            myGit.version(myGit.counter());
                        } else {
                            if (isNumeric(args[1]) || myGit.counter() < Integer.parseInt(args[1].trim())) {
                                System.out.println("Invalid version number: " + args[1]);
                                System.exit(60);
                            } else {
                                int x = Integer.parseInt(args[1].trim());
                                myGit.version(x);
                            }
                        }
                    }
                    break;
                default:
                    System.out.println("Unknown command " + args[0]);
                    System.exit(1);
            }
        }
    }
}

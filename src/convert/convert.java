package convert;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import pennconverter.PennConverter;
public class convert {
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		int start = 1024;
		int end = 2048;
		if (args.length>=3) {
			start = Integer.parseInt(args[1]);
			end = Integer.parseInt(args[2]);
		}
		
	//	toBinary("/disk/gpfs/scohen/gigaword-parsed-list.txt",start,end);
		
		convert("/afs/inf.ed.ac.uk/user/s15/s1544871/data/tmp","/afs/inf.ed.ac.uk/user/s15/s1544871/data/tmp2");
		
	}

	private static void convert(String foldername, String output) {
		File folder = new File(foldername);
		File[] listOfFiles = folder.listFiles();
System.out.println(folder.getAbsolutePath());
		for (File file : listOfFiles) {
		    if (file.isFile() ) {
		        System.out.println(file.getAbsolutePath());
		        System.out.println(file.getName());
		        PennConverter.main(new String[]{"-f",file.getAbsolutePath(),
		        		"-t",output+file.getName()});
		    }
		}
		
	}

	private static void toBinary(String fileName, int start,int end) throws IOException {
		int i =0;
		BufferedReader br = new BufferedReader(new FileReader(fileName));
		try {
		    String line = br.readLine();

		    while (line != null && i <end) {
		    	i++;
		    	if (i>start)
		    	unbinary(line);
		        line = br.readLine();
		    }
		} finally {
		    br.close();
		}
	}

	private static void unbinary(String line) throws IOException {
		// TODO Auto-generated method stub
 String[] fileNameV = line.split("/");
 System.out.println(line);
 String fileName = fileNameV[fileNameV.length-1];
		ProcessBuilder pb = new ProcessBuilder("/disk/scratch/snarayan/codes/denormalise-parsetrees.sh",
				 "-normalisedparsetrees",
				 line,
				 "-denormalisedparsetrees",
				 "/afs/inf.ed.ac.uk/user/s15/s1544871/data/tmp/"+fileName);
		Process p =pb.start();  
		try {
			p.waitFor(100,TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

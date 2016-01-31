package test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class NTReader {
	static String forbiddenChars = "àÁáÂâÃãÄäÅåÈèÉéÊàÁáÂâÃãÄäÅåÈèÉéÊêËëÌìÍíÎîÏïÒòÓóÔôÕõÖöØøÙùÚúÛûÜüİıŸÿÆæŒœÇçÑñ¬ßƒŠšşŞĞğ™®©¼½¾×÷¹²³‰€¢£¥¤¡¿‚‘’„“”†‡±•·§ªºµ¶ˆ˜¯°´¨¸«»¦—…ŠšşŞĞµ¶";

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		String fileName = "medium";

		File nTDoc = new File("E:\\medium.nt");
		File ntDocNew = new File("E:\\new\\" + fileName + "0.nt");

		FileReader fileReader = new FileReader(nTDoc);

		FileWriter fileWriter = new FileWriter(ntDocNew);
		BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		String line = null;
		long lineCount = 0;
		int fileCount = 1;

		while ((line = bufferedReader.readLine()) != null) {
			// !line.contains("<http://musicbrainz.org/recording/774d4660-15a1-4409-b1b9-cc5fa75029c8#_>
			// <http://purl.org/dc/elements/1.1/title>")
			// lineCount < 1000000
			System.out.println(lineCount);

			if (lineCount < 250000) {
				//if (line.contains("publication")) {
					if (checkChars(line)) {
						/*
						String[] array = line.split(" ");
						
						line = "";
						
						boolean take = true;
						for (int i = 0; i < array.length ; i ++ ) {
							if(array[i].contains("http")) {
								
								if (countChars(array[i], ">") == 1 && countChars(array[i], "<") == 1 && !array[i].contains("|") && !array[i].contains("^")) {
									String lineNew = array[i].replaceAll(" ", "%20");
									lineNew = lineNew.replaceAll("´", "");
									lineNew = lineNew.replaceAll("`", "");
									lineNew = lineNew.replaceAll("^", "");
									lineNew = lineNew.replaceAll("\\{", "");
									lineNew = lineNew.replaceAll("\\}", "");
									line += lineNew + " ";
								} else {
									take = false;
								}
								
									
							} else {
								if (i == array.length - 1) {
									line += array[i];
								} else {
									line += array[i] + " ";
								}
								
							}
						}
						
						System.out.println(line);
						
						//line = line.substring(0, line.lastIndexOf(" "));
						
						String lineBegin = line.substring(0, line.indexOf(">") + 1);
						*/
						//if (!lineBegin.contains("\"") && !line.contains("\\") && take) {
							lineCount++;
							System.out.println(line);
							bufferedWriter.write(line + "\n");
						} else {
							System.err.println(lineCount);
							System.err.println(line);
						}
						
						
					
					//} else {
					//System.err.println(lineCount);
					//System.err.println(line);
					//}
				} else {
					lineCount = 0;
					bufferedWriter.close();
					fileWriter.close();
					ntDocNew = new File("E:\\new\\" + fileName + fileCount + ".nt");
					fileWriter = new FileWriter(ntDocNew);
					bufferedWriter = new BufferedWriter(fileWriter);

					if (checkChars(line)) {
						bufferedWriter.write(line + "\n");
					}

					fileCount++;
				}

				lineCount++;
				}
				
		//}

		bufferedReader.close();
		bufferedWriter.close();
		return;
	}

	public static boolean checkChars(String line) {
		for (int i = 0; i < forbiddenChars.length(); i++) {
			if (line.contains("" + forbiddenChars.charAt(i))) {
				return false;
			}
		}

		return true;
	}
	
	public static int countChars(String line, String ch) {
		char character = ch.toCharArray()[0];
		System.out.println(line);
		int count = 0;
		for (char c : line.toCharArray()) {
			if (c == character) {
				count++;
			}
		}
		
		return count;
	}
}

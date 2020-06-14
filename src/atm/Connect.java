
package atm;
import java.io.*;

public class Connect {
    public static void main(String[] args) throws IOException {
        String str = "id:2,adi:Kaplan Arda,soyadi:Uçar,tel:53589266012,şifre:1793";

        File file = new File("personel.txt");
        if (!file.exists()) {
            file.createNewFile();
        }

        FileWriter fileWriter = new FileWriter(file, false);
        try (BufferedWriter bWriter = new BufferedWriter(fileWriter)) {
            bWriter.write(str);
        }


        FileReader fileReader = new FileReader(file);
        String line;

        try (BufferedReader br = new BufferedReader(fileReader)) {
            while ((line = br.readLine()) != null) {
                
                System.out.println(line);
                
            }
        }


    }
}



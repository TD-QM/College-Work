public class PA2 {
    public static void main(String[] args) {
        String key15 = "BB9D874D36B3";
        String key16 = "8B1E4BA8F3A1";
    
        key15 = hexToBin(key15);
        key16 = hexToBin(key16);    
        
        System.out.println("Key15: " + key15);
        System.out.println("Key16: " + key16);
        
        System.out.println("\nKey15 - PC2:");
        printRoundKey(invPC2(key15));
        System.out.println("Key16 - PC2:");
        printRoundKey(invPC2(key16));

        String C0 = "1110000111011100011001101011";
        String D0 = "0011110001101000111100100101";

        System.out.println("Incomplete Key:");
        printKey(invPC1(C0 + D0));

        String key = "10010000" +
                     "00101000" +
                     "01110010" +
                     "01000111" +
                     "01011010" +
                     "10111110" +
                     "11101101" +
                     "11011000";

        System.out.println("Binary Key: " + key);
        System.out.println("Hex Key   : " + binToHex(key));

    }

    // Hex to binary conversion written by Aleksander Wostal on Stack Exchange
    // https://stackoverflow.com/questions/9246326/convert-hexadecimal-string-hex-to-a-binary-string
    public static String hexToBin(String hex){
        hex = hex.replaceAll("0", "0000");
        hex = hex.replaceAll("1", "0001");
        hex = hex.replaceAll("2", "0010");
        hex = hex.replaceAll("3", "0011");
        hex = hex.replaceAll("4", "0100");
        hex = hex.replaceAll("5", "0101");
        hex = hex.replaceAll("6", "0110");
        hex = hex.replaceAll("7", "0111");
        hex = hex.replaceAll("8", "1000");
        hex = hex.replaceAll("9", "1001");
        hex = hex.replaceAll("A", "1010");
        hex = hex.replaceAll("B", "1011");
        hex = hex.replaceAll("C", "1100");
        hex = hex.replaceAll("D", "1101");
        hex = hex.replaceAll("E", "1110");
        hex = hex.replaceAll("F", "1111");
        return hex;
    }

    // This one was written by me
    public static String binToHex(String bin){
        String output = "";
        for(int i = 0; i < bin.length(); i += 4){
            switch(bin.substring(i, i+4)){
                case "0000":
                    output += "0";
                    break;
                case "0001":
                    output += "1";
                    break;
                case "0010":
                    output += "2";
                    break;
                case "0011":
                    output += "3";
                    break;
                case "0100":
                    output += "4";
                    break;
                case "0101":
                    output += "5";
                    break;
                case "0110":
                    output += "6";
                    break;
                case "0111":
                    output += "7";
                    break;
                case "1000":
                    output += "8";
                    break;
                case "1001":
                    output += "9";
                    break;
                case "1010":
                    output += "A";
                    break;
                case "1011":
                    output += "B";
                    break;
                case "1100":
                    output += "C";
                    break;
                case "1101":
                    output += "D";
                    break;
                case "1110":
                    output += "E";
                    break;
                case "1111":
                    output += "F";
                    break;
            }
        }

        return output;
    }

    public static String invPC2(String key){
        int[] table = {14, 17, 11, 24,  1,  5,  3, 28,
                       15,  6, 21, 10, 23, 19, 12,  4,
                       26,  8, 16,  7, 27, 20, 13,  2,
                       41, 52, 31, 37, 47, 55, 30, 40, 
                       51, 45, 33, 48, 44, 49, 39, 56,
                       34, 53, 46, 42, 50, 36, 29, 32};
        char[] outputTable = new char[56];

        for(int i = 0; i < outputTable.length; i++){
            outputTable[i] = '~';
        }

        for(int i = 0; i < key.length(); i++){
            outputTable[table[i]-1] = key.charAt(i);
        }

        String output = "";
        for(char x : outputTable){
            output += x;
        }

        return output;
    }

    public static String invPC1(String key){
        int[] table = {57, 49, 41, 33, 25, 17,  9,  1,
                       58, 50, 42, 34, 26, 18, 10,  2,
                       59, 51, 43, 35, 27, 19, 11,  3,
                       60, 52, 44, 36, 63, 55, 47, 39,
                       31, 23, 15,  7, 62, 54, 46, 38,
                       30, 22, 14,  6, 61, 53, 45, 37,
                       29, 21, 13,  5, 28, 20, 12,  4};
        char[] outputTable = new char[64];

        for(int i = 0; i < outputTable.length; i++){
            outputTable[i] = '~';
        }

        for(int i = 0; i < key.length(); i++){
            outputTable[table[i]-1] = key.charAt(i);
        }

        String output = "";
        for(char x : outputTable){
            output += x;
        }

        return output;
    }

    public static void printRoundKey(String key){
        String output = "";
        for(int i = 0; i < key.length(); i++){
            output += key.substring(i, i+1);
            if((i+1) % 28 == 0){
                output += "\n";
            }
        }
        System.out.println(output);
    }

    public static void printKey(String key){
        String output = "";
        for(int i = 0; i < key.length(); i++){
            output += key.substring(i, i+1);
            if((i+1) % 8 == 0){
                output += "\n";
            }
        }
        System.out.println(output);
    }
}

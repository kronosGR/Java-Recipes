import java.util.*;


class MapFunctions {

    public static void printWithSameLetter(Map<String, String> map) {
        // write your code here
        Iterator<Map.Entry<String, String>> itr = map.entrySet().iterator();

        while (itr.hasNext()) {
            Map.Entry<String, String> item = itr.next();
            if (item.getKey().substring(0, 1).equals(item.getValue().substring(0, 1))) {
                System.out.println(item.getKey() + " " + item.getValue());
            }
        }

    }
}

/* Do not change code below */
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Map<String, String> map = new HashMap<>();

        while (scanner.hasNextLine()) {
            String s = scanner.nextLine();
            String[] pair = s.split(" ");
            map.put(pair[0], pair[1]);
        }

        MapFunctions.printWithSameLetter(map);
    }
}
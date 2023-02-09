import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        // put your code here
        Scanner sc = new Scanner(System.in);
        int amount = Integer.parseInt(sc.nextLine());

        String[] arr = new String[amount];
        for (int i=0; i<amount; i++){
            arr[i] = sc.nextLine();
        }

        LocalTime time = LocalTime.parse("19:30:00");

        for(String line: arr){
            String storeName = line.split(" ")[0];
            String closeTime = line.split(" ")[1];
            LocalTime newTime = LocalTime.parse(closeTime);
            if (time.plus(30, ChronoUnit.MINUTES).isBefore(newTime)){
                System.out.println(storeName);
            }
        }

//        LocalTime user = LocalTime.parse("20:00");
//        int size = Integer.parseInt(scanner.nextLine());
//
//        scanner.useDelimiter("\n")
//                .tokens()
//                .limit(size)
//                .filter(d -> user.isBefore(LocalTime.parse(d.split("\\s+")[1])))
//                .forEach(d -> System.out.println(d.split("\\s+")[0]));

    }
}
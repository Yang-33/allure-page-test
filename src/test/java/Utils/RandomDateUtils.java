package Utils;

import com.github.javafaker.Faker;

import java.util.concurrent.ThreadLocalRandom;

public class RandomDateUtils {

    public static void main(String[] aer) {
        String[] names = {"a", "b", "c", "d", "e"};
        System.out.println(getRandomName());
    }

    public static int getRandomInt(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

    public static String getRandomItemFromArray(String[] values) {
        int index = getRandomInt(0, values.length - 1);
        return values[index];
    }

    public static String getRandomName() {
        return new Faker().name().firstName();
    }

    public static String getRandomLastName() {
        return new Faker().name().lastName();
    }

    public static String getRandomAdress() {
        return new Faker().address().fullAddress();
    }

    public static String getRandomEmail() {
        return new Faker().internet().emailAddress();
    }

    public static long getRandomNumber() {
        return new Faker().number().numberBetween(1000000000, 9999999999l);
    }
}

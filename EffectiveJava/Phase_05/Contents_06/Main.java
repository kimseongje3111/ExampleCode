package Phase_05.Contents_06;

/* ----------------------------------------------

5장. 제네릭

(32) < 타입 안전 이종 컨테이너를 고려하라 >

 ---------------------------------------------- */

public class Main {

    public static void main(String[] args) {

        // '타입 안전 이종 컨테이너'란 컨테이너 대신 키를 매개변수화한 후, 컨테이너에 값을 넣거가 뺄 때 매개변수화한 키를 함께 제공하는 것이다.
        // 그 결과, 제네릭 타입 시스템의 값의 타입이 키와 같음을 보장해준다.
        Favorites favorites = new Favorites();

        favorites.putFavorite(String.class, "Java");
        favorites.putFavorite(Integer.class, 0xcafebabe);
        favorites.putFavorite(Class.class, Favorites.class);

        String favoriteString = favorites.getFavorite(String.class);
        int favoriteInteger = favorites.getFavorite(Integer.class);
        Class<?> favoriteClass = favorites.getFavorite(Class.class);
    }
}

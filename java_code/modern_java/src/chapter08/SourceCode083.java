package chapter08;

import java.util.*;

import static java.util.Map.entry;

public class SourceCode083 {

    public static void main(String[] args) {

        //계산 패턴
        computePattern();

        //삭제 패턴
        removePattern();

        //교체 패턴
        replacePattern();

        //함칩
        merge();

    }

    private static void merge() {
        Map<String, String> family = Map.ofEntries(
                entry("Teo", "Star Wars"),
                entry("Cristina", "James Bond"));
        Map<String, String> friends = Map.ofEntries(entry("Raphael", "Star Wars"));

        System.out.println("--> Merging the old way");
        Map<String, String> everyone = new HashMap<>(family);
        everyone.putAll(friends); //friends의 모든 항목을 everyone으로 복사
        System.out.println(everyone);

        Map<String, String> friends2 = Map.ofEntries(
                entry("Raphael", "Star Wars"),
                entry("Cristina", "Matrix"));

        System.out.println("--> Merging maps using merge()");
        Map<String, String> everyone2 = new HashMap<>(family);
                                                        //  중복된 키가 있으면 두값을 연결
        friends2.forEach((k, v) -> everyone2.merge(k, v, (movie1, movie2) -> movie1 + " & " + movie2));
        System.out.println(everyone2);
    }

    private static void replacePattern() {
        //교체 패턴
        Map<String, String> famovie = new HashMap<>(); // replaceAll을 적용할 것이므로 바꿀수 있는 맵 사용
        famovie.put("a", "영1");
        famovie.put("b", "영2");
        famovie.put("c", "영3");

        famovie.replaceAll((name, movie) -> movie.toUpperCase());
        System.out.println("교체패턴" + famovie);
    }

    private static void removePattern() {
        //기초 정보
        Map<String, String> favouriteMovies = new HashMap<>();
        favouriteMovies.put("Raphael", "Jack Reacher 2");
        favouriteMovies.put("Cristina", "Matrix");
        favouriteMovies.put("Olivia", "James Bond");
        String key = "Raphael";
        String value = "Jack Reacher 2";

        System.out.println("--> Removing an unwanted entry the cumbersome way");
        boolean result = remove(favouriteMovies, key, value);
        System.out.printf("%s [%b]%n", favouriteMovies, result);

        // 두 번째 테스트를 하기 전에 삭제된 항목을 다시 돌려놓음
        favouriteMovies.put("Raphael", "Jack Reacher 2");

        System.out.println("--> Removing an unwanted the easy way");
        favouriteMovies.remove(key, value);
        System.out.printf("%s [%b]%n", favouriteMovies, result);
    }

    private static void computePattern() {
        //기초 영화정보
        Map<String, List<String>> friendsToMovies = new HashMap<>();
        friendsToMovies.put("가영", Arrays.asList("영화1", "영화2"));
        friendsToMovies.put("나영", Arrays.asList("영화3", "영화4"));
        friendsToMovies.put("다영", Arrays.asList("영화5", "영화6"));

        String friend = "라영";
        List<String> movies = friendsToMovies.get(friend);
        if(movies == null){
            movies = new ArrayList<>();
            friendsToMovies.put(friend, movies);
        }
        movies.add("영화8");
        System.out.println(friendsToMovies);

        //computeIfAbsnet 활용
        friendsToMovies.computeIfAbsent("마영", name -> new ArrayList<>()).add("영화 10");
        System.out.println(friendsToMovies);

    }

    private static <K, V> boolean remove(Map<K, V> favouriteMovies, K key, V value) {
        if (favouriteMovies.containsKey(key) && Objects.equals(favouriteMovies.get(key), value)) {
            favouriteMovies.remove(key);
            return true;
        }
        return false;
    }



}

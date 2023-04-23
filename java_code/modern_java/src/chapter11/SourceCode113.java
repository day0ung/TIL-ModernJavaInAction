package chapter11;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

public class SourceCode113 {
    public static void main(String[] args) {
        // flatMap으로 Optional객체 연결
        getCarInsuranceNameUseFlatMap(null);

        //Optional 스트림 조작
        getCarInsuranceNames(null);

        //두 Optional합치기
        findCheapestInsurance(null, null); //가장 저렴한 보혐료를 제공하는 보험회사를 찾는 메서드
        nullSafeFindCheapestInsurance(null, null); //두 Optional을 인수로 받아서 Optional<Insurance>을 반환


    }



    private static String getCarInsuranceNameUseFlatMap(Optional<Person> person) {
        // 컴파일되지 않음:
        // (1)에서 Optional<Person>에 map(Person::getCar) 호출을 시도함. flatMap()을 이용하면 문제가 해결됨.
        // 그리고 (2)에서 Optional<Car>에 map(Car::getInsurance) 호출을 시도함. flatMap()을 이용하면 문제가 해결됨.
        // Insurance::getName은 평범한 문자열을 반환하므로 추가 "flatMap"은 필요가 없음.
          /*
        public String getCarInsuranceName(Person person) {
            Optional<Person> optPerson = Optional.of(person);
            Optional<String> name = optPerson.map(Person::getCar) // (1)
                .map(Car::getInsurance) // (2) Optional<Optional<Car>> car 형태가 반환됨
                .map(Insurance::getName);
            return name.orElse("Unknown");
        }*/

        return person.flatMap(Person::getCar)
                    .flatMap(Car::getInsurance)
                    .map(Insurance::getName)
                    .orElse("Unknown");

    }

    public static Set<String> getCarInsuranceNames(List<Person> persons) {
        return persons.stream()
                .map(Person::getCar) // 사람 목록을 각 사람이 보유한 자동차의 Optional<Car> 스트림으로 변환
                .map(optCar -> optCar.flatMap(Car::getInsurance))//flatMap 연산을 통해 Optional<Car>를 해당 Optional<Insurane>로 변환
                .map(optInsurance -> optInsurance.map(Insurance::getName))// Optional<Insuracne>를 해당 이름의 Optional<String>으로 변환
                .flatMap(Optional::stream)// Stream<Optional<String>>을 이름을 포함하는 Stream<String>으로 변환
                .collect(toSet());
    }

    private static Insurance findCheapestInsurance(Person person, Car car) {
        // 다른 보험사에서 제공한 질의 서비스
        // 모든 데이터 비교
        Insurance cheapestCompany = new Insurance();
        return cheapestCompany;
    }

    private static Optional<Insurance> nullSafeFindCheapestInsurance(Optional<Person> person, Optional<Car> car) {
        if (person.isPresent() && car.isPresent()) {
            return Optional.of(findCheapestInsurance(person.get(), car.get()));
        } else {
            return Optional.empty();
        }
    }


    //optional 클래스 소개
    static class Person {
        private Optional<Car> car; // 사람이 차에대한 소유 여부가 있을수 있으므로, Optional

        public Optional<Car> getCar() {
            return car;
        }
    }

    static class Car {
        private Optional<Insurance> insurance; // 자동차가 보험에 가입되어 있는지 여부가 있을수 있으므로, Optional

        public Optional<Insurance> getInsurance() {
            return insurance;
        }
    }

    static class Insurance {
        private String name; //보험회사에는 반드시 이름이 잇다.

        public String getName() {
            return name;
        }
    }
}

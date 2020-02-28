package Phase_06.Contents_02;

// 상태와 전이를 매핑
// 중첩 EnumMap 으로 데이터와 열거 타입 쌍을 연결 !!

import java.util.EnumMap;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toMap;

public enum Phase {             // 상태
    SOLID, LIQUID, GAS;
    // 만일 PLASMA 상태를 추가한다 했을 때

    public enum Transition {    // 전이
        MELT(SOLID, LIQUID), FREEZE(LIQUID, SOLID),
        BOIL(LIQUID, GAS), CONDENSE(GAS, LIQUID),
        SUBLIME(SOLID, GAS), DEPOSIT(GAS, SOLID);
        // IONIZE(GAS, PLASMA), DEIONIZE(PLASMA, GAS) 만 추가하면 됨 !!
        // 만일 상태와 전이의 맵핑을 배열과 ordinal 으로 했다면 배열을 강제로 확장해야 하며, 경우에 따라 런타임에 문제를 일으킬 것이다.

        private final Phase from;   // 전
        private final Phase to;     // 후

        Transition(Phase from, Phase to) {
            this.from = from;
            this.to = to;
        }

        private static final Map<Phase, Map<Phase, Transition>> M =
                Stream.of(values()).collect(groupingBy(t -> t.from,     // a. groupingBy 수집기 : 이전 상태를 기준으로 묶음
                        () -> new EnumMap<>(Phase.class),
                        toMap(t -> t.to,                                // b. toMap 수집기 : 이후 상태를 전이에 대응시키는 EnumMap 생성
                                t -> t,
                                (x, y) -> y,
                                () -> new EnumMap<>(Phase.class))));

        public static Transition from(Phase from, Phase to) {
            return M.get(from).get(to);
        }
    }
}

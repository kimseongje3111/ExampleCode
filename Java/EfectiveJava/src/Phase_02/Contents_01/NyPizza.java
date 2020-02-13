package Phase_02.Contents_01;

import java.util.Objects;

public class NyPizza extends Pizza {
    public enum SIZE {SMALL, MEDIUM, LARGE}

    private final SIZE size;            // 뉴욕 피자는 사이즈를 결정해야 함

    public static class NyBuilder extends Pizza.Builder<NyBuilder> {
        private final SIZE size;

        public NyBuilder(SIZE size) {
            this.size = Objects.requireNonNull(size);
        }

        @Override
        public NyPizza build() {        // 객체를 빌드하는 것은 해당 객체를 반환한다는 것과 같음
            return new NyPizza(this);
        }

        @Override
        protected NyBuilder self() {
            return this;
        }
    }

    private NyPizza(NyBuilder nyBuilder) {
        super(nyBuilder);
        size = nyBuilder.size;
    }
}

package Phase_02.Contents_01;

public class Calzone extends Pizza {
    private final boolean sauceInside;      // 칼초네 피자는 소스 추가 가능 (기본은 추가 없음)

    public static class CalBuilder extends Pizza.Builder<CalBuilder> {
        private boolean sauceInside = false;

        public CalBuilder addSauce() {
            sauceInside = true;
            return this;
        }

        @Override
        public Calzone build() {            // 객체를 빌드하는 것은 해당 객체를 반환한다는 것과 같음
            return new Calzone(this);
        }

        @Override
        protected CalBuilder self() {
            return this;
        }
    }

    private Calzone(CalBuilder calBuilder) {
        super(calBuilder);
        sauceInside = calBuilder.sauceInside;
    }
}

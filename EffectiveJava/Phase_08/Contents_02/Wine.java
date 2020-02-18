package Phase_08.Contents_02;

public class Wine {
    String name() {
        return "포도주";
    }
}

class SparklingWine extends Wine {
    @Override
    String name() {
        return "스파클링 와인";
    }
}

class Champagne extends SparklingWine {
    @Override
    String name() {
        return "삼페인";
    }
}

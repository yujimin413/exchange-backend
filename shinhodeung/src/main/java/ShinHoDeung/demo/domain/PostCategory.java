package ShinHoDeung.demo.domain;

import java.util.Arrays;

public enum PostCategory {
    QNA("Q&A"),
    TIP("꿀팁"),
    FREE("자유");

    private final String label;

    PostCategory(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public static boolean isValid(String input) {
        return Arrays.stream(PostCategory.values())
                .anyMatch(c -> c.label.equals(input));
    }
}

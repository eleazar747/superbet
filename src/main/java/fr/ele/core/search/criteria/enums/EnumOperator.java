package fr.ele.core.search.criteria.enums;

import fr.ele.core.search.SearchOperator;

public enum EnumOperator implements SearchOperator {
    EQ("=") {
        @Override
        public void accept(Visitor visitor) {
            visitor.visitEQ();
        }
    },
    NULL("is null") {
        @Override
        public void accept(Visitor visitor) {
            visitor.visitNULL();
        }
    };

    public static interface Visitor {
        void visitEQ();

        void visitNULL();
    }

    private final String title;

    private EnumOperator(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public String getValue() {
        return name();
    }

    public abstract void accept(Visitor visitor);
}

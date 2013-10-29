package fr.ele.core.search.criteria.number;

import fr.ele.core.search.SearchOperator;

public enum NumberOperator implements SearchOperator {
    EQ("=") {
        @Override
        public void accept(Visitor visitor) {
            visitor.visitEQ();
        }
    },
    GT(">") {
        @Override
        public void accept(Visitor visitor) {
            visitor.visitGT();
        }
    },
    LT("<") {
        @Override
        public void accept(Visitor visitor) {
            visitor.visitLT();
        }
    },
    EGT("=>") {
        @Override
        public void accept(Visitor visitor) {
            visitor.visitEGT();
        }
    },
    ELT("=<") {
        @Override
        public void accept(Visitor visitor) {
            visitor.visitELT();
        }
    },
    NULL("is null") {
        @Override
        public void accept(Visitor visitor) {
            visitor.visitNULL();
        }
    };

    public static interface Visitor<T> {
        void visitEQ();

        void visitGT();

        void visitLT();

        void visitEGT();

        void visitELT();

        void visitNULL();
    }

    private final String title;

    private NumberOperator(String title) {
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

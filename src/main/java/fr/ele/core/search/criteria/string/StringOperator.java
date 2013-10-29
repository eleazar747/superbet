package fr.ele.core.search.criteria.string;

import fr.ele.core.search.SearchOperator;

public enum StringOperator implements SearchOperator {
    EQ("=") {
        @Override
        public void accept(Visitor visitor) {
            visitor.visitEQ();
        }
    },
    LIKE("like") {
        @Override
        public void accept(Visitor visitor) {
            visitor.visitLIKE();
        }
    },
    START_WITH("start with") {

        @Override
        public void accept(Visitor visitor) {
            visitor.visitSTART_WITH();
        }

    },
    CONTAINS("contains") {

        @Override
        public void accept(Visitor visitor) {
            visitor.visitCONTAINS();
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

        void visitLIKE();

        void visitSTART_WITH();

        void visitCONTAINS();

        void visitNULL();
    }

    private final String title;

    private StringOperator(String title) {
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

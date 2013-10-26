package fr.ele.core.search;

public enum StringOperator implements SearchOperator {
    EQ {
        @Override
        public void accept(Visitor visitor) {
            visitor.visitEQ();
        }
    },
    LIKE {
        @Override
        public void accept(Visitor visitor) {
            visitor.visitLIKE();
        }
    },
    START_WITH {

        @Override
        public void accept(Visitor visitor) {
            visitor.visitSTART_WITH();
        }

    },
    CONTAINS {

        @Override
        public void accept(Visitor visitor) {
            visitor.visitCONTAINS();
        }

    },
    NULL {
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

    public abstract void accept(Visitor visitor);
}

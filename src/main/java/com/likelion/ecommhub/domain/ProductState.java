package com.likelion.ecommhub.domain;

public enum ProductState {

    ON_SALE("판매중"),
    SOLD_OUT("품절");

    private final String label;

    ProductState(String label) {
        this.label = label;
    }

    public String label() {
        return label;
    }

    public static ProductState fromLabel(String label) {
        for (ProductState state : ProductState.values()) {
            if (state.label.equals(label)) {
                return state;
            }
        }
        throw new IllegalArgumentException("ProductState에 알맞은 label이 없습니다 : " + label);
    }
}

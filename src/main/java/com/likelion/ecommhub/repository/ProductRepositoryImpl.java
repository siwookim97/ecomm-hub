package com.likelion.ecommhub.repository;

import com.likelion.ecommhub.domain.Product;
import com.likelion.ecommhub.domain.ProductState;
import com.likelion.ecommhub.dto.ProductSearchCondition;
import com.likelion.ecommhub.dto.ProductSearchResult;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;
import java.util.stream.Collectors;

import static com.likelion.ecommhub.domain.QImage.*;
import static com.likelion.ecommhub.domain.QProduct.product;
import static org.springframework.util.StringUtils.hasText;

@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<ProductSearchResult> search(ProductSearchCondition condition, Pageable pageable) {
        List<Product> content = queryFactory
                .selectFrom(product)
                .leftJoin(product.images, image)
                .where(
                        productNameContains(condition.getProductName()),
                        sellerNameContains(condition.getSellerName()),
                        productStateEq(condition.getProductState())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(product.createdDate.desc())
                .fetch();

        List<ProductSearchResult> cont = content.stream()
                .map(p -> new ProductSearchResult(p.getId(), p.getName(), p.getDetail(), p.getPrice(),
                        p.getInventory(), p.getImages(), p.getMember().getId(), p.getMember().getNickname(), p.getModifiedDate()))
                .collect(Collectors.toList());

        JPAQuery<Long> countQuery = queryFactory
                .select(product.count())
                .from(product)
                .leftJoin(product.images, image)
                .where(
                        productNameContains(condition.getProductName()),
                        sellerNameContains(condition.getProductName()),
                        productStateEq(condition.getProductState())
                );

        return PageableExecutionUtils.getPage(cont, pageable, countQuery::fetchOne);
    }

    private BooleanExpression productNameContains(String name) {
        return hasText(name) ? product.name.contains(name) : null;
    }

    private BooleanExpression sellerNameContains(String nickname) {
        return hasText(nickname) ? product.member.nickname.contains(nickname) : null;
    }

    private BooleanExpression productStateEq(String productState) {
        return hasText(productState) ?
                product.productState.eq(ProductState.valueOf(productState)) : null;
    }
}

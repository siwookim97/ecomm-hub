package com.likelion.ecommhub.repository;

import com.likelion.ecommhub.dto.ProductSearchCondition;
import com.likelion.ecommhub.dto.ProductSearchResult;
import com.likelion.ecommhub.dto.QProductSearchResult;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;

import static com.likelion.ecommhub.domain.QMember.member;
import static com.likelion.ecommhub.domain.QProduct.product;
import static org.springframework.util.StringUtils.hasText;

@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<ProductSearchResult> search(ProductSearchCondition condition) {
        PageRequest pageRequest = PageRequest.of(0, 12);

        List<ProductSearchResult> content = queryFactory
                .select(new QProductSearchResult(
                        product.id.as("productId"),
                        product.name.as("productName"),
                        product.price,
                        product.inventory,
                        product.images,
                        member.id.as("sellerId"),
                        member.nickname.as("sellerName")
                ))
                .from(product)
                .leftJoin(product.member, member)
                .where(
                    productNameContains(condition.getProductName()),
                    sellerNameContains(condition.getSellerName())
                )
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(product.count())
                .from(product)
                .leftJoin(product.member, member)
                .where(
                        productNameContains(condition.getProductName()),
                        sellerNameContains(condition.getProductName())
                );

        return PageableExecutionUtils.getPage(content, pageRequest, countQuery::fetchOne);
    }

    private BooleanExpression productNameContains(String name) {
        return hasText(name) ? product.name.contains(name) : null;
    }

    private BooleanExpression sellerNameContains(String nickname) {
        return hasText(nickname) ? member.nickname.contains(nickname) : null;
    }
}

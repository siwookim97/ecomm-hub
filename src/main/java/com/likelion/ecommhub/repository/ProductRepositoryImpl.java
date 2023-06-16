package com.likelion.ecommhub.repository;

import com.likelion.ecommhub.domain.Product;
import com.likelion.ecommhub.dto.ProductSearchCondition;
import com.likelion.ecommhub.dto.ProductSearchResult;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;
import java.util.stream.Collectors;

import static com.likelion.ecommhub.domain.QImage.image;
import static com.likelion.ecommhub.domain.QProduct.product;
import static org.springframework.util.StringUtils.hasText;

@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<ProductSearchResult> search(ProductSearchCondition condition, Pageable pageable) {
        List<Product> content = queryFactory
                .selectFrom(product)
                .leftJoin(product.image, image)
                .where(
                        productAndNameContains(condition.getKeyword())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(getOrderByExpression(condition.getSortCode()))
                .fetch();

        List<ProductSearchResult> cont = content.stream()
                .map(p -> new ProductSearchResult(p.getId(), p.getName(), p.getDetail(), p.getPrice(),
                        p.getInventory(), p.getImage(), p.getMember().getId(), p.getMember().getNickname(), p.getModifiedDate()))
                .collect(Collectors.toList());

        JPAQuery<Long> countQuery = queryFactory
                .select(product.count())
                .from(product)
                .leftJoin(product.image, image)
                .where(
                        productAndNameContains(condition.getKeyword())
                );

        return PageableExecutionUtils.getPage(cont, pageable, countQuery::fetchOne);
    }

    private BooleanExpression productAndNameContains(String keyword) {
        return hasText(keyword) ? product.name.contains(keyword).or(product.member.nickname.contains(keyword)) : null;
    }

    private OrderSpecifier<?> getOrderByExpression(String sortCode) {
        if ("1".equals(sortCode)) {
            return product.createdDate.asc();
        }

        if ("2".equals(sortCode)) {
            return product.price.asc();
        }

        if ("3".equals(sortCode)) {
            return product.price.desc();
        }

        return product.createdDate.desc();
    }
}
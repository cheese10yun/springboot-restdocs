package com.rest.docs.springbootrestdocs;

import com.rest.docs.springbootrestdocs.member.Member;
import com.rest.docs.springbootrestdocs.member.MemberRepository;
import com.rest.docs.springbootrestdocs.order.Order;
import com.rest.docs.springbootrestdocs.order.OrderRepository;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DataSetUp implements ApplicationRunner {

    private final MemberRepository memberRepository;
    private final OrderRepository orderRepository;

    @Override
    public void run(ApplicationArguments args) {
        final List<Member> members = new ArrayList<>();
        members.add(new Member("yun@asd.com", "yun"));
        members.add(new Member("wan@asd.com", "wan"));
        members.add(new Member("jay@asd.com", "jay"));
        members.add(new Member("joo@asd.com", "joo"));
        memberRepository.saveAll(members);

        final List<Order> orders = new ArrayList<>();
        orders.add(new Order(1L, BigDecimal.TEN));
        orders.add(new Order(2L, BigDecimal.TEN));
        orders.add(new Order(3L, BigDecimal.TEN));
        orderRepository.saveAll(orders);
    }
}

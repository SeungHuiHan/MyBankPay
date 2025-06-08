package com.bankpay.money.aggregation.application.service;

import com.bankpay.common.UseCase;
import com.bankpay.money.aggregation.application.port.in.GetMoneySumByAddressCommand;
import com.bankpay.money.aggregation.application.port.in.GetMoneySumByAddressUseCase;
import com.bankpay.money.aggregation.application.port.out.GetMembershipPort;
import com.bankpay.money.aggregation.application.port.out.GetMoneySumPort;
import com.bankpay.money.aggregation.application.port.out.MemberMoney;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@UseCase
@RequiredArgsConstructor
@Transactional
public class GetMoneySumByAggregationService implements GetMoneySumByAddressUseCase {

    private final GetMoneySumPort getMoneySumPort;
    private final GetMembershipPort getMembershipPort;
    @Override
    public int getMoneySumByAddress(GetMoneySumByAddressCommand command) {
        // Aggregation 을 위한 비즈니스 로직

        // 강남구, 서초구, 관악구
        String targetAddress = command.getAddress();
        List<String> membershipIds = getMembershipPort.getMembershipByAddress(targetAddress);

        // 10,000명에 고객 중에서, "강남구" 고객은 약 3300명
        // max 100, 500 ~

        //100개씩 요청
        List<List<String>> membershipPartitionList = null;
        if (membershipIds.size() > 100) {
            //100개씩 나눠서 요청한다
            //N명의 고객을, 100명 단위로 List<List<String>>으로 만들어서, 100명의 요청을 보내야 한다.
            membershipPartitionList = partitionList(membershipIds, 100);
        }

        int sum = 0;
        for (List<String> partitionedList : membershipPartitionList) {
            // 100 개씩 요청해서, 값을 계산하기로 설계.
            List<MemberMoney> memberMoneyList = getMoneySumPort.getMoneySumByMembershipIds(partitionedList);

            for (MemberMoney memberMoney : memberMoneyList) {
                sum += memberMoney.getBalance();
            }
        }

        return sum;
    }
    // List를 n개씩 묶어서 List<List<T>>로 만드는 메서드
    private static <T> List<List<T>> partitionList(List<T> list, int partitionSize) {
        return IntStream.range(0, list.size())
                .boxed()
                .collect(Collectors.groupingBy(index -> index / partitionSize))
                .values()
                .stream()
                .map(indices -> indices.stream().map(list::get).collect(Collectors.toList()))
                .collect(Collectors.toList());
    }
}
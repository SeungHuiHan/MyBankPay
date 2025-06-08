package com.bankpay.membership.adapter.out.persistence;

import com.bankpay.common.PersistenceAdapter;
import com.bankpay.membership.adapter.out.vault.VaultAdapter;
import com.bankpay.membership.application.port.out.FindMembershipPort;
import com.bankpay.membership.application.port.out.ModifyMembershipPort;
import com.bankpay.membership.application.port.out.RegisterMembershipPort;
import com.bankpay.membership.domain.Membership;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@PersistenceAdapter
@RequiredArgsConstructor
public class MembershipPersistenceAdapter implements RegisterMembershipPort , FindMembershipPort , ModifyMembershipPort {

    private final SpringDataMembershipRepository membershipRepository;

   private final VaultAdapter vaultAdapter;

    @Override
    public MembershipJpaEntity createMembership(Membership.MembershipName membershipName, Membership.MembershipEmail membershipEmail, Membership.MembershipAddress membershipAddress, Membership.MembershipIsValid membershipIsValid, Membership.MembershipIsCorp membershipIsCorp) {
        // email 만 암호화 해보는 것으로 결정.
        String encryptedEmail = vaultAdapter.encrypt(membershipEmail.getMembershipEmail());
        MembershipJpaEntity jpaEntity = new MembershipJpaEntity(
                membershipName.getMembershipName(),
                encryptedEmail,
                membershipAddress.getMembershipAddress(),
                membershipIsValid.isMembershipIsValid(),
                membershipIsCorp.isMembershipIsCorp(),""
        );
//        return membershipRepository.save(new MembershipJpaEntity(
//                membershipName.getMembershipName(),
//                encryptedEmail,
//                membershipAddress.getMembershipAddress(),
//                membershipIsValid.isMembershipIsValid(),
//                membershipIsCorp.isMembershipIsCorp(),""
//        ));
        membershipRepository.save(jpaEntity);

        MembershipJpaEntity clone = jpaEntity.clone();
        clone.setEmail(membershipEmail.getMembershipEmail());

        return clone;

    }

    @Override
    public MembershipJpaEntity findMembership(Membership.MembershipId membership) {
       // return membershipRepository.getById(Long.parseLong(membership.getMembershipId()));

        MembershipJpaEntity membershipJpaEntity = membershipRepository.getById(Long.parseLong(membership.getMembershipId()));
        String encryptedEmailString = membershipJpaEntity.getEmail();
        String decryptedEmailString = vaultAdapter.decrypt(encryptedEmailString);

        MembershipJpaEntity clone = membershipJpaEntity.clone();
        clone.setEmail(decryptedEmailString);
        return clone;
//        membershipJpaEntity.setEmail(decryptedEmailString);
//        return membershipRepository.getById(Long.parseLong(membership.getMembershipId()));
    }

    @Override
    public MembershipJpaEntity modifyMembership(Membership.MembershipId membershipId, Membership.MembershipName membershipName, Membership.MembershipEmail membershipEmail, Membership.MembershipAddress membershipAddress,
                                                Membership.MembershipIsValid membershipIsValid, Membership.MembershipIsCorp membershipIsCorp, Membership.MembershipRefreshToken membershipRefreshToken) {
        MembershipJpaEntity entity = membershipRepository.getById(Long.parseLong(membershipId.getMembershipId()));
        // email 만 암호화 해보는 것으로 결정.
        String encryptedEmail = vaultAdapter.encrypt(membershipEmail.getMembershipEmail());

        entity.setName(membershipName.getMembershipName());
        entity.setEmail(encryptedEmail);
        entity.setAddress(membershipAddress.getMembershipAddress());
        entity.setValid(membershipIsValid.isMembershipIsValid());
        entity.setCorp(membershipIsCorp.isMembershipIsCorp());
        entity.setRefreshToken(membershipRefreshToken.getRefreshToken());
        membershipRepository.save(entity);

        // Todo 리턴 전에 새로운 객체로 평문화된 멤버 정보를 리턴해 줘야 해요.
        MembershipJpaEntity clone = entity.clone();
        clone.setEmail(membershipEmail.getMembershipEmail());
        return clone;
    }

    @Override
    public List<MembershipJpaEntity> findMembershipListByAddress(Membership.MembershipAddress membershipAddress) {
        // 관악구, 서초구, 강남구 중 하나
        String address = membershipAddress.getMembershipAddress();
        List<MembershipJpaEntity> membershipJpaEntityList = membershipRepository.findByAddress(address);
        List<MembershipJpaEntity> cloneList = new ArrayList<>();

        for (MembershipJpaEntity membershipJpaEntity : membershipJpaEntityList) {
            String encryptedEmailString = membershipJpaEntity.getEmail();
            String decryptedEmailString = vaultAdapter.decrypt(encryptedEmailString);

            //membershipJpaEntity.setEmail(decryptedEmailString);
            MembershipJpaEntity clone = membershipJpaEntity.clone();
            clone.setEmail(decryptedEmailString);
            cloneList.add(clone);
        }
        return cloneList;
        //return membershipJpaEntityList;
    }
}

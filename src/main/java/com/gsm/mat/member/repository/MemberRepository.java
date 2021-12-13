package com.gsm.mat.member.repository;

import com.gsm.mat.exceptionAdvice.exception.UserNotFoundException;
import com.gsm.mat.member.Member;
import com.gsm.mat.member.service.MemberService;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional
public class MemberRepository {
    @PersistenceContext
    private EntityManager em;
    public void save(Member member){
        em.persist(member);
    }
    public Member findById(Long id){
        return em.find(Member.class,id);
    }
    public List<Member> findByEmail(String email){
         return em.createQuery("select m from Member m where m.email=: email",Member.class)
                .setParameter("email",email)
                .getResultList();
    }
    public List<Member> findAll(){
        return em.createQuery("select m from Member m",Member.class)
                .getResultList();
    }
    public void updateMajority(String majority){
        List<Member> member = findByEmail(MemberService.getUserEmail());
        if(!member.isEmpty()){
            em.createQuery("update Member m set m.majority=: majority where m.id=:memberIdx")
                    .setParameter("majority",majority)
                    .setParameter("memberIdx",member.get(0).getId())
                    .executeUpdate();
        }
        else{
            throw new UserNotFoundException();
        }
    }
}

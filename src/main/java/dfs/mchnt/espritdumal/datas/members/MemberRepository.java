package dfs.mchnt.espritdumal.datas.members;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
  Optional<Member> findByIgn(String ign);

  Optional<Member> findBySnow(String snow);
}

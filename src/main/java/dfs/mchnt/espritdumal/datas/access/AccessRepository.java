package dfs.mchnt.espritdumal.datas.access;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccessRepository extends JpaRepository<Access, AccessKey> {
  Optional<Access> findById_Guild_SnowAndId_Member_Ign(String snow, String ign);
}

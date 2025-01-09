package dfs.mchnt.espritdumal.datas.guild;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GuildRepository extends JpaRepository<Guild, Long> {
  Optional<Guild> findBySnow(String snow);
}

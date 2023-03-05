package platform;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CodeSnippetRepository extends JpaRepository<CodeSnippet, UUID> {
    CodeSnippet findByCode(String code);
    List<CodeSnippet> findTop10ByRestrictedFalseOrderByActualDateDesc();
}

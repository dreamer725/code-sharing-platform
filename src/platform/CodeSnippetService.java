package platform;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CodeSnippetService {
    private final CodeSnippetRepository codeSnippetRepository;


    public CodeSnippetService(CodeSnippetRepository codeSnippetRepository) {
        this.codeSnippetRepository = codeSnippetRepository;
    }

    public Optional<CodeSnippet> findCodeById(UUID id){
        return codeSnippetRepository.findById(id);
    }

    public CodeSnippet findCode(String code){
        return codeSnippetRepository.findByCode(code);
    }

    public List<CodeSnippet> getLatestCodeSnippets(){
        return codeSnippetRepository.findTop10ByRestrictedFalseOrderByActualDateDesc();
    }

    public void updateCode(CodeSnippet code) {
        if (code.isRestrictedByTime()){
            code.setTime((int) (code.getTime() - Duration.between(code.getCheckDate(), LocalDateTime.now()).toSeconds()));
            code.setCheckDate(LocalDateTime.now());
            if(code.getTime() < 1){
                deleteCodeSnippet(code);
                throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "There is no code snippet with such id.");
            }
        }
        if (code.isRestrictedByViews()){
            code.setViews(code.getViews() - 1);
            if(code.getViews() < 0){
                deleteCodeSnippet(code);
                throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "There is no code snippet with such id.");
            }
        }
        saveCodeSnippet(code);
    }

    public void saveCodeSnippet(CodeSnippet code){
        codeSnippetRepository.save(code);
    }

    public void deleteCodeSnippet(CodeSnippet code){
        codeSnippetRepository.delete(code);
    }
}

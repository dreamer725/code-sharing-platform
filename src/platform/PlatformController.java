package platform;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.UUID;


@RestController
public class PlatformController {
    final CodeSnippetService codeSnippetService;

    public PlatformController(CodeSnippetService codeSnippetService) {
        this.codeSnippetService = codeSnippetService;
    }

    @GetMapping("/api/code/{UUID}")
    public CodeSnippet apiGetCode(@PathVariable UUID UUID){
        if (codeSnippetService.findCodeById(UUID).isPresent()) {
            CodeSnippet code = codeSnippetService.findCodeById(UUID).get();
            codeSnippetService.updateCode(code);
            return code;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "There is no code snippet with such id.");
        }
    }

    @GetMapping("/code/{UUID}")
    public ModelAndView webGetCode(@PathVariable UUID UUID, Model model){
        if (codeSnippetService.findCodeById(UUID).isPresent()) {
            CodeSnippet code = codeSnippetService.findCodeById(UUID).get();
            codeSnippetService.updateCode(code);

            model.addAttribute("code", code);
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName("getCode");
            return modelAndView;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "There is no code snippet with such id.");
        }
    }

    @GetMapping("/api/code/latest")
    public List<CodeSnippet> getLatest(){
        return codeSnippetService.getLatestCodeSnippets();
    }

    @GetMapping("/code/latest")
    public ModelAndView webGetLatest(Model model){
        model.addAttribute("code", codeSnippetService.getLatestCodeSnippets());

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("latestCodeSn");
        return modelAndView;
    }

    @PostMapping("/api/code/new")
    public Map<String,String> uploadCode(@RequestBody Map<String, String> newCode){
        codeSnippetService.saveCodeSnippet(new CodeSnippet(newCode.get("code"), Integer.parseInt(newCode.get("time").isBlank() ? "0" : newCode.get("time")), Integer.parseInt(newCode.get("views").isBlank() ? "0" : newCode.get("views"))));
        return Map.of("id", codeSnippetService.findCode(newCode.get("code")).getUUID().toString());
    }

    @GetMapping("/code/new")
    public ResponseEntity<String> createCode(){
        return ResponseEntity.ok().header("Content-Type", "text/html")
                .body("""
                        <html>
                        <head><title>Create</title></head>
                        <body>
                        <form> <!--action="[value]" method="[value]"-->
                        <textarea id="code_snippet" placeholder="Write your code here"></textarea>
                        <input id="time_restriction" type="text"/><input id="views_restriction" type="text"/><button id="send_snippet" type="submit" onclick="send()">Submit</button>
                        </form>
                        <script>
                        function send() {
                        let object = {
                                    "code": document.getElementById("code_snippet").value,
                                    "time": document.getElementById("time_restriction").value,
                                    "views": document.getElementById("views_restriction").value
                                };
                        let json = JSON.stringify(object);
                        let xhr = new XMLHttpRequest();
                        xhr.open("POST", '/api/code/new', false)
                        xhr.setRequestHeader('Content-type', 'application/json; charset=utf-8');
                        xhr.send(json);
                        if (xhr.status === 200) {
                        alert("Success!");
                        }
                        }</script>
                        </body>
                        """);
    }
}

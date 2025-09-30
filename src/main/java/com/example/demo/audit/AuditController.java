package com.example.demo.audit;


import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/audit")
public class AuditController {

    private final AuditService auditService;

    public AuditController(AuditService auditService) {
        this.auditService = auditService;
    }

    @PostMapping(value = "/log")
    public ResponseEntity<AuditResponse> audit(@Valid @RequestBody AuditRequest auditRequest) {
        AuditResponse data = auditService.audit(auditRequest);
        return ResponseEntity.ok(data);
    }

}

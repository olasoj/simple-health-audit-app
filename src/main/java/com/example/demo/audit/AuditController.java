package com.example.demo.audit;


import com.example.demo.model.response.model.ResponseError;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/audit")
@Tag(name = "Audit API", description = "Endpoints for auditing events")
public class AuditController {

    private final AuditService auditService;

    public AuditController(AuditService auditService) {
        this.auditService = auditService;
    }

    @PostMapping(value = "/log")
    @Operation(summary = "Log an audit event", description = "Records an event and returns audit response")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Audit event successfully logged",
                    content = @Content(schema = @Schema(implementation = AuditResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid request payload (validation error)",
                    content = @Content(schema = @Schema(implementation = ResponseError.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Unexpected server error",
                    content = @Content(schema = @Schema(implementation = ResponseError.class))
            )
    })
    public ResponseEntity<AuditResponse> audit(@Valid @RequestBody AuditRequest auditRequest) {
        AuditResponse data = auditService.audit(auditRequest);
        return ResponseEntity.ok(data);
    }

}

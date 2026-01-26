package org.isf.plugins.nxgt.auth.rest;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.isf.plugins.nxgt.auth.port.IPermissionService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "NXGT Permissions")
@RequestMapping(value = "/plugins/nxgt/permissions", produces = MediaType.APPLICATION_JSON_VALUE)
public class PermissionController {
	final IPermissionService service;

	public PermissionController(IPermissionService service) {
		this.service = service;
	}
}

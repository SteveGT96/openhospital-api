package org.isf.plugins.nxgt.auth.rest;

import org.isf.plugins.nxgt.openapi.model.Permission;
import org.isf.plugins.nxgt.openapi.model.PaginatedPermission;
import org.isf.plugins.nxgt.openapi.model.PatchPermissionRequest;
import org.isf.plugins.nxgt.openapi.model.PermissionRequest;
import org.isf.plugins.nxgt.openapi.model.SearchRequest;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.isf.plugins.nxgt.auth.port.IPermissionService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@Tag(name = "Plugins")
@RequestMapping(value = "/plugins")
public class PluginController {
	final IPermissionService service;

	public PluginController(IPermissionService service) {
		this.service = service;
	}

	@GetMapping
	public List<String> findAllPlugins() {
		return Collections.emptyList();
	}

	@PostMapping("/{id}/{endpoint}")
	public Object invokePostEndpoint(
		@PathVariable String id,
		@PathVariable String endpoint,
		@RequestBody(required = false) Object body
	) {
		return service.invokeEndpoint(id, endpoint, "POST", body);
	}

	@GetMapping("/{id}/{endpoint}")
	public Object invokePostEndpoint(
		@PathVariable String id,
		@PathVariable String endpoint
	) {
		return service.invokeEndpoint(id, endpoint, "GET", null);
	}

	@PostMapping("/search")
	public PaginatedPermission findPermissions(
		@RequestBody SearchRequest body,
		@RequestParam(required = false) Integer first,
		@RequestParam(required = false) Integer last,
		@RequestParam(required = false) String before,
		@RequestParam(required = false) String after
	) {
		return service.findAll(body, first, last, before, after);
	}

	@GetMapping
	public List<Permission> findAllPermissions(
		@RequestParam(required = false) Integer first,
		@RequestParam(required = false) Integer last,
		@RequestParam(required = false) String before,
		@RequestParam(required = false) String after
	) {
		return service.findAll(new SearchRequest(), first, last, before, after).getData();
	}

	@GetMapping("/{id}")
	public Permission findPermissionById(
		@PathVariable String id
	) {
		return service.findById(id);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Permission createPermission(
		@RequestBody PermissionRequest body
	) {
		return service.create(body);
	}

	@PutMapping("/{id}")
	public Permission updatePermission(
		@PathVariable String id, @RequestBody PermissionRequest body
	) {
		return service.update(id, body);
	}

	@PatchMapping("/{id}")
	public Permission patchPermission(
		@PathVariable String id, @RequestBody PatchPermissionRequest body
	) {
		return service.patch(id, body);
	}
}

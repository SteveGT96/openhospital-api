package org.isf.plugins.nxgt.auth.rest;

import com.strange.api.modules.users.model.Permission;
import com.strange.api.openapi.models.PaginatedPermission;
import com.strange.api.openapi.models.PatchPermissionRequest;
import com.strange.api.openapi.models.PermissionRequest;
import com.strange.api.openapi.models.SearchRequest;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.isf.plugins.nxgt.auth.port.IPermissionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("PermissionController")
@Tag(name = "NXGT Permissions")
@RequestMapping(value = "/plugins/nxgt/permissions")
public class PermissionController {
	final IPermissionService service;

	public PermissionController(IPermissionService service) {
		this.service = service;
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

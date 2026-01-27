package org.isf.plugins.nxgt.auth.port

import com.strange.api.modules.users.model.Permission
import com.strange.api.openapi.models.PaginatedPermission
import com.strange.api.openapi.models.PatchPermissionRequest
import com.strange.api.openapi.models.PermissionRequest
import com.strange.api.openapi.models.SearchRequest
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.service.annotation.*

@HttpExchange("/permissions", contentType = "application/json", accept = ["application/json"])
interface IPermissionService {
    @PostExchange("/search")
    fun findAll(
        @RequestBody payload: SearchRequest,
        @RequestParam first: Int? = null,
        @RequestParam last: Int? = null,
        @RequestParam before: String? = null,
        @RequestParam after: String? = null,
    ): PaginatedPermission

    @GetExchange("/{id}")
    fun findById(@PathVariable id: String): Permission

    fun findByIdOrThrow(id: String): Permission {
        TODO()
    }

    @PostExchange
    fun create(@RequestBody payload: PermissionRequest): Permission

    @PutExchange("/{id}")
    fun update(@PathVariable id: String, @RequestBody payload: PermissionRequest): Permission

    @PatchExchange("/{id}")
    fun patch(@PathVariable id: String, @RequestBody payload: PatchPermissionRequest): Permission

    @DeleteExchange("/{id}")
    fun deleteById(@PathVariable id: String)
}
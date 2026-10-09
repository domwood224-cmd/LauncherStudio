package com.napcity.launcherstudio.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.util.UUID

private val Context.dataStore by preferencesDataStore(name = "launcher_studio")

/**
 * Persists launcher projects + the active project id.
 * Projects serialize to versioned JSON — the same format used for export/import.
 */
class ProjectRepository(private val context: Context) {

    private val json = Json { ignoreUnknownKeys = true; prettyPrint = true }

    private val PROJECTS_KEY = stringPreferencesKey("projects_json")
    private val ACTIVE_ID_KEY = stringPreferencesKey("active_project_id")

    /** All saved projects, most-recently-updated first */
    val projects: Flow<List<LauncherProject>> =
        context.dataStore.data.map { prefs ->
            val raw = prefs[PROJECTS_KEY] ?: return@map defaultProjects()
            try {
                json.decodeFromString<List<LauncherProject>>(raw)
                    .sortedByDescending { it.updatedAt }
            } catch (_: Exception) {
                defaultProjects()
            }
        }

    /** The currently-active project driving the launcher runtime */
    val activeProject: Flow<LauncherProject> =
        context.dataStore.data.map { prefs ->
            val id = prefs[ACTIVE_ID_KEY]
            val all = try {
                val raw = prefs[PROJECTS_KEY] ?: return@map defaultProject()
                json.decodeFromString<List<LauncherProject>>(raw)
            } catch (_: Exception) {
                defaultProjects()
            }
            all.find { it.id == id } ?: all.firstOrNull() ?: defaultProject()
        }

    suspend fun saveProject(project: LauncherProject) {
        val updated = project.copy(updatedAt = System.currentTimeMillis())
        context.dataStore.edit { prefs ->
            val current = readAll(prefs[PROJECTS_KEY])
            val next = (listOf(updated) + current.filter { it.id != updated.id })
            prefs[PROJECTS_KEY] = json.encodeToString(next)
        }
    }

    suspend fun deleteProject(id: String) {
        context.dataStore.edit { prefs ->
            val current = readAll(prefs[PROJECTS_KEY]).filter { it.id != id }
            val safe = if (current.isEmpty()) defaultProjects() else current
            prefs[PROJECTS_KEY] = json.encodeToString(safe)
            if (prefs[ACTIVE_ID_KEY] == id) {
                prefs[ACTIVE_ID_KEY] = safe.first().id
            }
        }
    }

    suspend fun setActiveProject(id: String) {
        context.dataStore.edit { prefs -> prefs[ACTIVE_ID_KEY] = id }
    }

    suspend fun createProject(name: String, base: LauncherProject? = null): LauncherProject {
        val project = (base ?: defaultProject()).copy(
            id = UUID.randomUUID().toString(),
            name = name,
            createdAt = System.currentTimeMillis(),
            updatedAt = System.currentTimeMillis()
        )
        saveProject(project)
        return project
    }

    /** Serialize a project to shareable JSON */
    fun exportToJson(project: LauncherProject): String = json.encodeToString(project)

    /** Parse shared JSON back into a project (assigns a fresh id) */
    fun importFromJson(raw: String): LauncherProject? = try {
        val parsed = json.decodeFromString<LauncherProject>(raw)
        parsed.copy(
            id = UUID.randomUUID().toString(),
            createdAt = System.currentTimeMillis(),
            updatedAt = System.currentTimeMillis()
        )
    } catch (_: Exception) {
        null
    }

    private fun readAll(raw: String?): List<LauncherProject> {
        if (raw.isNullOrBlank()) return defaultProjects()
        return try {
            json.decodeFromString<List<LauncherProject>>(raw)
        } catch (_: Exception) {
            defaultProjects()
        }
    }

    private fun defaultProject(): LauncherProject = LauncherProject(
        id = "default",
        name = "Plasma Storm"
    )

    private fun defaultProjects(): List<LauncherProject> = listOf(defaultProject())
}

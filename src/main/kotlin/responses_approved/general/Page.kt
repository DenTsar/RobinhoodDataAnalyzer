package responses_approved.general

import kotlinx.serialization.Serializable

@Serializable
data class Page<T>(
    val next: String? = null,
    val previous: String? = null,
    val results: List<T>
)
